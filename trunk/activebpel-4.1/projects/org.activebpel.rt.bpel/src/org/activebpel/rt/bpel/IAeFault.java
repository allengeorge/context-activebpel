// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/IAeFault.java,v 1.11 2006/09/11 23:06:2
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


import java.io.Serializable;

import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.message.IAeMessageData;
import org.w3c.dom.Element;

/**
 * Interface describing a fault which can be the result of an operation or runtime error.
 * This interface also includes runtime information like the source of the fault as well
 * as optional error messages or stacktrace.
 */
public interface IAeFault extends IAeFaultTypeInfo, Serializable
{
   /**
    * Set the source of the fault.
    * @param aSource
    */
   public void setSource( IAeBpelObject aSource );

   /**
    * Getter for the source of the fault.
    *
    * @return IAeBpelObject
    */
   public IAeBpelObject getSource();

   /**
    * Getter for the message
    * @return IAeMessageData or null
    */
   public IAeMessageData getMessageData();

   /**
    * Getter for the element data.
    */
   public Element getElementData();

   /**
    * Returns true if the fault is rethrowable.
    */
   public boolean isRethrowable();

   /**
    * Set the info of the fault.
    * @param aInfo
    */
   public void setInfo( String aInfo );

   /**
    * Returns the info / msg string of the exception that caused this fault.
    */
   public String getInfo();

   /**
    * Gets detailed info relating to the fault. This might include a stacktrace or other
    * useful debugging information.
    */
   public String getDetailedInfo();

   /**
    * Sets detailed info relating to the fault.
    *
    * @param aDetailInfo
    */
   public void setDetailedInfo(String aDetailInfo);

   /**
    * Return true if this fault can cause the process to be suspended.
    * By default, this value will return true.  If will only return false
    * if this fault has already caused the process to be suspended.
    */
   public boolean isSuspendable();

   /**
    * Set the suspendable flag to the given value.
    * @param aValue
    */
   public void setSuspendable( boolean aValue );
}
