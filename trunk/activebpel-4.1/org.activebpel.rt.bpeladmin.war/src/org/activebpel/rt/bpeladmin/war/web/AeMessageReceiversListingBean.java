// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/AeMessageReceiversListingBean.java,v 1.6 2006/01/11 16:22:0
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

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.impl.list.AeMessageReceiverFilter;
import org.activebpel.rt.bpel.impl.list.AeMessageReceiverListResult;

/**
 *  Wraps the details for the Message Receivers Queue listing.
 */
public class AeMessageReceiversListingBean extends AeAbstractListingBean
{
   /** AeMessageReceives to display. */   
   protected AeMessageReceiverDetailWrapper[] mMessageReceivers;
   /** Process id. */
   protected long mProcessId = AeMessageReceiverFilter.NULL_ID;
   /** The current row being processed. */
   protected int mCurrentIndex;
   /** Partner link type name. */
   protected String mPartnerLinkTypeName = ""; //$NON-NLS-1$
   /** PortType QName. */
   protected QName mPortType;
   /** Operation name. */
   protected String mOperation  = ""; //$NON-NLS-1$

   /**
    * Constructor.  
    */
   public AeMessageReceiversListingBean()
   {
      super();
   }
   
   /**
    * Setting this flag to true signals that the 
    * bean should update its state based on the 
    * filter parameters.
    * @param aUpdateFlag
    */
   public void setFinished( boolean aUpdateFlag )
   {
      if( aUpdateFlag )
      {
         AeMessageReceiverFilter filter = new AeMessageReceiverFilter();
         filter.setPartnerLinkName( getPartnerLinkTypeName() );
         filter.setPortType( AeWebUtil.toQName( getPortType() ) );
         filter.setOperation( getOperation() );
         filter.setProcessId( mProcessId );
         filter.setListStart( getRowStart() );
         filter.setMaxReturn( getRowCount() );
         
         AeMessageReceiverListResult results = getAdmin().getMessageReceivers( filter );
            
         if( !results.isEmpty() )
         { 
            setTotalRowCount( results.getTotalRows() );
            updateNextPageStatus();
            mMessageReceivers = new AeMessageReceiverDetailWrapper[ results.getResults().length ];

            for(int i=0, len=results.getResults().length; i < len; i++)
               mMessageReceivers[i] = new AeMessageReceiverDetailWrapper(results.getResults()[i]);                 

            setRowsDisplayed( mMessageReceivers.length );
         }
         else
         {
            setNextPage( false );
         }
      }
   }
   
   /**
    * Indexed accessor for a message receiver.
    * @param aIndex
    * @return The AeMessageReceiverDetailWrapper mapped to the index.
    */
   public AeMessageReceiverDetailWrapper getMessageReceiver( int aIndex )
   {
      return mMessageReceivers[ aIndex ];          
   }
   
   /**
    * Accessor for the number of the receivers.
    */
   public int getMessageReceiverSize()
   {
      if( mMessageReceivers == null )
      {
         return 0;
      }
      else
      {
         return mMessageReceivers.length;
      }
   }

   /**
    * Setter for the process id.
    * @param aId A process id.
    */
   public void setProcessId( String aId )
   {
      try
      {
         mProcessId = Long.parseLong(aId);
      }
      catch( NumberFormatException e )
      {
         // @todo - error handling
      }
   }
   
   /**
    * Getter for the process id.
    */
   public String getProcessId()
   {
      if( mProcessId == AeMessageReceiverFilter.NULL_ID )
      {
         return ""; //$NON-NLS-1$
      }
      else
      {
         return String.valueOf(mProcessId);
      }
   }
   
   /**
    * Setter for the partner link type name.
    * @param aName
    */
   public void setPartnerLinkTypeName( String aName )
   {
      mPartnerLinkTypeName = aName;
   }
   
   /**
    * Getter for the partner link type name.
    */
   public String getPartnerLinkTypeName()
   {
      return mPartnerLinkTypeName;
   }
   
   /**
    * Setter for the operation.
    * @param aOperation
    */
   public void setOperation( String aOperation )
   {
      mOperation = aOperation;
   }
   
   /**
    * Getter for the operation.
    */
   public String getOperation()
   {
      return mOperation;
   }

   /**
    * Setter for the port type.
    * @param aQName
    */
   public void setPortType( String aQName )
   {
      mPortType = AeWebUtil.toQName( aQName );
   }
   
   /**
    * Getter for the port type qname.
    */
   public QName getPortTypeQName()
   {
      return mPortType;
   }
   
   /**
    * Getter for the port type.
    */
   public String getPortType()
   {
      return AeWebUtil.toString(mPortType);
   }
   
   /**
    * @see org.activebpel.rt.bpeladmin.war.web.AeAbstractListingBean#isEmpty()
    */
   public boolean isEmpty()
   {
      return mMessageReceivers == null || mMessageReceivers.length == 0;
   }
}
