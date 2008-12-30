// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeAbstractEntryPointVisitor.java,v 1.8 2006/06/26 16:50:4
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
package org.activebpel.rt.bpel.def.visitors;

import java.util.Iterator;

import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.activity.AeActivityPickDef;
import org.activebpel.rt.bpel.def.activity.AeActivityReceiveDef;
import org.activebpel.rt.bpel.def.activity.IAeReceiveActivityDef;
import org.activebpel.rt.bpel.def.activity.support.AeOnMessageDef;

/**
 * Abstract visitor for visiting entry point def objects
 * in an AeProcessDef object.
 * <br />
 * Looks for receive activities and pick activities with
 * onMessage children.
 */
abstract public class AeAbstractEntryPointVisitor extends AeAbstractDefVisitor
{
   /** holds onto the process def in case subclasses need access */
   private AeProcessDef mProcessDef;
   
   /**
    * Constructor.
    */
   protected AeAbstractEntryPointVisitor()
   {
      setTraversalVisitor( new AeTraversalVisitor( new AeDefTraverser(), this ) );
   }
   
   /**
    * Getter for the process def
    */
   protected AeProcessDef getProcessDef()
   {
      return mProcessDef;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeProcessDef)
    */
   public void visit(AeProcessDef aDef)
   {
      mProcessDef = aDef;
      super.visit(aDef);
   }

   /**
    * If the accept method return true call processReceive.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityReceiveDef)
    */
   public void visit(AeActivityReceiveDef aDef)
   {
      if( accept( aDef ) )
      {
         processEntryPoint( aDef );
      }
      super.visit(aDef);
   }
   
   /**
    * Returns a boolean indicating whether or not the receive should
    * be processed
    * @param aDef the receive
    * @return true if this receive should be processed further
    */
   protected boolean accept( AeActivityReceiveDef aDef )
   {
      return aDef.isCreateInstance();
   }

   /**
    * If the pick is acceptable, iterate over its onMessage children
    * and call process for each one.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.activity.AeActivityPickDef)
    */
   public void visit(AeActivityPickDef aDef)
   {
      // NOTE: a pick with a createInstance attribute that 
      // evaluates to true must have at least one onMessage
      // create keys for each onMessage
      if( accept( aDef ) )
      {
         for( Iterator iter = aDef.getOnMessageDefs(); iter.hasNext(); )
         {
            processEntryPoint( (AeOnMessageDef)iter.next() );
         }
      }
      super.visit(aDef);
   }
   
   /**
    * Determine if this pick should be processed further.
    * @param aDef
    * @return true if this pick should be processed further
    */
   protected boolean accept( AeActivityPickDef aDef )
   {
      return aDef.isCreateInstance();
   }

   /**
    * Subclasses will do something useful.
    * @param aDef an acceptable onMessage
    */
   abstract protected void processEntryPoint( IAeReceiveActivityDef aDef );
}
