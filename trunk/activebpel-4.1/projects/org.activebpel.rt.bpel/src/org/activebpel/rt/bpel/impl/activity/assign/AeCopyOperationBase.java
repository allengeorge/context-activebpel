//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/AeCopyOperationBase.java,v 1.8 2007/04/25 18:33:5
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
package org.activebpel.rt.bpel.impl.activity.assign; 

import org.activebpel.rt.attachment.IAeAttachmentContainer;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.impl.activity.IAeCopyFromParent;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * Abstract implementation of copy operation for <assign> activity. 
 * 
 * This implementation pairs an impl of a <from> and an impl of a <to> 
 * along with a strategy to handle the copy.  
 */
public abstract class AeCopyOperationBase implements IAeCopyOperation, IAeCopyFromParent
{
   /** impl object for the from */
   private IAeFrom mFrom;
   /** impl object for the to */
   private IAeTo mTo;
   /** context for the copy operation */
   private IAeCopyOperationContext mContext;
   /** keepSrcElementName attribute from ws-bpel */
   protected final boolean mKeepSrcElementName;
   /** ignoreMissingDataFrom attribute from ws-bpel */
   protected final boolean mIgnoreMissingFromData;
   
   /**
    * Constructs with explicit context and keep source element name flag.
    *
    * @param aContext
    * @param aKeepSrcElementName
    * @param aIgnoreMissingFromData
    */
   protected AeCopyOperationBase(IAeCopyOperationContext aContext, boolean aKeepSrcElementName, boolean aIgnoreMissingFromData)
   {
      setContext(aContext);
      
      mKeepSrcElementName = aKeepSrcElementName;
      mIgnoreMissingFromData = aIgnoreMissingFromData;
   }
   
   /**
    * Constructs w/o context and with explicit keep source element name flag.
    *
    * @param aKeepSrcElementName
    * @param aIgnoreMissingFromData
    */
   protected AeCopyOperationBase(boolean aKeepSrcElementName, boolean aIgnoreMissingFromData)
   {
      this(null, aKeepSrcElementName, aIgnoreMissingFromData);
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeAssignOperation#execute()
    */
   public void execute() throws AeBusinessProcessException
   {
      Object fromData = getFrom().getFromData();
      Object extractedFromData = extractData(fromData);
      
      if (extractedFromData == null && isIgnoreMissingFromData())
      {
         // the copy op is a no-op
         return;
      }

      IAeAttachmentContainer fromContainer = getFrom().getAttachmentsSource();
      IAeAttachmentContainer toContainer = getTo().getAttachmentsTarget();   
     
      if (fromContainer != null && toContainer != null)
      {
         // Copy attachments
         toContainer.copy(fromContainer);
      }
    
      Object toData = getTo().getTarget();

      // toData could be a Document as the result of a query
      if (toData instanceof Document)
         toData = ((Document)toData).getDocumentElement();
      
      IAeCopyStrategy strategy = AeCopyStrategyFactory.createStrategy(this, extractedFromData, toData);
      strategy.copy(this, extractedFromData, toData);
 
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation#getContext()
    */
   public IAeCopyOperationContext getContext()
   {
      return mContext;
   }
   
   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation#setContext(org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperationContext)
    */
   public void setContext(IAeCopyOperationContext aContext)
   {
      mContext = aContext;
   }

   /**
    * @return Returns the from.
    */
   public IAeFrom getFrom()
   {
      return mFrom;
   }

   /**
    * @param aFrom The from to set.
    */
   public void setFrom(IAeFrom aFrom)
   {
      mFrom = aFrom;
      aFrom.setCopyOperation(this);
   }

   /**
    * @return Returns the to.
    */
   public IAeTo getTo()
   {
      return mTo;
   }

   /**
    * @param aTo The to to set.
    */
   public void setTo(IAeTo aTo)
   {
      mTo = aTo;
      aTo.setCopyOperation(this);
   }

   /**
    * Method used to extract data from given data object. When data represents a
    * Node element and only contains text nodes, a concatentation of the data will
    * be returned. Otherwise the element itself will be returned. For PartnerLink
    * references, a document fragment of the reference will be returned.
    * @param aData The data which may need to be extracted.
    */
   protected Object extractData(Object aData)
   {
      Object extractedData = aData;
      
      if (! (aData instanceof IAeMessageVariableWrapper))
      {
         if (aData instanceof Node)
         {
            // For attributes get the underlying data, and doc fragments get the first element
            Node node = (Node)aData;
            if (node.getNodeType() == Node.ATTRIBUTE_NODE ||
                node.getNodeType() == Node.TEXT_NODE ||
                node.getNodeType() == Node.CDATA_SECTION_NODE)
               extractedData = node.getNodeValue();
            else if (node.getNodeType() == Node.DOCUMENT_NODE)
               extractedData = ((Document)node).getDocumentElement();
         }
         else if (aData instanceof Double)
         {
            // if the double can safely be converted to a Long then do it.
            // this handles the case where we are attempting to set an int
            // value on a complex type and introducing a floating point number
            // where we don't want one. 
            Double d = (Double) aData;
            if (Double.compare(d.doubleValue(), Math.ceil(d.doubleValue()) ) == 0 )
            {
               extractedData = new Long(d.longValue());
            }
            
         }
      }
      
      return extractedData;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation#isKeepSrcElementName()
    */
   public boolean isKeepSrcElementName()
   {
      return mKeepSrcElementName;
   }

   /**
    * @see org.activebpel.rt.bpel.impl.activity.assign.IAeCopyOperation#isVirtual()
    */
   public boolean isVirtual()
   {
      return false;
   }

   /**
    * @return the ignoreMissingFromData
    */
   public boolean isIgnoreMissingFromData()
   {
      return mIgnoreMissingFromData;
   }
}
