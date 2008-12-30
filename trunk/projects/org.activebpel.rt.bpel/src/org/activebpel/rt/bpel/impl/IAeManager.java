// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeManager.java,v 1.5 2004/10/26 20:24:2
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
 * Defines the methods that a manager must implement. The application architecture makes
 * use of managers to implement various pieces of the BPEL engine. By isolating the
 * logic in these various implementations (e.g. IAeQueueManager, IAeAlarmManager ...etc)
 * we're able to provide different implmentations without affecting the engine code proper.
 */
public interface IAeManager
{
   /**
    * Returns the engine for this queue manager.
    */
   public IAeBusinessProcessEngineInternal getEngine();

   /**
    * Sets the engine for this queue manager.
    */
   public void setEngine(IAeBusinessProcessEngineInternal aEngine);

   /**
    * Gets called once after the manager has been instantiated. If the manager runs
    * into any kind of fatal error during create then it should throw an exception which will 
    * halt the startup of the application.
    */
   public void create() throws Exception;
   
   /**
    * Called each time before the manager is going to start. 
    */
   public void prepareToStart() throws Exception;

   /**
    * Starts the manager running.
    */
   public void start() throws Exception;
   
   /**
    * Stops the manager. It may be restarted or destroyed from this point.  Note that this method
    * may be called by the engine even though a corresponding <code>start</code> method has not
    * yet been called.  This can happen when some other manager fails to start.
    */
   public void stop();
   
   /**
    * Destroys this instance of the manager. The manager should perform whatever cleanup 
    * work is necessary to shut down. 
    */
   public void destroy();
   
   /**
    * Used for the visitor pattern.
    * @param aVisitor
    */
   public void accept(IAeManagerVisitor aVisitor) throws Exception;
}
