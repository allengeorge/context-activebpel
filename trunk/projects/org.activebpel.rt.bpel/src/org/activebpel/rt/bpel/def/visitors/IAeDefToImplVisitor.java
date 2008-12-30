//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/IAeDefToImplVisitor.java,v 1.3 2006/09/27 00:36:2
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

import org.activebpel.rt.bpel.impl.IAeBusinessProcessInternal;

/**
 * interface for the def to impl visitor 
 */
public interface IAeDefToImplVisitor extends IAeDefVisitor
{
   /**
    * Gets the process that was created
    */
   public IAeBusinessProcessInternal getProcess();
   
   /**
    * Gets the traversal visitor
    */
   public IAeDefVisitor getTraversalVisitor();
   
   /**
    * Sets the traversal visitor
    * @param aDefVisitor
    */
   public void setTraversalVisitor(IAeDefVisitor aDefVisitor);
   
   /**
    * This will notify the process of all of the variables, partner links, and bpel objects created
    * by the visitor. 
    */
   public void reportObjects();
}
 
