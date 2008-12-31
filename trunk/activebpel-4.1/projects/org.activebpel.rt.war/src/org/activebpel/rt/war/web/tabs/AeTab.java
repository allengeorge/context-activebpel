//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.war/src/org/activebpel/rt/war/web/tabs/AeTab.java,v 1.1 2007/04/24 17:23:1
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
package org.activebpel.rt.war.web.tabs; 

/**
 * Simple data structure for a tab on a JSP. 
 */
public class AeTab
{
   /** Name of the JSP that should be loaded when the tab is active */
   private String mPage;
   /** name of the tab */
   private String mName;
   /** reference back to the bean to see if we're the selected tab */
   private AeTabBean mBean;
   /** this tabs offset w/in the tab set */
   private int mOffset;
   
   
   /**
    * @return Returns the jSPName.
    */
   public String getPage()
   {
      return mPage;
   }

   /**
    * @param aName The jSPName to set.
    */
   public void setPage(String aName)
   {
      mPage = aName;
   }
   
   /**
    * @return Returns the name.
    */
   public String getName()
   {
      return mName;
   }
   
   /**
    * @param aName The name to set.
    */
   public void setName(String aName)
   {
      mName = aName;
   }
   
   /**
    * @return Returns the selected.
    */
   public boolean isSelected()
   {
      return mBean.isSelected(this);
   }
   
   /**
    * Setter for the bean reference.
    * 
    * @param aBean
    */
   protected void setBean(AeTabBean aBean)
   {
      mBean = aBean;
   }
   
   /**
    * Getter for the offset
    */
   public int getOffset()
   {
      return mOffset;
   }
   
   /**
    * Setter for the offset
    * 
    * @param aOffset
    */
   protected void setOffset(int aOffset)
   {
      mOffset = aOffset;
   }
   
}
 
