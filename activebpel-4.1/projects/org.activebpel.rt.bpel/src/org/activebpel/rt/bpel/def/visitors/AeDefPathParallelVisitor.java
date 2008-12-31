//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeDefPathParallelVisitor.java,v 1.6 2006/10/26 13:35:1
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
package org.activebpel.rt.bpel.def.visitors;

import org.activebpel.rt.bpel.def.AeBaseDef;

/**
 * Extension of the location path visitor that creates a custom path for the
 * root scope of the forEach. All of the other nodes visited will get their
 * standard path which will include the scope's special instance predicate which
 * serves to make the paths unique. 
 */
public class AeDefPathParallelVisitor extends AeDefPathVisitor
{
   /** our root node that gets the special instance path */
   private AeBaseDef mInstanceDef;
   /** value used to construct the path information */
   private int mInstanceValue;
   
   /**
    * Ctor
    * @param aSegmentVisitor - visitor to produce the segment paths
    * @param aBasePath the base path that provides the context of our instance 
    * @param aDef the root def for our instance path
    */
   public AeDefPathParallelVisitor(IAeDefPathSegmentVisitor aSegmentVisitor, String aBasePath, AeBaseDef aDef)
   {
      super(aSegmentVisitor);
      mInstanceDef = aDef;
      setPath(aBasePath);
   }
   
   /**
    * Returns the root instance def.
    */
   protected AeBaseDef getInstanceDef()
   {
      return mInstanceDef;
   }

   /**
    * @see org.activebpel.rt.bpel.def.visitors.AeDefPathVisitor#createUniquePath(org.activebpel.rt.bpel.def.AeBaseDef, java.lang.String)
    */
   protected String createUniquePath(AeBaseDef aDef, String aAppendPath)
   {
      if (aDef == getInstanceDef())
      {
         StringBuffer buffer = new StringBuffer(aAppendPath);
         appendInstancePredicate(buffer, getInstanceValue()); 
         return super.createUniquePath(aDef, buffer.toString());
      }
      else
      {
         return super.createUniquePath(aDef, aAppendPath);
      }
   }
   
   /**
    * Setter for the counter
    * 
    * @param counter
    */
   public void setInstanceValue(int counter)
   {
      mInstanceValue = counter;
   }

   /**
    * Getter for the counter
    */
   public int getInstanceValue()
   {
      return mInstanceValue;
   }
   
   /**
    * Appends the scope instance counter as predicate.
    * @param aBuffer location path buffer
    * @param aInstanceCount current instance counter value.
    */
   public static void appendInstancePredicate(StringBuffer aBuffer, int aInstanceCount)
   {
      //
      // Note: The Admin console (for the process viewer)'s javascript  does some
      // pattern matching to determine if an activity selected in the outline (tree) view
      // is of a forEach parallel instance. The java script uses the pattern used below
      // e.g: [instance()=N]
      // If the pattern of the predicate is changed, then it must also be reflected in the
      // admin console's ae_graphview.js java script file.
      //
      
      aBuffer.append("[instance()="); //$NON-NLS-1$
      aBuffer.append(String.valueOf(aInstanceCount));
      aBuffer.append(']');      
   }
}
