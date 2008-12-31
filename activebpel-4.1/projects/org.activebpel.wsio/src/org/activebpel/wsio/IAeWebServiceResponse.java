//$Header: /Development/AEDevelopment/projects/org.activebpel.wsio/src/org/activebpel/wsio/IAeWebServiceResponse.java,v 1.6 2005/07/25 16:48:3
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

import javax.xml.namespace.QName;
import java.io.Serializable;
import java.util.Map;


/**
 * Wraps the invoke response data.
 */
public interface IAeWebServiceResponse extends Serializable
{
   /**
    * Accessor for the error code QName.
    */
   public QName getErrorCode();
   
   /**
    * Accessor for any message data.  This may be null.
    */
   public IAeWebServiceMessageData getMessageData();
   
   /**
    * Return true if the response wraps a fault.
    */
   public boolean isFaultResponse();
   
   /**
    * Returns an error message associated with the fault or null if there is none.
    */
   public String getErrorString();
   
   /**
    * Returns a stacktrace or other detailed information associated with the fault or null if there was none. 
    */
   public String getErrorDetail();
   
   /**
    * Return the <code>Map</code> of (string) name/value pairs from the
    * business process.
    */
   public Map getBusinessProcessProperties();
   
   /**
    * Flag that indicates that the actual response will be coming later. (Early Reply to the client)
    * @return Returns true if the response is early reply.
    */
   public boolean isEarlyReply();   
}
