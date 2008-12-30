// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/def/castor/AeURIResolver.java,v 1.5 2007/04/03 20:31:3
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
package org.activebpel.rt.wsdl.def.castor;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.wsdl.xml.WSDLLocator;

import org.activebpel.rt.wsdl.def.IAeStandardSchemaResolver;
import org.exolab.castor.net.URIException;
import org.exolab.castor.net.URILocation;
import org.exolab.castor.net.URIResolver;
import org.exolab.castor.net.util.URILocationImpl;
import org.exolab.castor.net.util.URIResolverImpl;
import org.xml.sax.InputSource;

/**
 * Implements the Castor interface for resolving URI references to instances of
 * <code>URILocation</code>.
 */
public class AeURIResolver implements URIResolver
{
   /** The WSDL locator to use for locating schema resources. */
   private final WSDLLocator mWSDLLocator;
   /** The standard schema resolver for locating 'well known' schemas. */
   private final IAeStandardSchemaResolver mStandardResolver;
   /** The Castor URI resolver to use if the WSDL locator is not provided. */
   private URIResolver mDelegate;
   /** Collection of all unique resolution requests */
   private Set mResolvedReferences;

   /**
    * Constructs a URI resolver using the specified WSDL locator, location, and standard
    * schema resolver.
    * 
    * @param aLocator
    * @param aStandardResolver
    */
   public AeURIResolver(WSDLLocator aLocator, IAeStandardSchemaResolver aStandardResolver)
   {
      mWSDLLocator = aLocator;
      mStandardResolver = aStandardResolver;
      mResolvedReferences = new HashSet();
   }

   /**
    * Returns the Castor URI resolver to use if the WSDL locator is not
    * provided.
    */
   protected URIResolver getDelegate()
   {
      if (mDelegate == null)
      {
         mDelegate = new URIResolverImpl();
      }

      return mDelegate;
   }

   /**
    * @see org.exolab.castor.net.URIResolver#resolve(java.lang.String, java.lang.String)
    */
   public URILocation resolve(String aHref, String aDocumentBase) throws URIException
   {
      URILocation loc;
      if (mWSDLLocator != null)
         loc = new AeURILocation(mWSDLLocator, aHref, aDocumentBase);
      else
         loc =  getDelegate().resolve(aHref, aDocumentBase);

      if (loc != null)
         mResolvedReferences.add(loc.getAbsoluteURI());

      return loc;
   }

   /**
    * @see org.exolab.castor.net.URIResolver#resolveURN(java.lang.String)
    */
   public URILocation resolveURN(String urn) throws URIException
   {
      InputSource iSource = null;
      if (mStandardResolver != null)
      {
         iSource = mStandardResolver.resolve(urn);
      }
      if (iSource != null)
      {
         return new AeInputSourceURILocation(iSource, urn);
      }
      else
      {
         URILocation loc = getDelegate().resolveURN(urn);
         if (loc == null)
         {
            return new URILocationImpl(urn);
         }
         else
         {
            return loc;
         }
      }
   }
   
   /**
    * Returns an iterator over the unique URI references we have been aaked to resolve.
    */
   public Iterator getURIReferences()
   {
      return mResolvedReferences.iterator();
   }
}
