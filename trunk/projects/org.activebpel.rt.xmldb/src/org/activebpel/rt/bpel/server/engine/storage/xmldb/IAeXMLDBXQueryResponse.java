// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/IAeXMLDBXQueryResponse.java,v 1.1 2007/08/17 00:40:5
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
package org.activebpel.rt.bpel.server.engine.storage.xmldb;

import org.w3c.dom.Element;

/**
 * Represents an XQuery response from an XML database.  Allows iteration over
 * the result set.
 */
public interface IAeXMLDBXQueryResponse
{
   /**
    * Returns true if the response has another unfetched element.
    */
   public boolean hasNextElement();

   /**
    * Returns the next element in the result set.
    */
   public Element nextElement();
}
