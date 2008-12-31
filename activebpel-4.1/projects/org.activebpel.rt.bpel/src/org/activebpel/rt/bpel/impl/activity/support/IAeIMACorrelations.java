//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/IAeIMACorrelations.java,v 1.1 2006/10/26 13:58:5
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
package org.activebpel.rt.bpel.impl.activity.support; 

import java.util.Map;
import java.util.Set;

import org.activebpel.rt.bpel.impl.AeCorrelationViolationException;

/**
 * Provides additional methods for correlations used for inbound message 
 * activities. Inbound message activities include <code>receive</code>, 
 * <code>onMessage</code>, and <code>onEvent</code>
 */
public interface IAeIMACorrelations extends IAeCorrelations
{
   /**
    * Creates a map of correlation properties. These properties and values are 
    * used to correlate inbound messages to the activity. 
    * @throws AeCorrelationViolationException thrown if the one or more 
    *         correlation sets were supposed to initiated but weren't
    */
   public Map getInitiatedProperties() throws AeCorrelationViolationException;
   
   /**
    * Creates a set of location paths for the correlationSets used for the 
    * activity. This is used in order to detect conflicting receives.
    */
   public Set getCSPathsForConflictingReceives();
}
 
