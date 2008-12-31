//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/filters/AeSQLReceiverFilter.java,v 1.2 2006/02/10 21:51:1
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
package org.activebpel.rt.bpel.server.engine.storage.sql.filters;

import org.activebpel.rt.bpel.impl.list.AeMessageReceiverFilter;
import org.activebpel.rt.bpel.server.engine.storage.AeStorageException;
import org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLConfig;
import org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLFilter;
import org.activebpel.rt.bpel.server.engine.storage.sql.AeSQLQueueStorageProvider;
import org.activebpel.rt.util.AeUtil;


/**
 * Wraps the AeMessageReceiverFilter and uses its selection criteria to format the select statement and the
 * matching criteria parameters.
 */
public class AeSQLReceiverFilter extends AeSQLFilter
{
   /** Query key. */
   private static final String SQL_GET_MESSAGE_RECEIVERS = "GetQueuedMessageReceivers"; //$NON-NLS-1$
   private static final String SQL_GET_MESSAGE_RECEIVERS_WHERE = "GetQueuedMessageReceiversWhere"; //$NON-NLS-1$

   /** Group By clause. */
   private static final String SQL_MESSAGE_RECEIVERS_ORDER_BY = "GetQueuedMessageReceiversOrderBy"; //$NON-NLS-1$

   /** Column constants. */
   private static final String SQL_PROCESS_ID = "AeQueuedReceive.ProcessId"; //$NON-NLS-1$
   private static final String SQL_PARTNER_LINK_NAME = "AeQueuedReceive.PartnerLinkName"; //$NON-NLS-1$
   private static final String SQL_OPERATION = "AeQueuedReceive.Operation"; //$NON-NLS-1$
   private static final String SQL_PORT_TYPE_NAMESPACE = "AeQueuedReceive.PortTypeNamespace"; //$NON-NLS-1$
   private static final String SQL_PORT_TYPE_LOCAL_PART = "AeQueuedReceive.PortTypeLocalPart"; //$NON-NLS-1$

   /**
    * Constructor.
    * @param aFilter The selection criteria.
    */
   public AeSQLReceiverFilter(AeMessageReceiverFilter aFilter, AeSQLConfig aConfig) throws AeStorageException
   {
      super(aFilter, aConfig, AeSQLQueueStorageProvider.SQLSTATEMENT_PREFIX);
      setOrderBy(getSQLStatement(SQL_MESSAGE_RECEIVERS_ORDER_BY));
      setSelectClause(getSQLStatement(SQL_GET_MESSAGE_RECEIVERS));
   }

   /**
    * Builds the sql statement.
    */
   protected void processFilter()
   {
      AeMessageReceiverFilter filter = (AeMessageReceiverFilter) getFilter();
      
      appendCondition(getSQLStatement(SQL_GET_MESSAGE_RECEIVERS_WHERE));
      
      if( filter != null )
      {
         if( !filter.isNullProcessId() )
         {
            appendCondition( SQL_PROCESS_ID + " = ?", new Long( filter.getProcessId() ) ); //$NON-NLS-1$
         }
         
         checkStringParam( filter.getPartnerLinkName(), SQL_PARTNER_LINK_NAME );
         checkStringParam( filter.getOperation(), SQL_OPERATION );            

         if( filter.getPortType() != null )
         {
            checkStringParam( filter.getPortType().getNamespaceURI(), SQL_PORT_TYPE_NAMESPACE );            
            checkStringParam( filter.getPortType().getLocalPart(), SQL_PORT_TYPE_LOCAL_PART );            
         }
      }
   }

   /**
    * Convenience method for adding where conditions to the 
    * sql.
    * @param aValue The value to match on.
    * @param aColumnName The corresponding column name.
    */
   protected void checkStringParam( String aValue, String aColumnName )
   {
      if( !AeUtil.isNullOrEmpty(aValue) )
      {
         appendCondition( aColumnName + " = ?", aValue ); //$NON-NLS-1$
      }
   }
}
