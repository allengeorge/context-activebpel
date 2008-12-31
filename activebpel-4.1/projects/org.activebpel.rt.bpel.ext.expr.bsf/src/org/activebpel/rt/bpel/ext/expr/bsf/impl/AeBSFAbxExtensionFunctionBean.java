//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr.bsf/src/org/activebpel/rt/bpel/ext/expr/bsf/impl/AeBSFAbxExtensionFunctionBean.java,v 1.4 2006/07/10 16:42:2
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
package org.activebpel.rt.bpel.ext.expr.bsf.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.function.IAeFunctionExecutionContext;

/**
 * This class provides convenient access to the set of ActiveBPEL extension functions, such
 * as abx:getProcessId().  This is a simple bean that exposes 
 */
public class AeBSFAbxExtensionFunctionBean extends AeBSFAbstractExtensionFunctionBean
{
   /**
    * Constructs the bean with the given function context.
    * 
    * @param aFunctionExecutionContext
    */
   public AeBSFAbxExtensionFunctionBean(IAeFunctionExecutionContext aFunctionExecutionContext)
   {
      super(aFunctionExecutionContext);
   }

   /**
    * @see org.activebpel.rt.bpel.ext.expr.bsf.impl.AeBSFAbstractExtensionFunctionBean#getNamespace()
    */
   protected String getNamespace()
   {
      return IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI;
   }

   /**
    * Implements the abx:getProcessId() method.
    */
   public Object getProcessId()
   {
      return callFunction("getProcessId", Collections.EMPTY_LIST); //$NON-NLS-1$
   }
   
   /**
   * An implementation of abx:getMyRoleProperty().
   * 
   * @param aArg1
   * @param aArg2
   * @param aArg3
   */
  public Object getMyRoleProperty(Object aArg1, Object aArg2, Object aArg3)
  {
     try
     {
        List args = new ArrayList();
        args.add(aArg1);
        args.add(aArg2);
        if (aArg3 != null) args.add(aArg3);
        return callFunction("getMyRoleProperty", args); //$NON-NLS-1$
     }
     catch (Exception e)
     {
        throw new RuntimeException(e);
     }
  }
}
