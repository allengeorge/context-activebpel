// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/AeBaseDef.java,v 1.36 2007/04/03 20:40:3
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
package org.activebpel.rt.bpel.def;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activebpel.rt.bpel.def.io.ext.IAeExtensionElementDef;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;
import org.activebpel.rt.util.AeUtil;

/**
 * Base definition object for bpel definitions
 */
public abstract class AeBaseDef implements IAeBPELConstants
{
   /** A map of all referenced namespaces referenced at the current node level */
   protected Map mNamespaceMap;
   /** Default namespace or null if not set */
   private String mDefaultNamespace;
   /** Path that uniquely identifies this element in the BPEL Process hierarchy. */
   private String mLocationPath ;
   /** Unique location identifier for this element in the BPEL Process hierarchy. */
   private int mLocationId ;
   /** Optional bpel entity comment */
   private String  mComment;
   /** List of documentation children of any bpel construct. */
   private List mDocumentationDefs;
   /** Parent def, if any. */
   private AeBaseDef mParent;
   /** A list of extension elements */
   private List mExtensionElementDefs;
   /** The extension attributes for the element. */
   private List mExtensionAttributeDefs;

   /**
    * Default constructor
    */
   public AeBaseDef()
   {
      super();
   }

   /**
    * Set this instance's parent def.
    *
    * @param aParent The parent to set.
    */
   public void setParent( AeBaseDef aParent )
   {
      mParent = aParent ;
   }

   /**
    * Get this instance's parent def.<p>
    *
    * N.B.: the AeDefAssignParentVisitor must be used before assuming that
    * parent-child def relationships are valid.  These relationships are NOT
    * updated automatically (e.g., during modification by the process design user,
    * etc.).
    *
    * @return AeBaseDef if parent exists, null otherwise.
    */
   public AeBaseDef getParent()
   {
      return mParent ;
   }

   /**
    * Returns the default namespace for this def, or null if one is not 
    * declared.
    */
   public String getDefaultNamespace()
   {
      return mDefaultNamespace;
   }
   
   /**
    * Sets the default namespace declaration for this def.
    * 
    * @param aNamespace
    */
   public void setDefaultNamespace(String aNamespace)
   {
      mDefaultNamespace = aNamespace;
   }
   
   /**
    * Removes the default namespace declaration, if any.
    */
   public void removeDefaultNamespace()
   {
      mDefaultNamespace = null;
   }

   /** 
    * @return Returns the namespace map.
    */
   protected Map getNamespaceMap()
   {
      return getNamespaceMap(false);
   }
   
   /**
    * Returns the namespacemap. If a map has not been created and 
    * <code>aCreate=true</code> then a map is created, otherwise returns 
    * <code>Collections.EMPTY_MAP</code>.
    * @param aCreate
    */
   protected Map getNamespaceMap(boolean aCreate)
   {
      if (mNamespaceMap != null)
      {
         return mNamespaceMap;
      }
      else if (mNamespaceMap == null && aCreate)
      {
         mNamespaceMap = new HashMap();
         return mNamespaceMap;         
      }
      else
      {
         return Collections.EMPTY_MAP;
      }
   }

   /**
    * Provides the ability to add a namespace to the list.
    * 
    * @param aPrefix  name of the namespace to be added (e.g. bpws) If null or
    *                 empty then this sets the default namespace for the def. 
    * @param aNamespace value of the namespace to be added
    */
   public void addNamespace(String aPrefix, String aNamespace)
   {
      if ("xmlns".equals(aPrefix) || AeUtil.isNullOrEmpty(aPrefix)) //$NON-NLS-1$
      {
         setDefaultNamespace(aNamespace);
      }
      else
      {
         getNamespaceMap(true).put(aPrefix, aNamespace);
      }
   }

   /**
    * Provides the ability to find a namespace given a prefix.
    *
    * @param aPrefix the prefix to search for.
    * @return the value of the namespace if found or null if not found
    */
   public String findNamespace(String aPrefix)
   {
      return (String) getNamespaceMap().get(aPrefix);
   }

   /**
    * Returns a list of the namespace prefixes. This will not include a mapping
    * for the default namespace.
    */
   public Iterator getNamespacePrefixList()
   {
      if (getNamespaceMap().isEmpty())
      {
         return Collections.EMPTY_LIST.iterator();
      }
      Set prefixes = new HashSet(getNamespaceMap().keySet());
      // remove the default ns 
      prefixes.remove(""); //$NON-NLS-1$
      return prefixes.iterator();
   }

   /**
    * Gets a set of prefixes mapped to the given namespace.
    *
    * @param aNamespace
    */
   public Set getPrefixesForNamespace(String aNamespace)
   {
      if ( !AeUtil.isNullOrEmpty(aNamespace) )
      {
         HashSet set = new HashSet();
         AeBaseDef def = this;
         while (def != null)
         {
            findPrefixesForNamespace(def, aNamespace, set);
            def = def.getParent();
         }
         return set;
      }
      else
      {
         return Collections.EMPTY_SET;
      }
   }
   
   /**
    * Finds the prefixes for the given namespace URI in the given def.
    * @param aDef
    * @param aNamespace
    * @param aResultSet
    */
   protected void findPrefixesForNamespace(AeBaseDef aDef, String aNamespace, Set aResultSet)
   {
      for (Iterator iter = aDef.getNamespaceMap().entrySet().iterator(); iter.hasNext(); )
      {
         Map.Entry entry = (Map.Entry) iter.next();
         String prefix = (String) entry.getKey();
         String ns = (String) entry.getValue();
         if (aNamespace.equals(ns) && AeUtil.notNullOrEmpty(prefix))
         {
            aResultSet.add(prefix);
         }
      }      
   }

   abstract public void accept(IAeDefVisitor aVisitor);

   /**
    * Get the unique location path for this element.
    *
    * @return String
    */
   public String getLocationPath()
   {
      return mLocationPath;
   }

   /**
    * Set the unique location path for this element.
    *
    * @param aString
    */
   public void setLocationPath(String aString)
   {
      mLocationPath = aString;
   }

   /**
    * Get the unique location id for this element.
    *
    * @return int
    */
   public int getLocationId()
   {
      return mLocationId;
   }

   /**
    * Set the unique location id for this element.
    *
    * @param aLocationId
    */
   public void setLocationId(int aLocationId)
   {
      mLocationId = aLocationId;
   }

   /**
    * Gets the BPEL comment.
    */
   public String getComment()
   {
      return mComment;
   }

   /**
    * Sets the BPEL comment.
    * @param aString
    */
   public void setComment(String aString)
   {
      mComment = aString;
   }

   /**
    * Mutator for adding an extension element.
    * @param aExtension the IAeExtensionElement impl to be added
    */
   public void addExtensionElementDef( IAeExtensionElementDef aExtension )
   {
      if( mExtensionElementDefs == null )
         mExtensionElementDefs = new ArrayList();
      mExtensionElementDefs.add( aExtension );
   }

   /**
    * Returns an iterator for any extension elements
    * (or any empty iterator if none are present).
    * @return iterator over any extension element
    */
   public Iterator getExtensionElementDefs()
   {
      if( mExtensionElementDefs == null )
         return Collections.EMPTY_LIST.iterator();
      return mExtensionElementDefs.iterator();
   }

   /**
    * @return Returns the documentation defs.
    */
   public Iterator getDocumentationDefs()
   {
      if (mDocumentationDefs == null)
         return Collections.EMPTY_LIST.iterator();
      return mDocumentationDefs.iterator();
   }

   /**
    * @param aDocumentationDef The documentation def to add.
    */
   public void addDocumentationDef(AeDocumentationDef aDocumentationDef)
   {
      if (mDocumentationDefs == null)
         mDocumentationDefs = new ArrayList();
      mDocumentationDefs.add(aDocumentationDef);
   }
   
   /**
    * Adds an extension attribute to the def.
    * 
    * @param aNamespace
    * @param aQualifiedName
    * @param aValue
    */
   public void addExtensionAttributeDef(String aNamespace, String aQualifiedName, String aValue)
   {
      if (mExtensionAttributeDefs == null)
         mExtensionAttributeDefs = new LinkedList();
      mExtensionAttributeDefs.add(new AeExtensionAttributeDef(aNamespace, aQualifiedName, aValue));
   }

   /**
    * @return Returns the extensionAttributes.
    */
   public Iterator getExtensionAttributeDefs()
   {
      if( mExtensionAttributeDefs == null )
         return Collections.EMPTY_LIST.iterator();
      return mExtensionAttributeDefs.iterator();
   }
}
