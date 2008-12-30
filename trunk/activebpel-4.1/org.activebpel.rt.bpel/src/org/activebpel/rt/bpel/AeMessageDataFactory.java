// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/AeMessageDataFactory.java,v 1.3 2007/06/10 19:07:5
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
package org.activebpel.rt.bpel;

import org.activebpel.rt.message.AeMessageData;
import org.activebpel.rt.message.IAeMessageData;

import javax.xml.namespace.QName;
import java.util.Map;

/**
 * The factory for creating message data implementations which contain the data
 * for variables in a bpel process.
 */
public class AeMessageDataFactory
{
   /** The singleton instance of the factory */
   private static AeMessageDataFactory mFactory = new AeMessageDataFactory();

   /**
    * Private constructor to force singleton.
    */
   private AeMessageDataFactory()
   {
   }

   /**
    * Returns the singleton instance of the Win32 Service factory.
    */
   public static AeMessageDataFactory instance()
   {
      return mFactory;
   }

   /**
    * Creates a message to be used during BPEL process execution.
    * @param aMsgName The qualified name of the message we are creating
    */
   public IAeMessageData createMessageData(QName aMsgName)
   {
      return new AeMessageData(aMsgName);
   }
   
   /**
    * Create a message with the given QName and message data.
    * @param aMsgName
    * @param aMessageData
    */
   public IAeMessageData createMessageData( QName aMsgName, Map aMessageData )
   {
      return new AeMessageData( aMsgName, aMessageData );
   }
}
