// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/AeBpelIO.java,v 1.7 2006/11/04 16:34:2
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
package org.activebpel.rt.bpel.def.io;

import javax.xml.parsers.DocumentBuilder;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Wraps the basic serialization/deserialization calls 
 * currently in use. 
 */
public class AeBpelIO
{
   /** IAeBpelFactory impl class for bpel 1.1. */
   private static IAeBpelFactory mBPEL4WSFactory = new AeBPWSFactory();
   /** IAeBpelFactory impl class for bpel 2.0. */
   private static IAeBpelFactory mWSBPEL20Factory = new AeWSBPELFactory();
   /** IAeBpelFactory impl class for bpel 2.x abstract processes. */
   private static IAeBpelFactory mWSBPEL20AbstractProcessFactory = new AeWSBPELAbstractProcessFactory();

   /**
    * private x-tor - no instantiation 
    */
   private AeBpelIO()
   {
   }
   
   /**
    * Deserialize inputstream to AeProcessDef.
    * @param aInputSource input source that contains the bpel xml
    * @return AeProcessDef object model
    * @throws AeBusinessProcessException
    */
   public static AeProcessDef deserialize( InputSource aInputSource )
   throws AeBusinessProcessException
   {
      try
      {
         DocumentBuilder builder = AeXmlUtil.getDocumentBuilder(); 
         Document doc = builder.parse(aInputSource);
         return deserialize(doc);
      }
      catch (Exception ex)
      {
         throw new AeBusinessProcessException(ex.getLocalizedMessage(), ex);
      }
   }
   
   /**
    * Deserialize the BPEL xml to AeProcessDef.
    * @param aDocument
    * @return AeProcessDef object model
    * @throws AeBusinessProcessException
    */
   public static AeProcessDef deserialize( Document aDocument )
   throws AeBusinessProcessException
   {
      String ns = aDocument.getDocumentElement().getNamespaceURI();
      IAeBpelFactory factory = getBpelFactory(ns);
      return factory.createBpelReader().readBPEL(aDocument);
   }

   /**
    * Serialize the AeProcessDef to its BPEL xml representation.  This implementation will
    * create prefixed XML.
    * 
    * @param aProcessDef the BPEL process object model
    * @return BPEL xml 
    * @throws AeBusinessProcessException
    */
   public static Document serialize(AeProcessDef aProcessDef) throws AeBusinessProcessException
   {
      return serialize(aProcessDef, true);
   }

   /**
    * Serialize the AeProcessDef to its BPEL xml representation.
    * 
    * @param aProcessDef the BPEL process object model
    * @param aUsePrefixes indicates whether the resulting XML should be 'fully qualified'
    * @return BPEL xml 
    * @throws AeBusinessProcessException
    */
   public static Document serialize(AeProcessDef aProcessDef, boolean aUsePrefixes)
         throws AeBusinessProcessException
   {
      String ns = aProcessDef.getNamespace();
      IAeBpelFactory factory = getBpelFactory(ns);
      return factory.createBpelWriter().writeBPEL( aProcessDef, aUsePrefixes );
   }
   
   /**
    * Gets the IAeBpelFactory by namespace
    * @throws AeBusinessProcessException
    */
   public static IAeBpelFactory getBpelFactory(String aNamespace) throws AeBusinessProcessException
   {
      if (IAeBPELConstants.BPWS_NAMESPACE_URI.equals(aNamespace))
      {
         return mBPEL4WSFactory;
      }
      else if (IAeBPELConstants.WSBPEL_2_0_NAMESPACE_URI.equals(aNamespace))
      {
         return mWSBPEL20Factory;
      }
      else if (IAeBPELConstants.WSBPEL_2_0_ABSTRACT_NAMESPACE_URI.equals(aNamespace))
      {
         return mWSBPEL20AbstractProcessFactory;
      }
      
      // Throw an exception if the namespace is unknown.
      String msg = AeMessages.format("AeBpelIO.NO_BPEL_FACTORY_FOUND_FOR_NS", new Object[] { aNamespace }); //$NON-NLS-1$
      throw new AeBusinessProcessException(msg);
   }
}
