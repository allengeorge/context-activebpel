//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/tx/AeXMLDBTransactionManagerFactory.java,v 1.1 2007/08/17 00:40:5
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
package org.activebpel.rt.bpel.server.engine.storage.xmldb.tx;

import java.util.Map;

import org.activebpel.rt.bpel.server.engine.transaction.IAeTransactionManagerFactory;

/**
 * Implements a transaction manager factory for the XMLDB storage layer.  This class
 * exists simply to provide a consistent type hierarchy for xml database impls.
 */
public abstract class AeXMLDBTransactionManagerFactory implements IAeTransactionManagerFactory
{
   /**
    * Constructs transaction manager factory with the specified configuration.
    *
    * @param aConfigMap
    */
   public AeXMLDBTransactionManagerFactory(Map aConfigMap)
   {
      // No configuration required.
   }
}
