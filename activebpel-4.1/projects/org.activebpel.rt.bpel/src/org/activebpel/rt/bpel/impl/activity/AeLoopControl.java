//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/AeLoopControl.java,v 1.2 2005/08/18 21:35:4
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

import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.AeStaticAnalysisException;
import org.activebpel.rt.bpel.def.AeActivityDef;
import org.activebpel.rt.bpel.impl.IAeActivityParent;
import org.activebpel.rt.bpel.impl.IAeBpelObject;
import org.activebpel.rt.bpel.impl.activity.support.IAeLoopActivity;

/**
 * A base class for the loop control activities "break" and "continue" 
 */
public abstract class AeLoopControl extends AeActivityImpl implements IAeLoopControl
{
   /**
    * Constructor for subclasses to pass their specific activity def and parent.
    * @param aActivityDef
    * @param aParent
    */
   public AeLoopControl(AeActivityDef aActivityDef, IAeActivityParent aParent)
   {
      super(aActivityDef, aParent);
   }
   
   /**
    * Walks up the parent axis looking for the enclosing loop container.
    * @throws AeStaticAnalysisException
    */
   protected IAeLoopActivity getEnclosedLoopContainer() throws AeStaticAnalysisException
   {
      IAeBpelObject parent = getParent();
      while(parent != null && !(parent instanceof IAeLoopActivity))
      {
         parent = parent.getParent();
      }
      
      if (parent == null)
      {
         staticAnalysisFailure(AeMessages.format("AeActivityContinueImpl.NO_LOOP_CONTAINER", getLocationPath())); //$NON-NLS-1$
      }
      return (IAeLoopActivity)parent;
   }
   

}
 
