//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/addressing/IAeAddressingHeaders.java,v 1.1 2006/08/08 16:44:2
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
package org.activebpel.rt.bpel.impl.addressing;

import java.util.List;

import org.activebpel.wsio.AeWsAddressingException;
import org.activebpel.wsio.IAeWsAddressingHeaders;
import org.w3c.dom.Element;

/**
 * Extension of IAeWsAddressingHeaders that deals with overlapping 
 * endpoint definitions in reference properties
 */
public interface IAeAddressingHeaders extends IAeWsAddressingHeaders
{
   /** 
    * Adds a WS-Addressing header to this instance.  
    * If the element is a known WS-Addressing header, the value of the appropriate member variable is updated. 
    * Otherwise, it is added as a ReferenceProperty.
    *   
    * @param aElement
    * @throws AeWsAddressingException
    */
   public void addHeaderElement(Element aElement) throws AeWsAddressingException;   

   /**
    * Sets the reference properties for this instance.  Any WSA headers embedded
    * in the list of reference properties will override the appropriate member variable
    * as opposed to blindly adding it to the list.
    * 
    * @param aElementList the list of reference properties to serialize as headers.
    */
   public void setReferenceProperties(List aElementList) throws AeWsAddressingException;
   
}
