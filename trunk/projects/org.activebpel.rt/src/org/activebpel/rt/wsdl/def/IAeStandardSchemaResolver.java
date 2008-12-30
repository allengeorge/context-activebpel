//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/wsdl/def/IAeStandardSchemaResolver.java,v 1.1 2005/01/07 19:32:5
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

import org.xml.sax.InputSource;

/**
 * Implementors of this interface will resolve "standard" or "well known" schemas given
 * the namespace of the schema.  For example, the Soap Encoding schema found at 
 * "http://schemas.xmlsoap.org/soap/encoding/" will be resolved to a valid URL object.
 * The list of well known schemas is dynamic and implementation dependent.
 */
public interface IAeStandardSchemaResolver
{
   /**
    * Resolves a "standard" schema by namespace.
    * 
    * @param aNamespace
    */
   public InputSource resolve(String aNamespace);

}
