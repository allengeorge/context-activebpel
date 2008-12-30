//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/graph/ui/controller/IAeGraphController.java,v 1.1 2005/04/18 18:32:0
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

import java.util.List;

import org.activebpel.rt.bpeladmin.war.graph.ui.figure.AeGraphFigure;

/**
 * Inteface that defines a controller based on the Model-View-Controller.
 */
public interface IAeGraphController
{
   /**
    * Returns the main view. 
    * @return main figure
    */
   public AeGraphFigure getFigure();
   
   /**
    * Returns the content view.
    * @return figure which contains the content of the model.
    */
   public AeGraphFigure getContentFigure();
   
   /**
    * Parent of this controller.
    * @return parent controller.
    */
   public IAeGraphController getParent();
   
   /**
    * Sets the parent controller.
    * @param aParent parent controller.
    */
   public void setParent(IAeGraphController aParent);
   
   /**
    * Adds a child controller.
    * @param aChild child controller.
    */
   public void addChild(IAeGraphController aChild);
   
   /**
    * Returns list of child controllers.
    * @return list of IAeGraphController child objects.
    */
   public List getChildren();
   
   /**
    * Returns the model associated with this controller.
    * @return model
    */
   public Object getModel();
   
   /**
    * Sets the model that is associated with this view.
    * @param model controller model.
    */
   public void setModel(Object model);
   
   /**
    * Returns list of model's children.
    * @return list of child models contained in the model.
    */
   public List getModelChildren();

}
