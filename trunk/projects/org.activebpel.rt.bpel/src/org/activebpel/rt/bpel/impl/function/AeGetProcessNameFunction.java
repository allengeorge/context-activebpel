//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/function/AeGetProcessNameFunction.java,v 1.3 2007/09/04 15:51:3
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
package org.activebpel.rt.bpel.impl.function;

import java.util.List;

import org.activebpel.rt.bpel.function.AeFunctionCallException;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;


/**
 * Class representing the XPath function used by the expression evaluator to handle 
 * the BPEL extension function call getProcessName().
 */
public class AeGetProcessNameFunction extends AeAbstractBpelFunction
{
   /** The name of the function implemented */
   public static final String FUNCTION_NAME = "getProcessName"; //$NON-NLS-1$

   /**
    * Constructor.
    */
   public AeGetProcessNameFunction()
   {
       super(FUNCTION_NAME);
   }
   
   /**
    * Execution of XPath getProcessId function. 
    */
   public Object call(IAeFunctionExecutionContext aContext, List aArgs) throws AeFunctionCallException
   {
      return aContext.getAbstractBpelObject().getProcess().getName().getLocalPart();
   }
}
