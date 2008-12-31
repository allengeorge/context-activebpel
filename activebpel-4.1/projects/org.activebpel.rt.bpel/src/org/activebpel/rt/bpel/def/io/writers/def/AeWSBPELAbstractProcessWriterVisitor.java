//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/writers/def/AeWSBPELAbstractProcessWriterVisitor.java,v 1.2 2006/11/04 16:34:2
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

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.xml.IAeMutableNamespaceContext;
import org.w3c.dom.Element;

/**
 * A WS-BPEL 2.0  Abstract Process implementation of a writer visitor.
 */
public class AeWSBPELAbstractProcessWriterVisitor extends AeWSBPELWriterVisitor
{

   /**
    * Constructs a ws-bpel 2.0 abstract process writer visitor.
    *
    * @param aDef
    * @param aParentElement
    * @param aNamespace
    * @param aTagName
    */
   public AeWSBPELAbstractProcessWriterVisitor(AeBaseDef aDef, Element aParentElement, String aNamespace, String aTagName)
   {
      super(aDef, aParentElement, aNamespace, aTagName);
   }

   /**
    * Overrides method to write the attribute in the default (abstract process) namespace.
    * @see org.activebpel.rt.bpel.def.io.writers.def.AeWSBPELWriterVisitor#writeAbstractProcessProfileAttribute(org.activebpel.rt.bpel.def.AeProcessDef, org.activebpel.rt.xml.IAeMutableNamespaceContext)
    */
   protected void writeAbstractProcessProfileAttribute(AeProcessDef aDef, IAeMutableNamespaceContext aNsContext)
   {
      setAttribute(IAeBPELConstants.TAG_ABSTRACT_PROCESS_PROFILE, aDef.getAbstractProcessProfile());
   }
}
