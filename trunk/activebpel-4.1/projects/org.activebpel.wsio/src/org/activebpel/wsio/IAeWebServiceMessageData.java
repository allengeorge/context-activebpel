//$Header: /Development/AEDevelopment/projects/org.activebpel.wsio/src/org/activebpel/wsio/IAeWebServiceMessageData.java,v 1.3 2007/05/01 16:59:4
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
package org.activebpel.wsio;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 * Wraps any message data returned from the invoke call.
 */
public interface IAeWebServiceMessageData extends Serializable
{
   /**
    * Returns the type of message this data is representing. 
    */
   public QName getMessageType();
   
   /**
    * Get the message part data.
    */
   public Map getMessageData();
   
   /**
    * Get the message attachments.
    */
   public List getAttachments();
}
