//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.ext.expr.bsf/src/org/activebpel/rt/bpel/ext/expr/bsf/impl/python/AeBSFPythonExpressionRunner.java,v 1.2 2006/07/10 16:42:2
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
package org.activebpel.rt.bpel.ext.expr.bsf.impl.python;

import org.activebpel.rt.bpel.ext.expr.bsf.impl.AeBSFExpressionRunner;
import org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext;
import org.activebpel.rt.bpel.impl.expr.IAeExpressionTypeConverter;

/**
 * Implements a Python expression runner by extending the BSF expression runner. This trivial extension simply
 * provides the BSF engine type to use and provides a type converter.
 */
public class AeBSFPythonExpressionRunner extends AeBSFExpressionRunner
{
   /**
    * Overrides method to supply the python impl's BSF engine type.
    * 
    * @see org.activebpel.rt.bpel.ext.expr.bsf.impl.AeBSFExpressionRunner#getEngineType()
    */
   protected String getEngineType()
   {
      return "jython"; //$NON-NLS-1$
   }

   /**
    * @see org.activebpel.rt.bpel.impl.expr.AeAbstractExpressionRunner#createExpressionTypeConverter(org.activebpel.rt.bpel.impl.expr.IAeExpressionRunnerContext)
    */
   protected IAeExpressionTypeConverter createExpressionTypeConverter(IAeExpressionRunnerContext aContext)
   {
      return new AeBSFPythonExpressionTypeConverter();
   }
}
