//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/graph/bpel/AeLayoutPrefs.java,v 1.2 2005/04/20 20:19:4
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
package org.activebpel.rt.bpeladmin.war.graph.bpel;

import org.activebpel.rt.bpeladmin.war.graph.AeGraphProperties;

/**
 * Layout preferences used by the Autolayout algorithm.
 */
public class AeLayoutPrefs
{  
   /** Indicates a vertical layout direction */   
   public static int LAYOUT_VERTICAL   = 0;

   /** Indicates a horizontal layout direction */
   public static int LAYOUT_HORIZONTAL = 1;

   /** Top margin */
   private int mMarginTop;
   /** Bottom margin */
   private int mMarginBottom;
   /** Left margin */
   private int mMarginLeft;
   /** Right margin */
   private int mMarginRight;
   /** spacing between levels */
   private int mInterLevelSpacing;
   /** spacing between activity nodes */
   private int mInterNodeSpacing;
   /** layout direction (horizontal or vertical */
   private int mLayoutDirection;
   /** spacing between partner lanes */
   private int mInterLaneSpacing ;
   
   /**
    * Constructor which loads the plugin preferences for the layout and stores 
    * them for later use.
    */
   public AeLayoutPrefs()
   {
      mLayoutDirection = LAYOUT_VERTICAL;
      // assign default values
      mMarginTop         = 5;
      mMarginBottom      = 5;
      mMarginLeft        = 5;
      mMarginRight       = 5;
      mInterLevelSpacing = 20;
      mInterNodeSpacing  = 20;
      mInterLaneSpacing  = 10;      
      // override the default values with the values defined in the properties file.
      readLayoutPreferences();
   }   
   
   /**
    * Getter method for inter level spacing value used during layout.
    * @return number of pixels used between levels during layout
    */
   public int getInterLevelSpacing()
   {
      return mInterLevelSpacing;
   }

   /**
    * Getter method for inter node spacing value used during layout.
    * @return number of pixels used between nodes during layout
    */
   public int getInterNodeSpacing()
   {
      return mInterNodeSpacing;
   }

   /**
    * Getter method for inter lane spacing value used during layout.
    * @return number of pixels used between lanes during layout
    */
   public int getInterLaneSpacing()
   {
      return mInterLaneSpacing;
   }

   /**
    * Getter method to return the layout orientation. 
    * @return numeric constant of either LAYOUT_VERTICAL or LAYOUT_HORIZONTAL
    */
   public int getLayoutDirection()
   {
      return mLayoutDirection;
   }

   /**
    * Getter method to return the top margin used during layout.
    * @return the margin in pixels for the top border of the component 
    */
   public int getMarginTop()
   {
      return mMarginTop;
   }

   /**
    * Getter method to return the bottom margin used during layout.
    * @return the margin in pixels for the bottom border of the component 
    */
   public int getMarginBottom()
   {
      return mMarginBottom;
   }

   /**
    * Getter method to return the left margin used during layout.
    * @return the margin in pixels for the left border of the component 
    */
   public int getMarginLeft()
   {
      return mMarginLeft;
   }

   /**
    * Getter method to return the right margin used during layout.
    * @return the margin in pixels for the right border of the component 
    */
   public int getMarginRight()
   {
      return mMarginRight;
   }
   
   /**
    * Read the layout preferences from file.
    */
   protected void readLayoutPreferences()
   {
      mMarginTop         = 5;
      mMarginBottom      = 5;
      mMarginLeft        = 5;
      mMarginRight       = 5;
      mInterLevelSpacing = 20;
      mInterNodeSpacing  = 20;
      mInterLaneSpacing  = 10;      
            
      AeGraphProperties props = AeGraphProperties.getInstance();
      mMarginTop = props.getPropertyInt(AeGraphProperties.LAYOUT_MARGIN_TOP, mMarginTop); 
      mMarginBottom = props.getPropertyInt(AeGraphProperties.LAYOUT_MARGIN_BOTTOM, mMarginBottom); 
      mMarginLeft = props.getPropertyInt(AeGraphProperties.LAYOUT_MARGIN_LEFT, mMarginLeft); 
      mMarginRight = props.getPropertyInt(AeGraphProperties.LAYOUT_MARGIN_RIGHT, mMarginRight);      
      
      mInterLevelSpacing = props.getPropertyInt(AeGraphProperties.LAYOUT_LEVEL_SPACING, mInterLevelSpacing);
      mInterNodeSpacing = props.getPropertyInt(AeGraphProperties.LAYOUT_NODE_SPACING, mInterNodeSpacing); 
   }   
}
