//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/logging/AeUnstructuredDeploymentLog.java,v 1.1 2004/12/10 15:59:2
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
package org.activebpel.rt.bpel.server.logging;

import java.io.PrintWriter;

/**
 * Provides an unstructured deployment log base class.  The unstructured deployment
 * logger simply writes all log messages to a PrintWriter (or the class can be
 * extended to provide some other implementation of the <code>writeMessage</code>
 * method.
 */
public class AeUnstructuredDeploymentLog extends AeDeploymentLog
{
   /** records the log as we're going. */
   protected PrintWriter mWriter;

   /**
    * Default constructor.
    */
   public AeUnstructuredDeploymentLog()
   {
   }
   
   /**
    * Creates the logger that sends its output to the provided writer.
    * @param aWriter 
    */
   public AeUnstructuredDeploymentLog(PrintWriter aWriter)
   {
      setWriter(aWriter);
   }

   /**
    * @see org.activebpel.rt.bpel.server.logging.IAeDeploymentLogger#close()
    */
   public void close()
   {
      if (getWriter() != null)
         getWriter().close();
   }
   
   /**
    * Getter for the writer.
    */
   protected PrintWriter getWriter()
   {
      return mWriter;
   }
   
   /**
    * Setter for the writer
    * @param aWriter
    */
   protected void setWriter(PrintWriter aWriter)
   {
      mWriter = aWriter;
   }

   /**
    * Writes the message to the buffer and the writer if it's present.
    * @param aMessage
    */
   protected void writeMessage(String aMessage)
   {
      if (getWriter() != null)
         getWriter().println(aMessage);
   }

}
