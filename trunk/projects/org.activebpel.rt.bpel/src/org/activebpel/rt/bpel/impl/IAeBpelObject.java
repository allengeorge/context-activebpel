// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeBpelObject.java,v 1.21 2006/10/12 20:15:2
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

import java.util.Iterator;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeFault;
import org.activebpel.rt.bpel.IAeLocatableObject;
import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.impl.activity.support.AeCorrelationSet;

/**
 * Base interface for Bpel object implementations. 
 */
public interface IAeBpelObject extends IAeLocatableObject
{
   /**
    * Returns the current state
    */
   public AeBpelState getState();
   
   /**
    * All implementation objects except the process will have a parent activity.
    */
   public IAeBpelObject getParent();
   
   /**
    * Returns an Iterator for the bpel object's child objects which participate
    * in the state change propagation. For structured activities, this merely
    * returns an Iterator for the child activity or activities. In some cases
    * though, like Scope, we're returning objects in addition to the 
    * single child activity. This is because scope needs to propagate the 
    *  
    * @return Iterator containing IAeBpelObjets
    */
   public Iterator getChildrenForStateChange();
   
   /**
    * Gets a variable by name. If the object implementing this interface is a scope
    * or process then it'll check to see if it owns the variable, otherwise the
    * request should propogate up to the parent activity.
    */
   public IAeVariable findVariable(String aName);
   
   /**
    * Gets the <code>partnerLink</code> by name
    */
   public AePartnerLink findPartnerLink(String aName);
   
   /**
    * Getter for the owning process.
    */
   public IAeBusinessProcessInternal getProcess();
   
   /**
    * Finds the correlation set by name. If the activity does not define this correlation set then it will
    * keep walking up the parent hierarchy until it is resolved.
    */
   public AeCorrelationSet findCorrelationSet(String aName);

   /**
    * Returns the suppress join condition attribute for this bpel object. This 
    * attribute is configurable at the activity level and inherited from the parent
    * object unless it is overridden.
    */
   public boolean isSuppressJoinConditionFailure();
   
   /**
    * Returns the namespace associated with the prefix from the associated model.
    */
   public String translateNamespacePrefixToUri(String aPrefix);
   
   /**
    * All implementions should return true. Only activities
    * may not be ready as a result of links not being ready.
    */
   public boolean isReadyToExecute();
   
   /**
    * All non-activity implementions should return true. Only activities
    * may not execute as a result of link status or join conditions being false.
    */
   public boolean isNotDeadPath() throws AeBusinessProcessException;
   
   /**
    * Setter for the state.
    * @param aState
    */
   public void setState(AeBpelState aState) throws AeBusinessProcessException;
   
   /**
    * Signals the object that it has faulted with the fault passed in.
    * @param aFault
    */
   public void setFaultedState(IAeFault aFault) throws AeBusinessProcessException;

   /**
    * Returns true if the child is in a state that can be terminated.
    */
   public boolean isTerminatable();

   /**
    * Returns true if the object is currently in the process of terminating.
    */
   public boolean isTerminating();
   
   /**
    * Returns true if the object is currently faulting or in the process of being retried
    */
   public boolean isFaultingOrRetrying();
   
   /**
    * Getter for the BPEL namespace
    */
   public String getBPELNamespace();
   
   /** 
    * @return Returns true if the object can be completed.
    */
   public boolean isCompletable();
}
