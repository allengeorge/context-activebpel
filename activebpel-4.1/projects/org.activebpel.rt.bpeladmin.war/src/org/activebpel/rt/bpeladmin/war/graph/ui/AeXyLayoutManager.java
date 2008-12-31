//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/graph/ui/AeXyLayoutManager.java,v 1.1 2005/04/18 18:31:4
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
package org.activebpel.rt.bpeladmin.war.graph.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;

/**
 * Implements a arbitrary coordinate based layout mechanism..
 */
public class AeXyLayoutManager extends AeLayoutManagerAdopter
{

   /**
    * Default constructor. 
    */
   public AeXyLayoutManager()
   {
      super();
   }
      
   /**
    * Overrides method to calculate the minumum size. 
    * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
    */
   public Dimension minimumLayoutSize(Container aParent)
   {
      int n = aParent.getComponentCount();
      Rectangle rect = new Rectangle();
      Rectangle rv = new Rectangle();
      for (int i = 0; i < n; i++)
      {  
         Component c = aParent.getComponent(i);
         c.getBounds(rv);
         rect = rect.union(rv);
      } 
      return aParent.getSize();
   }   
   
   /**
    * Overrides method to layout the components based on their absolute locations.
    * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
    */
   public void layoutContainer(Container aParent)
   {
      int n = aParent.getComponentCount();
      int x = 5;
      for (int i = 0; i < n; i++)
      {
         Component c = aParent.getComponent(i);
         Dimension dim = c.getPreferredSize();         
         c.setSize(dim);       
         x = x + 5 + dim.width;
      }             
   }   
}
