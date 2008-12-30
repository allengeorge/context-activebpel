//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/graph/bpel/controller/AeBpelSwitchActivityController.java,v 1.1 2005/04/18 18:31:5
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
package org.activebpel.rt.bpeladmin.war.graph.bpel.controller;

import java.awt.Color;

import org.activebpel.rt.bpeladmin.war.graph.bpel.figure.AeBpelFigureBase;
import org.activebpel.rt.bpeladmin.war.graph.ui.figure.AeGraphFigure;

/**
 * The AeBpelSwitchActivityController creates figure to Switch type activities.
 */
public class AeBpelSwitchActivityController extends AeBpelChoiceContainerController
{

   /**
    * Default constructor.
    */
   public AeBpelSwitchActivityController()
   {
      super();      
   }

   /** 
    * Overrides method to change the debug draw color of the figure. 
    * @see org.activebpel.rt.bpeladmin.war.graph.ui.controller.AeGraphController#createFigure()
    */
   protected AeGraphFigure createFigure()
   {
      AeBpelFigureBase fig = (AeBpelFigureBase) super.createFigure();
      fig.getUiPrefs().setDebugActivityContainerColor(Color.ORANGE);
      return fig;
   }

}
