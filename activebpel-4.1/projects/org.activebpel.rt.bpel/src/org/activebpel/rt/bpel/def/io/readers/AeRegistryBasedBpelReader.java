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
package org.activebpel.rt.bpel.def.io.readers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.io.IAeBpelReader;
import org.activebpel.rt.bpel.def.io.registry.IAeBpelRegistry;
import org.activebpel.rt.bpel.def.visitors.AeDefAssignParentVisitor;
import org.activebpel.rt.bpel.def.visitors.AeDefCreateInvokeScopeVisitor;
import org.activebpel.rt.bpel.def.visitors.AeDefPartnerLinkNameVisitor;
import org.activebpel.rt.bpel.def.visitors.AeDefVisitorFactory;
import org.activebpel.rt.bpel.def.visitors.IAeDefPathVisitor;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Base class for readers that use the registry to drive the deserialization process.
 */
public abstract class AeRegistryBasedBpelReader implements IAeBpelReader
{
   /** bpel reader/writer registry */
   private IAeBpelRegistry mBpelRegistry;

   /**
    * Ctor accepts both registries
    * @param aBpelRegistry
    * @param aExtRegistry
    */
   public AeRegistryBasedBpelReader(IAeBpelRegistry aBpelRegistry)
   {
      mBpelRegistry = aBpelRegistry;
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.IAeBpelReader#readBPEL(org.w3c.dom.Document)
    */
   public AeProcessDef readBPEL(Document aBpelDoc) throws AeBusinessProcessException
   {
      Element processElement = aBpelDoc.getDocumentElement();

      AeBpelDomTraverser traverser = AeBpelDomTraverser.createBpelDomTraverser(processElement,
            getBpelRegistry());
      traverser.traverseProcess( processElement );

      AeProcessDef def = traverser.getProcessDef();
      runCoreVisitors(def);
      return def;
   }

   /**
    * This is a post load step that assigns parents to all child defs and also gives each child a unique path.
    * It also pulls up Invoke "implicit" scopes.
    *
    * @throws AeBusinessProcessException
    */
   public void runCoreVisitors(AeProcessDef aDef)
   {
      // assign parents for definitions
      AeDefAssignParentVisitor parentVisitor = new AeDefAssignParentVisitor();
      parentVisitor.visit(aDef);

      // pull up any invoke scopes (for invokes that have implicit scopes)
      AeDefCreateInvokeScopeVisitor invokeScopeVizzy = new AeDefCreateInvokeScopeVisitor();
      invokeScopeVizzy.visit(aDef);

      IAeDefVisitor implicitVisitor = AeDefVisitorFactory.getInstance(aDef.getNamespace()).createImplicitVariableVisitor();
      aDef.accept(implicitVisitor);

      IAeDefVisitor messageExchangeVisitor = AeDefVisitorFactory.getInstance(aDef.getNamespace()).createMessageExchangeVisitor();
      messageExchangeVisitor.visit(aDef);

      assignPaths(aDef);

      AeDefPartnerLinkNameVisitor plNameVisitor = new AeDefPartnerLinkNameVisitor();
      plNameVisitor.visit(aDef);
   }

   /**
    * Assigns location paths (and ids) to each of the def objects and records these
    * paths on the process def.
    * @param aDef
    */
   protected void assignPaths(AeProcessDef aDef)
   {
      // assign location paths for definitions
      IAeDefPathVisitor pathVisitor = AeDefVisitorFactory.getInstance(aDef.getNamespace()).createDefPathVisitor();
      pathVisitor.visit(aDef);

      // populate bidirectional maps between location paths and location ids
      Map locationPathsToIds = new HashMap();
      for (Iterator i = pathVisitor.getLocationPaths().iterator(); i.hasNext(); )
      {
         String locationPath = (String) i.next();
         int locationId = pathVisitor.getLocationId(locationPath);
         locationPathsToIds.put(locationPath, new Integer(locationId));
      }
      aDef.setLocationPathsToIds(locationPathsToIds);
   }

   /**
    * Internal accessor for bpel registry
    * @return bpel registry
    */
   protected IAeBpelRegistry getBpelRegistry()
   {
      return mBpelRegistry;
   }
}
