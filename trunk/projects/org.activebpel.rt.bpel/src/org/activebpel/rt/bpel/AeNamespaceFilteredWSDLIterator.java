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

import java.util.Iterator;

import org.activebpel.rt.util.AeFilteredIterator;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.wsdl.def.AeBPELExtendedWSDLDef;

/**
 * Provides a namespace specific iterator over the available resource keys 
 */
public class AeNamespaceFilteredWSDLIterator extends AeFilteredIterator
{
   /** The namespace we're looking for */
   private String mNamespace;
   /** Provider used to dereference the iterator objects */
   private IAeWSDLProvider mProvider;
   
   /**
    * Creates the filtered iterator with the namespace we're looking for 
    * and the delegate iterator.
    * @param aNamespace
    * @param aDelegate
    */
   public AeNamespaceFilteredWSDLIterator(String aNamespace, Iterator aDelegate, IAeWSDLProvider aProvider)
   {
      super(aDelegate);
      setNamespace(aNamespace);
      setProvider(aProvider);
   }
   
   /**
    * @see org.activebpel.rt.util.AeFilteredIterator#accept(java.lang.Object)
    */
   protected boolean accept(Object aObject)
   {
      AeBPELExtendedWSDLDef def = getProvider().dereferenceIteration(aObject);
      return AeUtil.compareObjects(getNamespace(), def.getTargetNamespace());
   }

   /**
    * @return Returns the namespace.
    */
   protected String getNamespace()
   {
      return mNamespace;
   }

   /**
    * @param aNamespace The namespace to set.
    */
   protected void setNamespace(String aNamespace)
   {
      mNamespace = aNamespace;
   }
   
   /**
    * Getter for the provider, used to dereference the iter elements
    */
   protected IAeWSDLProvider getProvider()
   {
      return mProvider;
   }
   
   /**
    * Setter for the provider
    * @param aProvider
    */
   protected void setProvider(IAeWSDLProvider aProvider)
   {
      mProvider = aProvider;
   }
}
