//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeUnmatchedRequestException.java,v 1.2 2006/06/26 16:50:3
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
package org.activebpel.rt.bpel.impl; 

import org.activebpel.rt.bpel.AeMessages;

/**
 * A correlation violation resulting from an unmatched request. 
 */
public class AeUnmatchedRequestException extends AeCorrelationViolationException
{
   /**
    * Ctor takes namespace for the bpel fault
    * @param aNamespace
    */
   public AeUnmatchedRequestException(String aNamespace)
   {
      super(AeMessages.getString("AeUnmatchedRequestException.Message"), AeFaultFactory.getFactory(aNamespace).getUnmatchedRequest()); //$NON-NLS-1$
   }
}
 
