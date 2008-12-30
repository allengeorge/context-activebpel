//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis/src/org/activebpel/rt/axis/ser/AeSimpleValueWrapper.java,v 1.1 2005/04/15 13:44:0
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
package org.activebpel.rt.axis.ser; 


/**
 * A wrapper for a derived simple type. If we add the simple type directly to the
 * rpc param then it will be serialized as a simple type and not its derived type.
 * For example, the type we're wrapping might be a java.lang.Integer. However, the
 * schema type might be ns1:OrderId as opposed to xsd:int. If we added the Integer
 * directly to the rpc param then it'll be serialized as an xsd:int. The use of this
 * wrapper class enables our custom serializer to get called and handle it properly.
 */
public class AeSimpleValueWrapper
{
   /** The type that we're wrapping */
   private Object mDelegate; 

   public AeSimpleValueWrapper(Object aSimpleType)
   {
      mDelegate = aSimpleType;
   }
   
   /**
    * Getter for the delegate.
    */
   public Object getDelegate()
   {
      return mDelegate;
   }
}
 
