// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/catalog/IAeCatalogMapping.java,v 1.2 2006/08/04 17:57:5
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
package org.activebpel.rt.bpel.server.catalog;

import java.io.IOException;

import org.activebpel.rt.AeException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 * Interface for deploying resources to the catalog.
 */
public interface IAeCatalogMapping
{
   /** 
    * Location hint.  
    * Should be fully qualified url.
    */
   public String getLocationHint();
  
   /**
    * @return Returns the typeURI.
    */
   public String getTypeURI();
   
   /** 
    * Target namespace for this mapping entry, if it is WSDL or Schema it comes
    * from the targetNamespace attribute.  
    */
   public String getTargetNamespace();
   
   /**
    * Access resource as InputSource.
    * @throws IOException
    */
   public InputSource getInputSource() throws IOException;

   /**
    * Access resource as a Document.
    * @throws AeException
    */
   public Document getDocument() throws AeException;
   
   /**
    * Return true if this is a wsdl entry.
    */
   public boolean isWsdlEntry();
   
   /**
    * Return true if this is a schema entry.
    */
   public boolean isSchemaEntry();
}
