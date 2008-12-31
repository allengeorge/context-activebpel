//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/IAeFaultTypeInfo.java,v 1.2 2006/10/17 21:23:0
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

import javax.xml.namespace.QName;

/**
 * Provides the type info for the fault
 */
public interface IAeFaultTypeInfo
{
   /**
    * Getter for the fault name
    * @return QName
    */
   public QName getFaultName();
   
   /**
    * returns the QName of the message if the data is a message type
    */
   public QName getMessageType();
   
   /**
    * returns the QName of the element data if the data is an element type
    */
   public QName getElementType();
   
   /**
    * returns the QName of the single part element if the data is a message
    * and the message has a single element part.
    */
   public QName getSinglePartElementType();

   /**
    * Returns true if the fault contains message data.
    */
   public boolean hasMessageData();

   /**
    * Returns true if the fault has element data.
    */
   public boolean hasElementData();
   
   /**
    * Returns true if the fault has element or message data 
    */
   public boolean hasData();
}
 
