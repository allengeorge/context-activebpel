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
package org.activebpel.rt.bpel.def.io.writers.def;

import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;
import org.w3c.dom.Element;

/**
 * A simple extension to the def visitor that exposes the ability to get a resulting Element
 * from the visitor once the visit is complete.  A writer def visitor visits a def and creates
 * a DOM Element from it, presumably with the intent of serializing the Def to XML.
 */
public interface IAeWriterDefVisitor extends IAeDefVisitor
{
   /**
    * Gets the Element that was created by the writer def visitor.
    */
   public Element getElement();
}
