//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/processview/AeWebBpelProcessLoader.java,v 1.3 2006/06/26 18:38:2
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
package org.activebpel.rt.bpeladmin.war.web.processview;

import java.io.File;

import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.io.AeBpelIO;
import org.w3c.dom.Document;

/**
 *  Class to load the BPEL process definition object given the denition xml.
 */
public class AeWebBpelProcessLoader
{
   /** The xml document containing the BPEL (deployed) process. */
   private Document mBpelDocument = null;   
   /** The xml state document of the active process. */
   private Document mStateDocument = null;  
   /** Process def **/
   private AeProcessDef mProcessDef;  
   
   /**
    * Loads the given process into a internal model.
    * 
    * @param aBpelDoc
    *           xml document containing deployed BPEL process.
    * @param aStateDoc
    *           xml doucment containing the current state of the active process.
    */

   public AeWebBpelProcessLoader(Document aBpelDoc, Document aStateDoc) throws Exception
   {
      setBpelDocument(aBpelDoc);
      setStateDocument(aStateDoc);
      load();
   }

   /**
    * Loads process to an internal model given the process definition and optional
    * state xml documents.
    * @param aDefFile
    * @param aStateFile
    * @throws Exception
    */
   public AeWebBpelProcessLoader(File aDefFile, File aStateFile) throws Exception
   {
      Document defDom = AeProcessViewUtil.domFromFile(aDefFile);
      Document stateDom = null;
      if (aStateFile != null && aStateFile.exists())
      {
         stateDom = AeProcessViewUtil.domFromFile(aStateFile);
      }      
      setBpelDocument(defDom);
      setStateDocument(stateDom);
      load();
   }   
   
   /**
    * Returns true if the state has been restored.
    */
   public boolean hasStateDocument()
   {
      return getStateDocument() != null;
   }
   
   /** 
    * Returns the bpel definition xml document.
    * @return The BPEL xml document for the deployed process.
    */
   public Document getBpelDocument()
   {
      return mBpelDocument;
   }
   
   /**
    * Sets the bpel definition xml document.
    * @param aBpelDoc The bpelDoc to set.
    */
   protected void setBpelDocument(Document aBpelDoc)
   {
      mBpelDocument = aBpelDoc;
   }   
 
   /** 
    * Returns the state document if available or null otherwise.
    * @return The xml document containing the current state information.
    */
   public Document getStateDocument()
   {
      return mStateDocument;
   }
   
   /**
    * Sets the state document.
    * @param aStateDoc The stateDoc to set.
    */
   protected void setStateDocument(Document aStateDoc)
   {
      mStateDocument = aStateDoc;
   }
   
   /**
    * Loads the BPEL definition xml into a <code>AeProcessDef</code> and creates
    * a <code>AeBusinessProcess</code> implementation. If the state document is available,
    * then the state will also be restored.
    * @throws Exception
    */
   protected void load() throws Exception
   {
      AeProcessDef processDef = AeBpelIO.deserialize( getBpelDocument() );
      setProcessDef(processDef);
   }
   
   /**
    * @return Returns the processDef.
    */
   public AeProcessDef getProcessDef()
   {
      return mProcessDef;
   }
   
   /**
    * @param aProcessDef The processDef to set.
    */
   protected void setProcessDef(AeProcessDef aProcessDef)
   {
      mProcessDef = aProcessDef;
   }
      
}
