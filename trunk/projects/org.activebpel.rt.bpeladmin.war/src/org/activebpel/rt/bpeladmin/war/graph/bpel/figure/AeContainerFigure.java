//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/graph/bpel/figure/AeContainerFigure.java,v 1.1 2005/04/18 18:31:4
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
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import org.activebpel.rt.bpeladmin.war.graph.ui.AeIconLabel;


/**
 * Base class for containers which contain BPEL activity children.
 */
public class AeContainerFigure extends AeBpelFigureBase
{
   /** If true, draws a rounded border */
   private boolean mDrawBorder = true;
   /** If true, paints background */
   private boolean mDrawBackground = false;
   /** Indicates if the background should be transparent. */
   private boolean mBackgroundTransparent = false;
   /** Insets defining the background margins */
   private Insets mBackgroundInsets = new Insets(15,0,4,0);
   
   /**
    * Constructs the container with the given name.
    * @param aBpelName name of this container.
    */
   public AeContainerFigure(String aBpelName)
   {
      this(aBpelName,null);      
   }

   /**
    * Constructs the container with the given name and icon.
    * @param aBpelName name of this container.
    * @param aIconImage image
    */   
   public AeContainerFigure(String aBpelName, Image aIconImage)
   {   
      super(aBpelName, aIconImage);      
   }
   
   /** 
    * Overrides method so that a label is not added. 
    * @see org.activebpel.rt.bpeladmin.war.graph.bpel.figure.AeBpelFigureBase#addLabel(org.activebpel.rt.bpeladmin.war.graph.ui.AeIconLabel)
    */
   protected void addLabel(AeIconLabel aLabel)
   {
   }    
       
   /**
    * @return Returns the backgroundInsets.
    */
   public Insets getBackgroundInsets()
   {
      return mBackgroundInsets;
   }
   
   /**
    * @param aBackgroundInsets The backgroundInsets to set.
    */
   public void setBackgroundInsets(Insets aBackgroundInsets)
   {
      mBackgroundInsets = aBackgroundInsets;
   }
   
   /**
    * @return Returns the drawBackground.
    */
   public boolean isDrawBackground()
   {
      return mDrawBackground;
   }
   
   /**
    * @param aDrawBackground The drawBackground to set.
    */
   public void setDrawBackground(boolean aDrawBackground)
   {
      mDrawBackground = aDrawBackground;
   }
   
   /**
    * @return Returns the drawBorder.
    */
   public boolean isDrawBorder()
   {
      return mDrawBorder;
   }
   
   /**
    * @param aDrawBorder The drawBorder to set.
    */
   public void setDrawBorder(boolean aDrawBorder)
   {
      mDrawBorder = aDrawBorder;
   }
   
   /**
    * @return Returns the backgroundTransparent.
    */
   public boolean isBackgroundTransparent()
   {
      return mBackgroundTransparent;
   }
   
   /**
    * @param aBackgroundTransparent The backgroundTransparent to set.
    */
   public void setBackgroundTransparent(boolean aBackgroundTransparent)
   {
      mBackgroundTransparent = aBackgroundTransparent;
   }
   
   /** 
    * Overrides method to paint the border and or background. 
    * @see org.activebpel.rt.bpeladmin.war.graph.ui.AeContainer#paintComponent(java.awt.Graphics)
    */
   public void paintComponent(Graphics g) 
   {
      super.paintComponent(g);
      if (isDrawBackground())
      {
         paintComponentBackground(g, getBackgroundInsets(), getUiPrefs().getBackgroundColor());
      }
      else if (!isBackgroundTransparent())
      {
         paintComponentBackground(g, null, Color.WHITE);
      }
      if (isDrawBorder())
      {
         paintComponentBorder(g);
      }
   } 
      
   /**
    * Paints the container's rounded border.
    * @param g graphics context.
    */
   protected void paintComponentBorder(Graphics g)
   {
      int height = this.getHeight();
      int width = this.getWidth();
      Color c = g.getColor();
      g.setColor(getUiPrefs().getBorderColor());
      g.drawRoundRect(0, 0, width - 1, height - getInsets().bottom,10,10);
      g.setColor(c);                 
   }

   /**
    * Paints the container's rounded background.
    * @param g graphics context.
    */   
   public void paintComponentBackground(Graphics g, Insets insets, Color aColor) 
   {
      int x = 0;
      int y = 0;      
      int width = getWidth();
      int height = getHeight();
      if (insets != null)
      {
         x += insets.left;
         y += insets.top;
         height = height - insets.top - insets.bottom;
         width = width - insets.left - insets.right;
      }
      else {
         height = height - getInsets().bottom;
      }
      Color c = g.getColor();
      g.setColor(aColor);
      g.fillRoundRect(x, y, width, height,10,10);
      g.setColor(c);     
   } 

}
