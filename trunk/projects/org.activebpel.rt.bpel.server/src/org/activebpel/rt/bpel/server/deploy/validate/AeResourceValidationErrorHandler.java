//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/validate/AeResourceValidationErrorHandler.java,v 1.1 2006/07/18 20:05:3
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
package org.activebpel.rt.bpel.server.deploy.validate;

import org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter;
import org.activebpel.rt.bpel.server.AeMessages;
import org.w3c.dom.Node;

/**
 * This class implements an <code>IAeResourceValidationErrorHandler</code> that will delegate the
 * reporting of the erorrs and warnings to a base error reporter.
 */
public class AeResourceValidationErrorHandler implements IAeResourceValidationErrorHandler
{
   /** The resource name. */
   private String mName;
   /** The error reporter to use when an error is handled. */
   private IAeBaseErrorReporter mReporter;

   /**
    * Constructor.
    * 
    * @param aReporter
    */
   public AeResourceValidationErrorHandler(String aName, IAeBaseErrorReporter aReporter)
   {
      mName = aName;
      mReporter = aReporter;
   }

   /**
    * Reports an error to the error reporter.
    * 
    * @param aMessage
    */
   protected void reportError(String aMessage)
   {
      Object [] params = { getName(), aMessage };
      mReporter.addError(AeMessages.getString("AeResourceValidationErrorHandler.REPORT_ERROR_FORMAT_SANS_LINENUMBER"), params, null); //$NON-NLS-1$
   }

   /**
    * Reports an error to the error reporter (includes the error's line number).
    * 
    * @param aMessage
    * @param aLineNumber
    */
   protected void reportError(String aMessage, int aLineNumber)
   {
      Object [] params = { getName(), aMessage, new Integer(aLineNumber) };
      mReporter.addError(AeMessages.getString("AeResourceValidationErrorHandler.REPORT_ERROR_FORMAT_WITH_LINENUMBER"), params, null); //$NON-NLS-1$
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.validate.IAeResourceValidationErrorHandler#fatalError(java.lang.String)
    */
   public void fatalError(String aMessage)
   {
      reportError(aMessage);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.deploy.validate.IAeResourceValidationErrorHandler#parseError(java.lang.String, int)
    */
   public void parseError(String aMessage, int aLineNumber)
   {
      reportError(aMessage);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.deploy.validate.IAeResourceValidationErrorHandler#parseFatalError(java.lang.String, int)
    */
   public void parseFatalError(String aMessage, int aLineNumber)
   {
      reportError(aMessage);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.deploy.validate.IAeResourceValidationErrorHandler#parseWarning(java.lang.String, int)
    */
   public void parseWarning(String aMessage, int aLineNumber)
   {
      Object [] params = { getName(), aMessage, new Integer(aLineNumber) };
      mReporter.addWarning(AeMessages.getString("AeResourceValidationErrorHandler.REPORT_ERROR_FORMAT_WITH_LINENUMBER"), params, null); //$NON-NLS-1$
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.validate.IAeResourceValidationErrorHandler#contentError(java.lang.String, org.w3c.dom.Node)
    */
   public void contentError(String aMessage, Node aNode)
   {
      reportError(aMessage);
   }

   /**
    * @see org.activebpel.rt.bpel.server.deploy.validate.IAeResourceValidationErrorHandler#contentWarning(java.lang.String, org.w3c.dom.Node)
    */
   public void contentWarning(String aMessage, Node aNode)
   {
      Object [] params = { getName(), aMessage };
      mReporter.addWarning(AeMessages.getString("AeResourceValidationErrorHandler.REPORT_ERROR_FORMAT_SANS_LINENUMBER"), params, null); //$NON-NLS-1$
   }

   /**
    * @return Returns the name.
    */
   protected String getName()
   {
      return mName;
   }
}

