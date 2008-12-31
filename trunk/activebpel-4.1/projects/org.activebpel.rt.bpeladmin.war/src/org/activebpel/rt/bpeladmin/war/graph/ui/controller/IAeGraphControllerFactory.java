//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/graph/ui/controller/IAeGraphControllerFactory.java,v 1.1 2005/04/18 18:32:0
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
package org.activebpel.rt.bpeladmin.war.graph.ui.controller;

/**
 * Interface that defines the factory which is responsible for creating
 * the model-view-controller hierarchy.
 */
public interface IAeGraphControllerFactory
{
   /**
    * Creates and returns an implementation of IAeGraphController.
    * @param aContext context
    * @param model model to be assoicated with the controller.
    * @return controller.
    */
   public IAeGraphController createController(IAeGraphController aContext, Object model);
   
}
