//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/reply/AeDurableReplyInfo.java,v 1.1 2006/05/24 23:07:0
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
package org.activebpel.rt.bpel.impl.reply;

import java.util.HashMap;
import java.util.Map;

/**
 * Basic implementation of a durable reply info.
 */
public class AeDurableReplyInfo implements IAeDurableReplyInfo
{
   /** Durable reply type. */
   private String mType;
   /** Map containing reply information. */
   private Map mProperties;
   
   /**
    * Constructs a reply info for the given type and properties.
    * @param aType durable reply type.
    * @param aProperties reply properties.
    */
   public AeDurableReplyInfo(String aType, Map aProperties)
   {
      mType = aType;
      mProperties = aProperties;
   }
   
   /**
    * Constructs a reply info for the given type..
    * @param aType durable reply type.
    */
   public AeDurableReplyInfo(String aType)
   {
      this(aType, null);
   }
   
   /** 
    * Overrides method to return reply type. 
    * @see org.activebpel.rt.bpel.impl.reply.IAeDurableReplyInfo#getType()
    */
   public String getType()
   {
      return mType;
   }

   /** 
    * Overrides method to reply context properties. 
    * @see org.activebpel.rt.bpel.impl.reply.IAeDurableReplyInfo#getProperties()
    */
   public Map getProperties()
   {
      if (mProperties == null)
      {
         mProperties = new HashMap();
      }
      return mProperties;
   }

}
