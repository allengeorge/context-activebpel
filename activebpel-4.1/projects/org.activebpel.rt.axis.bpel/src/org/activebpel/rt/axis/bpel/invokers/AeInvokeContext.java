//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/invokers/AeInvokeContext.java,v 1.2 2006/03/14 18:16:3
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
package org.activebpel.rt.axis.bpel.invokers;

import org.activebpel.wsio.invoke.AeInvokeResponse;
import org.activebpel.wsio.invoke.IAeInvoke;
import org.apache.axis.client.Call;

import javax.wsdl.BindingOperation;
import javax.wsdl.Operation;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

/**
 * Context information necessary to execute and invoke.
 */
public class AeInvokeContext
{
   /** IAeInvoke object */
   private IAeInvoke mInvoke;
   /** Invoke response object. */
   private AeInvokeResponse mResponse;
   /** The invoke operation. */
   private Operation mOperation;
   /** The client Call object. */
   private Call mCall;
   /** Message parts that are destined for the header, they should not be added to the body */
   private Collection mInputHeaderParts;
   /** Message parts that should be extracted from the response soap headers */
   private Collection mOutputHeaderParts;
   /** Contains binding info for the operation being invoked */
   private BindingOperation mBindingOperation;
   
   /**
    * Default Constructor.
    */
   public AeInvokeContext()
   {
   }

   /**
    * @return Returns the invoke.
    */
   public IAeInvoke getInvoke()
   {
      return mInvoke;
   }
   
   /**
    * @param aInvoke The invoke to set.
    */
   public void setInvoke(IAeInvoke aInvoke)
   {
      mInvoke = aInvoke;
   }
   
   /**
    * @return Returns the call.
    */
   public Call getCall()
   {
      return mCall;
   }

   /**
    * @param aCall The call to set.
    */
   public void setCall(Call aCall)
   {
      mCall = aCall;
   }
   
   /**
    * @return Returns the operation.
    */
   public Operation getOperation()
   {
      return mOperation;
   }

   /**
    * @param aOperation The operation to set.
    */
   public void setOperation(Operation aOperation)
   {
      mOperation = aOperation;
   }
   
   /**
    * @return Returns the response.
    */
   public AeInvokeResponse getResponse()
   {
      return mResponse;
   }

   /**
    * @param aResponse The response to set.
    */
   public void setResponse(AeInvokeResponse aResponse)
   {
      mResponse = aResponse;
   }

   /**
    * @return Returns the inputHeaderParts.
    */
   public Collection getInputHeaderParts()
   {
      if (mInputHeaderParts == null)
      {
         mInputHeaderParts = Collections.EMPTY_SET;
      }
      return mInputHeaderParts;
   }

   /**
    * @param aInputHeaderParts The inputHeaderParts to set.
    */
   public void setInputHeaderParts(Collection aInputHeaderParts)
   {
      mInputHeaderParts = aInputHeaderParts.isEmpty() ? null : new HashSet(aInputHeaderParts);
   }

   /**
    * @return Returns the outputHeaderParts.
    */
   public Collection getOutputHeaderParts()
   {
      if (mOutputHeaderParts == null)
      {
         mOutputHeaderParts = Collections.EMPTY_SET;
      }
      return mOutputHeaderParts;
   }

   /**
    * @param aOutputHeaderParts The outputHeaderParts to set.
    */
   public void setOutputHeaderParts(Collection aOutputHeaderParts)
   {
      mOutputHeaderParts = aOutputHeaderParts.isEmpty() ? null : new HashSet(aOutputHeaderParts);
   }

   /**
    * @return Returns the bindingOperation.
    */
   public BindingOperation getBindingOperation()
   {
      return mBindingOperation;
   }

   /**
    * @param aBindingOperation The bindingOperation to set.
    */
   public void setBindingOperation(BindingOperation aBindingOperation)
   {
      mBindingOperation = aBindingOperation;
   }
   
   /**
    * Returns true if the part name is supposed to go in the soap header of the request.
    * @param aPartName
    */
   public boolean isInputHeader(String aPartName)
   {
      return getInputHeaderParts().contains(aPartName);
   }
   
   /**
    * Returns true if the part name is supposed to be in the soap header of the response
    * @param aPartName
    */
   public boolean isOutputHeader(String aPartName)
   {
      return getOutputHeaderParts().contains(aPartName);
   }
}
