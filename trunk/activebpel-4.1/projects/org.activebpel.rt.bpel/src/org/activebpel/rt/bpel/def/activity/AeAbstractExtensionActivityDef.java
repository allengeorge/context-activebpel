//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/activity/AeAbstractExtensionActivityDef.java,v 1.1 2007/09/12 02:48:1
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
package org.activebpel.rt.bpel.def.activity; 

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.AeActivityDef;

/**
 * Base class for defs that model the contents of an extension activity.
 * Extension activities are either understood or not understood. Those that are
 * understood add new behavior to the engine through an extensibility layer.
 * Those that are not understood, either cause the process to fail to validate
 * (assuming that they were marked as "mustUnderstand") or convert to empty
 * activities and are essentially no-ops.
 */
public abstract class AeAbstractExtensionActivityDef extends AeActivityDef implements IAeExtensionActivityDef
{
   /** The unknown activity's element name. */
   private QName mElementName;
   /** The map of attributes found on the unknown extension activity element. */
   private Map mAttributes = new HashMap();

   /**
    * @return Returns the elementName.
    */
   public QName getElementName()
   {
      return mElementName;
   }

   /**
    * @param aElementName The elementName to set.
    */
   public void setElementName(QName aElementName)
   {
      mElementName = aElementName;
   }

   /**
    * Add an attribute mapping (QName -> String).
    * 
    * @param aAttributeName
    * @param aAttributeValue
    */
   public void addAttribute(QName aAttributeName, String aAttributeValue)
   {
      mAttributes.put(aAttributeName, aAttributeValue);
   }

   /**
    * Get the map of attributes.
    */
   public Map getAttributes()
   {
      return mAttributes;
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAeExtensionActivityDef#getNamespace()
    */
   public String getNamespace()
   {
      return getElementName().getNamespaceURI();
   }
}
 
