//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/wsdl/IAeResourceResolver.java,v 1.2 2007/05/10 21:18:2
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
package org.activebpel.rt.bpel.server.wsdl;

import java.io.IOException;

import org.xml.sax.InputSource;

/**
 * Interface for defining a resource resolver.  A resource resolver is
 * responsible for finding and loading resources by location hints.
 */
public interface IAeResourceResolver
{
   /**
    * Return the InputSource for the resource deployment (or null if none is
    * found).
    * @param aLocationHint
    */
   public InputSource getInputSource(String aLocationHint) throws IOException;

   /**
    * Returns true if the resolver contains a mapping for the location hint.
    * @param aLocationHint
    */
   public boolean hasMapping(String aLocationHint);

}
