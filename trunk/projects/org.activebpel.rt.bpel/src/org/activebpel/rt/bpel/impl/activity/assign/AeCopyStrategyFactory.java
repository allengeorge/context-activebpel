//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/AeCopyStrategyFactory.java,v 1.13 2006/12/14 15:10:3
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
package org.activebpel.rt.bpel.impl.activity.assign;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.impl.AeSelectionFailureException;

/**
 * Factory used to create IAeCopyStrategy instances. These instances are used to implement the logic of
 * the given copy operation.
 */
public class AeCopyStrategyFactory
{
   /** The strategy factory for bpws. */
   public static IAeCopyStrategyFactory mBPWSFactory = new AeBPWSCopyStrategyFactoryImpl();
   /** The strategy factory for ws-bpel. */
   public static IAeCopyStrategyFactory mWSBPELFactory = new AeWSBPELCopyStrategyFactoryImpl();
   
   /**
    * Gets the strategy factory for the given bpel namespace.
    * 
    * @param aBPELNamespace
    */
   public static IAeCopyStrategyFactory getFactory(String aBPELNamespace) throws AeBusinessProcessException
   {
      if (IAeBPELConstants.BPWS_NAMESPACE_URI.equals(aBPELNamespace))
      {
         return mBPWSFactory;
      }
      else if (IAeBPELConstants.WSBPEL_2_0_NAMESPACE_URI.equals(aBPELNamespace))
      {
         return mWSBPELFactory;
      }
      else
      {
         throw new AeBusinessProcessException(AeMessages.format("AeCopyStrategyFactory.MissingCopyStrategyFactoryError", aBPELNamespace)); //$NON-NLS-1$
      }
   }

   /**
    * Factory method for getting the strategy based on the data type
    *
    * @param aCopyOperation
    * @param aFromData
    * @param aToData
    * @throws AeSelectionFailureException
    */
   public static IAeCopyStrategy createStrategy(IAeCopyOperation aCopyOperation, Object aFromData,
         Object aToData) throws AeBusinessProcessException
   {
      return getFactory(aCopyOperation.getContext().getBPELNamespace()).createStrategy(aCopyOperation, aFromData, aToData);
   }
}
