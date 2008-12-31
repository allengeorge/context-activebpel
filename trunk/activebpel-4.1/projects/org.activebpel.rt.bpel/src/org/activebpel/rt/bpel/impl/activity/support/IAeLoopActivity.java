//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/support/IAeLoopActivity.java,v 1.3 2005/12/06 22:27:0
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

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.activity.IAeLoopControl;

/**
 * An interface for containers that loop. This includes the <while> activity
 * and the <foreach> activity. These containers support nested <aex:continue>
 * and <aex:break> activities.
 */
public interface IAeLoopActivity extends IAeBpelObject
{
   /** default value indicating there is no early termination in play */
   public static final int REASON_NONE = 0;
   
   /** 
    * value that indicates that the loop container is terminating its children 
    * because it was asked to continue
    */
   public static final int REASON_CONTINUE = 1;
   
   /** 
    * value that indicates that the loop container is terminating its children 
    * because it was asked to break
    */
   public static final int REASON_BREAK = 2;
   
   /**
    * Signals the container that it should continue its loop.
    *  
    * @param aSource
    * @throws AeBusinessProcessException
    */
   public void onContinue(IAeLoopControl aSource) throws AeBusinessProcessException;
   
   /**
    * Signals the container that it should break out of its loop.
    *  
    * @param aSource
    * @throws AeBusinessProcessException
    */
   public void onBreak(IAeLoopControl aSource) throws AeBusinessProcessException;
}
 
