//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/graph/ui/AeIconLabel.java,v 1.2 2005/06/28 17:18:5
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

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

/**
 * Draws a text label with an icon on the left.
 */
public class AeIconLabel extends AeContainer
{
   /** Icon to be displayed. */
   private AeIcon mIcon = null;
   /** Text label to be displayed. */
   private AeTextLabel mLabel;

   /**
    * Constructs an label with the given text and icon image.
    * @param aText text to be displayed.
    * @param aIconImage image to be displayed. 
    */
   public AeIconLabel(String aText, Image aIconImage)
   {
      super(aText);
      setLayout( new FlowLayout(FlowLayout.CENTER,0,0));
      if (aIconImage != null)
      {
         mIcon = new AeIcon(aIconImage);
         add(mIcon);
      }      
      mLabel = new AeTextLabel(aText);
      add(mLabel);
   }
   
   /**
    * Overrides method to set the font.
    * @see java.awt.Component#setFont(java.awt.Font)
    */
   public void setFont(Font aFont)
   {
      super.setFont(aFont);
      if (mLabel != null)
      {
         mLabel.setFont(aFont);
      }
   }
   
   /** 
    * @return Icon associated with this component or null if one does not exist.
    */
   public AeIcon getIcon()
   {
      return mIcon;
   }
}
