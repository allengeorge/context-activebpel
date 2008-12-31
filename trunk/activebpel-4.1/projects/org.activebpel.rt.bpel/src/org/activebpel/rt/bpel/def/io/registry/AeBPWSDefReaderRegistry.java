// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/registry/AeBPWSDefReaderRegistry.java,v 1.3 2006/10/12 20:15:2
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

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.def.AeActivityDef;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeMessageExchangeDef;
import org.activebpel.rt.bpel.def.AeMessageExchangesDef;
import org.activebpel.rt.bpel.def.IAeActivityContainerDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.activity.AeActivityIfDef;
import org.activebpel.rt.bpel.def.activity.support.AeForEachBranchesDef;
import org.activebpel.rt.bpel.def.activity.support.AeForEachCompletionConditionDef;
import org.activebpel.rt.bpel.def.io.IAeBpelClassConstants;
import org.activebpel.rt.bpel.def.io.IAeBpelLegacyConstants;
import org.activebpel.rt.bpel.def.io.ext.AeExtensionElementDef;
import org.activebpel.rt.bpel.def.io.readers.def.AeBPWSReaderVisitor;
import org.activebpel.rt.bpel.def.io.readers.def.AeDispatchReader;
import org.activebpel.rt.bpel.def.io.readers.def.IAeBpelDefReader;
import org.activebpel.rt.bpel.def.io.readers.def.IAeReaderDefVisitor;
import org.activebpel.rt.bpel.def.io.readers.def.IAeReaderDefVisitorFactory;
import org.w3c.dom.Element;

/**
 * BPEL4WS registry for mapping BPEL elements to the
 * appropriate deserializer.
 * <br />
 * All readers, except activities and the standard
 * activity elements (target/source) are mapped to 
 * reader instances by parent def object and
 * QName.  This allows potential for registering
 * different readers for the same QName based on
 * parenting.
 * <br />
 * In this impl activities and standard elements
 * are mapped directly via QName, however this behavior
 * can be overridden by installing a custom reader 
 * (mapped via the parent class to QName to reader)
 * in the main registry (specifically by overriding
 * the <code>initMainRegistry</code> method).
 */
public class AeBPWSDefReaderRegistry implements IAeDefReaderRegistry, IAeBPELConstants,
      IAeBpelClassConstants, IAeBpelLegacyConstants
{
   /** The reader def visitor factory that the dispatch reader will use to create visitors. */
   private IAeReaderDefVisitorFactory mReaderDefVisitorFactory;
   /** The process reader. */
   private IAeBpelDefReader mProcessReader;
   /** top level map for well known BPEL elements */
   private Map mRegistryMap = new HashMap();
   /** holds the standard element readers for activities (target/source) */
   private Map mCommonActivityChildrenReadersMap = new HashMap();
   /** map of activity QNames to readers */
   private Map mActivityReadersMap = new HashMap();
   /** map of QNames to readers */
   private Map mGenericReadersMap = new HashMap();
   /** A reader that can read in an extension element. */
   private IAeBpelDefReader mExtensionElementReader;

   /**
    * Default constructor.
    */
   public AeBPWSDefReaderRegistry()
   {
      init();
   }
   
   /*
    * Initializes the registry maps.
    */
   protected void init()
   {
      setReaderDefVisitorFactory(createReaderVisitorFactory());

      initMainRegistry();
      initBpelActivities();
      initCommonActivityChildrenElements();
      initGenericElementRegistry();
      initExtensionActivityRegistry();
   }

   /**
    * Maps the parent AeDef class to its child readers based
    * on the child element QNames.
    */
   protected void initMainRegistry()
   {
      setProcessReader(createProcessReader());

      // maps readers for children of process element
      // exclusive of activity (which is handled sep)
      registerReader(PROCESS_CLASS,  TAG_VARIABLES,            createDispatchReader(VARIABLES_CLASS));
      registerReader(PROCESS_CLASS,  TAG_FAULT_HANDLERS,       createDispatchReader(FAULT_HANDLERS_CLASS));
      registerReader(PROCESS_CLASS,  TAG_EVENT_HANDLERS,       createDispatchReader(EVENT_HANDLERS_CLASS));
      registerReader(PROCESS_CLASS,  TAG_PARTNER_LINKS,        createDispatchReader(PARTNER_LINKS_CLASS));
      registerReader(PROCESS_CLASS,  TAG_PARTNERS,             createDispatchReader(PARTNERS_CLASS));
      registerReader(PROCESS_CLASS,  TAG_COMPENSATION_HANDLER, createDispatchReader(COMPENSATION_HANDLER_CLASS));
      registerReader(PROCESS_CLASS,  TAG_CORRELATION_SETS,     createDispatchReader(CORRELATION_SETS_CLASS));

      registerReader(ACTIVITY_SCOPE_CLASS,  TAG_PARTNER_LINKS,createDispatchReader(PARTNER_LINKS_CLASS));

      // map readers for children of copy element
      registerReader(ACTIVITY_ASSIGN_CLASS, TAG_COPY,  createDispatchReader(ASSIGN_COPY_CLASS));
      registerReader(ASSIGN_COPY_CLASS, TAG_TO,        createDispatchReader(ASSIGN_TO_CLASS));
      registerReader(ASSIGN_COPY_CLASS, TAG_FROM,      createDispatchReader(ASSIGN_FROM_CLASS));

      // maps readers for children of switch activity
      // exclusive of standard readers (source/target)
      registerReader(ACTIVITY_IF_CLASS, TAG_CASE,      new AeSwitchCaseReader());
      registerReader(ACTIVITY_IF_CLASS, TAG_OTHERWISE, createDispatchReader(ELSE_CLASS));
      
      // maps readers for children of pick activity
      // exclusive of standard readers (source/target)
      registerReader(ACTIVITY_PICK_CLASS, TAG_ON_MESSAGE, createDispatchReader(ON_MESSAGE_CLASS));
      registerReader(ACTIVITY_PICK_CLASS, TAG_ON_ALARM,   createDispatchReader(ON_ALARM_CLASS));

      // maps readers for children of scope activity
      // exclusive of standard readers (source/target)
      registerReader(ACTIVITY_SCOPE_CLASS, TAG_COMPENSATION_HANDLER, createDispatchReader(COMPENSATION_HANDLER_CLASS));
      registerReader(ACTIVITY_SCOPE_CLASS, TAG_FAULT_HANDLERS,       createDispatchReader(FAULT_HANDLERS_CLASS));
      registerReader(ACTIVITY_SCOPE_CLASS, TAG_VARIABLES,            createDispatchReader(VARIABLES_CLASS));
      registerReader(ACTIVITY_SCOPE_CLASS, TAG_CORRELATION_SETS,     createDispatchReader(CORRELATION_SETS_CLASS));
      registerReader(ACTIVITY_SCOPE_CLASS, TAG_EVENT_HANDLERS,       createDispatchReader(EVENT_HANDLERS_CLASS));

      // maps readers for children of invoke activity
      // exclusive of standard readers (source/target)
      registerReader(ACTIVITY_INVOKE_CLASS, TAG_CATCH,                createDispatchReader(CATCH_CLASS));
      registerReader(ACTIVITY_INVOKE_CLASS, TAG_CATCH_ALL,            createDispatchReader(CATCH_ALL_CLASS));
      registerReader(ACTIVITY_INVOKE_CLASS, TAG_COMPENSATION_HANDLER, createDispatchReader(COMPENSATION_HANDLER_CLASS));
      registerReader(ACTIVITY_INVOKE_CLASS, TAG_CORRELATIONS,         createDispatchReader(CORRELATIONS_CLASS));
      
      // maps readers for children of eventHandlers element
      registerReader(EVENT_HANDLERS_CLASS, TAG_ON_MESSAGE, createDispatchReader(ON_EVENT_CLASS)); // treat onMessage as an onEvent in the unified def
      registerReader(EVENT_HANDLERS_CLASS, TAG_ON_ALARM,   createDispatchReader(ON_ALARM_CLASS));

      // maps readers for children of faultHandlers element
      registerReader(FAULT_HANDLERS_CLASS, TAG_CATCH,     createDispatchReader(CATCH_CLASS));
      registerReader(FAULT_HANDLERS_CLASS, TAG_CATCH_ALL, createDispatchReader(CATCH_ALL_CLASS));

      // all of the various class to child element mappings
      registerReader(ACTIVITY_FLOW_CLASS,    TAG_LINKS,               createDispatchReader(LINKS_CLASS));
      registerReader(ACTIVITY_RECEIVE_CLASS, TAG_CORRELATIONS,        createDispatchReader(CORRELATIONS_CLASS));
      registerReader(ACTIVITY_REPLY_CLASS,   TAG_CORRELATIONS,        createDispatchReader(CORRELATIONS_CLASS));

      registerReader(PARTNERS_CLASS,         TAG_PARTNER,             createDispatchReader(PARTNER_CLASS));
      registerReader(PARNTER_LINKS_CLASS,    TAG_PARTNER_LINK,        createDispatchReader(PARTNER_LINK_CLASS));
      registerReader(PARTNER_CLASS,          TAG_PARTNER_LINK,        createDispatchReader(PARTNER_LINK_CLASS));
      registerReader(VARIABLES_CLASS,        TAG_VARIABLE,            createDispatchReader(VARIABLE_CLASS));

      registerReader(LINKS_CLASS,            TAG_LINK,                createDispatchReader(LINK_CLASS));
      registerReader(ON_MESSAGE_CLASS,       TAG_CORRELATIONS,        createDispatchReader(CORRELATIONS_CLASS));
      registerReader(ON_EVENT_CLASS,         TAG_CORRELATIONS,        createDispatchReader(CORRELATIONS_CLASS));

      registerReader(CORRELATIONS_CLASS,     TAG_CORRELATION,         createDispatchReader(CORRELATION_CLASS));
      registerReader(CORRELATION_SETS_CLASS, TAG_CORRELATION_SET,     createDispatchReader(CORRELATION_SET_CLASS));
      
      // forEach extensions
      registerReader(ACTIVITY_FOREACH_CLASS, new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_FOREACH_STARTCOUNTER), createDispatchReader(ACTIVITY_FOREACH_START));
      registerReader(ACTIVITY_FOREACH_CLASS, new QName(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_FOREACH_STARTCOUNTER), createDispatchReader(ACTIVITY_FOREACH_START));
      registerReader(ACTIVITY_FOREACH_CLASS, new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_FOREACH_FINALCOUNTER), createDispatchReader(ACTIVITY_FOREACH_FINAL));
      registerReader(ACTIVITY_FOREACH_CLASS, new QName(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_FOREACH_FINALCOUNTER), createDispatchReader(ACTIVITY_FOREACH_FINAL));
      registerReader(ACTIVITY_FOREACH_CLASS, new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_FOREACH_COMPLETION_CONDITION), createDispatchReader(AeForEachCompletionConditionDef.class));
      registerReader(ACTIVITY_FOREACH_CLASS, new QName(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_FOREACH_COMPLETION_CONDITION), createDispatchReader(AeForEachCompletionConditionDef.class));
      registerReader(ACTIVITY_FOREACH_COMPLETION_CONDITION, new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_FOREACH_BRANCHES), createDispatchReader(AeForEachBranchesDef.class));
      registerReader(ACTIVITY_FOREACH_COMPLETION_CONDITION, new QName(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_FOREACH_BRANCHES), createDispatchReader(AeForEachBranchesDef.class));

      // messageExchange extension
      registerReader(PROCESS_CLASS,           new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_MESSAGE_EXCHANGES), createDispatchReader(AeMessageExchangesDef.class));
      registerReader(ACTIVITY_SCOPE_CLASS,    new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_MESSAGE_EXCHANGES), createDispatchReader(AeMessageExchangesDef.class));
      registerReader(MESSAGE_EXCHANGES_CLASS, new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_MESSAGE_EXCHANGE), createDispatchReader(AeMessageExchangeDef.class));

      // legacy registration for the 2.0 beta that went out w/o a version qualifier in the namespace
      registerReader(PROCESS_CLASS,           new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, TAG_MESSAGE_EXCHANGES), createDispatchReader(AeMessageExchangesDef.class));
      registerReader(ACTIVITY_SCOPE_CLASS,    new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, TAG_MESSAGE_EXCHANGES), createDispatchReader(AeMessageExchangesDef.class));
      registerReader(MESSAGE_EXCHANGES_CLASS, new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, TAG_MESSAGE_EXCHANGE), createDispatchReader(AeMessageExchangeDef.class));
      registerReader(ACTIVITY_FOREACH_CLASS,  new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, TAG_FOREACH_STARTCOUNTER), createDispatchReader(ACTIVITY_FOREACH_START));
      registerReader(ACTIVITY_FOREACH_CLASS,  new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, TAG_FOREACH_FINALCOUNTER), createDispatchReader(ACTIVITY_FOREACH_FINAL));
   }

   /**
    * Creates a process reader.
    */
   protected AeDispatchReader createProcessReader()
   {
      return createDispatchReader(PROCESS_CLASS);
   }

   /**
    * Populates the <code>mActivityReadersMap</code> with all of the
    * activity readers. 
    */
   protected void initBpelActivities()
   {
      addActivityReader(TAG_ASSIGN,     ACTIVITY_ASSIGN_CLASS     );
      getActivityReaders().put(makeDefaultQName(TAG_COMPENSATE), new AeCompensateActivityReader());
      addActivityReader(TAG_EMPTY,      ACTIVITY_EMPTY_CLASS      );
      addActivityReader(TAG_FLOW,       ACTIVITY_FLOW_CLASS       );
      addActivityReader(TAG_INVOKE,     ACTIVITY_INVOKE_CLASS     );
      addActivityReader(TAG_PICK,       ACTIVITY_PICK_CLASS       );
      addActivityReader(TAG_RECEIVE,    ACTIVITY_RECEIVE_CLASS    );
      addActivityReader(TAG_REPLY,      ACTIVITY_REPLY_CLASS      );
      addActivityReader(TAG_SCOPE,      ACTIVITY_SCOPE_CLASS      );
      addActivityReader(TAG_SEQUENCE,   ACTIVITY_SEQUENCE_CLASS   );
      addActivityReader(TAG_SWITCH,     ACTIVITY_IF_CLASS         );
      addActivityReader(TAG_TERMINATE,  ACTIVITY_TERMINATE_CLASS  );
      addActivityReader(TAG_THROW,      ACTIVITY_THROW_CLASS      );
      addActivityReader(TAG_WAIT,       ACTIVITY_WAIT_CLASS       );
      addActivityReader(TAG_WHILE,      ACTIVITY_WHILE_CLASS      );

      // Our custom BPEL extensions
      addActivityReader(new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_CONTINUE), ACTIVITY_CONTINUE_CLASS);
      addActivityReader(new QName(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_CONTINUE), ACTIVITY_CONTINUE_CLASS);
      addActivityReader(new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_BREAK), ACTIVITY_BREAK_CLASS);
      addActivityReader(new QName(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_BREAK), ACTIVITY_BREAK_CLASS);
      addActivityReader(new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_FOREACH), ACTIVITY_FOREACH_CLASS);
      addActivityReader(new QName(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_FOREACH), ACTIVITY_FOREACH_CLASS);
      addActivityReader(new QName(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_SUSPEND), ACTIVITY_SUSPEND_CLASS);
      addActivityReader(new QName(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_SUSPEND), ACTIVITY_SUSPEND_CLASS);
      
      // legacy registration for the 2.0 beta that went out w/o a version qualifier in the namespace
      addActivityReader(new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, TAG_CONTINUE), ACTIVITY_CONTINUE_CLASS);
      addActivityReader(new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, TAG_BREAK), ACTIVITY_BREAK_CLASS);
      addActivityReader(new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, TAG_FOREACH), ACTIVITY_FOREACH_CLASS);
      addActivityReader(new QName(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI, TAG_SUSPEND), ACTIVITY_SUSPEND_CLASS);
   }
   
   /**
    * Populates the <code>mStandardReadersMap</code> with the
    * target and source element readers.
    */
   protected void initCommonActivityChildrenElements()
   {
      getCommonActivityChildrenReaders().put(makeDefaultQName(TAG_TARGET), createDispatchReader(TARGET_CLASS));
      getCommonActivityChildrenReaders().put(makeDefaultQName(TAG_SOURCE), createDispatchReader(SOURCE_CLASS));
   }

   /**
    * Populates the mGenericReadersMap with any generic readers.
    */
   protected void initGenericElementRegistry()
   {
   }

   /**
    * Populates the extension activity registry with entries.
    */
   protected void initExtensionActivityRegistry()
   {
      setExtensionElementReader(createExtensionElementReader());
   }

   /**
    * Creates a dispatch reader for the given def class.
    * 
    * @param aClass
    */
   protected AeDispatchReader createDispatchReader(Class aClass)
   {
      return new AeDispatchReader(aClass, getReaderDefVisitorFactory());
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
            return new AeBPWSReaderVisitor(aParentDef, aElement);
         }
      };
   }
   
   /**
    * Convenience method for adding readers to the <code>mRegistryMap</code>.
    * <br />
    * <code>mRegistryMap</code> is a map of maps where the inner map contains
    * the QName to IAeBpelDefReader mappings.  The top level map contains the
    * AeDef class to inner map mappings.
    * 
    * @param aClass the parent AeDef class
    * @param aElementName child BPEL element for this parent
    * @param aReader IAeBpelDefReader impl
    */
   protected void registerReader( Class aClass, String aElementName, IAeBpelDefReader aReader )
   {
      registerReader(aClass, makeDefaultQName(aElementName), aReader);
   }
   
   /**
    * Adds the readers to the map with the specified qname, allowing for our extensions
    * not assuming that they're in the bpel namespace.
    * @param aClass
    * @param aElementQName
    * @param aReader
    */
   protected void registerReader( Class aClass, QName aElementQName, IAeBpelDefReader aReader )
   {
      Map innerMap = getOrCreateInnerRegistryMap(aClass);
      innerMap.put( aElementQName, aReader );
   }

   /**
    * Unregister a reader from the main registry.
    * 
    * @param aClass
    * @param aElementName
    */
   protected void unregisterReader(Class aClass, String aElementName)
   {
      unregisterReader(aClass, makeDefaultQName(aElementName));
   }
   
   /**
    * Unregisters a reader from the main registry.
    * 
    * @param aClass
    * @param aElementQName
    */
   protected void unregisterReader(Class aClass, QName aElementQName)
   {
      Map innerMap = getOrCreateInnerRegistryMap(aClass);
      innerMap.remove(aElementQName);
   }

   /**
    * Gets the registry's inner map for the given Class.  If the map does not already
    * exist, this method will create and add it.
    * 
    * @param aClass
    */
   private Map getOrCreateInnerRegistryMap(Class aClass)
   {
      Map innerMap = (Map) getRegistry().get(aClass);
      if (innerMap == null)
      {
         innerMap = new HashMap();
         getRegistry().put(aClass, innerMap);
      }
      return innerMap;
   }

   /**
    * Convenience method for registering activity readers.
    * @param aElementName
    * @param aClass
    */
   protected void addActivityReader( String aElementName, Class aClass  )
   {
      QName qname = makeDefaultQName(aElementName);
      addActivityReader(qname, aClass);
   }
   
   /**
    * Overloaded to accept QName for the element and the class
    * @param aQName
    * @param aClass
    */
   protected void addActivityReader(QName aQName, Class aClass)
   {
      getActivityReaders().put(aQName, createDispatchReader(aClass));
   }

   /**
    * Removes an activity reader from the list of activity readers.
    * 
    * @param aElementName
    */
   protected void removeActivityReader(String aElementName)
   {
      QName qname = makeDefaultQName(aElementName);
      removeActivityReader(qname);
   }
   
   /**
    * Removes an activity reader from the list of activity readers.
    * 
    * @param aQName
    */
   protected void removeActivityReader(QName aQName)
   {
      getActivityReaders().remove(aQName);
   }

   /**
    * Return the appropriate IAeBpelDefReader impl for this
    * parent def and QName mapping.
    * @param aParentDef parent AeDef in the object model
    * @param aElementQName the child element QName
    * @return IAeBpelReader impl for deserializing this element or null if not found
    */
   public IAeBpelDefReader getReader(AeBaseDef aParentDef, QName aElementQName)
         throws UnsupportedOperationException
   {
      if (aParentDef == null)
         return getProcessReader();
      
      // look for any specific reader mappings
      IAeBpelDefReader reader = null;
      Map childReadersMap = (Map)getRegistry().get(aParentDef.getClass());
      
      if( childReadersMap != null )
      {
         reader = (IAeBpelDefReader)childReadersMap.get(aElementQName);
      }
      
      // if a reader was installed, return it
      // otherwise continue looking
      if( reader != null )
      {
         return reader;
      }


      // if my parent def class is an activity,
      // check for standard elements first (target/source)
      if( aParentDef instanceof AeActivityDef )
      {
         IAeBpelDefReader standardReader = 
                 (IAeBpelDefReader)getCommonActivityChildrenReaders().get( aElementQName );

         if( standardReader != null )
         {
            return standardReader;
         }
      }

      // if my parent def class is an activity container
      // look for my standard activities
      if(aParentDef instanceof IAeActivityContainerDef) 
      {
         IAeBpelDefReader activityReader = 
                 (IAeBpelDefReader)getActivityReaders().get( aElementQName );

         if( activityReader != null )
         {
            return activityReader;
         }
      }
      
      // look in the generic QName -> reader map
      return (IAeBpelDefReader) getGenericReadersMap().get(aElementQName);
   }

   /**
    * @return Returns the bPELNamespace.
    */
   protected String getBPELNamespace()
   {
      return IAeBPELConstants.BPWS_NAMESPACE_URI;  // v1.1 specific
   }

   /**
    * Convenience method for QName creation based on default 
    * BPEL namespace.
    * 
    * @see org.activebpel.rt.bpel.def.IAeBPELConstants#BPWS_NAMESPACE_URI
    * @param aElementName
    * @return QName
    */
   protected QName makeDefaultQName( String aElementName )
   {
      return new QName( getBPELNamespace(), aElementName );
   }

   /**
    * Accessor for bpel registry map.
    * @return registry map
    */
   protected Map getRegistry()
   {
      return mRegistryMap;
   }

   /**
    * Accessor for the common activity children (like source/target)
    * @return map for target and source readers
    */
   protected Map getCommonActivityChildrenReaders()
   {
      return mCommonActivityChildrenReadersMap;
   }
   
   /**
    * Accessor for activity readers map.
    * @return map of QNames to activities
    */
   protected Map getActivityReaders()
   {
      return mActivityReadersMap;
   }

   /**
    * @return Returns the genericReadersMap.
    */
   protected Map getGenericReadersMap()
   {
      return mGenericReadersMap;
   }

   /**
    * @param aGenericReadersMap The genericReadersMap to set.
    */
   protected void setGenericReadersMap(Map aGenericReadersMap)
   {
      mGenericReadersMap = aGenericReadersMap;
   }

   /**
    * @return Returns the processReader.
    */
   protected IAeBpelDefReader getProcessReader()
   {
      return mProcessReader;
   }

   /**
    * @param aProcessReader The processReader to set.
    */
   protected void setProcessReader(IAeBpelDefReader aProcessReader)
   {
      mProcessReader = aProcessReader;
   }

   /**
    * @return Returns the readerDefVisitorFactory.
    */
   protected IAeReaderDefVisitorFactory getReaderDefVisitorFactory()
   {
      return mReaderDefVisitorFactory;
   }

   /**
    * @param aReaderDefVisitorFactory The readerDefVisitorFactory to set.
    */
   protected void setReaderDefVisitorFactory(IAeReaderDefVisitorFactory aReaderDefVisitorFactory)
   {
      mReaderDefVisitorFactory = aReaderDefVisitorFactory;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.registry.IAeDefReaderRegistry#getExtensionReader()
    */
   public IAeBpelDefReader getExtensionReader()
   {
      return getExtensionElementReader();
   }

   /**
    * @return Returns the extensionElementReader.
    */
   protected IAeBpelDefReader getExtensionElementReader()
   {
      return mExtensionElementReader;
   }

   /**
    * @param aExtensionElementReader The extensionElementReader to set.
    */
   protected void setExtensionElementReader(IAeBpelDefReader aExtensionElementReader)
   {
      mExtensionElementReader = aExtensionElementReader;
   }
   
   /**
    * Creates the extension element reader.
    */
   protected IAeBpelDefReader createExtensionElementReader()
   {
      return new AeExtensionElementReader();
   }
   
   /**
    * A specialty reader for reading in the 'case' child of a switch activity.  This is needed
    * because we create a different def for the first case in the list.
    */
   protected class AeSwitchCaseReader extends AeDispatchReader
   {
      /**
       * Default c'tor.
       */
      public AeSwitchCaseReader()
      {
         super(null, getReaderDefVisitorFactory());
      }

      /**
       * @see org.activebpel.rt.bpel.def.io.readers.def.AeDispatchReader#createChild(org.activebpel.rt.bpel.def.AeBaseDef, Element)
       */
      protected AeBaseDef createChild(AeBaseDef aParent, Element aElement) throws AeBusinessProcessException
      {
         // If this is the first case child - make it the 'if'.  Otherwise go with an 'elseif'.
         AeActivityIfDef ifActivityDef = (AeActivityIfDef) aParent;
         if (ifActivityDef.getIfDef() == null)
         {
            setChildClass(IF_CLASS);
         }
         else
         {
            setChildClass(ELSEIF_CLASS);
         }
         return super.createChild(aParent, aElement);
      }
   }
   
   /**
    * Special reader for the compensate activity.  If the compensate activity has a 'scope' attribute,
    * then we will model it using the BPEL 2.0 compensateScope def.
    */
   protected class AeCompensateActivityReader extends AeDispatchReader
   {
      /**
       * Default constructor.
       */
      public AeCompensateActivityReader()
      {
         super(null, getReaderDefVisitorFactory());
      }
      
      /**
       * @see org.activebpel.rt.bpel.def.io.readers.def.AeDispatchReader#createChild(org.activebpel.rt.bpel.def.AeBaseDef, Element)
       */
      protected AeBaseDef createChild(AeBaseDef aParent, Element aElement) throws AeBusinessProcessException
      {
         // If the compensate activity has a 'scope' attribute, then model it as a compensateScope
         // def...otherwise use the compensate def.
         if (aElement.hasAttribute(TAG_SCOPE))
         {
            setChildClass(ACTIVITY_COMPENSATE_SCOPE_CLASS);
         }
         else
         {
            setChildClass(ACTIVITY_COMPENSATE_CLASS);
         }

         return super.createChild(aParent, aElement);
      }
   }
   
   /**
    * Special reader for reading in extension elements.
    */
   protected class AeExtensionElementReader implements IAeBpelDefReader
   {
      /**
       * @see org.activebpel.rt.bpel.def.io.readers.def.IAeBpelDefReader#read(org.activebpel.rt.bpel.def.AeBaseDef, org.w3c.dom.Element)
       */
      public AeBaseDef read(AeBaseDef aParent, Element aElement) throws AeBusinessProcessException
      {
         AeExtensionElementDef extElemDef = new AeExtensionElementDef();
         QName qName = new QName(aElement.getNamespaceURI(), aElement.getLocalName());
         extElemDef.setElementQName(qName);
         extElemDef.setExtensionElement(aElement);
         aParent.addExtensionElementDef(extElemDef);
         return extElemDef;
      }
   }
}
