//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/extensions/AeExtensionValidator.java,v 1.3 2006/12/14 22:49:0
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
package org.activebpel.rt.bpel.def.validation.extensions; 

import org.activebpel.rt.bpel.def.AeExtensionDef;
import org.activebpel.rt.bpel.def.validation.AeBaseValidator;
import org.activebpel.rt.bpel.def.validation.IAeValidator;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * model provides validation for an extension model
 */
public class AeExtensionValidator extends AeBaseValidator
{
   /** Users of this extension. */
   private List mExtensionUsers = new LinkedList();

   /**
    * ctor
    * @param aDef
    */
   public AeExtensionValidator(AeExtensionDef aDef)
   {
      super(aDef);
   }

   /**
    * Gets the extension's namespace.
    */
   public String getNamespace()
   {
      return getDef().getNamespace();
   }
   
   /**
    * Gets the extension's 'mustUnderstand' flag.
    */
   public boolean isMustUnderstand()
   {
      return getDef().isMustUnderstand();
   }
   
   /**
    * Adds a usage of the extension.
    * 
    * @param aValidator
    * @param aUnderstood
    */
   public void addUsage(IAeValidator aValidator, boolean aUnderstood)
   {
      getExtensionUsers().add(new AeExtensionUsage(aValidator, aUnderstood));
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.validation.AeBaseValidator#validate()
    */
   public void validate()
   {
      if (!isReferenced())
      {
         getReporter().addWarning( WARNING_EXTENSION_NOT_USED,  new String[] { getDef().getNamespace() }, getDefinition() );
      }
      else if (isMustUnderstand())
      {
         // Find any usages that are not understood.
         for (Iterator iter = getExtensionUsers().iterator(); iter.hasNext(); )
         {
            AeExtensionUsage usage = (AeExtensionUsage) iter.next();
            if (!usage.isUnderstood())
            {
               getReporter().addError(ERROR_DID_NOT_UNDERSTAND_EXTENSION, null, usage.getValidator().getDefinition());
            }
         }
      }
      super.validate();
   }

   /**
    * Returns true if someone references this extension.
    */
   protected boolean isReferenced()
   {
      return getExtensionUsers().size() > 0;
   }
   
   /**
    * @return Returns the extensionUsers.
    */
   public List getExtensionUsers()
   {
      return mExtensionUsers;
   }

   /**
    * @param aExtensionUsers The extensionUsers to set.
    */
   public void setExtensionUsers(List aExtensionUsers)
   {
      mExtensionUsers = aExtensionUsers;
   }

   /**
    * Getter for the def
    */
   public AeExtensionDef getDef()
   {
      return (AeExtensionDef) getDefinition();
   }
 
   /**
    * Simple class that contains information about how the extension is being used.
    */
   protected static class AeExtensionUsage
   {
      /** The validator that uses the extension. */
      private IAeValidator mValidator;
      /** Flag indicating whether the extension usage was understood. */
      private boolean mUnderstood;

      /**
       * Constructor.
       * 
       * @param mValidator
       * @param aUnderstood
       */
      public AeExtensionUsage(IAeValidator mValidator, boolean aUnderstood)
      {
         setValidator(mValidator);
         setUnderstood(aUnderstood);
      }

      /**
       * @return Returns the validator.
       */
      public IAeValidator getValidator()
      {
         return mValidator;
      }

      /**
       * @param aValidator The validator to set.
       */
      public void setValidator(IAeValidator aValidator)
      {
         mValidator = aValidator;
      }

      /**
       * @return Returns the understood.
       */
      public boolean isUnderstood()
      {
         return mUnderstood;
      }

      /**
       * @param aUnderstood The understood to set.
       */
      public void setUnderstood(boolean aUnderstood)
      {
         mUnderstood = aUnderstood;
      }
   }
}
 
