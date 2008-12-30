// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/support/IAeQueryParentDef.java,v 1.2 2006/08/18 19:50:3
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
package org.activebpel.rt.bpel.def.activity.support;

/**
 * All defs that can parent a <code>query</code> construct should implement this interface.
 */
public interface IAeQueryParentDef
{
   /**
    * Called to set the query def child on the parent.
    * 
    * @param aQueryDef
    */
   public void setQueryDef(AeQueryDef aQueryDef);
   
   /**
    * Called to remove the query def from the parent.
    */
   public void removeQueryDef();
}
