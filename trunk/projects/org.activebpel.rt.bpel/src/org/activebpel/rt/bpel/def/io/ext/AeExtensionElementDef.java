// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/ext/AeExtensionElementDef.java,v 1.2 2007/03/03 02:58:4
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
package org.activebpel.rt.bpel.def.io.ext;


import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.AeBaseDef;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;

/**
 * Impl of the IAeExtensionElementDef interface.
 * <br />
 * Simply wraps the extension element.
 */
public class AeExtensionElementDef extends AeBaseDef implements IAeExtensionElementDef
{
   /** extension element QName */
   private QName mQName;
   /** the actual dom element */
   private Element mExtensionElement;
   /** any accumulated comments */
   private String mComments;
   
   /**
    * Default c'tor.
    */
   public AeExtensionElementDef()
   {
      super();
   }

   /**
    * Mutator for setting extension element.
    * @param aElement extension element
    */
   public void setExtensionElement( Element aElement )
   {
      mExtensionElement = AeXmlUtil.cloneElement(aElement);
   }
   
   /**
    * Accessor for extension element.
    * @return actual dom element
    */
   public Element getExtensionElement()
   {
      return mExtensionElement;
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.io.ext.IAeExtensionElementDef#setComments(java.lang.String)
    */
   public void setComments( String aComments )
   {
      mComments = aComments;
   }
   
   /**
    * Accessor for comment string.
    * @return any comment or null if none have been set
    */
   public String getComments()
   {
      return mComments;
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.ext.IAeExtensionElementDef#getElementQName()
    */
   public QName getElementQName()
   {
      return mQName;
   }

   /**
    * @see org.activebpel.rt.bpel.def.io.ext.IAeExtensionElementDef#setElementQName(javax.xml.namespace.QName)
    */
   public void setElementQName(QName aQName)
   {
      mQName = aQName;
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);
   }
}
