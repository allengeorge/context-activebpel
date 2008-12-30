// $Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/base64/BASE64Encoder.java,v 1.1 2005/08/09 19:57:3
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
package org.activebpel.rt.base64;

/**
 * Replacement for <code>sun.misc.BASE64Encoder</code> that uses public domain
 * Base64 implementation.
 */
public class BASE64Encoder
{
   public String encode(byte[] aBytes)
   {
      return Base64.encodeBytes(aBytes);
   }

   public String encodeBuffer(byte[] aBytes)
   {
      return Base64.encodeBytes(aBytes);
   }
}
