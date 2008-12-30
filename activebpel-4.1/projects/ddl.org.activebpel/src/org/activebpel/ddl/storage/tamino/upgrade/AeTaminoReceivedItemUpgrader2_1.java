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
package org.activebpel.ddl.storage.tamino.upgrade;

import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.tamino.AeTaminoConfig;
import org.activebpel.rt.bpel.server.engine.storage.tamino.upgrade.AeAbstractTaminoUpgrader;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl;

/**
 * A Tamino upgrader that undefines/drops the AeReceivedItem doc type.
 */
public class AeTaminoReceivedItemUpgrader2_1 extends AeAbstractTaminoUpgrader
{
   /**
    * Default constructor.
    * 
    * @param aSchemaName
    * @param aStorageImpl
    */
   public AeTaminoReceivedItemUpgrader2_1(String aSchemaName, IAeXMLDBStorageImpl aStorageImpl)
   {
      super(aSchemaName, aStorageImpl);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.tamino.upgrade.AeAbstractTaminoUpgrader#createTaminoConfig()
    */
   protected AeTaminoConfig createTaminoConfig()
   {
      return null;
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.tamino.upgrade.AeAbstractTaminoUpgrader#upgrade()
    */
   public void upgrade() throws AeStorageException
   {
      undefineReceivedItemDocType();
   }

   /**
    * Undefines the AeReceivedItem doc type.
    * 
    * @throws AeStorageException
    */
   protected void undefineReceivedItemDocType() throws AeStorageException
   {
      undefineDocType(getCollectionName(), getSchemaName(), "AeReceivedItem"); //$NON-NLS-1$
   }
}
