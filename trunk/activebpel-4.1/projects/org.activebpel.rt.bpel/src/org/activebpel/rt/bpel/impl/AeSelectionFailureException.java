//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeSelectionFailureException.java,v 1.4 2007/05/23 15:25:0
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
 * Standard BPEL fault thrown when there is an error selecting data for a copy operation
 * or using a propertyAlias in selecting correlation data. 
 */
public class AeSelectionFailureException extends AeBpelException
{
   // fixme (MF) create real error messages for the errors below

   /** error message when the expression failed to select a single EII or TII */
   private static final String SELECTION_COUNT_ERROR = "AeSelectionFailureException.SELECTION_COUNT_ERROR"; //$NON-NLS-1$
   /** signals a selection failure due to having the copy op's keepSrcElementName option enabled but they failed to select an element */
   public static final String KEEP_SRC_ELEMENT_ERROR = "AeSelectionFailureException.KEEP_SRC_ELEMENT_ERROR"; //$NON-NLS-1$
   
   /**
    * Accepts the reason code uses it for the message.
    * 
    * @param aReason
    */
   public AeSelectionFailureException(String aNamespace, String aReason)
   {
      super(aReason, AeFaultFactory.getFactory(aNamespace).getSelectionFailure());
   }
   
   /**
    * Accepts the number of items selected by the expression.
    * @param aSelectionCount
    */
   public AeSelectionFailureException(String aNamespace, int aSelectionCount)
   {
      super(AeMessages.format(SELECTION_COUNT_ERROR, new Integer(aSelectionCount)), AeFaultFactory.getFactory(aNamespace).getSelectionFailure());
   }
}
 
