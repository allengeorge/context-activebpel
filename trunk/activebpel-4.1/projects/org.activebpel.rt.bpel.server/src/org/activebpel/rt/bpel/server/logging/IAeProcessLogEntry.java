//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/logging/IAeProcessLogEntry.java,v 1.2 2005/04/13 17:26:4
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

/**
 * Provides an interface for a data structure that contains a segment of logging
 * statements from a process log. These statements are accumulated in memory for
 * the process until such time as the process is persisted. At this time, the 
 * log entry will be extracted from the logger and written to the underlying
 * storage. 
 */
public interface IAeProcessLogEntry
{
   /**
    * Gets the contents of this log entry.
    */
   public String getLog();
   
   /**
    * Gets the number of lines in the entry
    */
   public int getLineCount();
   
   /**
    * Gets the process id for this entry
    */
   public long getProcessId();

   /**
    * Clears the contents of this log entry from the log.
    */
   public void clearFromLog();
}
 
