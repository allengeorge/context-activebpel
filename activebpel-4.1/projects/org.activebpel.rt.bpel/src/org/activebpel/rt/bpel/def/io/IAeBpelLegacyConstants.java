//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/IAeBpelLegacyConstants.java,v 1.2 2006/06/26 16:50:2
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
package org.activebpel.rt.bpel.def.io; 

/**
 * Interface for attribute or element names that changed from version 1.1 to 2.0. 
 */
public interface IAeBpelLegacyConstants
{
   /** new attr is successfulBranchesOnly */
   public static final String COUNT_COMPLETED_BRANCHES_ONLY = "countCompletedBranchesOnly"; //$NON-NLS-1$
   public static final String TAG_VARIABLE_ACCESS_SERIALIZABLE = "variableAccessSerializable"; //$NON-NLS-1$

   /** Switch activity is replace with the if activity in 2.0. */
   public static final String TAG_CASE = "case"; //$NON-NLS-1$
   public static final String TAG_OTHERWISE = "otherwise"; //$NON-NLS-1$
   public static final String TAG_SWITCH = "switch"; //$NON-NLS-1$

   /** terminate activity is now 'exit'. */
   public static final String TAG_TERMINATE = "terminate"; //$NON-NLS-1$
}
