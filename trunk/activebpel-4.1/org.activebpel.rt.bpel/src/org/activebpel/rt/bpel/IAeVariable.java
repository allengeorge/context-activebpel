// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/IAeVariable.java,v 1.26 2007/04/23 23:09:2
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
package org.activebpel.rt.bpel;

import javax.xml.namespace.QName;

import org.activebpel.rt.attachment.IAeAttachmentContainer;
import org.activebpel.rt.bpel.def.AeVariableDef;
import org.activebpel.rt.bpel.impl.AeBpelException;
import org.activebpel.rt.bpel.impl.AeUninitializedVariableException;
import org.activebpel.rt.bpel.impl.activity.IAeVariableContainer;
import org.activebpel.rt.message.IAeMessageData;
import org.w3c.dom.Element;

/**
 * Describes the interface used for interacting with business process variables
 * these Business Process variables are associated with either messages or
 * standard schema described xml data.
 */
public interface IAeVariable extends IAeLocatableObject
{
   /**
    * Get the name of the variable.
    */
   public String getName();

   /**
    * Get the definition of the variable.
    */
   public AeVariableDef getDefinition();

   /**
    * Get the version number.
    */
   public int getVersionNumber();

   /**
    * Get the message data associated with the variable or throws an exception if
    * the variable has not been initialized.
    */
   public IAeMessageData getMessageData() throws AeUninitializedVariableException;

   /**
    * Sets the message data associated with the variable.
    */
   public void setMessageData(IAeMessageData aMessage);

   /**
    * Returns true if the variable has data associated with it.
    */
   public boolean hasMessageData();
  
   /**
    * Get the attachment container associated with the variable, returns null if there are no attachments
    * @return
    */
   public IAeAttachmentContainer getAttachmentData();
   
   /**
    * Sets the Attachment container associated with the variable.
    * @param aAttachmentContainer
    */
   public void setAttachmentData(IAeAttachmentContainer aAttachmentContainer);

   /**
    * Get the data if the variable is specified as type. Returns null if not type.
    * @throws AeUninitializedVariableException 
    */
   public Object getTypeData() throws AeUninitializedVariableException;

   /**
    * Set the type data associated with this variable.
    */
   public void setTypeData(Object aTypeData);

   /**
    * Get the data if the variable is specified as element. Returns null if not element.
    */
   public Element getElementData() throws AeUninitializedVariableException;;

   /**
    * Set the element data associated with this variable.
    */
   public void setElementData(Element aElement);

   /**
    * Pass through to definition, declared here as a convenience method. Returns
    * null if not a message.
    */
   public QName getMessageType();

   /**
    * Returns true if the variable is an Element variable.
    */
   public boolean isElement();

   /**
    * Returns true if the variable is a Message variable.
    */
   public boolean isMessageType();

   /**
    * Returns true if the variable is a Type variable (complex or simple type).
    */
   public boolean isType();

   /**
    * Pass through to definition, declared here as a convenience method. Returns
    * null if not an Element.
    */
   public QName getElement();

   /**
    * Pass through to definition, declared here as a convenience method. Returns
    * null if not a type.
    */
   public QName getType();

   /**
    * Performs validation against the data set for this variable. An
    * AeBusinessProcessException is thrown if the data is invalid.
    * @throws AeBusinessProcessException
    */
   public void validate() throws AeBpelException;

   /**
    * Performs validation against the data set for this variable. An
    * AeBusinessProcessException is thrown if the data is invalid.
    * @param aAllowEmptyPartData set to True if an empty message should pass validation False otherwise
    * @throws AeBusinessProcessException
    */
   public void validate(boolean aAllowEmptyPartData) throws AeBusinessProcessException;

   /**
    * Clears the value for the variable. Called from the variable's declaring
    * scope each time the scope is going to execute since the variable's state
    * is not preserved across invocations.
    */
   public void clear();

   /**
    * Returns <code>true</code> if and only if the variable has data.
    */
   public boolean hasData();
   

   /**
    * Returns <code>true</code> if and only if the variable has attachments.
    */
   public boolean hasAttachments();

   /**
    * Sets the version number.
    *
    * @param aVersionNumber
    */
   public void setVersionNumber(int aVersionNumber);

   /**
    * Increments the version number for the variable
    */
   public void incrementVersionNumber();

   /**
    * @return Returns the scope which owns this variable.
    */
   public IAeVariableContainer getParent();

   /**
    * Clones the variable
    */
   public Object clone();

   /**
    * Restores the variable's data from its clone
    *
    * @param aMyClone
    */
   public void restore(IAeVariable aMyClone);
   
   /**
    * Initializes the variable with its default value prior to being used in
    * an <assign>'s copy operation. If the variable (and part) has already been
    * initialized then this call has no effect.
    * 
    * @param aPartName The part name of null if the variable is not a message.
    */
   public void initializeForAssign(String aPartName);
}
