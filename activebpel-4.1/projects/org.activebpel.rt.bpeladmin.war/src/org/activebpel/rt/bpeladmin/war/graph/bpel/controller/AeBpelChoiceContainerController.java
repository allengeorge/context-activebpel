//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/graph/bpel/controller/AeBpelChoiceContainerController.java,v 1.2 2005/06/14 17:17:2
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

import java.awt.LayoutManager;

import org.activebpel.rt.bpeladmin.war.graph.bpel.figure.AeBpelChoiceContainerFigure;
import org.activebpel.rt.bpeladmin.war.graph.ui.AeFlowLayoutManager;
import org.activebpel.rt.bpeladmin.war.graph.ui.figure.AeGraphFigure;

/**
 * Controller which creates and returns ChoiceContainerFigure as the content.
 * Swith and Pick acitivities are based on this controller.
 */
public class AeBpelChoiceContainerController extends AeBpelContainerController
{

   /**
    * Default constructor
    */
   public AeBpelChoiceContainerController()
   {
      super();      
   }

   /**
    * Overrides method to return a flow layout manager.
    * @see org.activebpel.rt.bpeladmin.war.graph.bpel.controller.AeBpelContainerController#getContentLayoutManager(org.activebpel.rt.bpeladmin.war.graph.ui.figure.AeGraphFigure)
    */
   protected LayoutManager getContentLayoutManager(AeGraphFigure aForFigure)
   {
      return new AeFlowLayoutManager(true);
   }
   
   /** 
    * Overrides method to return a AeBpelChoiceContainerFigure. 
    * @see org.activebpel.rt.bpeladmin.war.graph.bpel.controller.AeBpelContainerController#createContentFigure()
    */
   protected AeGraphFigure createContentFigure()
   {
      AeBpelChoiceContainerFigure figure = new AeBpelChoiceContainerFigure(getLabelText());
      return figure;
   }   
}
