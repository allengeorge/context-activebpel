//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/util/AeConsoleLogHandler.java,v 1.1 2005/11/15 17:09:3
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
package org.activebpel.rt.util;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Logging handler that will write messages to the system console. If the
 * message is at Warning level, it will be surrounded by asterisks.
 */
public class AeConsoleLogHandler extends Handler
{

   public AeConsoleLogHandler()
   {
      super();
   }
   
   /**
    * Write out the message to the system console.
    * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
    */
   public void publish(LogRecord aLogRecord)
   {
    if (aLogRecord.getLevel() == Level.WARNING)
       System.out.println("*******************************"); //$NON-NLS-1$
    
    System.out.println(aLogRecord.getMessage());
    
    if (aLogRecord.getLevel() == Level.WARNING)
       System.out.println("*******************************"); //$NON-NLS-1$    
   }
   
   /**
    * Nothing to do
    * @see java.util.logging.Handler#close()
    */
   public void close()
   {
   }
   
   /**
    * Nothing to do
    * @see java.util.logging.Handler#flush()
    */
   public void flush()
   {
   }
   
}
