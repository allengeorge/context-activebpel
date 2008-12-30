// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/AeVariableDef.java,v 1.22 2006/12/14 22:38:4
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
package org.activebpel.rt.bpel.def;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.activity.support.AeFromDef;
import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;
import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.AeFaultFactory;
import org.activebpel.rt.message.AeMessagePartTypeInfo;
import org.activebpel.rt.message.AeMessagePartsMap;
import org.activebpel.rt.wsdl.def.AeBPELExtendedWSDLDef;
import org.exolab.castor.xml.schema.XMLType;

import javax.wsdl.Part;
import javax.xml.namespace.QName;
import java.util.Iterator;

/**
 * Definition for bpel Variable construct.
 */
public class AeVariableDef extends AeNamedDef implements IAeFromParentDef
{
   /** Holds a map of part names to part type information. */
   private AeMessagePartsMap mMessageParts;
   /** The message type of the variable, null if not a message type */
   private QName mMessageType;
   /** The XML type of the variable, null if not a type */
   private QName mType;
   /** The element type of the variable, null if not a type */
   private QName mElement;
   /** Flag that indicates the variable definition is implicit as in the case of the counter var in forEach */
   private boolean mImplicit = false;
   /** The variable's 'from' child. */
   private AeFromDef mFrom;
   /** The schema type of the variable, only set when the variable is a type variable */
   private XMLType mXMLType;
  /** Indicates whether a variable is stateful (i.e. linked to an external subscription source) or not */
   private boolean mIsStateful = false;

   /**
    * Default constructor
    */
   public AeVariableDef()
   {
   }
   
   /**
    * Constructs variable definition with given message parts map.
    */
   public AeVariableDef(AeMessagePartsMap aMessageParts)
   {
      mMessageParts = aMessageParts;
      mMessageType = aMessageParts.getMessageType();
   }
   
   /**
    * Returns true if the variable is an Element.
    */
   public boolean isElement()
   {
      return getElement() != null;
   }
   
   /**
    * Returns true if the variable is a Message.
    */
   public boolean isMessageType()
   {
      return getMessageType() != null;
   }
   
   /**
    * Returns true if the variable is a Type.
    */
   public boolean isType()
   {
      return getType() != null;
   }

   /**
    * Accessor method to obtain messageType of this object.
    * 
    * @return messageType of object
    */
   public QName getMessageType()
   {
      return mMessageType;
   }

   /**
    * Mutator method to set messageType of this object.  
    * @param aMessageType of object
    */
   public void setMessageType(QName aMessageType)
   {
      mMessageType = aMessageType;
   }
   
   /**
    * Returns the element decaration associated with this variable, null if
    * not an element.
    */
   public QName getElement()
   {
      return mElement;
   }

   /**
    * Sets the element decaration associated with this variable, set to null if
    * not an element.
    */
   public void setElement(QName aElementName)
   {
      mElement = aElementName;
   }

   /**
    * Returns the type decaration associated with this variable, null if
    * not an type.
    */
   public QName getType()
   {
      return mType;
   }

   /**
    * Sets the type decaration associated with this variable, set to null if
    * not an type.  
    */
   public void setType(QName aSchemaType)
   {
      mType = aSchemaType;
   }
   
   /**
    * @return Returns the xMLType.
    */
   public XMLType getXMLType()
   {
      return mXMLType;
   }

   /**
    * @param aType The xMLType to set.
    */
   public void setXMLType(XMLType aType)
   {
      mXMLType = aType;
   }

   /**
    * @see org.activebpel.rt.bpel.def.AeBaseDef#accept(org.activebpel.rt.bpel.def.visitors.IAeDefVisitor)
    */
   public void accept(IAeDefVisitor aVisitor)
   {
      aVisitor.visit(this);      
   }
   
   /**
    * Returns an iterator over the part names for this message.
    */
   public Iterator getPartNames()
   {
      return getMessageParts().getPartNames();
   }
   
   /**
    * Returns true if this variable has parts.
    */
   public boolean hasParts()
   {
      return getMessageParts().getPartsCount() != 0;
   }
   
   /**
    * Gets the number of parts
    */
   public int getPartCount()
   {
      return getMessageParts().getPartsCount();
   }

   /**
    * Adds the part type information for this Part to the variable definition. 
    * @param aPart the part to be added.
    * @param aDef
    */
   public void addPartTypeInfo(Part aPart, AeBPELExtendedWSDLDef aDef) throws AeException
   {
      getMessageParts().addPartInfo(aPart, aDef);
   }
   
   /**
    * Returns the XML type for the given message part. 
    * @param aPartName the part name we are looking for
    * @throws AeBusinessProcessException when unknown part is requested
    */
   public XMLType getPartType(String aPartName) throws AeBpelException
   {
      return getPartInfo(aPartName).getXMLType();
   }
   
   /**
    * Returns a flag indicating if the given message part is an element. 
    * @param aPartName the part name we are looking for
    * @throws AeBusinessProcessException when unknown part is requested
    */
   public boolean isPartElement(String aPartName) throws AeBpelException
   {
      AeMessagePartTypeInfo partType = getPartInfo(aPartName);
      return partType.isElement();
   }

   /**
    * Returns the WSDL Part for the given message part. 
    * @param aPartName the part name we are looking for
    * @throws AeBpelException when unknown part is requested
    */
   public AeMessagePartTypeInfo getPartInfo(String aPartName) throws AeBpelException
   {
      AeMessagePartTypeInfo partType = (AeMessagePartTypeInfo)getMessageParts().getPartInfo(aPartName);
      if (partType == null)
      {
         throw new AeBpelException(AeMessages.format("AeVariableDef.ERROR_4", aPartName), AeFaultFactory.getSystemErrorFault());          //$NON-NLS-1$
      }
      return partType;
   }
   
   /**
    * Getter for the implicit flag
    */
   public boolean isImplicit()
   {
      return mImplicit;
   }
   
   /**
    * Setter for the implicit flag
    * 
    * @param aImplicit
    */
   public void setImplicit(boolean aImplicit)
   {
      mImplicit = aImplicit;
   }

   /**
    * @return Returns the from.
    */
   public AeFromDef getFromDef()
   {
      return mFrom;
   }

   /**
    * @param aFrom The from to set.
    */
   public void setFromDef(AeFromDef aFrom)
   {
      mFrom = aFrom;
   }
   
   /**
    * Getter for the message parts info
    */
   public AeMessagePartsMap getMessageParts()
   {
      if (mMessageParts == null)
      {
         mMessageParts = new AeMessagePartsMap(getMessageType());
      }
      return mMessageParts;
   }

   public boolean isStateful() {
      return mIsStateful;
   }

   public void setIsStateful(boolean mIsStateful) {
     this.mIsStateful = mIsStateful;
   }
}
