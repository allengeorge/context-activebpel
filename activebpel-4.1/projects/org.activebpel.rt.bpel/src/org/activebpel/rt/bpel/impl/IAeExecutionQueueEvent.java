// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeExecutionQueueEvent.java,v 1.1 2004/07/31 00:33:3
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

/**
 * Defines execution queue events.
 */
public interface IAeExecutionQueueEvent
{
   /** Executing queue event id */
   public static final int EXECUTING = 1;
   /** Quiescent queue event id */
   public static final int QUIESCENT = 2;
   
   /**
    * Returns the event id for the execution queue event.
    */
   public int getEventID();
   
   /**
    * Returns the execution queue firing the event.
    */
   public AeExecutionQueue getExecutionQueue();
}
