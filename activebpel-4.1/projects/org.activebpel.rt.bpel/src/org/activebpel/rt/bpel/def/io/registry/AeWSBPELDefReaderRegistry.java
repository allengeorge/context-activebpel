// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/registry/AeWSBPELDefReaderRegistry.java,v 1.10 2007/09/12 02:48:1
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

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeExtensionActivityDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.activity.AeActivityBreakDef;
import org.activebpel.rt.bpel.def.activity.AeActivityContinueDef;
import org.activebpel.rt.bpel.def.activity.AeActivityForEachDef;
import org.activebpel.rt.bpel.def.activity.AeActivityOpaqueDef;
import org.activebpel.rt.bpel.def.activity.AeActivitySuspendDef;
import org.activebpel.rt.bpel.def.activity.AeNotUnderstoodExtensionActivityDef;
import org.activebpel.rt.bpel.def.activity.support.AeAssignCopyDef;
import org.activebpel.rt.bpel.def.activity.support.AeFromDef;
import org.activebpel.rt.bpel.def.activity.support.AeQueryDef;
import org.activebpel.rt.bpel.def.io.readers.def.AeWSBPELReaderVisitor;
import org.activebpel.rt.bpel.def.io.readers.def.IAeBpelDefReader;
import org.activebpel.rt.bpel.def.io.readers.def.IAeReaderDefVisitor;
import org.activebpel.rt.bpel.def.io.readers.def.IAeReaderDefVisitorFactory;
import org.w3c.dom.Element;

/**
 * An implementation of the Def Reader Registry for WS-BPEL 2.0. This implementation extends the BPEL4WS 1.1
 * implementation and then adds and/or removes mappings as needed to account for the differences between the
 * two variants of BPEL.
 */
public class AeWSBPELDefReaderRegistry extends AeBPWSDefReaderRegistry
{
   /** A reader to use when we don't recognize the child of an extensionActivity. */
   private IAeBpelDefReader mNotUnderstoodActivityReader;
   
   /**
    * Default c'tor.
    */
   public AeWSBPELDefReaderRegistry()
   {
      super();
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.registry.AeBPWSDefReaderRegistry#init()
    */
   protected void init()
   {
      super.init();
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.registry.AeBPWSDefReaderRegistry#initMainRegistry()
    */
   protected void initMainRegistry()
   {
      super.initMainRegistry();

      unregisterReader(PROCESS_CLASS, TAG_PARTNERS);

      registerReader(PROCESS_CLASS, TAG_IMPORT, createDispatchReader(IMPORT_CLASS));
      registerReader(ACTIVITY_ASSIGN_CLASS, TAG_EXTENSIBLE_ASSIGN, createDispatchReader(ASSIGN_EXTENSIBLE_ASSIGN_CLASS));
      registerReader(VARIABLE_CLASS, TAG_FROM, createDispatchReader(ASSIGN_FROM_CLASS));
      registerReader(PROCESS_CLASS, TAG_EXTENSIONS, createDispatchReader(EXTENSIONS_CLASS));
      registerReader(EXTENSIONS_CLASS, TAG_EXTENSION, createDispatchReader(EXTENSION_CLASS));

      registerReader(ACTIVITY_INVOKE_CLASS, TAG_FROM_PARTS, createDispatchReader(FROM_PARTS_CLASS));
      registerReader(ACTIVITY_RECEIVE_CLASS, TAG_FROM_PARTS, createDispatchReader(FROM_PARTS_CLASS));
      registerReader(ON_MESSAGE_CLASS, TAG_FROM_PARTS, createDispatchReader(FROM_PARTS_CLASS));
      registerReader(ON_EVENT_CLASS, TAG_FROM_PARTS, createDispatchReader(FROM_PARTS_CLASS));

      registerReader(ACTIVITY_INVOKE_CLASS, TAG_TO_PARTS, createDispatchReader(TO_PARTS_CLASS));
      registerReader(ACTIVITY_REPLY_CLASS, TAG_TO_PARTS, createDispatchReader(TO_PARTS_CLASS));

      registerReader(TO_PARTS_CLASS, TAG_TO_PART, createDispatchReader(TO_PART_CLASS));
      registerReader(FROM_PARTS_CLASS, TAG_FROM_PART, createDispatchReader(FROM_PART_CLASS));

      // 'target' and 'source' constructs will be children of the 'targets' and 'sources' constructs, respectively.
      registerReader(TARGETS_CLASS, TAG_TARGET, createDispatchReader(TARGET_CLASS));
      registerReader(SOURCES_CLASS, TAG_SOURCE, createDispatchReader(SOURCE_CLASS));

      registerReader(TARGETS_CLASS, TAG_JOIN_CONDITION, createDispatchReader(JOIN_CONDITION_CLASS));
      registerReader(SOURCE_CLASS, TAG_TRANSITION_CONDITION, createDispatchReader(TRANSITION_CONDITION_CLASS));

      // Get rid of the 1.1 AE messageExchange(s) extension
      unregisterReader(PROCESS_CLASS, new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_MESSAGE_EXCHANGES));
      unregisterReader(ACTIVITY_SCOPE_CLASS, new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_MESSAGE_EXCHANGES));
      unregisterReader(MESSAGE_EXCHANGES_CLASS, new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_MESSAGE_EXCHANGE));
      // And again at the legacy NS
      unregisterReader(PROCESS_CLASS, new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, TAG_MESSAGE_EXCHANGES));
      unregisterReader(ACTIVITY_SCOPE_CLASS, new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, TAG_MESSAGE_EXCHANGES));
      unregisterReader(MESSAGE_EXCHANGES_CLASS, new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, TAG_MESSAGE_EXCHANGE));

      // Add the ws-bpel 2.0 messageExchange mappings
      registerReader(PROCESS_CLASS, TAG_MESSAGE_EXCHANGES, createDispatchReader(MESSAGE_EXCHANGES_CLASS));
      registerReader(ACTIVITY_SCOPE_CLASS, TAG_MESSAGE_EXCHANGES, createDispatchReader(MESSAGE_EXCHANGES_CLASS));
      registerReader(MESSAGE_EXCHANGES_CLASS, TAG_MESSAGE_EXCHANGE, createDispatchReader(MESSAGE_EXCHANGE_CLASS));

      registerReader(ACTIVITY_WAIT_CLASS, TAG_FOR, createDispatchReader(FOR_CLASS));
      registerReader(ACTIVITY_WAIT_CLASS, TAG_UNTIL, createDispatchReader(UNTIL_CLASS));
      registerReader(ON_ALARM_CLASS, TAG_FOR, createDispatchReader(FOR_CLASS));
      registerReader(ON_ALARM_CLASS, TAG_UNTIL, createDispatchReader(UNTIL_CLASS));

      registerReader(ACTIVITY_IF_CLASS, TAG_CONDITION, createDispatchReader(CONDITION_CLASS));
      registerReader(ACTIVITY_IF_CLASS, TAG_ELSEIF, createDispatchReader(ELSEIF_CLASS));
      registerReader(ACTIVITY_IF_CLASS, TAG_ELSE, createDispatchReader(ELSE_CLASS));
      registerReader(ELSEIF_CLASS, TAG_CONDITION, createDispatchReader(CONDITION_CLASS));

      registerReader(ACTIVITY_WHILE_CLASS, TAG_CONDITION, createDispatchReader(CONDITION_CLASS));
      registerReader(ACTIVITY_REPEAT_UNTIL_CLASS, TAG_CONDITION, createDispatchReader(CONDITION_CLASS));

      registerReader(ON_ALARM_CLASS, TAG_REPEAT_EVERY, createDispatchReader(REPEAT_EVERY_CLASS));

      unregisterReader(EVENT_HANDLERS_CLASS, TAG_ON_MESSAGE);
      registerReader(EVENT_HANDLERS_CLASS, TAG_ON_EVENT, createDispatchReader(ON_EVENT_CLASS));

      registerReader(ACTIVITY_SCOPE_CLASS, TAG_TERMINATION_HANDLER, createDispatchReader(TERMINATION_HANDLER_CLASS));

      registerReader(ASSIGN_FROM_CLASS, TAG_LITERAL, createDispatchReader(LITERAL_CLASS));

      // Unregister BPWS entries for foreach elements
      unregisterReader(ACTIVITY_FOREACH_CLASS, new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_FOREACH_STARTCOUNTER));
      unregisterReader(ACTIVITY_FOREACH_CLASS, new QName(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_FOREACH_STARTCOUNTER));
      unregisterReader(ACTIVITY_FOREACH_CLASS, new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_FOREACH_FINALCOUNTER));
      unregisterReader(ACTIVITY_FOREACH_CLASS, new QName(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_FOREACH_FINALCOUNTER));
      unregisterReader(ACTIVITY_FOREACH_CLASS, new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_FOREACH_COMPLETION_CONDITION));
      unregisterReader(ACTIVITY_FOREACH_CLASS, new QName(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_FOREACH_COMPLETION_CONDITION));
      unregisterReader(ACTIVITY_FOREACH_COMPLETION_CONDITION, new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_FOREACH_BRANCHES));
      unregisterReader(ACTIVITY_FOREACH_COMPLETION_CONDITION, new QName(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_FOREACH_BRANCHES));

      // Register BPEL 2.0 entries for foreach elements
      registerReader(ACTIVITY_FOREACH_CLASS, TAG_FOREACH_STARTCOUNTER, createDispatchReader(ACTIVITY_FOREACH_START));
      registerReader(ACTIVITY_FOREACH_CLASS, TAG_FOREACH_FINALCOUNTER, createDispatchReader(ACTIVITY_FOREACH_FINAL));
      registerReader(ACTIVITY_FOREACH_CLASS, TAG_FOREACH_COMPLETION_CONDITION, createDispatchReader(ACTIVITY_FOREACH_COMPLETION_CONDITION));
      registerReader(ACTIVITY_FOREACH_COMPLETION_CONDITION, TAG_FOREACH_BRANCHES, createDispatchReader(ACTIVITY_FOREACH_BRANCHES));
      
      registerReader(ASSIGN_FROM_CLASS, TAG_QUERY, createDispatchReader(AeQueryDef.class));
      registerReader(ASSIGN_TO_CLASS, TAG_QUERY, createDispatchReader(AeQueryDef.class));

      // Process level compensation handler
      unregisterReader(PROCESS_CLASS,  TAG_COMPENSATION_HANDLER);
      
      // Process level compensation handler and termination handling
      registerReader(PROCESS_CLASS, new QName(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ALLOW_PROCESS_COORDINATION, TAG_COMPENSATION_HANDLER), createDispatchReader(COMPENSATION_HANDLER_CLASS));
      registerReader(PROCESS_CLASS, new QName(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ALLOW_PROCESS_COORDINATION, TAG_TERMINATION_HANDLER), createDispatchReader(TERMINATION_HANDLER_CLASS));

      // opaqueFrom activity for bpel 2.0 abstract processes.
      // (note: this reader is registered in this 'bpel executable process' namespace to allow users to load executable processes that may have opaque activities (from AbxProcess->Save-As)
      registerReader(AeAssignCopyDef.class, new QName(IAeBPELConstants.WSBPEL_2_0_ABSTRACT_NAMESPACE_URI, TAG_OPAQUE_FROM), createDispatchReader(AeFromDef.class));
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.registry.AeBPWSDefReaderRegistry#initBpelActivities()
    */
   protected void initBpelActivities()
   {
      super.initBpelActivities();

      // Remove some 1.1 mappings.
      removeActivityReader(TAG_TERMINATE);
      removeActivityReader(TAG_COMPENSATE);
      removeActivityReader(new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_FOREACH));

      removeActivityReader(new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_CONTINUE));
      removeActivityReader(new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_BREAK));
      removeActivityReader(new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_FOREACH));
      removeActivityReader(new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_SUSPEND));

      addActivityReader(new QName(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_CONTINUE), AeActivityContinueDef.class);
      addActivityReader(new QName(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_BREAK), AeActivityBreakDef.class);
      addActivityReader(new QName(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_FOREACH), AeActivityForEachDef.class);
      addActivityReader(new QName(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_SUSPEND), AeActivitySuspendDef.class);

      // Add some 2.0 mappings.
      addActivityReader(TAG_VALIDATE, ACTIVITY_VALIDATE_CLASS);
      addActivityReader(TAG_EXIT, ACTIVITY_EXIT_CLASS);
      addActivityReader(TAG_EXTENSION_ACTIVITY, EXTENSION_ACTIVITY_CLASS);
      addActivityReader(TAG_IF, ACTIVITY_IF_CLASS);
      addActivityReader(TAG_REPEAT_UNTIL, ACTIVITY_REPEAT_UNTIL_CLASS);
      addActivityReader(TAG_RETHROW, ACTIVITY_RETHROW_CLASS);
      addActivityReader(TAG_FOREACH, ACTIVITY_FOREACH_CLASS);
      addActivityReader(TAG_COMPENSATE, ACTIVITY_COMPENSATE_CLASS);
      addActivityReader(TAG_COMPENSATE_SCOPE, ACTIVITY_COMPENSATE_SCOPE_CLASS);
      
      // Opaque activity for bpel 2.0 abstract processes.
      // (note: this reader is registered in this 'bpel executable process' namespace to allow users to load executable processes that may have opaque activities (from AbxProcess->Save-As)      
      addActivityReader(new QName(IAeBPELConstants.WSBPEL_2_0_ABSTRACT_NAMESPACE_URI, TAG_OPAQUE_ACTIVITY), AeActivityOpaqueDef.class);
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.registry.AeBPWSDefReaderRegistry#initCommonActivityChildrenElements()
    */
   protected void initCommonActivityChildrenElements()
   {
      super.initCommonActivityChildrenElements();

      // Remove the Target and Source mappings (there are now wrappers for them).
      getCommonActivityChildrenReaders().remove(makeDefaultQName(TAG_TARGET));
      getCommonActivityChildrenReaders().remove(makeDefaultQName(TAG_SOURCE));

      // Now add the 'targets' and 'sources' mappings.
      getCommonActivityChildrenReaders().put(makeDefaultQName(TAG_TARGETS),
            createDispatchReader(TARGETS_CLASS));
      getCommonActivityChildrenReaders().put(makeDefaultQName(TAG_SOURCES),
            createDispatchReader(SOURCES_CLASS));
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.registry.AeBPWSDefReaderRegistry#initGenericElementRegistry()
    */
   protected void initGenericElementRegistry()
   {
      super.initGenericElementRegistry();

      getGenericReadersMap().put(makeDefaultQName(TAG_DOCUMENTATION), createDispatchReader(DOCUMENTATION_CLASS));
   }

   /**
    * Populates the extension activity registry with entries.
    */
   protected void initExtensionActivityRegistry()
   {
      super.initExtensionActivityRegistry();

      setNotUnderstoodActivityReader(createDispatchReader(AeNotUnderstoodExtensionActivityDef.class));
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.registry.AeBPWSDefReaderRegistry#getReader(org.activebpel.rt.bpel.def.AeBaseDef, javax.xml.namespace.QName)
    */
   public IAeBpelDefReader getReader(AeBaseDef aParentDef, QName aElementQName) throws UnsupportedOperationException
   {
      IAeBpelDefReader reader = super.getReader(aParentDef, aElementQName);
      
      if (reader == null && aParentDef instanceof AeExtensionActivityDef)
      {
         reader = getNotUnderstoodActivityReader();
      }
      
      return reader;
   }
   
   /**
    * Creates the reader def visitor factory that the dispatch reader will use.
    */
   protected IAeReaderDefVisitorFactory createReaderVisitorFactory()
   {
      return new IAeReaderDefVisitorFactory()
      {
         /**
          * @see org.activebpel.rt.bpel.def.io.readers.def.IAeReaderDefVisitorFactory#createReaderDefVisitor(org.activebpel.rt.bpel.def.AeBaseDef, org.activebpel.rt.bpel.def.AeBaseDef, org.w3c.dom.Element)
          */
         public IAeReaderDefVisitor createReaderDefVisitor(AeBaseDef aParentDef, AeBaseDef aNewDef, Element aElement)
         {
            return new AeWSBPELReaderVisitor(aParentDef, aElement);
         }
      };
   }

   /**
    * Overrides the base impl in order to return the WSBPEL 2.0 namespace.
    *
    * @see org.activebpel.rt.bpel.def.io.registry.AeBPWSDefReaderRegistry#getBPELNamespace()
    */
   protected String getBPELNamespace()
   {
      return IAeBPELConstants.WSBPEL_2_0_NAMESPACE_URI;
   }

   /**
    * @return Returns the unknownActivityReader.
    */
   public IAeBpelDefReader getNotUnderstoodActivityReader()
   {
      return mNotUnderstoodActivityReader;
   }

   /**
    * @param aUnknownActivityReader The unknownActivityReader to set.
    */
   public void setNotUnderstoodActivityReader(IAeBpelDefReader aUnknownActivityReader)
   {
      mNotUnderstoodActivityReader = aUnknownActivityReader;
   }
}
