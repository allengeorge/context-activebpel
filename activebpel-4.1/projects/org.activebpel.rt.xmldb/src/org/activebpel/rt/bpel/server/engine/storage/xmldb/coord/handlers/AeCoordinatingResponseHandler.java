//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/coord/handlers/AeCoordinatingResponseHandler.java,v 1.1 2007/08/17 00:40:5
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
package org.activebpel.rt.bpel.server.engine.storage.xmldb.coord.handlers;

import org.activebpel.rt.bpel.coord.IAeCoordinating;
import org.activebpel.rt.bpel.coord.IAeCoordinationManager;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageUtil;
import org.activebpel.rt.bpel.server.engine.storage.sql.IAeCoordinationColumns;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBException;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.coord.IAeCoordinationElements;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBSingleObjectResponseHandler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Implements a XMLDB response handler that returns a single instance of IAeCoordinating.
 */
public class AeCoordinatingResponseHandler extends AeXMLDBSingleObjectResponseHandler
{
   /** The coordination manager. */
   private IAeCoordinationManager mManager;

   /**
    * Default ctor.
    */
   public AeCoordinatingResponseHandler(IAeCoordinationManager aManager)
   {
      super();

      setManager(aManager);
   }

   /**
    * Creates an IAeCoordinating instance from the given XMLDB response element.
    * 
    * @param aElement
    * @param aManager
    * @throws AeXMLDBException
    */
   public static IAeCoordinating createCoordinatingFromElement(Element aElement,
         IAeCoordinationManager aManager) throws AeXMLDBException
   {
      int coordRole = getIntFromElement(aElement, IAeCoordinationElements.COORDINATION_ROLE).intValue();
      String coordId = getStringFromElement(aElement, IAeCoordinationElements.COORDINATION_ID);
      String state = getStringFromElement(aElement, IAeCoordinationElements.STATE);
      long processId = getLongFromElement(aElement, IAeCoordinationElements.PROCESS_ID).longValue();
      Document contextDocument = getDocumentFromElement(aElement, IAeCoordinationColumns.COORDINATION_DOC);

      try
      {
         return AeStorageUtil.createCoordinating(processId, coordId, state, coordRole, contextDocument, aManager);
      }
      catch (AeStorageException ex)
      {
         throw new AeXMLDBException(ex);
      }
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.handlers.AeXMLDBSingleObjectResponseHandler#handleElement(org.w3c.dom.Element)
    */
   protected Object handleElement(Element aElement) throws AeXMLDBException
   {
      return AeCoordinatingResponseHandler.createCoordinatingFromElement(aElement, getManager());
   }
   
   /**
    * @return Returns the manager.
    */
   protected IAeCoordinationManager getManager()
   {
      return mManager;
   }
   
   /**
    * @param aManager The manager to set.
    */
   protected void setManager(IAeCoordinationManager aManager)
   {
      mManager = aManager;
   }
}
