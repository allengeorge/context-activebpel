//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeDynamicScopeParent.java,v 1.2 2006/11/06 23:36:1
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

import java.util.List;

import org.activebpel.rt.bpel.def.activity.AeActivityScopeDef;
import org.activebpel.rt.bpel.impl.visitors.IAeVisitable;

/**
 * Interface for BPEL constructs that contain scopes that are created through
 * the execution of an enclosing activity. In each case, the parent will create
 * zero or more scope instances during its normal execution. The parallel forEach
 * will create the proper number of scope instances in order to execute its loop.
 * The onEvent in WS-BPEL will create one instance each time its message arrives. 
 * The onAlarm will create one instance each time a repeatEvery expression causes
 * the onAlarm to execute.
 */
public interface IAeDynamicScopeParent extends IAeActivityParent, IAeVisitable
{
   /**
    * Gets the list of children. 
    */
   public List getChildren();
   
   /**
    * Gets the child scope def. This is the def that is visited to produce the
    * dynamic scope instance.
    */
   public AeActivityScopeDef getChildScopeDef();
   
   /**
    * Gets the list of compensatable children. 
    */
   public List getCompensatableChildren();
   
   /**
    * Setter for the instance value
    * @param aInstanceValue
    */
   public void setInstanceValue(int aInstanceValue);
   
   /**
    * Getter for the instance value
    */
   public int getInstanceValue();
}
