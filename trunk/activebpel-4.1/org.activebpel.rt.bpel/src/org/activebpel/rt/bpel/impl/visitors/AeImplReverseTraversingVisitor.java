// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/visitors/AeImplReverseTraversingVisitor.java,v 1.1 2007/05/23 15:28:0
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
package org.activebpel.rt.bpel.impl.visitors;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;

/**
 * A visitor that traverses a BPEL implementation object tree.
 */
public class AeImplReverseTraversingVisitor extends AeImplTraversingVisitor
{
   /**
    * Traverses the specified implementation object's children, if any.
    *
    * @param aImpl The implementation object to traverse.
    */
   protected void traverse(AeAbstractBpelObject aImpl) throws AeBusinessProcessException
   {
      if (aImpl.getParent() != null && aImpl.getParent() instanceof IAeVisitable)
         ((IAeVisitable) aImpl.getParent()).accept(this);
   }
}
