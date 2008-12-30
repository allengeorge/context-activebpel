//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/copy/AeReplaceContentElementStrategy.java,v 1.4 2007/01/27 14:41:5
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
package org.activebpel.rt.bpel.impl.activity.assign.copy; 

import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation;
import org.activebpel.rt.bpel.impl.activity.assign.IAeVariableDataWrapper;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * Specialized version of ReplaceContent strategy that involves copying a text
 * type to an element. 
 */
public class AeReplaceContentElementStrategy extends AeReplaceContentStrategy
{
   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeCopyStrategy#copy(org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation, java.lang.Object, java.lang.Object)
    */
   public void copy(IAeCopyOperation aCopyOperation, Object aFromData, Object aToData) throws AeBpelException
   {
      String from = toString(aCopyOperation, aFromData);

      // we're copying text to an element
      Element target = getElement(aToData);
      AeXmlUtil.removeNodeContents(target, false);
      
      Text text = target.getOwnerDocument().createTextNode(from);
      target.appendChild(text);
   }
   
   /**
    * Type safe getter for the target element
    * @param aToData
    * @throws AeBpelException
    */
   protected Element getElement(Object aToData) throws AeBpelException
   {
      if (aToData instanceof IAeVariableDataWrapper)
      {
         return (Element) ((IAeVariableDataWrapper)aToData).getValue();
      }
      return (Element)aToData;
   }
}
 
