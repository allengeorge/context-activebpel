//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/graph/ui/controller/AeGraphController.java,v 1.1 2005/04/18 18:32:0
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

import java.util.ArrayList;
import java.util.List;

import org.activebpel.rt.bpeladmin.war.graph.ui.figure.AeGraphFigure;

/**
 * Basic implementation of the IAeGraphController interface.
 */
public class AeGraphController implements IAeGraphController
{
   /** parent controller */
   private IAeGraphController mParent = null;
   
   /** list of child controllers */
   private List mChildren = new ArrayList();
   
   /** main figure container */
   private AeGraphFigure mFigure = null;
   
   /** Content figure */
   private AeGraphFigure mContentFigure = null;
   
   /** Model associated with this controller */
   private Object mModel = null;
   
   /** List children belonging to the model */
   private List mModelChildren = new ArrayList();
   
   /**
    * Default constructor.
    */
   public AeGraphController()
   {
   }

   /**
    * Returns the main view. 
    * @return main figure
    */   
   public AeGraphFigure getFigure()
   {
      if (mFigure == null)
      {
         mFigure = createFigure();
      }
      return mFigure;
   }
   
   /**
    * Sets the figure.
    * @param aFigure main figure
    */
   public void setFigure(AeGraphFigure aFigure)
   {
      mFigure = aFigure;
   }   
   
   /**
    * Creates and returns the figure. Subclasses may override this method to return the
    * appropriate figure.
    * @return main figure.
    */
   protected AeGraphFigure createFigure()
   {
      return new AeGraphFigure();
   }

   /**
    * @return Returns the contentFigure.
    */
   public AeGraphFigure getContentFigure()
   {
      if (mContentFigure == null)
      {
         setContentFigure(getFigure());
      }
      return mContentFigure;
   }
   
   /**
    * @param aContentFigure The contentFigure to set.
    */
   public void setContentFigure(AeGraphFigure aContentFigure)
   {
      mContentFigure = aContentFigure;
   }
   
   /**
    * Parent of this controller.
    * @return parent controller.
    */   
   public IAeGraphController getParent()
   {
      return mParent;
   }
     
   /**
    * Sets the parent controller.
    * @param aParent parent controller.
    */   
   public void setParent(IAeGraphController aParent)
   {
      if (aParent != mParent)
      {
         mParent = aParent;
      }
   }
   
   /**
    * Adds a child controller.
    * @param aChild child controller.
    */   
   public void addChild(IAeGraphController aChild)
   {
      if (aChild != null && aChild != this && !getChildren().contains(aChild))
      {
         aChild.setParent(this);
         getChildren().add(aChild);
      }
   }
   
   /**
    * Removes the given child from the controller hierarchy.
    * @param aChild controller to be removed
    */
   public void removeChild(IAeGraphController aChild)
   {
      if (aChild != null && getChildren().contains(aChild))
      {         
         getChildren().remove(aChild);
         aChild.setParent(null);
      }
   }
   
   /**
    * Returns list of child controllers.
    * @return list of IAeGraphController child objects.
    */  
   public List getChildren()
   {
      return mChildren;
   }
   
   /**
    * Returns the model associated with this controller.
    * @return model
    */   
   public Object getModel()
   {
      return mModel;
   }
   
   /**
    * Sets the model that is associated with this view.
    * @param model controller model.
    */   
   public void setModel(Object aModel)
   {
      mModel = aModel;
   }
   
   /**
    * Returns list of model's children.
    * @return list of child models contained in the model.
    */   
   public List getModelChildren()
   {
      return mModelChildren;  
   }   
   
   /** 
    * @return name of this controller.
    */
   protected String getName()
   {
      int idx = getClass().getName().lastIndexOf("."); //$NON-NLS-1$
      return getClass().getName().substring(idx + 1);         
   }
   
   /** 
    * Overrides method to 
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      if (getModel() != null)
      {
         return getName() + " " + getModel(); //$NON-NLS-1$
      }
      else
      {
         return getName();
      }
   }
}
