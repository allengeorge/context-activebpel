//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeCompensatableActivity.java,v 1.2 2005/11/09 21:55:4
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

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.activity.support.AeCompInfo;
import org.activebpel.rt.bpel.impl.activity.support.AeCompensationHandler;

/**
 * Interface mark an activity as compensatable.
 */
public interface IAeCompensatableActivity
{

   /**
    * Getter for the AeCompInfo object.
    */
   public AeCompInfo getCompInfo();
   
   /** 
    * Returns the compensation handler. If a handler has not been installed, then
    * an implicit handler will be installed and returned.
    * @return the compensation handler.
    */
   public AeCompensationHandler getCompensationHandler();
   
   /**
    * Terminates currently active compensation handler if the compensation
    * handler is executing.
    */
   public void terminateCompensationHandler() throws AeBusinessProcessException;
}
