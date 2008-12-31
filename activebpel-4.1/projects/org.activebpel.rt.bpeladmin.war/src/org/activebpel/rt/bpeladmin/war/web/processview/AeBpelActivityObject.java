//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/processview/AeBpelActivityObject.java,v 1.3 2006/07/25 17:56:3
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
package org.activebpel.rt.bpeladmin.war.web.processview;

import java.util.ArrayList;
import java.util.List;

import org.activebpel.rt.bpel.def.AeBaseDef;

/**
 * Represents a BPEL "activity" as per BPEL v1.1. An activity in this case
 * is for example, Receive, Pick, Reply etc. which have the required
 * "standard elements" target and source.
 */
public class AeBpelActivityObject extends AeBpelObjectContainer
{
   /** List containing source links. */
   private List mSourceLinks = new ArrayList();
   
   /** List of target links */
   private List mTargetLinks = new ArrayList();
   
   /** BPEL activity specific condition attribute. This attribute may be used to
    *  display text next to the activity (as an adornment).
    */
   private String mCondition = ""; //$NON-NLS-1$
      
   /**
    * Default ctor.
    * @param aTagName activity tag name
    * @param aDef activity definition.
    */
   public AeBpelActivityObject(String aTagName, AeBaseDef aDef)
   {
      super(aTagName, aDef);
   }
      
   /**
    * Default ctor.
    * @param aTagName activity tag name
    * @param aIconName activity icon.
    * @param aDef activity definition.
    */   
   public AeBpelActivityObject(String aTagName, String aIconName, AeBaseDef aDef)
   {
      super(aTagName, aIconName, aDef);
   }
   
   /** 
    * @return List of source links.
    */
   public List getSourceLinks()
   {
      return mSourceLinks;
   }
   
   /**
    * Adds a source link (outbound) to this activity. 
    * @param aLink outbound source link.
    */
   public void addSourceLink(AeBpelLinkObject aLink)
   {
      if (!mSourceLinks.contains(aLink))
      {
         mSourceLinks.add(aLink);
         aLink.setSource(this);
      }
   }

   /** 
    * @return List of link targets associated with this activity.
    */
   public List getTargetLinks()
   {
      return mTargetLinks;
   }
   
   /**
    * Adds a target link.
    * @param aLink
    */
   public void addTargetLink(AeBpelLinkObject aLink)
   {
      if (!mTargetLinks.contains(aLink))
      {
         mTargetLinks.add(aLink);
         aLink.setTarget(this);
      }
   }
   
   /**
    * Returns, condition for the used in the BPEL activity to be displayed
    * as an adorment in the presentation layer. For example, the 'duration'
    * in a Wait activity, or 'condition' in a While activity.
    * 
    * @return activity condition.
    */
   public String getCondition()
   {
      return mCondition;
   }
   
   /**
    * Sets the BPEL activity condition, when applicable.
    * @param aCond
    */
   public void setCondition(String aCond)
   {
      mCondition = aCond;
   }
      
}
