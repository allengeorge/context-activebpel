//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/graph/ui/AeLayoutManagerAdopter.java,v 1.1 2005/04/18 18:31:4
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
import java.awt.LayoutManager2;

/**
 * Adaptor for the AWT LayoutManager2 interface.
 */
public class AeLayoutManagerAdopter implements LayoutManager2
{

   /**
    * Default construtor
    */
   public AeLayoutManagerAdopter()
   {
      super();
   }

   /**
    * Empty method.
    * @see java.awt.LayoutManager2#addLayoutComponent(java.awt.Component, java.lang.Object)
    */
   public void addLayoutComponent(Component aComp, Object aConstraints)
   {
   }

   /**
    * Overrides method to delegate to minimumLayoutSize. 
    * @see java.awt.LayoutManager2#maximumLayoutSize(java.awt.Container)
    */
   public Dimension maximumLayoutSize(Container aTarget)
   {
      return minimumLayoutSize(aTarget);
   }

   /**
    * Overrides method to return 0.5f (centered)
    * @see java.awt.LayoutManager2#getLayoutAlignmentX(java.awt.Container)
    */
   public float getLayoutAlignmentX(Container aTarget)
   {
      return 0.5f;
   }

   /**
    * Overrides method to return 0.5f (centered)
    * @see java.awt.LayoutManager2#getLayoutAlignmentY(java.awt.Container)
    */
   public float getLayoutAlignmentY(Container aTarget)
   {
      return 0.5f;
   }

   /**
    * Empty method. 
    * @see java.awt.LayoutManager2#invalidateLayout(java.awt.Container)
    */
   public void invalidateLayout(Container aTarget)
   {
   }

   /**
    * Empty method. 
    * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
    */
   public void addLayoutComponent(String aName, Component aComp)
   {
   }

   /**
    * Empty method. 
    * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
    */
   public void removeLayoutComponent(Component aComp)
   {
   }

   /**
    * Overrides method to delegate to minimumLayoutSize. 
    * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
    */
   public Dimension preferredLayoutSize(Container aParent)
   {
      return minimumLayoutSize(aParent);
   }

   /**
    * Overrides method to return size of (0,0)
    * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
    */
   public Dimension minimumLayoutSize(Container aParent)
   {
      return new Dimension(0,0);
   }

   /**
    * Empty method. 
    * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
    */
   public void layoutContainer(Container aParent)
   {
   }

}
