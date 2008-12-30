//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/message/AeMessagePartsMap.java,v 1.4 2006/09/27 00:33:5
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
package org.activebpel.rt.message; 

import org.activebpel.rt.AeException;
import org.activebpel.rt.AeMessages;
import org.activebpel.rt.wsdl.def.AeBPELExtendedWSDLDef;
import org.exolab.castor.xml.schema.ElementDecl;
import org.exolab.castor.xml.schema.XMLType;

import javax.wsdl.Message;
import javax.wsdl.Part;
import javax.xml.namespace.QName;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Container for the part info for a specific WSDL message.
 */
public class AeMessagePartsMap
{
   /** map of part names to AeMessagePartTypeInfo objects */
   private Map mParts = new HashMap();
   /** name of the message */
   private QName mMessageType;
   
   /**
    * Ctor accepts message name
    * @param aMessageType
    */
   public AeMessagePartsMap(QName aMessageType)
   {
      mMessageType = aMessageType;
   }
   
   /**
    * Returns true if the message has a single part that is an element
    */
   public boolean isSinglePartElement()
   {
      return getSingleElementPart() != null;
   }
   
   /**
    * Returns the QName of the single message part element or null if this message isn't a single message part element message.
    */
   public QName getSingleElementPart()
   {
      if (getPartsCount() == 1)
      {
         AeMessagePartTypeInfo info = (AeMessagePartTypeInfo) getPartsMap().values().iterator().next();
         return info.getElementName();
      }
      return null;
   }
   
   /**
    * Getter for the parts count
    */
   public int getPartsCount()
   {
      return getPartsMap().size();
   }
   
   /**
    * Adds the part info
    * @param aMessagePartTypeInfo
    */
   public void addPartInfo(AeMessagePartTypeInfo aMessagePartTypeInfo)
   {
      getPartsMap().put(aMessagePartTypeInfo.getName(), aMessagePartTypeInfo);
   }
   
   /**
    * Adds the part info 
    * @param aPart the part to be added.
    * @param aDef
    */
   public void addPartInfo(Part aPart, AeBPELExtendedWSDLDef aDef) throws AeException
   {
      XMLType type = null;
      
      if (aPart.getTypeName() != null)
      {
         type = aDef.findType(aPart.getTypeName());
      }
      else if (aPart.getElementName() != null)
      {
         ElementDecl element = aDef.findElement(aPart.getElementName());
         if (element != null)
         {
            type = element.getType();
         }
      }
      
      if (type == null)
      {
         throw new AeException(AeMessages.format("AeMessagePartsMap.ERROR_NoPartType", new Object[] { aPart.getName(), getMessageType() })); //$NON-NLS-1$
      }
      
      addPartInfo(new AeMessagePartTypeInfo(aPart, type));
   }

   /**
    * Getter for the part info
    * @param aName - name of the part
    */
   public AeMessagePartTypeInfo getPartInfo(String aName)
   {
      return (AeMessagePartTypeInfo) getPartsMap().get(aName);
   }
   
   /**
    * Gets the single element part info or null if its not a single element part message.
    */
   public AeMessagePartTypeInfo getSingleElementPartInfo()
   {
      if (isSinglePartElement())
         return getPartInfo((String) getPartNames().next());
      return null;
   }
   
   /**
    * Gets an iterator for the part names
    */
   public Iterator getPartNames()
   {
      return getPartsMap().keySet().iterator();
   }

   /**
    * @return Returns the messageName.
    */
   public QName getMessageType()
   {
      return mMessageType;
   }

   /**
    * @return Returns the parts map.
    */
   protected Map getPartsMap()
   {
      return mParts;
   }
   
   /**
    * Provides string representation for debugging
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      StringBuffer sb = new StringBuffer();
      sb.append("AeMessagePartsMap messageType: ").append(getMessageType()); //$NON-NLS-1$
      for (Iterator it = getPartsMap().values().iterator(); it.hasNext();)
      {
         AeMessagePartTypeInfo part = (AeMessagePartTypeInfo) it.next();
         sb.append('\n');
         sb.append(part.toString());
      }
      return sb.toString();
   }

   /**
    * Returns a {@link AeMessagePartsMap} for the given WSDL <code>Message</code>.
    *
    * @param aMessage
    * @param aDef
    */
   public static AeMessagePartsMap createMessagePartsMap(Message aMessage, AeBPELExtendedWSDLDef aDef) throws AeException
   {
      AeMessagePartsMap map = new AeMessagePartsMap(aMessage.getQName());

      for (Iterator i = aMessage.getParts().values().iterator(); i.hasNext(); )
      {
         Part part = (Part) i.next();
         map.addPartInfo(part, aDef);
      }
      
      return map;
   }
}
 
