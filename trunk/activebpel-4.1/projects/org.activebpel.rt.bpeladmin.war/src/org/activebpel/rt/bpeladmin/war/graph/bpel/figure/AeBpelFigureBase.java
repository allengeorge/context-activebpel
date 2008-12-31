//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/graph/bpel/figure/AeBpelFigureBase.java,v 1.1 2005/04/18 18:31:4
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
package org.activebpel.rt.bpeladmin.war.graph.bpel.figure;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;

import org.activebpel.rt.bpeladmin.war.graph.ui.AeIconLabel;
import org.activebpel.rt.bpeladmin.war.graph.ui.figure.AeGraphFigure;

/**
 * Base class for all figures used to render BPEL definitions.
 */
public class AeBpelFigureBase extends AeGraphFigure
{   
   /** UI color preferences */
   private AeUiPrefs mUiPrefs = AeUiPrefs.getDefaultPrefs();
   /** BPEL defition's label */
   private AeIconLabel mLabel = null;
   /** Indicates that the underlying BPEL activity has been executed or evaluated. */
   private boolean mEvaluated = false;
   /**
    * Constructs the component with the given name.
    * @param aName name of this component.
    */
   public AeBpelFigureBase(String aBpelName)
   {
      this(aBpelName, null);      
   } 
   
   /**
    * Constructs the component with the given name and activity icon image.
    * @param aName name of this component.
    * @param aIconImage activity image.
    */   
   public AeBpelFigureBase(String aBpelName, Image aIconImage)
   {
      super(aBpelName);    
      setLayout(new FlowLayout(FlowLayout.CENTER,4,4));

      AeIconLabel label = null;
      if (aIconImage != null)
      {
         label = new AeIconLabel(aBpelName, aIconImage);
      }
      if (label != null)
      {
         label.setFont(getUiPrefs().getFont());
         label.setForeground(getUiPrefs().getTextColor());
      }
      setLabel(label);
   }
   
   
   /**
    * @return Returns the uiPrefs.
    */
   public AeUiPrefs getUiPrefs()
   {
      return mUiPrefs;
   }
   
   /**
    * @param aUiPrefs The uiPrefs to set.
    */
   public void setUiPrefs(AeUiPrefs aUiPrefs)
   {
      mUiPrefs = aUiPrefs;
   }
   
   /**
    * @return Returns the evaluated.
    */
   public boolean isEvaluated()
   {
      return mEvaluated;
   }
   
   /**
    * @param aEvaluated The evaluated to set.
    */
   public void setEvaluated(boolean aEvaluated)
   {
      mEvaluated = aEvaluated;
   }
   /**
    * @return Returns the label.
    */
   public AeIconLabel getLabel()
   {
      return mLabel;
   }

   /**
    * @return Returns the label.
    */
   public void setLabel(AeIconLabel aLabel)
   {
      mLabel = aLabel;
      addLabel(aLabel);
   }
   
   /**
    * Adds the icon label component as a child of this figure. Subclasses should override this method
    * with an empty block if it does not want to add and display a label. This method is called as part
    * of the components initialization during construction.
    *  
    * @param aLabel activity label.
    */
   protected void addLabel(AeIconLabel aLabel)
   {
      if (aLabel != null)
      {
         add(aLabel);         
      }
   }
   
   /** 
    * Overrides method to return insets for BPEL activities. 
    * @see java.awt.Container#getInsets()
    */
   public Insets getInsets() 
   {
      return new Insets(5,5,5,5);
   } 
   
   /**
    * Returns the component that contains the bounding box (hit test area) for the anchor point.
    * In general, this component is the icon label component of the BPEL activity.
    * 
    * @return component containing the anchor point bounding box.
    */
   public Component getAnchorComponent()
   {
      if (getLabel() != null)
      {
         return getLabel();
      }
      else
      {
         return this;
      }      
   }
   
   /**
    * Returns the bounding box of the anchorpoint. In general this is the bounding box of the
    * icon image of the activity.
    * 
    * @return bounding box defining the anchor point.
    */
   public Rectangle getAnchorBounds()
   {
      Rectangle r = new Rectangle();  
      if (getLabel() != null)
      {
         r.setLocation(getLabel().getLocation().x, getLabel().getLocation().y);
         if (getLabel().getIcon() != null)
         {
            r.setSize(getLabel().getIcon().getIconWidth(), getLabel().getIcon().getIconHeight());
         }
         else
         {
            r.setSize(getLabel().getSize().width, getLabel().getSize().height);
         }
      }
      else
      {
         getBounds(r);
         // set origin to (0,0) since this should be relative co-ordinates.
         r.setLocation(0,0);
      }
      return r;
   }   
   
   /** 
    * Overrides method to paint debug draw lines if debug mode is enabled. 
    * @see org.activebpel.rt.bpeladmin.war.graph.ui.AeContainer#paintComponent(java.awt.Graphics)
    */
   public void paintComponent(Graphics g) 
   { 
      if (AeUiPrefs.isDrawDebugLines())
      {
         super.paintComponent(g);
         int height = this.getHeight();
         int width = this.getWidth();
         Color c = g.getColor();
         g.setColor(getUiPrefs().getDebugActivityColor());
         g.drawRect(0, 0, width - 1, height - 1);
         g.setColor(c);
      }
   }       
}
