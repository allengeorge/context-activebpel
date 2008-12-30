//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/processview/AeBpelObjectContainer.java,v 1.5 2006/07/25 17:56:3
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
package org.activebpel.rt.bpeladmin.war.web.processview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.activebpel.rt.bpel.def.AeBaseDef;

/**
 * Composite pattern container representing a BPEL definition element which may have children.
 * For example, the Process, Sequence, While contain other activities. 
 */
public class AeBpelObjectContainer extends AeBpelObjectBase
{
   /** BPEL child model objects contained by this parent. */
   private List mChildren = new ArrayList();

   /**
    * 
    * @param aTagName definition tag name
    * @param aName definition
    */
   public AeBpelObjectContainer(String aTagName, AeBaseDef aDef)
   {
      super(aTagName, aDef);
   }
   
   /**
    * Constructs a container with given tag, icon and underlying def.
    * @param aTagName
    * @param aIconName
    * @param aDef
    */
   public AeBpelObjectContainer(String aTagName, String aIconName, AeBaseDef aDef)
   {
      super(aTagName, aIconName, aDef);
   }   
   
   /**
    * Adds the given object as a child of this parent.
    * @param aChild BPEL child object.
    */
   public void addChild(AeBpelObjectBase aChild)
   {
      if (!mChildren.contains(aChild))
      {
         mChildren.add(aChild);
         aChild.setParent(this);
      }
   }
   
   /**
    * Removes the child from the container.
    * @param aChild
    */
   public void removeChild(AeBpelObjectBase aChild)
   {
      if (mChildren.contains(aChild))
      {
         mChildren.remove(aChild);
         aChild.setParent(null);
      }      
   }
   
   /** 
    * @return List of children in this container.
    */
   public List getChildren()
   {
      return mChildren;
   }
   
   /**
    * Returns the number of children in the container.
    */
   public int size()
   {
      return getChildren().size();
   }
   
   /** 
    * @return List of children in this container for the given type.
    */
   public List getChildren(String aBpelTagName)
   {
      List children =  getChildren();
      List rList = new ArrayList();
      Iterator iterator = children.iterator();
      while (iterator.hasNext())
      {
         AeBpelObjectBase child = (AeBpelObjectBase)iterator.next();
         if (child.getTagName().equalsIgnoreCase(aBpelTagName))
         {
            rList.add(child);
         }
      }
      return rList;
   }      
}
