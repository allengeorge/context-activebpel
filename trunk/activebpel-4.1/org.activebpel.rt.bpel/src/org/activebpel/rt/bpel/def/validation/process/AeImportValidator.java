//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/process/AeImportValidator.java,v 1.3 2006/11/02 04:57:0
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
package org.activebpel.rt.bpel.def.validation.process; 

import java.net.URI;

import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.AeImportDef;
import org.activebpel.rt.bpel.def.validation.AeBaseValidator;
import org.activebpel.rt.util.AeUtil;

/**
 * Validates the <imports> for the process.
 */
public class AeImportValidator extends AeBaseValidator
{
   /**
    * ctor takes the def
    * @param aDef
    */
   public AeImportValidator(AeImportDef aDef)
   {
      super(aDef);
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validate()
    */
   public void validate()
   {
      super.validate();
      
      AeImportDef importDef =  (AeImportDef)getDefinition();
      
      if(AeUtil.isNullOrEmpty(importDef.getImportType()))
      {
         getReporter().addError( AeMessages.getString("AeImportValidator.NO_IMPORT_TYPE"), null, getDefinition() ); //$NON-NLS-1$
      }
      else
      {
         try
         {
            URI uri = new URI(importDef.getImportType());
            if(! uri.isAbsolute())
               getReporter().addError( AeMessages.getString("AeImportValidator.IMPORT_TYPE_NOT_ABSOLUTE"), new Object[] {importDef.getImportType() }, getDefinition() ); //$NON-NLS-1$
         }
         catch (Exception ex)
         {
            getReporter().addError( AeMessages.getString("AeImportValidator.IMPORT_TYPE_INVALID_URI"), new Object[] {importDef.getImportType() }, getDefinition() ); //$NON-NLS-1$
         }
      }
      
      // if the import references a namespace then validate that the location is associated with that namespace
      if(AeUtil.notNullOrEmpty(importDef.getNamespace()) && AeUtil.notNullOrEmpty(importDef.getLocation()))
      {
         // TODO (cck) add check that location matches namespace
      }
   }

}
 
