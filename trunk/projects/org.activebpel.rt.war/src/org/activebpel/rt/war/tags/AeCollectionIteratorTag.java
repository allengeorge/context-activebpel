//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.war/src/org/activebpel/rt/war/tags/AeCollectionIteratorTag.java,v 1.1 2007/04/24 17:23:1
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

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;

/**
 * Simple tag to iterate over a collection.
 *
 * &lt;ae:CollectionIterator name="nameOfBean" property="beanColletionPropertyName" id="indexName" /&gt;
 *    Item = &lt;jsp:getProperty name="indexName" property="propName" /&gt;  &lt;br/&gt;
 * &lt;/ae:CollectionIterator&gt;
 */
public class AeCollectionIteratorTag extends AeAbstractBeanPropertyTag
{
   /**
    * Collection iterator.
    */
   private Iterator mCollectionIterator = null;

   /**
    * @return the collectionIterator
    */
   protected Iterator getCollectionIterator()
   {
      return mCollectionIterator;
   }

   /**
    * @param aCollectionIterator the collectionIterator to set
    */
   protected void setCollectionIterator(Iterator aCollectionIterator)
   {
      mCollectionIterator = aCollectionIterator;
   }

   /**
    * @see javax.servlet.jsp.tagext.Tag#doStartTag()
    */
   public int doStartTag() throws JspException
   {
      if (getCollectionIterator() == null)
      {
         Object obj = getPropertyFromBean();
         if (obj != null && obj instanceof Collection)
         {
            setCollectionIterator( ((Collection) obj).iterator() );
         }
      }

      if( getCollectionIterator() != null && getCollectionIterator().hasNext() )
      {
         setPageContextValue();
         return EVAL_BODY_INCLUDE;
      }
      else
      {
         return SKIP_BODY;
      }
   }

   /**
    * @see javax.servlet.jsp.tagext.BodyTagSupport#doEndTag()
    */
   public int doEndTag() throws JspException
   {
      if (getId() != null)
      {
         pageContext.removeAttribute(getId(), PageContext.PAGE_SCOPE);
      }
      setName(null);
      setId(null);
      setProperty(null);
      setCollectionIterator(null);
      return super.doEndTag();
   }

   /**
    * @see javax.servlet.jsp.tagext.IterationTag#doAfterBody()
    */
   public int doAfterBody() throws JspTagException
   {
      if( getCollectionIterator() != null && getCollectionIterator().hasNext() )
      {
         setPageContextValue();
         return EVAL_BODY_AGAIN;
      }
      else
      {
         return SKIP_BODY;
      }
   }

   /**
    * Sets the current iterator value in the page context.
    */
   protected void setPageContextValue()
   {
      Object obj = getCollectionIterator().next();
      pageContext.setAttribute(getId(), obj);
   }

}



