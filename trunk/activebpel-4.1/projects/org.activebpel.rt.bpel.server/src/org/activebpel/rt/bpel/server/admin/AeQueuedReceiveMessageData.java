// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/admin/AeQueuedReceiveMessageData.java,v 1.4 2005/02/01 19:56:3
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
package org.activebpel.rt.bpel.server.admin;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

/**
 *  Wraps the message data for web ui.
 */
public class AeQueuedReceiveMessageData
{

   /** message data qname */
   private QName mQName;
   /** map of message data */
   private Map mPartData;
   
   /**
    * Constructor.
    * @param aQName
    */
   public AeQueuedReceiveMessageData( QName aQName )
   {
      mQName = aQName;
      mPartData = new HashMap();
   }
   
   /**
    * Add message parts.
    * @param aName
    * @param aData
    */
   public void addPartData( String aName, Object aData )
   {
      getPartData().put( aName, aData );
   }
   
   /**
    * Return part data map.
    */
   public Map getPartData()
   {
      return mPartData;
   }
   
   /**
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return mQName + "-" + getPartData(); //$NON-NLS-1$
   }
}
