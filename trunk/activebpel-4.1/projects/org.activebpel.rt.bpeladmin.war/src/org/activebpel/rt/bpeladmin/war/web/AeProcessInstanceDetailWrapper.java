// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/AeProcessInstanceDetailWrapper.java,v 1.14 2006/02/14 22:44:2
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
package org.activebpel.rt.bpeladmin.war.web;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.util.Date;

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.IAeBusinessProcess;
import org.activebpel.rt.bpel.impl.AeSuspendReason;
import org.activebpel.rt.bpel.impl.list.AeProcessInstanceDetail;
import org.activebpel.rt.bpel.server.engine.AeEngineFactory;
import org.activebpel.rt.bpeladmin.war.AeMessages;
import org.activebpel.rt.util.AeUtil;

/**
 * Wraps an <code>AeProcessInstanceDetail</code> object and provides
 * bean accessors for ui client.
 */
public class AeProcessInstanceDetailWrapper
{
   /** Process instance detail. */
   protected AeProcessInstanceDetail mDelegate;
   /** PID */
   protected long mPid;
   /** Empty log message */
   protected String mMessage = ""; //$NON-NLS-1$
   /** Eror message if any*/
   protected String mErrorMessage = ""; //$NON-NLS-1$
   /** process log */
   protected String mLog;

   
   /**
    * Constructor.
    * @param aDetail The delegate process instance detail.
    */
   public AeProcessInstanceDetailWrapper( AeProcessInstanceDetail aDetail )
   {
      mDelegate = aDetail;
   }
   
   /**
    * Default constructor.
    */
   public AeProcessInstanceDetailWrapper()
   {
      
   }
      
   /**
    * Setting the terminate flag causes the process to be terminated.
    */
   public void setTerminate(boolean aFlag)
   {
      if(aFlag)
      {
         try
         {
            AeEngineFactory.getEngine().terminateProcess(mPid);
            mDelegate = AeEngineFactory.getEngineAdministration().getProcessDetail( mPid );
         }
         catch (AeBusinessProcessException e)
         {
            String error = AeMessages.getString("AeProcessInstanceDetailWrapper.2"); //$NON-NLS-1$
            CharArrayWriter writer = new CharArrayWriter();
            e.printStackTrace(new PrintWriter(writer));
            error += writer.toCharArray();
            writer.close();
            setErrorMessage(error);
         }
      }
   }

   /**
    * Setting the suspend flag causes the process to be suspended.
    */
   public void setSuspend(boolean aFlag)
   {
      if(aFlag)
      {
         try
         {
            AeEngineFactory.getEngine().suspendProcess(mPid);
            mDelegate = AeEngineFactory.getEngineAdministration().getProcessDetail( mPid );
         }
         catch (AeBusinessProcessException e)
         {
            String error = AeMessages.getString("AeProcessInstanceDetailWrapper.3"); //$NON-NLS-1$
            CharArrayWriter writer = new CharArrayWriter();
            e.printStackTrace(new PrintWriter(writer));
            error += writer.toCharArray();
            writer.close();
            setErrorMessage(error);
         }
      }
   }

   /**
    * Setting the resume flag causes the process to be resumed.
    */
   public void setResume(boolean aFlag)
   {
      if(aFlag)
      {
         try
         {
            AeEngineFactory.getEngine().resumeProcess(mPid);
            mDelegate = AeEngineFactory.getEngineAdministration().getProcessDetail( mPid );
         }
         catch (AeBusinessProcessException e)
         {
            String error = AeMessages.getString("AeProcessInstanceDetailWrapper.4"); //$NON-NLS-1$
            CharArrayWriter writer = new CharArrayWriter();
            e.printStackTrace(new PrintWriter(writer));
            error += writer.toCharArray();
            writer.close();
            setErrorMessage(error);
         }
      }
   }

   /**
    * Setter for the process id property.
    * Loads the delegate.
    * @param aPid
    */
   public void setStrProcessId( String aPid )
   {
      long pid = -1;
      try
      {
         pid = Long.valueOf(aPid).longValue();
      }
      catch (Exception ex)
      {
         // ingore will lookup -1 which indicates invalid id
      }
      setProcessId(pid); 
   }
   
   /**
    * Setter for the process id property.
    * Loads the delegate.
    * @param aPid
    */
   public void setProcessId( long aPid )
   {
      mPid = aPid;
      mDelegate = AeEngineFactory.getEngineAdministration().getProcessDetail( aPid );
   }
   
   /**
    * Setter for default message if log file is empty.
    * @param aMessage
    */
   public void setMessage( String aMessage )
   {
      mMessage = aMessage;
   }
   
   /**
    * Returns true if no delegate is found.
    */
   public boolean isEmpty()
   {
      return mDelegate == null;
   }
   
   /**
    * Accessor for the process instance detail delegate.
    */
   protected AeProcessInstanceDetail getDelegate()
   {
      return mDelegate;
   }
   
   /**
    * Accessor for the process qname local part.
    */
   public String getLocalPart()
   {
      return getDelegate().getName().getLocalPart();
   }
   
   /**
    * Accessor for the namespace uri.
    */
   public String getNamespaceURI()
   {
      return getDelegate().getName().getNamespaceURI();
   }

   /**
    * Getter for the process id
    */
   public long getProcessId()
   {
      return getDelegate().getProcessId();
   }
   
   /**
    * Getter for the start date
    */
   public Date getStarted()
   {
      return getDelegate().getStarted();
   }
   
   /**
    * Getter for the end date.
    */
   public Date getEnded()
   {
      return getDelegate().getEnded();
   }

   /**
    * Gets a displayable value for the state.
    */
   public String getStateString()
   {
      // TODO (RN) - Move externalization to JSP layer
      switch (getDelegate().getState())
      {
         case IAeBusinessProcess.PROCESS_LOADED :
            return AeMessages.getString("AeProcessInstanceDetailWrapper.5"); //$NON-NLS-1$
         case IAeBusinessProcess.PROCESS_RUNNING:
            return AeMessages.getString("AeProcessInstanceDetailWrapper.6"); //$NON-NLS-1$
         case IAeBusinessProcess.PROCESS_SUSPENDED:
         {
            if (getDelegate().getStateReason() == AeSuspendReason.SUSPEND_CODE_AUTOMATIC)
               return AeMessages.getString("AeProcessInstanceDetailWrapper.7"); //$NON-NLS-1$
            else if (getDelegate().getStateReason() == AeSuspendReason.SUSPEND_CODE_LOGICAL)
               return AeMessages.getString("AeProcessInstanceDetailWrapper.8"); //$NON-NLS-1$
            else if (getDelegate().getStateReason() == AeSuspendReason.SUSPEND_CODE_MANUAL)
               return AeMessages.getString("AeProcessInstanceDetailWrapper.9"); //$NON-NLS-1$
            else
               return AeMessages.getString("AeProcessInstanceDetailWrapper.10"); //$NON-NLS-1$
         }
         case IAeBusinessProcess.PROCESS_COMPLETE :
            return AeMessages.getString("AeProcessInstanceDetailWrapper.11"); //$NON-NLS-1$
         case IAeBusinessProcess.PROCESS_FAULTED :
            return AeMessages.getString("AeProcessInstanceDetailWrapper.12"); //$NON-NLS-1$
         case IAeBusinessProcess.PROCESS_COMPENSATABLE:
            return AeMessages.getString("AeProcessInstanceDetailWrapper.compensatable"); //$NON-NLS-1$
         default:
            return AeMessages.getString("AeProcessInstanceDetailWrapper.13"); //$NON-NLS-1$
      }
   }
   
   /**
    * Returns true if the process can be terminated.
    */
   public boolean isTerminatable()
   {
      switch (getDelegate().getState())
      {
         case IAeBusinessProcess.PROCESS_LOADED :
         case IAeBusinessProcess.PROCESS_RUNNING:
         case IAeBusinessProcess.PROCESS_SUSPENDED:
            return true;
         case IAeBusinessProcess.PROCESS_COMPLETE :
         case IAeBusinessProcess.PROCESS_COMPENSATABLE:
         case IAeBusinessProcess.PROCESS_FAULTED :
         default:
            return false;
      }
   }
   
   /**
    * Returns true if the process can be suspended.
    */
   public boolean isSuspendable()
   {
      switch (getDelegate().getState())
      {
         case IAeBusinessProcess.PROCESS_LOADED :
         case IAeBusinessProcess.PROCESS_RUNNING:
            return true;
         case IAeBusinessProcess.PROCESS_SUSPENDED:
         case IAeBusinessProcess.PROCESS_COMPLETE :
         case IAeBusinessProcess.PROCESS_COMPENSATABLE:
         case IAeBusinessProcess.PROCESS_FAULTED :
         default:
            return false;
      }
   }
   
   /**
    * Returns true if the process can be resumed.
    */
   public boolean isResumable()
   {
      switch (getDelegate().getState())
      {
         case IAeBusinessProcess.PROCESS_SUSPENDED:
            return true;
         case IAeBusinessProcess.PROCESS_LOADED :
         case IAeBusinessProcess.PROCESS_RUNNING:
         case IAeBusinessProcess.PROCESS_COMPLETE :
         case IAeBusinessProcess.PROCESS_COMPENSATABLE:
         case IAeBusinessProcess.PROCESS_FAULTED :
         default:
            return false;
      }
   }
   
   /**
    * Accessor for the log file text.
    */
   public String getLog()
   {
      fetchLog();

      if( AeUtil.isNullOrEmpty( mLog ) )
      {
         return mMessage + " " + getProcessId(); //$NON-NLS-1$
      }
      else
      {
         return mLog;
      }
   }

   /**
    * fetches the log from the logger if it's not already present 
    */
   private void fetchLog()
   {
      if (mLog == null)
         mLog = AeEngineFactory.getEngineAdministration().getProcessLog( getProcessId() ); 
   }
   
   /**
    * Returns true if there are logging statements available
    */
   public boolean isLogAvailable()
   {
      fetchLog();
      return !AeUtil.isNullOrEmpty(mLog);
   }
   
   /**
    * Returns the error message if any.
    */
   public String getErrorMessage()
   {
      return mErrorMessage;
   }

   /**
    * Sets the error message, null if no error.
    */
   public void setErrorMessage(String aString)
   {
      mErrorMessage = aString;
   }
   
   /**
    * Returns true if there is an error message.
    */
   public boolean isError()
   {
      return ! AeUtil.isNullOrEmpty(getErrorMessage());
   }
   

}
