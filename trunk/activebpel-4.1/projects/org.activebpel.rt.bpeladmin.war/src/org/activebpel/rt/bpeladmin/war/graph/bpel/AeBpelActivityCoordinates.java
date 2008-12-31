//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/graph/bpel/AeBpelActivityCoordinates.java,v 1.1 2005/04/18 18:31:5
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

import java.awt.Rectangle;

import org.activebpel.rt.bpeladmin.war.web.processview.AeBpelObjectBase;

/**
 * A simple object that contains a BPEL activity's bounding rectangle,
 * the activity model and its location path. This is used for hit testing.
 * The coordinates of the bounding rectangle is relative to the root container.
 * 
 */
public class AeBpelActivityCoordinates
{
   /** Location path (xpath) of the activity */
   private String mLocationPath = null;
   /** Hit test bounds */
   private Rectangle mBounds = null;
   /** BPEL model associated with this area */
   private AeBpelObjectBase mBpelModel = null;
   
   /**
    * Default constructor. 
    */
   public AeBpelActivityCoordinates()
   {
   }
   
   /**
    * @return Returns the bounds.
    */
   public Rectangle getBounds()
   {
      return mBounds;
   }
   
   /**
    * @param aBounds The bounds to set.
    */
   public void setBounds(Rectangle aBounds)
   {
      mBounds = aBounds;
   }
   
   /**
    * @return Returns the bpelModel.
    */
   public AeBpelObjectBase getBpelModel()
   {
      return mBpelModel;
   }
   
   /**
    * @param aBpelModel The bpelModel to set.
    */
   public void setBpelModel(AeBpelObjectBase aBpelModel)
   {
      mBpelModel = aBpelModel;
      setLocationPath(mBpelModel.getLocationPath());
   }
   
   /**
    * @return Returns the locationPath.
    */
   public String getLocationPath()
   {
      return mLocationPath;
   }
   
   /**
    * @param aLocationPath The locationPath to set.
    */
   public void setLocationPath(String aLocationPath)
   {
      mLocationPath = aLocationPath;
   }
}
