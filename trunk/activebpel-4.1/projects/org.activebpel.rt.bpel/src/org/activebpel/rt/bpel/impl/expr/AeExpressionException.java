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
package org.activebpel.rt.bpel.impl.expr;

import org.activebpel.rt.bpel.impl.AeBpelException;

/**
 * Runtime exception that wraps an AeException.  This is used when we want to throw an exception
 * from a place where it would not normally be allowed (for example, in a Jaxen VariableContext
 * implementation).
 */
public class AeExpressionException extends RuntimeException
{
   /** The wrapped AeException. */
   private AeBpelException mWrappedException;

   /**
    * Constructor.
    */
   public AeExpressionException(AeBpelException aWrappedException)
   {
      setWrappedException(aWrappedException);
   }

   /**
    * @return Returns the wrappedException.
    */
   public AeBpelException getWrappedException()
   {
      return mWrappedException;
   }

   /**
    * @param aWrappedException The wrappedException to set.
    */
   protected void setWrappedException(AeBpelException aWrappedException)
   {
      mWrappedException = aWrappedException;
   }
}
