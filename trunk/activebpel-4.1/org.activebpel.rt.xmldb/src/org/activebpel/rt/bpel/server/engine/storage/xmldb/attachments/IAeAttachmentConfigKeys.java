// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/attachments/IAeAttachmentConfigKeys.java,v 1.1 2007/08/17 00:40:5
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
package org.activebpel.rt.bpel.server.engine.storage.xmldb.attachments;

/**
 * Contains xmldb config keys for looking up xquery statements.
 */
public interface IAeAttachmentConfigKeys
{
   public static final String INSERT_ATTACHMENT_GROUP = "InsertAttachmentGroup"; //$NON-NLS-1$
   public static final String ATTACH_PROCESS = "AttachProcess"; //$NON-NLS-1$
   public static final String INSERT_ATTACHMENT = "InsertAttachment"; //$NON-NLS-1$
   public static final String QUERY_ATTACHMENT_CONTENT_ID = "QueryAttachmentContentId"; //$NON-NLS-1$
   public static final String QUERY_ATTACHMENT_HEADERS = "QueryAttachmentHeaders"; //$NON-NLS-1$
   public static final String CLEANUP_ATTACHMENTS = "CleanupAttachments"; //$NON-NLS-1$
}
