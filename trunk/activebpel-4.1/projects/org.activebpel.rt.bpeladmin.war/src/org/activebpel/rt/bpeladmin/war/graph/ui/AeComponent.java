//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/graph/ui/AeComponent.java,v 1.1 2005/04/18 18:31:4
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

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import org.activebpel.rt.util.AeUtil;

/**
 * Base component for a user interface widget.
 */
public class AeComponent extends Container
{
   /** Preferred sized of the component */
   private Dimension mPreferredSize = null;
   
   /** 
    * Default constructor.
    */
   public AeComponent()
   {
      this(null);
   }
   
   /**
    * Constructs the component with the given name.
    * @param aName component name.
    */
   public AeComponent(String aName)
   {
      super();
      setLayout(new FlowLayout(FlowLayout.LEFT));       
      setBackground(Color.WHITE);      
      if (! AeUtil.isNullOrEmpty(aName))
      {
         setName(aName);
      }
      else
      {
         int idx = getClass().getName().lastIndexOf(".");  //$NON-NLS-1$
         setName(getClass().getName().substring(idx + 1));
      }
   }

   /**
    * Sets the preferred size of this component.
    * @param aWidth width of the component, in pixels.
    * @param aHeight height of the component.
    */
   public void setPreferredSize(int aWidth, int aHeight) {
      setPreferredSize( new Dimension(aWidth, aHeight));
   }   

   /**
    * Sets the preferred size of this component.
    * @param aPreferredSize the preferred size of the component..
    */
   public void setPreferredSize(Dimension aPreferredSize) {
      mPreferredSize = aPreferredSize;
   }
   
   /**
    * Return the preferred size of this component.
    * Overrides method to 
    * @see java.awt.Component#getPreferredSize()
    */
   public Dimension getPreferredSize() {
       if (mPreferredSize != null) 
       {
           return mPreferredSize;
       }
       return super.getPreferredSize();
   }   
   
   /**
    * Used for debugging. Sends to the console standard out.
    * @param aMessage message to printed to the standard output.
    */
   protected void trace(String aMessage)
   {
      System.out.println(getName() + ": " + aMessage); //$NON-NLS-1$
   } 
   
   /** 
    * Overrides method to 
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return getName();
   }
}
