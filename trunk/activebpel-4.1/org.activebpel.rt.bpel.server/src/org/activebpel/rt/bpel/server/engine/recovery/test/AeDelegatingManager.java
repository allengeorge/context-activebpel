// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/recovery/test/AeDelegatingManager.java,v 1.1 2005/07/12 00:26:3
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
package org.activebpel.rt.bpel.server.engine.recovery.test;

import org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal;
import org.activebpel.rt.bpel.impl.IAeManager;
import org.activebpel.rt.bpel.impl.IAeManagerVisitor;

/**
 * Implements a manager that delegates all method calls to an underlying
 * delegate manager.
 */
public class AeDelegatingManager implements IAeManager
{
   /** The underlying delegate manager. */
   private final IAeManager mBaseManager;

   /**
    * Constructs a manager that delegates all method calls to the given base
    * manager.
    *
    * @param aBaseManager
    */
   public AeDelegatingManager(IAeManager aBaseManager)
   {
      mBaseManager = aBaseManager;
   }

   /**
    * Returns the base process manager.
    */
   protected IAeManager getBaseManager()
   {
      return mBaseManager;
   }

   /*======================================================================
    * org.activebpel.rt.bpel.impl.IAeManager methods
    *======================================================================
    */

   /**
    * @see org.activebpel.rt.bpel.impl.IAeManager#accept(org.activebpel.rt.bpel.impl.IAeManagerVisitor)
    */
   public void accept(IAeManagerVisitor aVisitor) throws Exception
   {
      getBaseManager().accept(aVisitor);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeManager#create()
    */
   public void create() throws Exception
   {
      getBaseManager().create();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeManager#destroy()
    */
   public void destroy()
   {
      getBaseManager().destroy();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeManager#getEngine()
    */
   public IAeBusinessProcessEngineInternal getEngine()
   {
      return getBaseManager().getEngine();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeManager#prepareToStart()
    */
   public void prepareToStart() throws Exception
   {
      getBaseManager().prepareToStart();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeManager#setEngine(org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal)
    */
   public void setEngine(IAeBusinessProcessEngineInternal aEngine)
   {
      getBaseManager().setEngine(aEngine);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeManager#start()
    */
   public void start() throws Exception
   {
      getBaseManager().start();
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeManager#stop()
    */
   public void stop()
   {
      getBaseManager().stop();
   }
}
