// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/validate/main/AeErrorReporterConsole.java,v 1.3 2005/02/08 15:36:0
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
package org.activebpel.rt.bpel.server.deploy.validate.main;

import java.text.MessageFormat;

import org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter;
import org.activebpel.rt.bpel.server.AeMessages;

/**
 *  Sends all errors/messages to System.out.
 */
public class AeErrorReporterConsole implements IAeBaseErrorReporter
{
   /** Error message indicator. */
   protected boolean mHasErrors;
   /** Warning message indicator */
   protected boolean mHasWarnings;

   /**
    * @see org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter#addError(java.lang.String, java.lang.Object[], java.lang.Object)
    */
   public void addError(String aErrorCode, Object[] aArgs, Object aNode)
   {
      display( AeMessages.getString("AeErrorReporterConsole.0") + aErrorCode, aArgs ); //$NON-NLS-1$
      mHasErrors = true;
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter#addInfo(java.lang.String, java.lang.Object[], java.lang.Object)
    */
   public void addInfo(String aInfoCode, Object[] aArgs, Object aNode)
   {
      display( AeMessages.getString("AeErrorReporterConsole.1") + aInfoCode, aArgs ); //$NON-NLS-1$
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter#addWarning(java.lang.String, java.lang.Object[], java.lang.Object)
    */
   public void addWarning(String aWarnCode, Object[] aArgs, Object aNode)
   {
      display( AeMessages.getString("AeErrorReporterConsole.2") + aWarnCode, aArgs ); //$NON-NLS-1$
      mHasWarnings = true;
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter#hasErrors()
    */
   public boolean hasErrors()
   {
      return mHasErrors;
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter#hasWarnings()
    */
   public boolean hasWarnings()
   {
      return mHasWarnings;
   }
   
   /**
    * Handles any message formatting and writes the
    * results to the console.
    * @param aMessage A message template.
    * @param aArgs Message parameters.
    */
   protected void display( String aMessage, Object[] aArgs )
   {
      if( aArgs != null )
      {
         System.out.println( MessageFormat.format(aMessage,aArgs) );
      }
   }
}
