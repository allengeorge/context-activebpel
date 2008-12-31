//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeUninitializedVariableException.java,v 1.1 2006/08/02 14:40:2
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
 * Thrown when data is read from a variable that has not been initialized
 */
public class AeUninitializedVariableException extends AeBpelException
{
   /**
    * Creates the exception with the proper namespace
    * @param aNamespace
    */
   public AeUninitializedVariableException(String aNamespace)
   {
      super(AeMessages.getString("AeUninitializedVariableException.Error"), AeFaultFactory.getFactory(aNamespace).getUninitializedVariable()); //$NON-NLS-1$
   }

}
 
