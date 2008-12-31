//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/log/AeNullXMLDBPerformanceLogger.java,v 1.1 2007/08/17 00:40:5
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
package org.activebpel.rt.bpel.server.engine.storage.xmldb.log;

/**
 * A no-op logger.
 */
public class AeNullXMLDBPerformanceLogger implements IAeXMLDBPerformanceLogger
{
   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.log.IAeXMLDBPerformanceLogger#logXQueryTime(java.lang.String, long, long)
    */
   public void logXQueryTime(String aQuery, long aQueryTime, long aHandlerTime)
   {
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.log.IAeXMLDBPerformanceLogger#logInsertTime(java.lang.String, long)
    */
   public void logInsertTime(String aInsert, long aInsertTime)
   {
   }
}
