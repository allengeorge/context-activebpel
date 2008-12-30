// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/AeElementHolder.java,v 1.1 2004/09/16 15:31:2
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
package org.activebpel.rt.axis.bpel;

import javax.xml.namespace.QName;

import org.w3c.dom.Element;

/**
 * A wrapper for an element so Axis won't think we're trying to serialize a primitive type.
 */
public class AeElementHolder
{
   /** The xsi:type value for the element we're holding. */
   private QName mType;
   
   /** element we're holding */
   private Element mElement;
   
   /**
    * Constructor
    * @param aElement
    */
   public AeElementHolder(QName aType, Element aElement)
   {
      mType = aType;
      mElement = aElement;
   }
   
   /**
    * Getter for the element.
    */
   public Element getElement()
   {
      return mElement;
   }
   
   /**
    * Getter for the type that we're holding.
    */
   public QName getType()
   {
      return mType;
   }
}
