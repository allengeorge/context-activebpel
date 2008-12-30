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
package org.activebpel.rt.xml.schema;

import org.exolab.castor.xml.schema.ComplexType;
import org.exolab.castor.xml.schema.ElementDecl;
import org.exolab.castor.xml.schema.SimpleType;
import org.exolab.castor.xml.schema.XMLType;

/**
 * Accepts all global elements that are complex
 */
public class AeAcceptAllGlobalElements implements IAeElementFilter
{
   /**
    * Returns true if the element is a global element of a complex type.
    * 
    * @see org.activebpel.rt.xml.schema.IAeElementFilter#accept(org.exolab.castor.xml.schema.ElementDecl)
    */
   public boolean accept(ElementDecl aElementDecl)
   {
      if (aElementDecl.getParent() == aElementDecl.getSchema())
      {
         XMLType type = aElementDecl.getReference() != null ? aElementDecl.getReference().getType() : aElementDecl.getType();
         return type instanceof ComplexType || type instanceof SimpleType;
      }
      return false;
   }
}
