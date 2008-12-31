// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/AeEngineEventFormatter.java,v 1.3 2005/02/08 15:33:1
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.activebpel.rt.AeException;
import org.activebpel.rt.util.AER;
import org.activebpel.rt.util.AeMessageFormatter;
import org.activebpel.rt.util.AeUtil;


/**
 * Basic engine event formatting.<p>
 */
public abstract class AeEngineEventFormatter extends AeMessageFormatter
{
   /** Properties object used to access format specifiers. */
   private Properties mFmtProps;
   
   /**
    * @see org.activebpel.rt.util.AeMessageFormatter#populateMap(java.lang.String)
    */
   public void populateMap( String aPropRoot )
   {
      // Populate map with local formats
      //
      super.populateMap( "MessageFormatting.Engine.Event" ); //$NON-NLS-1$
      
      // Populate map with additional subclass specializations, if any.
      //
      if ( !AeUtil.isNullOrEmpty( aPropRoot ))
         super.populateMap( aPropRoot );      
   }

   /**
    * @see org.activebpel.rt.util.AeMessageFormatter#getResourceString(java.lang.String)
    */   
   public String getResourceString(String aKey)
   {
      return (String)getFmtProps().get( aKey );
   }

   /**
    * @see org.activebpel.rt.util.AeMessageFormatter#getFormatString(java.lang.String)
    */
   public String getFormatString(String aKey)
   {
      return getResourceString( aKey );
   }

   /**
    * Returns the base set of event arguments.  If subclasses add args to the
    * list, they should override this method to return the true # of args.
    *
    * @see org.activebpel.rt.util.AeMessageFormatter#getMaxArgs()
    */
   public int getMaxArgs()
   {
      return AER.ARG_COUNT ;
   }

   /**
    * Set the event-based argument values.
    * 
    * @param aEventID The event ID.
    * @param aPath The activity's node path.
    * @param aPID The process engine instance PID.
    * @param aFault The name of the fault involved, if any.
    * @param aInfo Any ancillary info object sent with the event.
    */
   public void setEventArguments( int aEventID, String aPath, long aPID, String aFault, String aInfo  )
   {
      setArgument( AER.ARG_NODE_OR_LINK_XPATH, aPath );
      setArgument( AER.ARG_PID, aPID + ""); //$NON-NLS-1$
      setArgument( AER.ARG_EVENT_ID, aEventID );
      setArgument( AER.ARG_FAULT_NAME, aFault );
      setArgument( AER.ARG_ANCILLARY_INFO, aInfo );
      setArgument( AER.ARG_TIMESTAMP, AER.getFormattedTimestamp( getResourceString( AER.sTSFormatKey )));
   }
   
   /**
    * Returns the (populated) format properties object.
    * 
    * @return Properties
    */
   public Properties getFmtProps()
   {
      if ( mFmtProps == null )
      {
         mFmtProps = new Properties();
         try {
            InputStream in = getClass().getResourceAsStream("/org/activebpel/rt/bpel/eventFormat.properties"); //$NON-NLS-1$
            mFmtProps.load( in );
         }
         catch ( FileNotFoundException fnfe )
         {
            AeException.logError( fnfe, AeMessages.getString("AeEngineEventFormatter.ERROR_3") ); //$NON-NLS-1$
         }
         catch ( IOException ioe )
         {
            AeException.logError( ioe, AeMessages.getString("AeEngineEventFormatter.ERROR_4") ); //$NON-NLS-1$
         }
      }

      return mFmtProps;
   }
}
