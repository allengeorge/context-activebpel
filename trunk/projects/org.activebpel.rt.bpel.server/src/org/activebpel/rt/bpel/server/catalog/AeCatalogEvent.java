// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/catalog/AeCatalogEvent.java,v 1.1 2006/07/18 20:05:3
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
package org.activebpel.rt.bpel.server.catalog;

/**
 *  Provides catalog resource notification information.
 */
public class AeCatalogEvent
{
   /** the location hint */
   private String mLocationHint;
   /** the replace boolean value */
   private boolean mReplacementFlag;
   
   /**
    * Factory create method.
    * @param aLocationHint
    * @param aIsReplacement
    */
   public static AeCatalogEvent create( String aLocationHint, boolean aIsReplacement )
   {
      return new AeCatalogEvent( aLocationHint, aIsReplacement );
   }

   /**
    * Constructor.
    * @param aLocationHint
    * @param aIsReplacement
    */
   protected AeCatalogEvent( String aLocationHint, boolean aIsReplacement )
   {
      mLocationHint = aLocationHint;
      mReplacementFlag = aIsReplacement;
   }
   
   /**
    * Accessor for location hint.
    */
   public String getLocationHint()
   {
      return mLocationHint;
   }
   
   /**
    * @return Returns true if this is a wsdl replacement.
    */
   public boolean isReplacement()
   {
      return mReplacementFlag;
   }
}
