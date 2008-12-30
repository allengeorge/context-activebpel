//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.war/src/org/activebpel/rt/war/tags/AeQNamePropertyTag.java,v 1.1 2007/04/24 17:23:1
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
package org.activebpel.rt.war.tags; 

import javax.servlet.jsp.JspException;
import javax.xml.namespace.QName;

/**
 * Extracts the local name or the namespace uri from a property that is a qname.
 */
public class AeQNamePropertyTag extends AeAbstractBeanPropertyTag
{
   /** part of the qname that we're reading, either "uri" or "local" */
   protected String mPart;
   
   /**
    * @see javax.servlet.jsp.tagext.Tag#doStartTag()
    */
   public int doStartTag() throws JspException
   {
      QName qname = (QName) getPropertyFromBean();
      if (qname != null)
      {
         if ("local".equalsIgnoreCase(getPart())) //$NON-NLS-1$
         {
            write(qname.getLocalPart());
         }
         else
         {
            write(qname.getNamespaceURI());
         }
            }
      return SKIP_BODY;
   }
   
   /**
    * @return Returns the part.
    */
   public String getPart()
   {
      return mPart;
   }
   /**
    * @param aPart The part to set.
    */
   public void setPart(String aPart)
   {
      mPart = aPart;
   }
}
 
