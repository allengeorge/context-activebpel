// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/AeLocationPathImplVisitor.java,v 1.4 2006/10/26 13:51:5
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
package org.activebpel.rt.bpel.impl.activity;

import java.util.Iterator;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;
import org.activebpel.rt.bpel.def.visitors.AeDefVisitorFactory;
import org.activebpel.rt.bpel.def.visitors.IAeDefPathSegmentVisitor;
import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;
import org.activebpel.rt.bpel.impl.AeBusinessProcess;
import org.activebpel.rt.bpel.impl.AePartnerLink;
import org.activebpel.rt.bpel.impl.AeVariable;
import org.activebpel.rt.bpel.impl.activity.support.AeCorrelationSet;
import org.activebpel.rt.bpel.impl.visitors.AeImplTraversingVisitor;

/**
 * Visits each of the implementation objects and sets a custom location path
 * and id on each object. 
 */
public class AeLocationPathImplVisitor extends AeImplTraversingVisitor
{
   /** our modified path visitor that creates a custom path with the [instance()=n] predicate */
   protected AeImplPathParallelVisitor mPathVisitor;
   /** reference to the process since we might be asked to report all of the mappings */
   protected AeBusinessProcess mProcess;
   /** reference to the scope instance from the forEach */
   protected AeActivityScopeImpl mScope;
   
   /**
    * Creates the visitor and starts the process
    * 
    * @param aProcess
    * @param aImpl
    * @param aInstanceValue
    * @param aCreateMode
    * @throws AeBusinessProcessException
    */
   public AeLocationPathImplVisitor(AeBusinessProcess aProcess, AeActivityScopeImpl aImpl, int aInstanceValue, boolean aCreateMode)
   {
      mProcess = aProcess;
      IAeDefPathSegmentVisitor segmentVisitor = AeDefVisitorFactory.getInstance(aProcess.getBPELNamespace()).createDefPathSegmentVisitor();
      mPathVisitor = new AeImplPathParallelVisitor(segmentVisitor, aProcess.getMaxLocationId() + 1, aImpl.getParent().getLocationPath(), aImpl.getDefinition(), aCreateMode);
      mPathVisitor.setInstanceValue(aInstanceValue);
      mScope = aImpl;
   }
   
   /**
    * Visits the scope's def with the path generator and then the impl visitor
    * sets the newly generated paths.
    */
   public void startVisiting()
   {
      mScope.getDefinition().accept(mPathVisitor);
      try
      {
         mScope.accept(this);
      }
      catch (AeBusinessProcessException e)
      {
         // the impl visitors all throw AeBusinessProcessException. 
         // There shouldn't be any errors in setting the paths. The exception is related more to the I/O layer which 
         // reads and writes the impl state to a DOM.
         e.logError();
      }
      if (mPathVisitor.isCreateMode())
      {
         // after visiting all defs, update the process's maxLocationId with the next id - 1
         mProcess.setMaxLocationId(mPathVisitor.getNextLocationId() -1);
      }
   }

   /**
    * @see org.activebpel.rt.bpel.impl.visitors.IAeImplVisitor#visit(org.activebpel.rt.bpel.impl.activity.AeActivityScopeImpl)
    */
   public void visit(AeActivityScopeImpl aImpl)
         throws AeBusinessProcessException
   {
      super.visit(aImpl);
      
      AeActivityScopeDef def = (AeActivityScopeDef) aImpl.getDefinition();
      if (def.getScopeDef().hasVariables())
      {
         for (Iterator iter = aImpl.getVariableContainer().iterator(); iter.hasNext();)
         {
            AeVariable v = (AeVariable) iter.next();
            setVariablePath(v);
         }
      }
      
      if (def.getScopeDef().hasCorrelationSets())
      {
         for (Iterator iter = aImpl.getCorrelationSetMap().values().iterator(); iter.hasNext();)
         {
            AeCorrelationSet corrSet = (AeCorrelationSet) iter.next();
            setCorrelationSetPath(corrSet);
         }
      }

      for (Iterator iter = aImpl.getPartnerLinks().values().iterator(); iter.hasNext(); )
      {
         AePartnerLink plink = (AePartnerLink) iter.next();
         setPartnerLinkPath(plink);
      }
   }
   
   
   /**
    * Sets the variable's location path
    * @param aVariable
    */
   protected void setVariablePath(AeVariable aVariable)
   {
      String path = mPathVisitor.getLocationPath(aVariable.getDefinition());
      aVariable.setLocationPath(path);
   }

   /**
    * Sets the correlation set's location path.
    */
   protected void setCorrelationSetPath(AeCorrelationSet aCorrelationSet)
   {
      String path = mPathVisitor.getLocationPath(aCorrelationSet.getDefinition());
      aCorrelationSet.setLocationPath(path);
   }

   /**
    * Sets the partner link's location path
    * 
    * @param aPartnerLink
    */
   protected void setPartnerLinkPath(AePartnerLink aPartnerLink)
   {
      String path = mPathVisitor.getLocationPath(aPartnerLink.getDefinition());
      aPartnerLink.setLocationPath(path);
   }
   
   /**
    * Sets the bpel object's location path and id and then traverses.
    * 
    * @see org.activebpel.rt.bpel.impl.visitors.AeImplTraversingVisitor#traverse(org.activebpel.rt.bpel.impl.AeAbstractBpelObject)
    */
   protected void traverse(AeAbstractBpelObject aImpl)
         throws AeBusinessProcessException
   {
      String path = mPathVisitor.getLocationPath(aImpl.getDefinition());
      aImpl.setLocationPath(path);
      int id = mPathVisitor.getLocationId(path);
      aImpl.setLocationId(id);

      super.traverse(aImpl);
   }
}
