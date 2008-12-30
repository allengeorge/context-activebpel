// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/def/AeRoleImpl.java,v 1.8 2006/06/26 16:46:4
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


/**
 * This class represents a Partner Link Type's Role element. Roles contain Port
 * Types elements.
 */
public class AeRoleImpl implements IAeRole, IAeBPELExtendedWSDLConst
{
   /** The name of this Role */
   private String mName;

   /** The port type for this role which has a 1 to 1 relationship */
   private IAePortType mPortType;

   /**
    * Constructor. Creates a new Role with the given name. 
    * @param aName
    */
   public AeRoleImpl(String aName)
   {
      setName(aName);
   }

   /**
    * Get the name attribute value of this Role
    * @return String
    */
   public String getName()
   {
      return mName;
   }
   
   /**
    * Set the name attribute value of this Role
    * @param aName
    */
   public void setName(String aName)
   {
      mName = aName;
   }

   /**
    * Sets the PortType for this Role
    * @param aPortType the port type to be set
    */
   public void setPortType(IAePortType aPortType)
   {
      mPortType = aPortType;
   }

   /**
    * Gets the PortType for this Role.
    */
   public IAePortType getPortType()
   {
      return mPortType;
   }
}
