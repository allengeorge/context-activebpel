// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/fastdom/IAeFastParent.java,v 1.1 2004/09/07 22:08:2
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
package org.activebpel.rt.bpel.impl.fastdom;

import java.util.List;

/**
 * Defines the interface for nodes that can be parents of other nodes.
 */
public interface IAeFastParent
{
   /**
    * Appends the specified child node to this node's child nodes.
    */
   public void appendChild(AeFastNode aChild);

   /**
    * Returns <code>List</code> of this nodes's child nodes.
    */
   public List getChildNodes();

   /**
    * Removes the specified node from this node's child nodes.
    *
    * @param aChild
    * @return <code>true</code> if and only if the removal occurred.
    */
   public boolean removeChild(AeFastNode aChild);
}
