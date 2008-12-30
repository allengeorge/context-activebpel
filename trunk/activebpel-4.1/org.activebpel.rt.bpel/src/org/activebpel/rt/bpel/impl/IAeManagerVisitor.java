// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeManagerVisitor.java,v 1.1 2004/08/25 22:28:0
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
 * Visitor interface for managers.  
 */
public interface IAeManagerVisitor
{
   /** Visitor that calls the create method */
   public static final IAeManagerVisitor CREATE = new IAeManagerVisitor()
   {
      public void visit(IAeManager aManager) throws Exception
      {
         aManager.create(); 
      }
   };

   /** Visitor that calls the prepareToStart method */
   public static final IAeManagerVisitor PREPARE = new IAeManagerVisitor()
   {
      public void visit(IAeManager aManager) throws Exception
      {
         aManager.prepareToStart(); 
      }
   };

   /** Visitor that calls the start method */
   public static final IAeManagerVisitor START = new IAeManagerVisitor()
   {
      public void visit(IAeManager aManager) throws Exception
      {
         aManager.start(); 
      }
   };
   
   /** Visitor that calls the stop method */
   public static final IAeManagerVisitor STOP = new IAeManagerVisitor()
   {
      public void visit(IAeManager aManager) throws Exception
      {
         aManager.stop(); 
      }
   };
   
   /** Visitor that calls the destroy method */
   public static final IAeManagerVisitor DESTROY = new IAeManagerVisitor()
   {
      public void visit(IAeManager aManager) throws Exception
      {
         aManager.destroy(); 
      }
   };

   /**
    * Visitor method for managers.
    * @param aManager
    */
   public void visit(IAeManager aManager) throws Exception;
}
