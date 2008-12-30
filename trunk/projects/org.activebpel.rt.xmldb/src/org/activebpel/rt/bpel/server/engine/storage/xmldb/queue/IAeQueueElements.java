//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/queue/IAeQueueElements.java,v 1.1 2007/08/17 00:40:5
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


/**
 * Constants that define the Queue element names.
 */
public interface IAeQueueElements
{
   public static final String DEADLINE = "Deadline"; //$NON-NLS-1$

   public static final String QUEUED_RECEIVE_ID = "QueuedReceiveID"; //$NON-NLS-1$
   public static final String PROCESS_ID = "ProcessID"; //$NON-NLS-1$
   public static final String LOCATION_PATH_ID = "LocationPathID"; //$NON-NLS-1$
   public static final String GROUP_ID = "GroupID"; //$NON-NLS-1$
   public static final String OPERATION = "Operation"; //$NON-NLS-1$
   public static final String PARTNER_LINK_NAME = "PartnerLinkName"; //$NON-NLS-1$
   public static final String PARTNER_LINK_ID = "PartnerLinkID"; //$NON-NLS-1$
   public static final String PORT_TYPE = "PortType"; //$NON-NLS-1$
   public static final String CORRELATION_PROPERTIES = "CorrelationProperties"; //$NON-NLS-1$
   public static final String MATCH_HASH = "MatchHash"; //$NON-NLS-1$
   public static final String CORRELATE_HASH = "CorrelateHash"; //$NON-NLS-1$
   public static final String PROCESS_NAME = "ProcessName"; //$NON-NLS-1$
   public static final String ALARM_ID = "AlarmID"; //$NON-NLS-1$
   public static final String ALLOWS_CONCURRENCY = "AllowsConcurrency"; //$NON-NLS-1$
}
