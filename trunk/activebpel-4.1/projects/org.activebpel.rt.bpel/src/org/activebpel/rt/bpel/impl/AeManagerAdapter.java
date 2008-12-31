// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeManagerAdapter.java,v 1.4 2004/08/30 12:40:1
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
 * A base class for managers that don't want to have to implement all of the manager methods.
 */
public abstract class AeManagerAdapter implements IAeManager
{
   /** The engine for this manager. */
   private IAeBusinessProcessEngineInternal mEngine;

   /**
    * @see org.activebpel.rt.bpel.impl.IAeQueueManager#getEngine()
    */
   public IAeBusinessProcessEngineInternal getEngine()
   {
      return mEngine;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeManager#setEngine(org.activebpel.rt.bpel.impl.IAeBusinessProcessEngineInternal)
    */
   public void setEngine(IAeBusinessProcessEngineInternal aEngine)
   {
      mEngine = aEngine;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeManager#create()
    */
   public void create() throws Exception
   {
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.IAeManager#prepareToStart()
    */
   public void prepareToStart() throws Exception
   {
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeManager#start()
    */
   public void start() throws Exception
   {
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeManager#stop()
    */
   public void stop()
   {
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeManager#destroy()
    */
   public void destroy()
   {
   }

   /**
    * @see org.activebpel.rt.bpel.impl.IAeManager#accept(org.activebpel.rt.bpel.impl.IAeManagerVisitor)
    */
   public void accept(IAeManagerVisitor aVisitor) throws Exception
   {
      aVisitor.visit(this);
   }
}
