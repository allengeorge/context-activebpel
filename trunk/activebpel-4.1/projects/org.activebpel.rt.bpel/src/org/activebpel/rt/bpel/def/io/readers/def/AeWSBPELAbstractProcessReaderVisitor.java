//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/readers/def/AeWSBPELAbstractProcessReaderVisitor.java,v 1.1 2006/10/24 21:23:5
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
package org.activebpel.rt.bpel.def.io.readers.def;

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.AeProcessDef;
import org.w3c.dom.Element;

/**
 * Implements a WS-BPEL 2.0 abstract process version of the def reader visitor.
 */
public class AeWSBPELAbstractProcessReaderVisitor extends AeWSBPELReaderVisitor
{
   /**
    * Constructor.
    */
   public AeWSBPELAbstractProcessReaderVisitor(AeBaseDef aParentDef, Element aElement)
   {
      super(aParentDef, aElement);
   }

   /**
    * Overrides method to read abstract process profile attribute.
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitor#visit(org.activebpel.rt.bpel.def.AeProcessDef)
    */
   public void visit(AeProcessDef aDef)
   {
      super.visit(aDef);
      aDef.setAbstractProcessProfile(getAttribute(TAG_ABSTRACT_PROCESS_PROFILE));
   }
}
