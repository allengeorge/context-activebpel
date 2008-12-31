//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/faults/IAeCatch.java,v 1.2 2006/10/06 21:32:4
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
package org.activebpel.rt.bpel.def.faults; 

import javax.xml.namespace.QName;

/**
 * Provides interface for faultHandlers. This interface is used to expose 
 * the info necessary to see if a fault matches to a catch element.
 */
// fixme (MF) repackage to rt.bpel.faults 
public interface IAeCatch
{
   /**
    * Returns true if the catch defines a variable
    */
   public boolean hasFaultVariable();
   
   /**
    * Returns the QName of the fault element or null if no variable is defined or if its catching a message
    */
   public QName getFaultElementName();
   
   /**
    * Returns the QName of the fault message or null if no variable is defined or if its catching an element
    */
   public QName getFaultMessageType();
   
   /**
    * Returns the QName of the fault being caught or null if not provided
    */
   public QName getFaultName();
}
 
