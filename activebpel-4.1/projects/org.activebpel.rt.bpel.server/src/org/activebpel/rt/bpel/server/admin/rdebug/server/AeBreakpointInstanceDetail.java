// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/admin/rdebug/server/AeBreakpointInstanceDetail.java,v 1.2 2006/02/24 16:37:3
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
package org.activebpel.rt.bpel.server.admin.rdebug.server;

import java.io.Serializable;

import javax.xml.namespace.QName;

/**
 * JavaBean for holding some data for a single breakpoint definition.
 */
public class AeBreakpointInstanceDetail implements Serializable
{
   /** name of the process */
   private QName mProcessName;
   /** node path of this breakpoint */
   private String mNodePath;

   /**
    * No-arg constructor
    */
   public AeBreakpointInstanceDetail()
   {
   }
   
   /**
    * Getter for the process name
    */
   public QName getProcessName()
   {
      return mProcessName;
   }

   /**
    * Setter for the name
    * @param aName
    */
   public void setProcessName(QName aName)
   {
      mProcessName = aName;
   }
   
   /**
    * Getter for the node path where this breakpoint is defined.
    * 
    * @return String
    */
   public String getNodePath()
   {
      return mNodePath;
   }

   /**
    * Setter for the node path.
    * 
    * @param aNodePath The node path to set.
    */
   public void setNodePath(String aNodePath)
   {
      mNodePath = aNodePath;
   }
}
