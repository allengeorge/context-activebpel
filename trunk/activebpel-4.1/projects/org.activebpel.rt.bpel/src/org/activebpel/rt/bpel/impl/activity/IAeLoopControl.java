//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/IAeLoopControl.java,v 1.1 2005/08/18 21:35:4
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
package org.activebpel.rt.bpel.impl.activity; 

import org.activebpel.rt.bpel.impl.IAeBpelObject;

/**
 * Tagging interface for the loop controls continue and break that is used as
 * a param to the onContinue and onBreak methods to indicate the source
 * of the loop control that caused the change. This is used primarily for the 
 * parallel forEach since it needs to know which child instance is continuing
 * or breaking.  
 */
public interface IAeLoopControl extends IAeBpelObject
{

}
 
