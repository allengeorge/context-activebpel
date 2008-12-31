//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/queue/AeXMLDBFilteredAlarmListQueryBuilder.java,v 1.1 2007/08/17 00:40:5
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
package org.activebpel.rt.bpel.server.engine.storage.xmldb.queue;

import java.util.LinkedList;
import java.util.List;

import org.activebpel.rt.bpel.impl.list.AeAlarmFilter;
import org.activebpel.rt.bpel.impl.list.IAeListingFilter;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBConfig;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBQueryBuilder;
import org.activebpel.rt.bpel.server.engine.storage.xmldb.IAeXMLDBStorageImpl;
import org.activebpel.rt.xml.schema.AeSchemaDateTime;


/**
 * A filtered alarm list query builder.
 */
public class AeXMLDBFilteredAlarmListQueryBuilder extends AeXMLDBQueryBuilder
{
   /**
    * Constructs a filtered alarm list query builder.
    * 
    * @param aFilter
    * @param aConfig
    * @param aStorageImpl
    */
   public AeXMLDBFilteredAlarmListQueryBuilder(IAeListingFilter aFilter, AeXMLDBConfig aConfig,
         IAeXMLDBStorageImpl aStorageImpl)
   {
      super(aFilter, aConfig, AeXMLDBQueueStorageProvider.CONFIG_PREFIX,
            IAeQueueConfigKeys.GET_ALARMS_FILTERED, aStorageImpl);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBQueryBuilder#getWhere()
    */
   protected String getWhere()
   {
      AeAlarmFilter filter = (AeAlarmFilter) getFilter();
      return joinAndClauseList(getWhereFromFilter(filter));
   }

   /**
    * This creates a list of and clauses based on the passed filter. Derived classes
    * can extend here to add their own 'and' clauses.
    * @param aFilter
    * @return List of and clauses added by filter
    */
   protected List getWhereFromFilter(AeAlarmFilter aFilter)
   {
      List andClauses = new LinkedList();
      if (aFilter.getProcessId() != AeAlarmFilter.NULL_ID)
         andClauses.add("$alarm/ProcessID = " + aFilter.getProcessId()); //$NON-NLS-1$
      if (aFilter.getAlarmFilterStart() != null)
      {
         AeSchemaDateTime sdt = new AeSchemaDateTime(aFilter.getAlarmFilterStart());
         andClauses.add("$alarm/Deadline >= xsd:dateTime(\"" + sdt.toString() + "\")"); //$NON-NLS-1$ //$NON-NLS-2$
      }
      if (aFilter.getAlarmFilterEnd() != null)
      {
         AeSchemaDateTime sdt = new AeSchemaDateTime(aFilter.getAlarmFilterEnd());
         andClauses.add("$alarm/Deadline <= xsd:dateTime(\"" + sdt.toString() + "\")"); //$NON-NLS-1$ //$NON-NLS-2$
      }

      if (aFilter.getProcessName() != null)
      {
         andClauses.add("$proc/ProcessName/LocalPart = '" + aFilter.getProcessName().getLocalPart() + "'"); //$NON-NLS-1$ //$NON-NLS-2$
      }
      return andClauses;
   }
}
