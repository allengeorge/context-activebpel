// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/registry/AeWSBPELDefWriterRegistry.java,v 1.9 2007/09/12 02:48:1
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
package org.activebpel.rt.bpel.def.io.registry;

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeCompensationHandlerDef;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.AeTerminationHandlerDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.activity.AeActivityOpaqueDef;
import org.activebpel.rt.bpel.def.activity.AeNotUnderstoodExtensionActivityDef;
import org.activebpel.rt.bpel.def.activity.support.AeFromDef;
import org.activebpel.rt.bpel.def.activity.support.AeQueryDef;
import org.activebpel.rt.bpel.def.io.writers.def.AeWSBPELWriterVisitor;
import org.activebpel.rt.bpel.def.io.writers.def.IAeBpelDefWriter;
import org.activebpel.rt.bpel.def.io.writers.def.IAeWriterDefVisitor;
import org.activebpel.rt.bpel.def.io.writers.def.IAeWriterDefVisitorFactory;
import org.w3c.dom.Element;

/**
 * A WSBPEL 2.0 implementation of a Def Writer Registry.
 */
public class AeWSBPELDefWriterRegistry extends AeBPWSDefWriterRegistry
{
   /**
    * Default c'tor.
    */
   public AeWSBPELDefWriterRegistry()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.registry.AeBPWSDefWriterRegistry#init()
    */
   protected void init()
   {
      super.init();
      
      // Remove some writers from the registry.
      unregisterWriter(ACTIVITY_TERMINATE_CLASS);
      unregisterWriter(MESSAGE_EXCHANGES_CLASS);

      // Add some writers to the registry for WS-BPEL 2.0.
      registerWriter(IMPORT_CLASS, TAG_IMPORT);
      registerWriter(DOCUMENTATION_CLASS, TAG_DOCUMENTATION);
      registerWriter(ACTIVITY_VALIDATE_CLASS, TAG_VALIDATE);
      registerWriter(ACTIVITY_EXIT_CLASS, TAG_EXIT);
      registerWriter(ASSIGN_EXTENSIBLE_ASSIGN_CLASS, TAG_EXTENSIBLE_ASSIGN);
      registerWriter(EXTENSIONS_CLASS, TAG_EXTENSIONS);
      registerWriter(EXTENSION_CLASS, TAG_EXTENSION);
      registerWriter(FROM_PARTS_CLASS, TAG_FROM_PARTS);
      registerWriter(TO_PARTS_CLASS, TAG_TO_PARTS);
      registerWriter(FROM_PART_CLASS, TAG_FROM_PART);
      registerWriter(TO_PART_CLASS, TAG_TO_PART);
      registerWriter(MESSAGE_EXCHANGES_CLASS, TAG_MESSAGE_EXCHANGES);
      registerWriter(MESSAGE_EXCHANGE_CLASS, TAG_MESSAGE_EXCHANGE);

      // fixme we need to put in an activity extension registry so that we can map custom activity QNames to custom writers
      registerWriter(EXTENSION_ACTIVITY_CLASS, TAG_EXTENSION_ACTIVITY);
      registerWriter(ACTIVITY_NOTUNDERSTOOD_CLASS, new AeUnknownExtensionActivityWriter());
      
      registerWriter(ACTIVITY_IF_CLASS, TAG_IF);
      // Skip over the 'if' wrapper
      registerWriter(IF_CLASS, new AeSkipWriter());
      registerWriter(ELSEIF_CLASS, TAG_ELSEIF);
      registerWriter(ELSE_CLASS, TAG_ELSE);
      
      registerWriter(ACTIVITY_REPEAT_UNTIL_CLASS, TAG_REPEAT_UNTIL);
      registerWriter(ACTIVITY_RETHROW_CLASS, TAG_RETHROW);

      registerWriter(REPEAT_EVERY_CLASS, TAG_REPEAT_EVERY);

      registerWriter(ON_EVENT_CLASS, TAG_ON_EVENT);
      registerWriter(TERMINATION_HANDLER_CLASS, TAG_TERMINATION_HANDLER);

      registerWriter(SOURCES_CLASS, TAG_SOURCES);
      registerWriter(TARGETS_CLASS, TAG_TARGETS);

      registerWriter(JOIN_CONDITION_CLASS, TAG_JOIN_CONDITION);
      registerWriter(TRANSITION_CONDITION_CLASS, TAG_TRANSITION_CONDITION);
      registerWriter(FOR_CLASS, TAG_FOR);
      registerWriter(UNTIL_CLASS, TAG_UNTIL);

      registerWriter(CONDITION_CLASS, TAG_CONDITION);

      registerWriter(LITERAL_CLASS, TAG_LITERAL);

      unregisterWriter(ACTIVITY_FOREACH_CLASS);
      unregisterWriter(ACTIVITY_FOREACH_COMPLETION_CONDITION);
      unregisterWriter(ACTIVITY_FOREACH_BRANCHES);
      unregisterWriter(ACTIVITY_FOREACH_START);
      unregisterWriter(ACTIVITY_FOREACH_FINAL);

      registerWriter(ACTIVITY_FOREACH_CLASS, TAG_FOREACH);
      registerWriter(ACTIVITY_FOREACH_COMPLETION_CONDITION, TAG_FOREACH_COMPLETION_CONDITION);
      registerWriter(ACTIVITY_FOREACH_BRANCHES, TAG_FOREACH_BRANCHES);
      registerWriter(ACTIVITY_FOREACH_START, TAG_FOREACH_STARTCOUNTER);
      registerWriter(ACTIVITY_FOREACH_FINAL, TAG_FOREACH_FINALCOUNTER);

      registerWriter(ACTIVITY_COMPENSATE_SCOPE_CLASS, TAG_COMPENSATE_SCOPE);

      registerWriter(AeQueryDef.class, TAG_QUERY);

      // Compensation handler (write out as an extension element in BPEL 2.0)
      unregisterWriter(COMPENSATION_HANDLER_CLASS);
      registerWriter(COMPENSATION_HANDLER_CLASS, new AeWSBPELCompensationHandlerWriter());
      
      registerWriter(TERMINATION_HANDLER_CLASS, new AeWSBPELTerminationHandlerWriter());
     
      // Writer for BPEL 2.0 abstract processe's opaque activity.
      // (note: this writer is registered in this 'bpel executable process' namespace to allow users to save abstract processes as executable processes (with the opaque activities)
      registerWriter(AeActivityOpaqueDef.class, createWriter(IAeBPELConstants.WSBPEL_2_0_ABSTRACT_NAMESPACE_URI, TAG_OPAQUE_ACTIVITY));
      
      // new writer to handle BPEL 2.0 <from/> or abstract process's <opaqueFrom/>
      unregisterWriter(AeFromDef.class);
      registerWriter(AeFromDef.class, new AeWSBPELFromDefWriter());
   }
   
   /**
    * Creates the writer def factory that the dispatch writer will use to create visitors to dispatch to.
    */
   protected IAeWriterDefVisitorFactory createWriterVisitorFactory()
   {
      return new IAeWriterDefVisitorFactory()
      {
         /**
          * @see org.activebpel.rt.bpel.def.io.writers.def.IAeWriterDefVisitorFactory#createWriterDefVisitor(org.activebpel.rt.bpel.def.AeBaseDef, org.w3c.dom.Element, java.lang.String, java.lang.String)
          */
         public IAeWriterDefVisitor createWriterDefVisitor(AeBaseDef aDef, Element aParentElement, String aNamespaceUri, String aTagName)
         {
            return new AeWSBPELWriterVisitor(aDef, aParentElement, aNamespaceUri, aTagName);
         }
      };
   }

   /**
    * Overrides the base class in order to return the WSBPEL 2.0 namespace.
    * 
    * @see org.activebpel.rt.bpel.def.io.registry.AeBPWSDefWriterRegistry#getBpelNamespace()
    */
   protected String getBpelNamespace()
   {
      return IAeBPELConstants.WSBPEL_2_0_NAMESPACE_URI;
   }

   /**
    * Writes an activity extension (found as the child of an extensionActivity).  The
    * technique is similar to the dispatch writer, except this class gets the namespace and local
    * part information from the unknown activity def itself.
    */
   protected class AeUnknownExtensionActivityWriter implements IAeBpelDefWriter
   {
      /**
       * @see org.activebpel.rt.bpel.def.io.writers.def.IAeBpelDefWriter#createElement(org.activebpel.rt.bpel.def.AeBaseDef, org.w3c.dom.Element)
       */
      public Element createElement(AeBaseDef aBaseDef, Element aParentElement)
      {
         AeNotUnderstoodExtensionActivityDef actExtDef = (AeNotUnderstoodExtensionActivityDef) aBaseDef;

         IAeWriterDefVisitor visitor = getWriterVisitorFactory().createWriterDefVisitor(aBaseDef,
               aParentElement, actExtDef.getElementName().getNamespaceURI(),
               actExtDef.getElementName().getLocalPart());
         aBaseDef.accept(visitor);
         return visitor.getElement();
      }
   }

   /**
    * Writes out a compensation handler.
    */
   protected class AeWSBPELCompensationHandlerWriter implements IAeBpelDefWriter
   {
      /**
       * @see org.activebpel.rt.bpel.def.io.writers.def.IAeBpelDefWriter#createElement(org.activebpel.rt.bpel.def.AeBaseDef, org.w3c.dom.Element)
       */
      public Element createElement(AeBaseDef aBaseDef, Element aParentElement)
      {
         AeCompensationHandlerDef compHandlerDef = (AeCompensationHandlerDef) aBaseDef;
         String namespace = getBpelNamespace();
         String elementName = TAG_COMPENSATION_HANDLER;
         if (compHandlerDef.getParent() instanceof AeProcessDef)
            namespace = IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ALLOW_PROCESS_COORDINATION;

         IAeWriterDefVisitor visitor = getWriterVisitorFactory().createWriterDefVisitor(aBaseDef,
               aParentElement, namespace, elementName);
         aBaseDef.accept(visitor);
         return visitor.getElement();
      }
   }
   
   /**
    * Writes out a termination handler.  Need special handling in ws-bpel in order to properly
    * write out the process-level termination handler.
    */
   protected class AeWSBPELTerminationHandlerWriter implements IAeBpelDefWriter
   {
      /**
       * @see org.activebpel.rt.bpel.def.io.writers.def.IAeBpelDefWriter#createElement(org.activebpel.rt.bpel.def.AeBaseDef, org.w3c.dom.Element)
       */
      public Element createElement(AeBaseDef aBaseDef, Element aParentElement)
      {
         AeTerminationHandlerDef termHandlerDef = (AeTerminationHandlerDef) aBaseDef;
         String namespace = getBpelNamespace();
         String elementName = TAG_TERMINATION_HANDLER;
         if (termHandlerDef.getParent() instanceof AeProcessDef)
            namespace = IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ALLOW_PROCESS_COORDINATION;

         IAeWriterDefVisitor visitor = getWriterVisitorFactory().createWriterDefVisitor(aBaseDef,
               aParentElement, namespace, elementName);
         aBaseDef.accept(visitor);
         return visitor.getElement();
      }
   }
   
   /**
    * Writes out a <from> (for executable processes) or <opaqueFrom> (for abstract processes).
    */
   protected class AeWSBPELFromDefWriter implements IAeBpelDefWriter
   {
      /**
       * @see org.activebpel.rt.bpel.def.io.writers.def.IAeBpelDefWriter#createElement(org.activebpel.rt.bpel.def.AeBaseDef, org.w3c.dom.Element)
       */
      public Element createElement(AeBaseDef aBaseDef, Element aParentElement)
      {
         AeFromDef fromDef = (AeFromDef) aBaseDef;
         String namespace = getBpelNamespace();
         String elementName = TAG_FROM;
         if (fromDef.isOpaque())
         {
            elementName = TAG_OPAQUE_FROM;
            namespace = IAeBPELConstants.WSBPEL_2_0_ABSTRACT_NAMESPACE_URI;
         }
         IAeWriterDefVisitor visitor = getWriterVisitorFactory().createWriterDefVisitor(aBaseDef,
               aParentElement, namespace, elementName);
         aBaseDef.accept(visitor);
         return visitor.getElement();
      }
   }   
}
