// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/IAeWSDLProvider.java,v 1.6 2004/07/27 19:23:3
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

import org.activebpel.rt.wsdl.def.AeBPELExtendedWSDLDef;

import java.util.Iterator;

/**
 * Interface which defines a provider of WSDL definitions.
 */
public interface IAeWSDLProvider
{
   /**
    * Returns an iterator over the WSDLs known to this provider.
    * 
    * @return Iterator
    */
   public Iterator getWSDLIterator( String aNamespaceUri );
   
   /**
    * The implementation will provide an iterator over a given type.  If that type
    * needs to be dereferenced in any way, this method will provide that logic.  If
    * not, then this method simply returns the passed argument.
    * 
    * @return AeBPELExtendedWSDLDef
    */
   public AeBPELExtendedWSDLDef dereferenceIteration( Object aIteration );
}
