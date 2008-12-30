// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/def/AePortTypeImpl.java,v 1.8 2006/06/26 16:46:4
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
 * This class represents a Role's PortType element.  This PortType element
 * references a WSDL PortType.
 */
public class AePortTypeImpl implements IAePortType, IAeBPELExtendedWSDLConst
{
   // The name of this PortType
   private QName mQName;
   
   /**
    * Constructor. Creates a new PortType with the given name.
    */
   public AePortTypeImpl(QName aName)
   {
      setQName(aName);
   }
   
   /**
    * Get the name of this PortType.
    * @return QName
    */
   public QName getQName()
   {
      return mQName;
   }

   /**
    * Set the name of this port type.
    * @param aQName
    */
   public void setQName(QName aQName)
   {
      mQName = aQName;
   }
}
