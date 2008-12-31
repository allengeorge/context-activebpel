// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeFaultFactory.java,v 1.18 2006/11/06 21:22:5
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

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.impl.activity.support.AeFault;
import org.activebpel.rt.message.IAeMessageData;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;

/**
 * Factory for selecting the IAeFaultFactory by namespace
 */
public class AeFaultFactory
{
   /** provides faults for WSBPEL */
   private static final IAeFaultFactory WSBPEL = new AeWSBPELFaultFactory(IAeBPELConstants.WSBPEL_2_0_NAMESPACE_URI);
   /** provides faults for BPEL4WS */
   private static final IAeFaultFactory BPEL4WS = new AeBPWSFaultFactory(IAeBPELConstants.BPWS_NAMESPACE_URI);

   /** Identifier for internal ae:systemError fault: used for unrecoverable system errors */
   protected static final String SYSTEM_ERROR = "systemError"; //$NON-NLS-1$
   /** Identifier for internal ae:bad process fault: used when we encounter invalid BPEL (static analysis should catch all of these issues */
   protected static final String BAD_PROCESS = "badProcess"; //$NON-NLS-1$

   /**
    * no ctor access
    */
   private AeFaultFactory()
   {
   }

   /**
    * Gets the fault factory given a namespace value
    * @param aNamespace
    */
   public static IAeFaultFactory getFactory(String aNamespace)
   {
      if (IAeBPELConstants.WSBPEL_2_0_NAMESPACE_URI.equals(aNamespace))
         return WSBPEL;
      else
         return BPEL4WS;
   }

   /**
    * Gets system fault
    * @param aThrowable
    * @param aMessage
    */
   public static IAeFault getSystemErrorFault(Throwable aThrowable, String aMessage)
   {
      IAeFault fault = makeAeNamespaceFault(SYSTEM_ERROR);
      fault.setInfo(aMessage);
      if (aThrowable != null)
         fault.setDetailedInfo(AeUtil.getStacktrace(aThrowable));
      return fault;
   }

   /**
    * Gets system fault
    * @param aThrowable
    */
   public static IAeFault getSystemErrorFault(Throwable aThrowable)
   {
      return getSystemErrorFault(aThrowable, aThrowable.getLocalizedMessage());
   }

   /**
    * The system errors are the same for the different versions so this is
    * provided as a convenience
    */
   public static IAeFault getSystemErrorFault()
   {
      return getSystemErrorFault(null, null);
   }

   /**
    * Makes a fault in the system namespace
    * @param aName
    */
   protected static IAeFault makeAeNamespaceFault(String aName)
   {
      return new AeFault(new QName(IAeBPELConstants.AE_NAMESPACE_URI, aName), (IAeMessageData) null);
   }

   /**
    * Makes a system fault with the specified faultname and detailed error info
    * @param aFaultName
    * @param aThrowable
    * @param aMessage
    */
   protected static IAeFault makeAeNamespaceFault(String aFaultName, Throwable aThrowable, String aMessage)
   {
      IAeFault fault = makeAeNamespaceFault(aFaultName);
      fault.setInfo(aMessage);
      fault.setDetailedInfo(AeUtil.getStacktrace(aThrowable));
      return fault;
   }

   /**
    * Gets the 'bad process' fault instance.
    */
   public static IAeFault getBadProcess()
   {
      return makeAeNamespaceFault(BAD_PROCESS);
   }

   /**
    * Returns a copy of the given fault that is rethrowable, suspendable and the fault source
    * reset to <code>null</code>.
    * @param aFault
    * @return copy of fault that is suspendable and rethrowable.
    */
   public static IAeFault makeUncaughtFault(IAeFault aFault)
   {
      AeFault copy = null;
      if (aFault.hasElementData())
      {
         copy = new AeFault(aFault.getFaultName(), AeXmlUtil.cloneElement( aFault.getElementData() ));
      }
      else if (aFault.hasMessageData() )
      {
         copy = new AeFault(aFault.getFaultName(), (IAeMessageData)aFault.getMessageData().clone());
      }
      else
      {
         copy = new AeFault(aFault.getFaultName(), (IAeMessageData) null);
      }
      copy.setDetailedInfo( aFault.getDetailedInfo() );
      copy.setInfo( aFault.getInfo() );
      copy.setSource( null );
      copy.setSuspendable( true);
      copy.setRethrowable( true );
      return copy;
   }
}
