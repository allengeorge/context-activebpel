// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/def/IAeProperty.java,v 1.3 2006/06/26 16:46:4
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
package org.activebpel.rt.wsdl.def;

import javax.xml.namespace.QName;

/**
 * This interface represents a Message Property extention element.  It contains
 * information about operations associated with this Property element.
 */
public interface IAeProperty
{
   /**
    * Get the Message Property name.
    * 
    * @return QName
    */
   public QName getQName();

   /**
    * Set the Message Property name.
    * 
    * @param aName
    */
   public void setQName(QName aName);

   /**
    * Get the Message Property type QName.
    * 
    * @return QName
    */
   public QName getTypeName();

   /**
    * Set the Message Property type QName.
    * 
    * @param aType
    */
   public void setTypeName(QName aType);
   
   /**
    * Getter for the element name or null if property is a type
    */
   public QName getElementName();
   
   /**
    * Setter for the element name
    * @param aQName
    */
   public void setElementName(QName aQName);

}
