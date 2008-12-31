//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/logging/AeTeeDeploymentLogger.java,v 1.3 2004/12/10 15:59:2
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
package org.activebpel.rt.bpel.server.logging; 

/**
 * T's the output of the log to two separate loggers.
 */
public class AeTeeDeploymentLogger implements IAeDeploymentLogger
{
   /** left branch of the T */
   private IAeDeploymentLogger mLeft;
   /** right branch of the T */
   private IAeDeploymentLogger mRight;
   
   /**
    * Creates the T logger with the two branches.
    * @param aLeft
    * @param aRight
    */
   public AeTeeDeploymentLogger(IAeDeploymentLogger aLeft, IAeDeploymentLogger aRight)
   {
      mLeft = aLeft;
      mRight = aRight;
   }

   /**
    * @see org.activebpel.rt.bpel.server.logging.IAeDeploymentLogger#setContainerName(java.lang.String)
    */
   public void setContainerName(String aContainerName)
   {
      getLeft().setContainerName(aContainerName);
      getRight().setContainerName(aContainerName);
   }

   /**
    * @see org.activebpel.rt.bpel.server.logging.IAeDeploymentLogger#close()
    */
   public void close()
   {
      getLeft().close();
      getRight().close();
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter#addInfo(java.lang.String, java.lang.Object[], java.lang.Object)
    */
   public void addInfo(String aInfoCode, Object[] aArgs, Object aNode)
   {
      getLeft().addInfo(aInfoCode, aArgs, aNode);
      getRight().addInfo(aInfoCode, aArgs, aNode);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter#addError(java.lang.String, java.lang.Object[], java.lang.Object)
    */
   public void addError(String aErrorCode, Object[] aArgs, Object aNode)
   {
      getLeft().addError(aErrorCode, aArgs, aNode);
      getRight().addError(aErrorCode, aArgs, aNode);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter#addWarning(java.lang.String, java.lang.Object[], java.lang.Object)
    */
   public void addWarning(String aWarnCode, Object[] aArgs, Object aNode)
   {
      getLeft().addWarning(aWarnCode, aArgs, aNode);
      getRight().addWarning(aWarnCode, aArgs, aNode);
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter#hasErrors()
    */
   public boolean hasErrors()
   {
      return getLeft().hasErrors() || getRight().hasErrors();
   }

   /**
    * @see org.activebpel.rt.bpel.def.validation.IAeBaseErrorReporter#hasWarnings()
    */
   public boolean hasWarnings()
   {
      return getLeft().hasWarnings() || getRight().hasWarnings();
   }

   /**
    * @see org.activebpel.rt.bpel.server.logging.IAeDeploymentLogger#resetWarningAndErrorFlags()
    */
   public void resetWarningAndErrorFlags()
   {
      getLeft().resetWarningAndErrorFlags();
      getRight().resetWarningAndErrorFlags();
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.logging.IAeDeploymentLogger#setPddName(java.lang.String)
    */
   public void setPddName(String aPddName)
   {
      getLeft().setPddName(aPddName);
      getRight().setPddName(aPddName);
   }
   
   /**
    * @see org.activebpel.rt.bpel.server.logging.IAeDeploymentLogger#addInfo(java.lang.String)
    */
   public void addInfo(String aMessage)
   {
      addInfo(aMessage, null, null);
   }

   /**
    * @see org.activebpel.rt.bpel.server.logging.IAeDeploymentLogger#processDeploymentFinished(boolean)
    */
   public void processDeploymentFinished(boolean aBool)
   {
      getLeft().processDeploymentFinished(aBool);
      getRight().processDeploymentFinished(aBool);
   }

   /**
    * Gets the left half of the tee.
    */
   protected IAeDeploymentLogger getLeft()
   {
      return mLeft;
   }
   
   /**
    * Gets the right half of the tee.
    */
   protected IAeDeploymentLogger getRight()
   {
      return mRight;
   }
}
