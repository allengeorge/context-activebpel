//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/graph/bpel/controller/AeBpelContainerImplicitActivityController.java,v 1.1 2005/04/18 18:31:5
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

import org.activebpel.rt.bpeladmin.war.graph.bpel.figure.AeBpelFigureBase;
import org.activebpel.rt.bpeladmin.war.graph.ui.AeXyLayoutManager;
import org.activebpel.rt.bpeladmin.war.graph.ui.figure.AeGraphFigure;

/**
 * Controller which is responsible for creating implicit activity
 * containers.
 */
public class AeBpelContainerImplicitActivityController extends AeBpelControllerBase
{

   /**
    * Default constructer.
    */
   public AeBpelContainerImplicitActivityController()
   {
      super();
   }

   /** 
    * Overrides method to return a figure without an icon. 
    * @see org.activebpel.rt.bpeladmin.war.graph.ui.controller.AeGraphController#createFigure()
    */
   protected AeGraphFigure createFigure()
   {      
      AeGraphFigure fig = new AeBpelFigureBase(getLabelText());
      fig.setLayout(new AeXyLayoutManager());
      return fig;
   }
         
}
