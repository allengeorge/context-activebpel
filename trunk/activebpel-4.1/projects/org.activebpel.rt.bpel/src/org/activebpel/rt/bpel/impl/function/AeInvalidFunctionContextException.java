//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/function/AeInvalidFunctionContextException.java,v 1.1 2005/06/08 12:50:3
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

import org.activebpel.rt.AeException;

/**
 * Generated when the addition of a function context fails.
 */
public class AeInvalidFunctionContextException extends AeException
{
   /**
    * Constructor. 
    */
   public AeInvalidFunctionContextException()
   {
      super();
   }

   /**
    * Constructor. 
    * @param aInfo
    */
   public AeInvalidFunctionContextException(String aInfo)
   {
      super(aInfo);
   }

   /**
    * Constructor. 
    * @param aInfo
    * @param aRootCause
    */
   public AeInvalidFunctionContextException(String aInfo, Throwable aRootCause)
   {
      super(aInfo, aRootCause);
   }

   /**
    * Constructor. 
    * @param aRootCause
    */
   public AeInvalidFunctionContextException(Throwable aRootCause)
   {
      super(aRootCause);
   }
}
