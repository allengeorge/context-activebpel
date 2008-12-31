// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/AeActivityPartnerLinkBaseDef.java,v 1.12 2006/09/11 23:06:2
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
package org.activebpel.rt.bpel.def;

import org.activebpel.rt.bpel.def.activity.IAePartnerLinkActivityDef;
import org.activebpel.rt.bpel.def.activity.support.AeCorrelationDef;
import org.activebpel.rt.bpel.def.util.AeDefUtil;
import org.activebpel.rt.message.AeMessagePartsMap;

import javax.xml.namespace.QName;
import java.util.Iterator;

/**
 * Definition for bpel partner link based activity
 */
public abstract class AeActivityPartnerLinkBaseDef extends AeActivityDef implements
      IAePartnerLinkActivityDef, IAeCorrelationsParentDef
{
   /** delegate which handle the partner link and correlation information. */
   private AePartnerLinkDelegate mDelegate = new AePartnerLinkDelegate();
   
   /**
    * Returns the delegate.
    */
   protected AePartnerLinkDelegate getDelegate()
   {
      return mDelegate;
   }

   /**
    * Provides the ability to add a correlation element to the correlation list.
    * 
    * @param aCorrelation the correlation to be added
    */
   public void addCorrelation(AeCorrelationDef aCorrelation)
   {
      getDelegate().addCorrelation(aCorrelation);
   }
   
   /**
    * Provide a list of the Correlation objects for the user to iterate .
    * 
    * @return iterator of AeCorrelationDef object
    */
   public Iterator getCorrelationList()
   {
      return getDelegate().getCorrelationDefs();
   }
   
   /**
    * Utility method to determine if a message has a correlation list 
    * @return true if there are elements in the correlation list.
    */
   public boolean hasCorrelationList()
   {
      return getDelegate().hasCorrelationList();
   }
   
   /**
    * Returns the name of the partner link associated with this activity.
    */
   public String getPartnerLink()
   {
      return getDelegate().getPartnerLink();
   }

   /**
    * Set the name of the partner link associated with this activity.
    */
   public void setPartnerLink(String aPartnerLink)
   {
      getDelegate().setPartnerLink(aPartnerLink);
   }

   /**
    * Accessor method to obtain the port type for the object.
    * 
    * @return QName of the port type for the object
    */
   public QName getPortType()
   {
      return getDelegate().getPortType();
   }

   /**
    * Mutator method to set the port type for the object.
    * 
    * @param aPortType the port type value to be set
    */
   public void setPortType(QName aPortType)
   {
      getDelegate().setPortType(aPortType);
   }

   /**
    * Accessor method to obtain the operation for the object.
    * 
    * @return name of the operation for the object
    */
   public String getOperation()
   {
      return getDelegate().getOperation();
   }

   /**
    * Mutator method to set the operation for the object.
    * 
    * @param aOperation the operation value to be set
    */
   public void setOperation(String aOperation)
   {
      getDelegate().setOperation(aOperation);
   }

   /**
    * @see org.activebpel.rt.bpel.def.IAeCorrelationsParentDef#setCorrelationsDef(org.activebpel.rt.bpel.def.AeCorrelationsDef)
    */
   public void setCorrelationsDef(AeCorrelationsDef aDef)
   {
      getDelegate().setCorrelationsDef(aDef);
   }

   /**
    * @see org.activebpel.rt.bpel.def.IAeCorrelationsParentDef#getCorrelationsDef()
    */
   public AeCorrelationsDef getCorrelationsDef()
   {
      return getDelegate().getCorrelationsDef();
   }
   
   /**
    * Gets the partner link key.
    */
   private AePartnerLinkDefKey getPartnerLinkKey()
   {
      AePartnerLinkDef plinkDef = getPartnerLinkDef();
      if (plinkDef == null)
         return null;

      return new AePartnerLinkDefKey(plinkDef);
   }
   
   /**
    * Gets the partner link def referenced by this activity
    */
   public AePartnerLinkDef getPartnerLinkDef()
   {
      return AeDefUtil.findPartnerLinkDef(this, getPartnerLink());
   }

   /**
    * @see org.activebpel.rt.bpel.def.activity.IAePartnerLinkActivityDef#getPartnerLinkOperationKey()
    */
   public AePartnerLinkOpKey getPartnerLinkOperationKey()
   {
      AePartnerLinkDefKey defKey = getPartnerLinkKey();
      if (defKey == null)
         return null;

      return new AePartnerLinkOpKey(defKey, getOperation());
   }

   /**
    * Sets the message parts map for the request message.
    */
   public void setConsumerMessagePartsMap(AeMessagePartsMap aInputMessagePartsMap)
   {
      getDelegate().setConsumerMessagePartsMap(aInputMessagePartsMap);
   }

   /**
    * Returns the message parts map for the request message.
    */
   public AeMessagePartsMap getConsumerMessagePartsMap()
   {
      return getDelegate().getConsumerMessagePartsMap();
   }

   /**
    * Sets the message parts map for the response message.
    */
   public void setProducerMessagePartsMap(AeMessagePartsMap aOutputMessagePartsMap)
   {
      getDelegate().setProducerMessagePartsMap(aOutputMessagePartsMap);
   }

   /**
    * Returns the message parts map for the response message.
    */
   public AeMessagePartsMap getProducerMessagePartsMap()
   {
      return getDelegate().getProducerMessagePartsMap();
   }
}
