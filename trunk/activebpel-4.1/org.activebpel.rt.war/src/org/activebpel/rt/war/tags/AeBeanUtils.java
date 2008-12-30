// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.war/src/org/activebpel/rt/war/tags/AeBeanUtils.java,v 1.1 2007/04/24 17:23:1
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
package org.activebpel.rt.war.tags;

import java.beans.BeanInfo;
import java.beans.IndexedPropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.activebpel.rt.war.AeMessages;

/**
 * Utility class for common reflection operations used by
 * JSP tags.
 */
public class AeBeanUtils
{
   /**
    * Extract the named accessor method.  The method should follow the
    * getXxx() naming convention.
    * For example, to locate the getName method, the property string should
    * be "name".
    * @param aBeanClass The bean class.
    * @param aProperty The name of the accessor method.
    * @param aReturnType The class of the return type.
    * @return Method The specified "getXXXX" method.
    * @throws IntrospectionException Thrown if the method is not found.
    */
   public static Method getAccessor( Class aBeanClass, String aProperty, 
                                                            Class aReturnType )
   throws IntrospectionException
   {
      Method m = findMethod(aBeanClass, aProperty, aReturnType, true);

      if (m == null)
         throw new IntrospectionException( AeMessages.getString("AeBeanUtils.ERROR_0") + aProperty ); //$NON-NLS-1$
      return m;
   }
   
   /**
    * Extract the named mutator method.  The method should follow the
    * setXxx() naming convention.
    * For example, to locate the setName(String) method, the property string should
    * be "name".
    * 
    * @param aBeanClass The bean class
    * @param aProperty The name of the property
    * @param aType The type of the property argument
    * @throws IntrospectionException
    */
   public static Method getMutator(Class aBeanClass, String aProperty, Class aType)
   throws IntrospectionException
   {
      Method m = findMethod(aBeanClass, aProperty, aType, false);

      if (m == null)
         throw new IntrospectionException( AeMessages.getString("AeBeanUtils.ERROR_1") + aProperty ); //$NON-NLS-1$
      return m;
   }

   /**
    * Walks the property descriptors looking for the getter or setter.
    * 
    * @param aBeanClass The bean class
    * @param aProperty Name of the property
    * @param aType Type of return value for a getter or type of arg for a setter
    * @param aGetterFlag True if you're looking for the getter
    * @throws IntrospectionException
    */
   private static Method findMethod(Class aBeanClass, String aProperty, Class aType, boolean aGetterFlag) throws IntrospectionException
   {
      Method m = null;
      PropertyDescriptor[] descriptors = getDescriptors(aBeanClass);
      for( int i = 0; m == null && i < descriptors.length; ++i )
      {
         PropertyDescriptor descriptor = descriptors[i];
         
         if( descriptor.getName().equals( aProperty ) )
         {
            // no need to check the property type if it's a getter since you
            // cannot have multiple getters with the same name and diff return
            // types. This code used to check the property type up front in the
            // outter if but that prevented me from passing null for the class. 
            // I want to pass null for a getter since it saves me from having to
            // specify the class name in the jsp.
            // i.e.
            //   <ae:TypeFormatter name="myBean" property="addressHandlingType" prefixKey="addressHandling_"/>
            //       vs.
            //   <ae:TypeFormatter name="myBean" property="addressHandlingType" prefixKey="addressHandling_" propertyClass="org.activebpel.wsio.invoke.AeAddressHandlingType"/>
            //
            if (aGetterFlag)
            {
               m = descriptor.getReadMethod();
            }
            // it's possible to have a setter overloaded so we need to check that 
            // the argument type matches what we're looking for. 
            else if (descriptor.getPropertyType().isAssignableFrom(aType))
            {
               m = descriptor.getWriteMethod();
            }
         }
      }
      return m;
   }

   /**
    * Returns an indexed accessor.
    * For example, to locate the getPoint(int aIndex) method, the string
    * property should be "point".
    * @param aBeanClass The bean class.
    * @param aProperty The indexed accessor property name.
    * @param aReturnType The return type of the method.
    * @return Method The method object.
    * @throws IntrospectionException Thrown if the method is not found.
    */
   public static Method getIndexedAccessor( Class aBeanClass, String aProperty,
      Class aReturnType ) throws IntrospectionException
   {
      PropertyDescriptor[] descriptors = getDescriptors(aBeanClass);
      for( int i = 0; i < descriptors.length; ++i )
      {
         PropertyDescriptor descriptor = descriptors[i];
         if( descriptor instanceof IndexedPropertyDescriptor && descriptor.getName().equals( aProperty ) )
         {
            IndexedPropertyDescriptor ipd = (IndexedPropertyDescriptor)descriptor;
            if( ipd.getIndexedPropertyType().isAssignableFrom(aReturnType) )
            {
               return ipd.getIndexedReadMethod();
            }
         }
      }      
      throw new IntrospectionException( AeMessages.getString("AeBeanUtils.ERROR_2") + aProperty ); //$NON-NLS-1$
   }

   /**
    * Utility method for accessing <code>PropertyDescriptors</code> of
    * the given bean class.
    * @param aBeanClass
    * @throws IntrospectionException
    */
   protected static PropertyDescriptor[] getDescriptors( Class aBeanClass )
   throws IntrospectionException
   {
      BeanInfo info = Introspector.getBeanInfo( aBeanClass );
      return info.getPropertyDescriptors();
   }
}
