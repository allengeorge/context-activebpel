// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeCorrelationViolationException.java,v 1.6 2006/08/15 18:32:5
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
import org.activebpel.rt.bpel.IAeFault;

/**
 * models a bpws:correlationViolation
 */
public class AeCorrelationViolationException extends AeBpelException
{
   /** an activity attempted to access a correlation set before it was initialized */
   public static final int UNINITIALIZED_CORRELATION_SET = 0;
   /** the properties for a correlation were going to be changed */
   public static final int IMMUTABLE = 1;
   /** a property contained a null value after being updated */
   public static final int NULL_VALUE = 2;
   /** a subprocess invoke where create instance is false */
   public static final int NOT_CREATEINSTANCE = 3;
   /** signals an attempt to initialize a correlationSet after its been initialized*/
   public static final int ALREADY_INITIALIZED = 4;
   
   private static final String[] sMessages = 
   {
      AeMessages.getString("AeCorrelationViolationException.0"), //$NON-NLS-1$
      AeMessages.getString("AeCorrelationViolationException.1"), //$NON-NLS-1$
      AeMessages.getString("AeCorrelationViolationException.2"), //$NON-NLS-1$
      AeMessages.getString("AeCorrelationViolationException.3"),  //$NON-NLS-1$
      AeMessages.getString("AeCorrelationViolationException.4")  //$NON-NLS-1$
   };
   
   /**
    * Ctor accepts namespace and error code
    * @param aNamespace
    * @param aCode
    */
   public AeCorrelationViolationException(String aNamespace, int aCode)
   {
      super(sMessages[aCode], AeFaultFactory.getFactory(aNamespace).getCorrelationViolation());
   }
   
   /**
    * Ctor for subclasses
    * @param aMessage
    * @param aFault
    */
   protected AeCorrelationViolationException(String aMessage, IAeFault aFault)
   {
      super(aMessage, aFault);
   }
}
