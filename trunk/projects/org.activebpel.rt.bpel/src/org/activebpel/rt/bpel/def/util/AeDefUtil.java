// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/util/AeDefUtil.java,v 1.21 2007/09/12 02:48:1
/*
 * Copyright (c) 2004-2006 Active Endpoints, Inc.
 *
 * This program is licensed under the terms of the GNU General Public License
 * Version 2 (the "License") as published by the Free Software Foundation, and 
 * the ActiveBPEL Licensing Policies (the "Policies").  A copy of the License 
 * and the Policies were distributed with this program.  
 *
 * The License is available at:
 * http: *www.gnu.org/copyleft/gpl.html
 *
 * The Policies are available at:
 * http: *www.activebpel.org/licensing/index.html
 *
 * Unless required by applicable law or agreed to in writing, this program is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied.  See the License and the Policies
 * for specific language governing the use of this program.
 */
package org.activebpel.rt.bpel.def.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.AeActivityDef;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeCorrelationSetDef;
import org.activebpel.rt.bpel.def.AeCorrelationSetsDef;
import org.activebpel.rt.bpel.def.AePartnerLinkDef;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.IAeCorrelationSetsParentDef;
import org.activebpel.rt.bpel.def.IAeExpressionDef;
import org.activebpel.rt.bpel.def.IAePartnerLinksParentDef;
import org.activebpel.rt.bpel.def.IAeVariableParentDef;
import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;
import org.activebpel.rt.util.AeUtil;

/**
 * Contains some helper methods for the def visitors and validation
 */
public class AeDefUtil
{
   /**
    * Finds the appropriate partner link def with the given partner link name.  This method will
    * search up the process def tree until it finds the partner link or runs out of parents.  This
    * method returns null if no partner link def is found.
    * 
    * @param aDef
    * @param aPartnerLinkName
    */
   public static AePartnerLinkDef findPartnerLinkDef(AeBaseDef aDef, String aPartnerLinkName)
   {
      for (AeBaseDef currentDef = aDef; currentDef != null; currentDef = currentDef.getParent())
      {
         if (currentDef instanceof IAePartnerLinksParentDef)
         {
            AePartnerLinkDef plinkDef = ((IAePartnerLinksParentDef) currentDef).getPartnerLinkDef(aPartnerLinkName);
            if (plinkDef != null)
               return plinkDef;
         }
      }

      return null;
   }
   
   /**
    * Locate a correlation set among the process' definitions, by name.
    *
    * @param aCorrSet The name to search.
    * @param aDef The def object from which to search (up).
    *
    * @return AeCorrelationSetDef if found, otherwise null.
    */
   public static AeCorrelationSetDef findCorrSetByName( String aName, AeBaseDef aDef )
   {
      for (AeBaseDef currentDef = aDef; currentDef != null; currentDef = currentDef.getParent())
      {
         if (currentDef instanceof IAeCorrelationSetsParentDef)
         {
            AeCorrelationSetsDef correlationsSetDef = ((IAeCorrelationSetsParentDef) currentDef).getCorrelationSetsDef();
            if (correlationsSetDef != null)
            {
               AeCorrelationSetDef csDef = correlationsSetDef.getCorrelationSetDef(aName);
               if (csDef != null)
                  return csDef;
            }
         }
      }
      return null ;
   }

   /**
    * Given an arbitrary base def, returns the associated activity def.  This method may simply
    * return the def passed to it, in the case that that def WAS itself an activity.  If the
    * passed in def is not an activity, then this method will walk up the tree until it finds an
    * acivity.
    * 
    * @param aDef
    */
   public static AeActivityDef getActivityDef( AeBaseDef aDef )
   {
      AeBaseDef def = aDef;
      while (def != null && !(def instanceof AeActivityDef))
      {
         def = def.getParent();
      }
      return (AeActivityDef) def;
   }

   /**
    * Retrieve a variable from the process scope heirarchy, by name. The search begins
    * with the def passed in
    *
    * @param aVariable The name of the variable to search.
    * @param aDef The def object from which to search (up).
    *
    * @return AeVariableDef or null if not found.
    */
   public static AeVariableDef getVariableByName( String aVariable, AeBaseDef aDef )
   {
      // TODO (MF) make the methods here consistent, either String,Def or Def,String
      if (AeUtil.notNullOrEmpty(aVariable) && aDef != null )
      {
         // Search all enclosing scopes.
         //
         for (AeBaseDef currentDef = aDef; currentDef != null; currentDef = currentDef.getParent())
         {
            if (currentDef instanceof IAeVariableParentDef)
            {
               AeVariableDef var = ((IAeVariableParentDef)currentDef).getVariableDef(aVariable);
               if (var != null)
               {
                  return var;
               }
            }
         }
      }

      return null ;
   }

   /**
    * Given a QName, returns a prefix:localpart formatted String representation of that QName.
    *
    * @param aDef
    * @param aQName
    */
   public static String formatQName(AeBaseDef aDef, QName aQName)
   {
      // Quick null check to avoid a NPE when displaying a variable (the 'property' of the variable may be null)
      if (aQName == null)
         return ""; //$NON-NLS-1$
      // If there is no NS, just return the local part.
      if (aQName.getNamespaceURI() == null)
         return aQName.getLocalPart();

      // Get the set of prefixes mapped to this namespace (stop once a prefix is found).
      Set prefixSet = new HashSet();
      for (AeBaseDef def = aDef; def != null && prefixSet.isEmpty(); def = def.getParent())
      {
         prefixSet.addAll(def.getPrefixesForNamespace(aQName.getNamespaceURI()));
      }
      String prefix = "ns"; //$NON-NLS-1$
      if (!prefixSet.isEmpty())
      {
         prefix = (String) prefixSet.iterator().next();
      }
      return prefix + ":" + aQName.getLocalPart(); //$NON-NLS-1$
   }

   /**
    * Given a string which represents a qualified name and a context, this method will parse
    * it and return a QName object. Note this method returns null if the string
    * is not properly formed.
    * @param aDef the def from which to resolve the prefix to a namespace
    * @param aQstr the string to be parsed
    */
   public static QName parseQName(AeBaseDef aDef, String aQstr)
   {
      QName qname;

      int colon = aQstr.indexOf(":"); //$NON-NLS-1$
      if (colon < 0 )
         qname = new QName(null, aQstr);
      else
      {
         String nsURI = translateNamespacePrefixToUri(aDef, aQstr.substring(0, colon));
         qname = new QName(nsURI, aQstr.substring(colon+1));
      }

      return qname;
   }

   /**
    * Returns the namespace associated with the prefix from the associated model.
    */
   private static String translateNamespacePrefixToUriInternal(AeBaseDef aDef, String aPrefix)
   {
      // check def for the prefix we are looking for
      String nsURI = aDef.findNamespace(aPrefix);

      // if we haven't found the prefix ask our parent if it can find it
      if(nsURI == null)
      {
         AeBaseDef parent = aDef.getParent();
         if(parent != null)
            nsURI = translateNamespacePrefixToUriInternal(parent, aPrefix);
      }
      return nsURI;
   }

   /**
    * Returns the namespace associated with the prefix from the associated model.
    * TODO we should cache prefixes in process for non-conflicting prefixes and only do brute force search for conflicts.
    */
   public static String translateNamespacePrefixToUri(AeBaseDef aDef, String aPrefix)
   {
      String nsURI = null;

      // if we have a definiton test if it has the prefix we are looking for
      // TODO (MF) get rid of this xmlns: mojo and also the recursion.
      if(aDef != null)
      {
         nsURI = translateNamespacePrefixToUriInternal(aDef, aPrefix);
      }
      return nsURI;
   }

   /**
    * Returns a set of prefixes mapped to the given namespace.
    *
    * @param aDef
    * @param aNamespace
    */
   private static Set getPrefixesForNamespaceInternal(AeBaseDef aDef, String aNamespace)
   {
      HashSet set = new HashSet();

      // First add the parent prefixes if there is a parent.
      if (aDef.getParent() != null)
      {
         set.addAll(getPrefixesForNamespaceInternal(aDef.getParent(), aNamespace));
      }
      set.addAll(aDef.getPrefixesForNamespace(aNamespace));

      return set;
   }

   /**
    * Returns a set of prefixes that are mapped to the given namespace.
    *
    * @param aDef
    * @param aNamespace
    */
   public static Set getPrefixesForNamespace(AeBaseDef aDef, String aNamespace)
   {
      if (aDef != null)
      {
         return getPrefixesForNamespaceInternal(aDef, aNamespace);
      }
      else
      {
         return Collections.EMPTY_SET;
      }

   }

   /**
    * Gets the root process def parent of a given def object.
    *
    * @param aDef
    */
   public static AeProcessDef getProcessDef(AeBaseDef aDef)
   {
      if (aDef instanceof AeProcessDef)
      {
         return (AeProcessDef) aDef;
      }

      AeBaseDef def = null;
      for (def = aDef; def != null && !(def instanceof AeProcessDef); def = def.getParent() );
      return (AeProcessDef) def;
   }
   
   /**
    * Returns true if this is a BPWS def
    * @param aDef
    */
   public static boolean isBPWS(AeBaseDef aDef)
   {
      AeProcessDef def = getProcessDef(aDef);
      return def.getNamespace().equals(IAeBPELConstants.BPWS_NAMESPACE_URI);
   }
   
   /**
    * Gets the expression language to use when validating the given expression def.  This method
    * assumes that the given expression def is also an AeBaseDef, so that it can find the 
    * AeProcessDef.
    * 
    * @param aExpressionDef
    */
   public static String getExpressionLanguage(IAeExpressionDef aExpressionDef)
   {
      return getExpressionLanguage(aExpressionDef, AeDefUtil.getProcessDef((AeBaseDef) aExpressionDef));
   }

   /**
    * Gets the expression language to use when validating the given expression def.
    * 
    * @param aExpressionDef
    * @param aProcessDef
    */
   public static String getExpressionLanguage(IAeExpressionDef aExpressionDef, AeProcessDef aProcessDef)
   {
      String expressionLanguage = aExpressionDef.getExpressionLanguage();
      if (AeUtil.isNullOrEmpty(expressionLanguage))
      {
         expressionLanguage = aProcessDef.getExpressionLanguage();
      }
      return expressionLanguage;
   }
   
   /**
    * Return true if the exitOnStandardFault is set to yes, false if set to no or have no definition in process/scope, false if .
    * @param aDef
    */
   public static boolean isExitOnStandardFaultEnabled(AeBaseDef aDef)
   {	
      AeBaseDef parent = aDef.getParent();
      Boolean value = null;      
      while (parent != null)
      {
         if (parent instanceof AeActivityScopeDef)
         {
            AeActivityScopeDef scopeDef = (AeActivityScopeDef)parent;
            value = scopeDef.getExitOnStandardFault();
         }
         else if (parent instanceof AeProcessDef)
         {
            AeProcessDef processDef = (AeProcessDef)parent;
            value = processDef.getExitOnStandardFault();
         }
         if ( value != null )
         {
            return value.booleanValue();
         }
         parent = parent.getParent(); 
      }
      return false;      
   }

   /**
    * Utility method for replacing an activity within the list.
    * @param aListOfChildren
    * @param aOldActivityDef
    * @param aNewActivityDef
    */
   public static void replaceActivityDef(List aListOfChildren, AeActivityDef aOldActivityDef, AeActivityDef aNewActivityDef)
   {
      boolean replaced = false;
      for (ListIterator liter = aListOfChildren.listIterator(); liter.hasNext() && !replaced; )
      {
         AeActivityDef activityDef = (AeActivityDef) liter.next();
         if (activityDef == aOldActivityDef)
         {
            liter.set(aNewActivityDef);
            replaced = true;
         }
      }
      
      if (!replaced)
         throw new IllegalArgumentException(AeMessages.getString("AeDefUtil.ActivityNotFound")); //$NON-NLS-1$
   }
}
