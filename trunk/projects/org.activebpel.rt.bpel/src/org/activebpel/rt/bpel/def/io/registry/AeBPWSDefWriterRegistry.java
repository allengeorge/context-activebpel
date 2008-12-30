// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/registry/AeBPWSDefWriterRegistry.java,v 1.6 2007/03/03 02:58:1
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
import java.util.Iterator;
import java.util.Map;

import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeExtensionAttributeDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.def.activity.support.AeLiteralDef;
import org.activebpel.rt.bpel.def.activity.support.AeQueryDef;
import org.activebpel.rt.bpel.def.io.AeCommentIO;
import org.activebpel.rt.bpel.def.io.IAeBpelClassConstants;
import org.activebpel.rt.bpel.def.io.IAeBpelLegacyConstants;
import org.activebpel.rt.bpel.def.io.ext.AeExtensionElementDef;
import org.activebpel.rt.bpel.def.io.writers.def.AeBPWSWriterVisitor;
import org.activebpel.rt.bpel.def.io.writers.def.AeDispatchWriter;
import org.activebpel.rt.bpel.def.io.writers.def.IAeBpelDefWriter;
import org.activebpel.rt.bpel.def.io.writers.def.IAeWriterDefVisitor;
import org.activebpel.rt.bpel.def.io.writers.def.IAeWriterDefVisitorFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This impl simply maps the AeDef class object to its associated writer.
 */
public class AeBPWSDefWriterRegistry implements IAeDefWriterRegistry, IAeBPELConstants, IAeBpelClassConstants, IAeBpelLegacyConstants
{
   /** A Skip Writer */
   private static final AeSkipWriter SKIP_WRITER = new AeSkipWriter();

   /** writer registry */
   private Map mRegistry = new HashMap();
   /** The writer def visitor factory. */
   private IAeWriterDefVisitorFactory mWriterVisitorFactory;

   /**
    * Default constructor.
    */
   public AeBPWSDefWriterRegistry()
   {
      init();
   }

   /**
    * Returns the bpel namespace to be used by the dispatch writers.
    */
   protected String getBpelNamespace()
   {
      return IAeBPELConstants.BPWS_NAMESPACE_URI;    // v1.1 specific
   }

   /**
    * Populates the registry with entries.
    */
   protected void init()
   {
      setWriterVisitorFactory(createWriterVisitorFactory());

      registerWriter( PROCESS_CLASS,              TAG_PROCESS );

      registerWriter( ACTIVITY_ASSIGN_CLASS,      TAG_ASSIGN );
      registerWriter( ACTIVITY_COMPENSATE_CLASS,  TAG_COMPENSATE );
      registerWriter( ACTIVITY_COMPENSATE_SCOPE_CLASS,  TAG_COMPENSATE );
      registerWriter( ACTIVITY_EMPTY_CLASS,       TAG_EMPTY );
      registerWriter( ACTIVITY_FLOW_CLASS,        TAG_FLOW );
      registerWriter( ACTIVITY_INVOKE_CLASS,      TAG_INVOKE );
      registerWriter( ACTIVITY_PICK_CLASS,        TAG_PICK );
      registerWriter( ACTIVITY_RECEIVE_CLASS,     TAG_RECEIVE );
      registerWriter( ACTIVITY_REPLY_CLASS,       TAG_REPLY );
      registerWriter( ACTIVITY_SCOPE_CLASS,       TAG_SCOPE );
      registerWriter( ACTIVITY_SEQUENCE_CLASS,    TAG_SEQUENCE );
      registerWriter( ACTIVITY_IF_CLASS,          TAG_SWITCH );
      registerWriter( ACTIVITY_TERMINATE_CLASS,   TAG_TERMINATE );
      registerWriter( ACTIVITY_THROW_CLASS,       TAG_THROW );
      registerWriter( ACTIVITY_WAIT_CLASS,        TAG_WAIT );
      registerWriter( ACTIVITY_WHILE_CLASS,       TAG_WHILE );
      registerWriter( ACTIVITY_CONTINUE_CLASS, createWriter(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_CONTINUE));
      registerWriter( ACTIVITY_BREAK_CLASS,    createWriter(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_BREAK));
      registerWriter( ACTIVITY_FOREACH_CLASS,  createWriter(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_FOREACH));
      registerWriter( ACTIVITY_SUSPEND_CLASS,  createWriter(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_SUSPEND));

      registerWriter( IF_CLASS,                   TAG_CASE );
      registerWriter( ELSEIF_CLASS,               TAG_CASE );
      registerWriter( ELSE_CLASS,                 TAG_OTHERWISE );
      registerWriter( LINK_CLASS,                 TAG_LINK );
      registerWriter( TARGET_CLASS,               TAG_TARGET );
      registerWriter( SOURCE_CLASS,               TAG_SOURCE );
      registerWriter( MESSAGE_EXCHANGES_CLASS, createWriter(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_MESSAGE_EXCHANGES));
      registerWriter( MESSAGE_EXCHANGE_CLASS,  createWriter(IAeBPELConstants.ABX_2_0_NAMESPACE_URI, TAG_MESSAGE_EXCHANGE));
      registerWriter( ACTIVITY_FOREACH_COMPLETION_CONDITION, createWriter(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_FOREACH_COMPLETION_CONDITION));
      registerWriter( ACTIVITY_FOREACH_BRANCHES, createWriter(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_FOREACH_BRANCHES));
      registerWriter( ACTIVITY_FOREACH_START,  createWriter(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_FOREACH_STARTCOUNTER));
      registerWriter( ACTIVITY_FOREACH_FINAL,  createWriter(IAeBPELConstants.AE_EXTENSION_NAMESPACE_URI_ACTIVITY, TAG_FOREACH_FINALCOUNTER));
      registerWriter( ON_ALARM_CLASS,             TAG_ON_ALARM );
      registerWriter( ON_MESSAGE_CLASS,           TAG_ON_MESSAGE );
      registerWriter( ON_EVENT_CLASS,             TAG_ON_MESSAGE );
      registerWriter( ASSIGN_COPY_CLASS,          TAG_COPY );
      registerWriter( CORRELATION_CLASS,          TAG_CORRELATION );
      registerWriter( CATCH_CLASS,                TAG_CATCH );
      registerWriter( CATCH_ALL_CLASS,            TAG_CATCH_ALL );

      registerWriter( ASSIGN_FROM_CLASS,          TAG_FROM );
      registerWriter( ASSIGN_TO_CLASS,            TAG_TO );

      registerWriter( PARTNER_LINK_CLASS,         TAG_PARTNER_LINK );
      registerWriter( PARTNER_CLASS,              TAG_PARTNER );
      registerWriter( VARIABLE_CLASS,             TAG_VARIABLE );
      registerWriter( EVENT_HANDLERS_CLASS,       TAG_EVENT_HANDLERS );
      registerWriter( CORRELATION_SET_CLASS,      TAG_CORRELATION_SET );
      registerWriter( CORRELATIONS_CLASS,         TAG_CORRELATIONS );
      registerWriter( LINKS_CLASS,                TAG_LINKS );
      registerWriter( PARTNERS_CLASS,             TAG_PARTNERS );
      registerWriter( VARIABLES_CLASS,            TAG_VARIABLES );
      registerWriter( CORRELATION_SETS_CLASS,     TAG_CORRELATION_SETS );

      registerWriter( PARNTER_LINKS_CLASS,        TAG_PARTNER_LINKS );
      registerWriter( FAULT_HANDLERS_CLASS,       TAG_FAULT_HANDLERS );
      registerWriter( COMPENSATION_HANDLER_CLASS, TAG_COMPENSATION_HANDLER );

      registerWriter( EXTENSION_ACTIVITY_CLASS,   SKIP_WRITER );
      
      registerWriter( SOURCES_CLASS,              SKIP_WRITER );
      registerWriter( TARGETS_CLASS,              SKIP_WRITER );

      registerWriter( JOIN_CONDITION_CLASS,       SKIP_WRITER );
      registerWriter( TRANSITION_CONDITION_CLASS, SKIP_WRITER );
      registerWriter( FOR_CLASS,                  SKIP_WRITER );
      registerWriter( UNTIL_CLASS,                SKIP_WRITER );

      registerWriter( CONDITION_CLASS,            SKIP_WRITER);

      registerWriter( LITERAL_CLASS,              new AeBPWSLiteralWriter());
      registerWriter( AeQueryDef.class,           SKIP_WRITER);
      
      registerWriter( AeExtensionElementDef.class, new AeExtensionElementWriter());
      registerWriter( AeExtensionAttributeDef.class, SKIP_WRITER);
   }

   /**
    * Registers a dispatch writer.
    *
    * @param aClass
    * @param aTagName
    */
   protected void registerWriter(Class aClass, String aTagName)
   {
      registerWriter(aClass, createWriter(aTagName));
   }
   
   /**
    * Registers a bpel def writer.
    * 
    * @param aClass
    * @param aWriter
    */
   protected void registerWriter(Class aClass, IAeBpelDefWriter aWriter)
   {
      getRegistry().put(aClass, aWriter);
   }

   /**
    * Removes (unregisters) a writer from the registry.
    *
    * @param aClass
    */
   protected void unregisterWriter(Class aClass)
   {
      getRegistry().remove(aClass);
   }

   /**
    * Creates a dispatch writer with the given tag name (in the bpel namespace).
    *
    * @param aTagName
    */
   protected AeDispatchWriter createWriter(String aTagName)
   {
      return createWriter(getBpelNamespace(), aTagName);
   }

   /**
    * Creates a dispatch writer with the given tag name (in the given namespace).
    *
    * @param aNamespace
    * @param aTagName
    */
   protected AeDispatchWriter createWriter(String aNamespace, String aTagName)
   {
      return new AeDispatchWriter(aNamespace, aTagName, getWriterVisitorFactory());
   }

   /**
    * Gets the writer visitor factory.
    */
   protected IAeWriterDefVisitorFactory getWriterVisitorFactory()
   {
      return mWriterVisitorFactory;
   }

   /**
    * Creates the writer def factory that the dispatch writer will use to create visitors
    * to dispatch to.
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
            return new AeBPWSWriterVisitor(aDef, aParentElement, aNamespaceUri, aTagName);
         }
      };
   }

   /**
    * Retrieve the writer class for the AeDef object.  In this
    * impl, the parent class is ignored as no special mappings
    * were required.
    * @param aParentClass ignored
    * @param aDef the base def object to be serialized
    * @return the appropriate writer
    */
   public IAeBpelDefWriter getWriter(Class aParentClass, AeBaseDef aDef)
   {
      IAeBpelDefWriter writer = (IAeBpelDefWriter)mRegistry.get(aDef.getClass());
      if( writer == null )
      {
         throw new UnsupportedOperationException(AeMessages.format("AeDefWriterRegistry.ERROR_0",  //$NON-NLS-1$
               aDef.getClass().getName() ));
      }
      return writer;
   }

   /**
    * @return Returns the registry.
    */
   protected Map getRegistry()
   {
      return mRegistry;
   }

   /**
    * @param aRegistry The registry to set.
    */
   protected void setRegistry(Map aRegistry)
   {
      mRegistry = aRegistry;
   }

   /**
    * @param aWriterVisitorFactory The writerVisitorFactory to set.
    */
   protected void setWriterVisitorFactory(IAeWriterDefVisitorFactory aWriterVisitorFactory)
   {
      mWriterVisitorFactory = aWriterVisitorFactory;
   }

   /**
    * A bpel def writer that will skip the creation of an element and instead return the parent
    * element.  This is useful for writing 1.1 bpel because the AE Def model includes some Defs
    * for constructs that aren't really there in the 1.1 bpel source.
    */
   protected static class AeSkipWriter implements IAeBpelDefWriter
   {
      /**
       * Default c'tor.
       */
      public AeSkipWriter()
      {
         super();
      }

      /**
       * @see org.activebpel.rt.bpel.def.io.writers.def.IAeBpelDefWriter#createElement(org.activebpel.rt.bpel.def.AeBaseDef, org.w3c.dom.Element)
       */
      public Element createElement(AeBaseDef aBaseDef, Element aParentElement)
      {
         return aParentElement;
      }
   }

   /**
    * A special writer that is able to write out the literal for a bpel4ws 1.1 def.
    */
   protected class AeBPWSLiteralWriter implements IAeBpelDefWriter
   {
      /**
       * @see org.activebpel.rt.bpel.def.io.writers.def.IAeBpelDefWriter#createElement(org.activebpel.rt.bpel.def.AeBaseDef, org.w3c.dom.Element)
       */
      public Element createElement(AeBaseDef aBaseDef, Element aParentElement)
      {
         AeLiteralDef def = (AeLiteralDef) aBaseDef;
         for (Iterator iter = def.getChildNodes().iterator(); iter.hasNext(); )
         {
            Node node = (Node) iter.next();
            
            Node importedNode = aParentElement.getOwnerDocument().importNode(node, true);
            aParentElement.appendChild(importedNode);
         }

         return aParentElement;
      }
   }
   
   /**
    * Writes an extension element.
    */
   protected class AeExtensionElementWriter implements IAeBpelDefWriter
   {
      /**
       * @see org.activebpel.rt.bpel.def.io.writers.def.IAeBpelDefWriter#createElement(org.activebpel.rt.bpel.def.AeBaseDef, org.w3c.dom.Element)
       */
      public Element createElement(AeBaseDef aBaseDef, Element aParentElement)
      {
         AeExtensionElementDef extensionElementDef = (AeExtensionElementDef) aBaseDef;
         Document bpelDoc = aParentElement.getOwnerDocument();
         // copy the existing node set over
         Element extNode = (Element) bpelDoc.importNode(extensionElementDef.getExtensionElement(), true);
         aParentElement.appendChild(extNode);
         AeCommentIO.writeFormattedComments(extNode, extensionElementDef.getComments());
         return extNode;
      }
   }
}
