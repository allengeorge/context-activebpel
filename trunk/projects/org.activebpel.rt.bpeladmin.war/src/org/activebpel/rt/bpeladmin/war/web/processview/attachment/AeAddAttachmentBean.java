//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/processview/attachment/AeAddAttachmentBean.java,v 1.2 2007/08/22 21:46:3
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
package org.activebpel.rt.bpeladmin.war.web.processview.attachment;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpeladmin.war.AeMessages;
import org.activebpel.rt.bpeladmin.war.web.upload.AeNewAttachmentUploader;
import org.activebpel.rt.util.AeMimeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.activebpel.wsio.AeWebServiceAttachment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * This bean is responsible for adding process variable attachments on the engine from the BpelAdmin console.
 */
public class AeAddAttachmentBean extends AeNewAttachmentUploader
{

   public static final String ATTRIBUTES_TAG = "attributes"; //$NON-NLS-1$

   public static final String ATTRIBUTE_TAG = "attribute"; //$NON-NLS-1$

   public static final String NAME_ATTR = "name"; //$NON-NLS-1$

   public static final String VALUE_ATTR = "value"; //$NON-NLS-1$

   /**********************************************************************************************************
    * The requested operation. /** Default constructor.
    */
   public AeAddAttachmentBean()
   {
      setStatusCode(IGNORE);
   }

   /**
    * Adds an attachment to the process variable 
    * @param aAttributes
    */
   public void addAttachment(Map aAttributes)
   {
      try
      {
         getAdmin().addVariableAttachment(getPidAsLong(), getPath(), new AeWebServiceAttachment(getContent(), aAttributes));
      }
      catch (AeException ex)
      {
         setError(ex);
      }
   }
   
   /**
    * @return truw - when process is in a active state; otherwise returns false
    */
   public boolean getEditable()
   {
      int state = getAdmin().getProcessDetail(getPidAsLong()).getState();
      return state == IAeBusinessProcess.PROCESS_LOADED ||
             state == IAeBusinessProcess.PROCESS_SUSPENDED ||
             state == IAeBusinessProcess.PROCESS_RUNNING ||
             state == IAeBusinessProcess.PROCESS_COMPENSATABLE;
   }

   /**
    * Sets the current HTTP/servlet request into this bean.
    */
   public void setRequest(HttpServletRequest aRequest) throws Exception
   {
      super.setRequest(aRequest);

      if ( isMultiPartContent(getRequest()) )
      {
         uploadFile(getRequest(), getResponse());

         if ( !isErrorDetail() )
         {
            Map attributes = convertXml(getAttributeXml());
            if ( !isErrorDetail() )
            {
               addAttachment(attributes);
               setStatusCode(SUCCESS);
            }
         }
      }
      else
      {
         setStatusCode(IGNORE);
      }
   }

   /**
    * Converts xml attributes to a Map
    * @param aXml
    * @return
    */
   protected Map convertXml(String aXml)
   {
      Map attributes = new HashMap();
      attributes.put(AeMimeUtil.CONTENT_TYPE_ATTRIBUTE, getContentType());

      try
      {
         Document aDoc = AeXmlUtil.toDoc(aXml);

         if ( aDoc.getDocumentElement().getTagName() != ATTRIBUTES_TAG )
         {
            throw new AeException(
                  MessageFormat
                        .format(
                              AeMessages.getString("AeAddAttachmentBean.ERROR_INVALID_RESPONSE_TAG"), new Object[] { ATTRIBUTES_TAG, aDoc.getDocumentElement().getTagName() })); //$NON-NLS-1$

         }

         NodeList attributeNodes = aDoc.getDocumentElement().getElementsByTagName(ATTRIBUTE_TAG);
         String attName;
         String attValue;

         for (int i = 0; i < attributeNodes.getLength(); i++)
         {
            Element elem = (Element)attributeNodes.item(i);
            attName = elem.getAttribute(NAME_ATTR);
            attValue = elem.getAttribute(VALUE_ATTR);
            attributes.put(attName, attValue);
         }
      }
      catch (AeException ex)
      {
         setError(ex);
      }

      // Assign a default Content-Id if none set
      if ( !attributes.containsKey(AeMimeUtil.CONTENT_ID_ATTRIBUTE) )
      {
         attributes.put(AeMimeUtil.CONTENT_ID_ATTRIBUTE, AeMimeUtil.AE_DEFAULT_ADMIN_CONTENT_ID);
      }
      // Assign a default Content-Location if none set
      if ( !attributes.containsKey(AeMimeUtil.CONTENT_LOCATION_ATTRIBUTE) )
      {
         attributes.put(AeMimeUtil.CONTENT_LOCATION_ATTRIBUTE, getFileName());
      }
      return attributes;
   }
}
