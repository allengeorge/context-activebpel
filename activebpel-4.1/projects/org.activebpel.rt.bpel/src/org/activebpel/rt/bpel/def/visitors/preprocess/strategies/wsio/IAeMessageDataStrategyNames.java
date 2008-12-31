//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/preprocess/strategies/wsio/IAeMessageDataStrategyNames.java,v 1.1 2006/08/18 22:20:3
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
package org.activebpel.rt.bpel.def.visitors.preprocess.strategies.wsio; 

/**
 * Provides the names for the valid strategies for producing and consuming message data 
 */
public interface IAeMessageDataStrategyNames
{
   /** name of the strategy where the variable is not present because it's an empty message */
   public static final String EMPTY_MESSAGE = "empty-message"; //$NON-NLS-1$
   /** name of the strategy where a message variable is used to produce/consume the message data */
   public static final String MESSAGE_VARIABLE = "message-variable";  //$NON-NLS-1$
   /** name of the strategy where an element variable is used to produce/consume the message data (single part element message only) */
   public static final String ELEMENT_VARIABLE = "element-variable"; //$NON-NLS-1$
   /** name of the strategy where the invoke/reply contains the toParts construct which 
    * copies other variable data into an anonymous temporary WSDL message variable */
   public static final String TO_PARTS = "to-parts"; //$NON-NLS-1$
   /** name of the strategy where the receive/onEvent/onMessage contains the fromParts construct which 
    * copies the message data to other variables within scope*/
   public static final String FROM_PARTS = "from-parts"; //$NON-NLS-1$
}
 
