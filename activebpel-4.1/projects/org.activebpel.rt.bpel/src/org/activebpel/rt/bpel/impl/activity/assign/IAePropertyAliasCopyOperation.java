//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/IAePropertyAliasCopyOperation.java,v 1.2 2006/06/26 16:50:3
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
package org.activebpel.rt.bpel.impl.activity.assign; 

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.wsdl.def.IAePropertyAlias;

/**
 * Interface for copy operation components that use property aliases to select the data for the
 * source or target. 
 */
public interface IAePropertyAliasCopyOperation
{
   /**
    * Gets the property alias for use in selecting the property
    * @throws AeBusinessProcessException
    */
   public IAePropertyAlias getPropertyAlias() throws AeBusinessProcessException;
   
   /**
    * Gets the data to use for the property alias query.
    * @param aPropAlias
    * @throws AeBusinessProcessException 
    */
   public Object getDataForQueryContext(IAePropertyAlias aPropAlias) throws AeBusinessProcessException;
}
 
