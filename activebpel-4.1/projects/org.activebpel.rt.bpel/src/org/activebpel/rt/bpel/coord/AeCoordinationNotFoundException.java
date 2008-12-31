//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/coord/AeCoordinationNotFoundException.java,v 1.2 2006/06/26 16:50:4
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
package org.activebpel.rt.bpel.coord;

/**
 * Exception to indicate a coordination or context was not found in the server.
 */
public class AeCoordinationNotFoundException extends AeCoordinationException
{

   /**
    * Coordination lookup id or key..
    */
   private String mCoordinationIdKey;

   /**
    * Default ctor
    */
   public AeCoordinationNotFoundException()
   {
   }

   /**
    * @param aCoordinationIdKey
    */
   public AeCoordinationNotFoundException(String aCoordinationIdKey)
   {
      setCoordinationIdKey(aCoordinationIdKey);
   }

   /**
    * @param aRootCause
    */
   public AeCoordinationNotFoundException(Throwable aRootCause)
   {
      this("", aRootCause);//$NON-NLS-1$
   }

   /**
    * @param aCoordinationIdKey
    * @param aRootCause
    */
   public AeCoordinationNotFoundException(String aCoordinationIdKey, Throwable aRootCause)
   {
      super(aRootCause);
      setCoordinationIdKey(aCoordinationIdKey);
   }

   /**
    * @return Returns the coordinationIdKey.
    */
   public String getCoordinationIdKey()
   {
      return mCoordinationIdKey;
   }

   /**
    * @param aCoordinationIdKey The coordinationIdKey to set.
    */
   public void setCoordinationIdKey(String aCoordinationIdKey)
   {
      mCoordinationIdKey = aCoordinationIdKey;
   }
}
