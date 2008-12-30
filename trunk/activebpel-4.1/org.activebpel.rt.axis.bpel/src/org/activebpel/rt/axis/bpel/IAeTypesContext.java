//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/IAeTypesContext.java,v 1.2 2005/06/22 17:10:1
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
package org.activebpel.rt.axis.bpel; 

import javax.xml.namespace.QName;

import org.exolab.castor.xml.schema.ElementDecl;
import org.exolab.castor.xml.schema.XMLType;

/**
 * Defines methods for looking up schema types. 
 */
public interface IAeTypesContext
{
   /**
    * Finds the element with the given name or returns null if not found within
    * the current context.
    * 
    * @param aElementName
    */
   public ElementDecl findElement(QName aElementName);
   
   /**
    * Finds the type with the given name or returns null if not found within
    * the current context.
    * 
    * @param aTypeName
    */
   public XMLType findType(QName aTypeName);
}
 
