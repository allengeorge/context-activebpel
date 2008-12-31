//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/AeUiDataBean.java,v 1.1 2005/11/04 15:08:4
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

import org.activebpel.rt.util.AeUtil;

/**
 *  A generic wrapper to contain UI form and other data.
 */
public class AeUiDataBean
{
   /** Data field name */
   private String mName;
   /** Data field value */
   private String mValue;
   /** Field label */
   private String mLabel;
   /** Field tooltip or help string */
   private String mTooltip;
   /** Extra parameter to store other data */
   private String mParameter;
   
   /**
    *  Default constructor.
    */
   public AeUiDataBean()
   {
      this(null, null, null);
   }
   
   /**
    * Constructs given field name and value.
    * @param aName field name
    * @param aValue field value  
    */   
   public AeUiDataBean(String aName, String aValue)
   {
       this(aName, aValue, null);
   }
   
   /**
    * Constructs given field name, value and label
    * @param aName field name
    * @param aValue field value
    * @param aLabel field display label. 
    */
   public AeUiDataBean(String aName, String aValue, String aLabel)
   {
      setName(aName); 
      setValue(aValue); 
      setLabel(aLabel); 
      setTooltip(""); //$NON-NLS-1$
      setParameter(""); //$NON-NLS-1$      
   }
   
   
   /**
    * @return Returns the label.
    */
   public String getLabel()
   {
      return mLabel;
   }
   
   /**
    * @param aLabel The label to set.
    */
   public void setLabel(String aLabel)
   {
      if ( AeUtil.isNullOrEmpty(aLabel) )
      {
         aLabel = ""; //$NON-NLS-1$
      }
      mLabel = aLabel;
   }
   
   /**
    * @return Returns the name.
    */
   public String getName()
   {
      return mName;
   }
   
   /**
    * @param aName The name to set.
    */
   public void setName(String aName)
   {
      if ( AeUtil.isNullOrEmpty(aName) )
      {
         aName = ""; //$NON-NLS-1$
      }      
      mName = aName;
   }
   /**
    * @return Returns the parameter.
    */
   public String getParameter()
   {
      return mParameter;
   }
   
   /**
    * @param aParameter The parameter to set.
    */
   public void setParameter(String aParameter)
   {
      if ( AeUtil.isNullOrEmpty(aParameter) )
      {
         aParameter = ""; //$NON-NLS-1$
      }      
      mParameter = aParameter;
   }
   
   /**
    * @return Returns the tooltip.
    */
   public String getTooltip()
   {
      return mTooltip;
   }
   
   /**
    * @param aTooltip The tooltip to set.
    */
   public void setTooltip(String aTooltip)
   {
      if ( AeUtil.isNullOrEmpty(aTooltip) )
      {
         aTooltip = ""; //$NON-NLS-1$
      }      
      mTooltip = aTooltip;
   }
   
   /**
    * @return Returns the value.
    */
   public String getValue()
   {
      return mValue;
   }
   
   /**
    * @param aValue The value to set.
    */
   public void setValue(String aValue)
   {
      if ( AeUtil.isNullOrEmpty(aValue) )
      {
         aValue = ""; //$NON-NLS-1$
      }      
      mValue = aValue;
   }
}
