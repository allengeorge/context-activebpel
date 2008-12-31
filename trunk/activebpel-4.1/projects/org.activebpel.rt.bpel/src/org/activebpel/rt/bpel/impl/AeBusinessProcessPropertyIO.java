//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeBusinessProcessPropertyIO.java,v 1.2 2006/06/26 16:50:2
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
package org.activebpel.rt.bpel.impl;

import java.util.Map;

import org.activebpel.rt.bpel.impl.fastdom.AeFastElement;
import org.activebpel.rt.bpel.impl.fastdom.AeFastText;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;

/**
 * Utility methods for serializing <code>IAeBusinessProcessProperty</code> to an
 * <code>AeFastElement</code> and deserializing from the element back to the
 * object.
 */
public class AeBusinessProcessPropertyIO implements IAeImplStateNames
{
   
   //----------[ serialization methods ]----------------------------------------
   
   /**
    * Serialze to <code>AeFastElement</code>.
    * 
    * @param aName
    * @param aValue
    */
   public static AeFastElement getBusinessProcessPropertyElement( String aName, String aValue )
   {
      AeFastElement propertyElement = new AeFastElement( STATE_PROCESSPROPERTY );
      propertyElement.setAttribute( STATE_NAME, aName );
      AeFastText valueNode = new AeFastText( aValue );
      propertyElement.appendChild( valueNode );
      return propertyElement;
   }
   
   //----------[ deserialization methods ]--------------------------------------

   /**
    * Deserialize the element to it's name/value pair and add it to the 
    * given <code>Map</code> arg.
    * 
    * @param aElement
    * @param aMap
    */
   public static void extractBusinessProcessProperty( Element aElement, Map aMap )
   {
      String name = aElement.getAttribute( STATE_NAME );
      String value = AeXmlUtil.getText( aElement );
      aMap.put( name, value );
   }
}
