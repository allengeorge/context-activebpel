//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeDefFindVisitor.java,v 1.1 2006/02/07 23:48:1
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
 * Visitor to find a definition given a specific path.
 */
public class AeDefFindVisitor extends AeAbstractDefVisitor
{
   /** the path to find. */
   private String mPath;
   
   /** The definition object which is not null if it has been found. */
   private AeBaseDef mFoundDef = null;

   /** Viositr to find the definition for the passe path. */
   public AeDefFindVisitor(String aPath)
   {
      setPath(aPath);
      setTraversalVisitor( new AeTraversalVisitor( new AeDefTraverser(), this ) );
   }
   
   /**
    * Overrides method to see if the object is the path we are looking for and to stop traversal if
    * we have a mtach.  
    * @see org.activebpel.rt.bpel.def.visitors.AeAbstractDefVisitor#traverse(org.activebpel.rt.bpel.def.AeBaseDef)
    */
   protected void traverse(AeBaseDef aDef)
   {
      if(getFoundDef() == null)
      {
         if(getPath().equals(aDef.getLocationPath()))
         {
            setFoundDef(aDef);
         }
         else
         {
            super.traverse(aDef);
         }
      }
   }

   /**
    * @return Returns the foundDef.
    */
   public AeBaseDef getFoundDef()
   {
      return mFoundDef;
   }

   /**
    * @param aFoundDef The foundDef to set.
    */
   public void setFoundDef(AeBaseDef aFoundDef)
   {
      mFoundDef = aFoundDef;
   }

   /**
    * @return Returns the path.
    */
   public String getPath()
   {
      return mPath;
   }

   /**
    * @param aPath The path to set.
    */
   public void setPath(String aPath)
   {
      mPath = aPath;
   }

}
