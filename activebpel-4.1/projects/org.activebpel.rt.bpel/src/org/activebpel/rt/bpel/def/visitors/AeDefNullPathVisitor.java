//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeDefNullPathVisitor.java,v 1.1 2006/11/14 19:47:3
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
package org.activebpel.rt.bpel.def.visitors;

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.util.AeUtil;

/**
 * Visitor responsible for creating location paths for process definition objects
 * which <strong>do not</strong> already have location paths assigned. 
 */
public class AeDefNullPathVisitor extends AeDefPathVisitor
{

   /**
    * Ctor
    * @param aPathSegmentVisitor
    */
   public AeDefNullPathVisitor(IAeDefPathSegmentVisitor aPathSegmentVisitor)
   {
      super(aPathSegmentVisitor);
   }

   /**
    * Ctor.
    * @param aPathSegmentVisitor
    * @param aNextLocationId
    */
   public AeDefNullPathVisitor(IAeDefPathSegmentVisitor aPathSegmentVisitor, int aNextLocationId)
   {
      super(aPathSegmentVisitor, aNextLocationId);
   }

   /** 
    * Overrides method to update and record location id and path if and only if the def does
    * not already have a location id and path assigned. 
    * @see org.activebpel.rt.bpel.def.visitors.AeDefPathVisitor#updateLocationId(org.activebpel.rt.bpel.def.AeBaseDef)
    */
   protected void updateLocationId(AeBaseDef aDef)
   {
      if (AeUtil.isNullOrEmpty( aDef.getLocationPath() ))
      {
         super.updateLocationId(aDef);
      }
   }    
   
}
