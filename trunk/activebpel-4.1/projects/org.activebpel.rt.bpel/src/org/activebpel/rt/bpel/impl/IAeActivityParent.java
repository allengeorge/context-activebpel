// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeActivityParent.java,v 1.2 2004/07/08 13:09:5
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
package org.activebpel.rt.bpel.impl;

import org.activebpel.rt.bpel.IAeActivity;

/**
 * Describes objects that can be a parent of an activity.
 */
public interface IAeActivityParent extends IAeBpelObject
{
   /**
    * Adds the activity to this parent. The method implies that
    * there may be more than 1 activity as a child of this parent.
    * This is a side effect of deciding to have a single interface
    * to handle cases with a single child and those with multiple
    * children.
    */
   public void addActivity(IAeActivity aActivity);
}
