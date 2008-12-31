//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/AeSetVariableHandler.java,v 1.4 2007/06/28 22:00:4
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
package org.activebpel.rt.bpel.impl;

import org.activebpel.rt.attachment.IAeAttachmentContainer;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeBusinessProcessEngine;

/**
 *  This class is used to set the new value of a variable from 
 * user/console input.
 */
public class AeSetVariableHandler extends AeVariableDeserializer
{
   /** Validate document when 'true' */
   private boolean mValidate;
   
   /**
    * Constructor.
    * @param aEngine
    */
   public AeSetVariableHandler(IAeBusinessProcessEngine aEngine, boolean aValidate)
   {
      super(aEngine);
      mValidate = aValidate;
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.AeVariableDeserializer#hasData()
    */
   protected boolean hasData() throws AeBusinessProcessException
   {
      // TODO (MF) can user set variable back to uninitialized state?
      return true;
   }
   
   /**
    * Return validate mode
    */
   public boolean isValidate()
   {
      return mValidate;
   }
   
   /**
    * Restores the variable from the serialized variable document.
    *
    * @throws AeBusinessProcessException
    */
   public void restoreVariable() throws AeBusinessProcessException
   {
      IAeAttachmentContainer preserveAttachments = null; 
      if (getVariable().hasAttachments())   
         preserveAttachments = getVariable().getAttachmentData();
      
      super.restoreVariable();
      
      if (preserveAttachments != null)
         getVariable().setAttachmentData(preserveAttachments);
 
      if (isValidate())
         getVariable().validate(true);
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.AeVariableDeserializer#restoreVersionNumber()
    */
   protected void restoreVersionNumber() throws AeBusinessProcessException
   {
      // no op here - let the version number get updated every time the variable data is set
   }
}
