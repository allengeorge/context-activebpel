// $Header: /Development/AEDevelopment/projects/org.activebpel.wsio/src/org/activebpel/wsio/invoke/AeNullInvokeHandler.java,v 1.1 2007/07/26 14:06:3
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
package org.activebpel.wsio.invoke;

import org.activebpel.wsio.IAeWebServiceResponse;

/**
 * Null invoke handler - this invoke handler simply returns null
 * from handleInvoke().  Useful, for example, for handling one-way
 * invokes.
 */
public class AeNullInvokeHandler implements IAeInvokeHandler
{
   /**
    * C'tor.
    */
   public AeNullInvokeHandler()
   {
   }

   /**
    * @see org.activebpel.wsio.invoke.IAeInvokeHandler#handleInvoke(org.activebpel.wsio.invoke.IAeInvoke, java.lang.String)
    */
   public IAeWebServiceResponse handleInvoke(IAeInvoke aInvoke, String aQueryData)
   {
      return null;
   }
}
