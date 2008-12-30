// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/IAeWSDLFactory.java,v 1.4 2006/06/26 16:46:4
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
package org.activebpel.rt;

import org.xml.sax.InputSource;

/**
 * Interface to define a factory mechanism for obtaining a WSDL source
 */
public interface IAeWSDLFactory
{
   /**
    * Gets the InputStream for passed wsdl file after first checking catalog for local
    * access (via classpath) to passed url. The WSDL URL should be an absolute path to
    * the resource.
    * @param aWsdlUrl The url of the wsdl to load.
    * @return the InputStream representing the passed wsdl url.
    * @throws AeException If a failure is encountered loading the WSDL Document.
    */
   public InputSource getWSDLSource(String aWsdlUrl) throws AeException;
   
   /**
    * Returns the wsdl source for a passed namespace or null if none.
    * @param aNamespace The namespace to retrieve the input source for.
    */
   public InputSource getWSDLForNamespace(String aNamespace) throws AeException;
   
   /**
    * Returns the url for the wsdl source for a passed namespace or null if none.
    * @param aNamespace The namespace to retrieve the input source for.
    */
   public String getWSDLLocationForNamespace(String aNamespace) throws AeException;
}
