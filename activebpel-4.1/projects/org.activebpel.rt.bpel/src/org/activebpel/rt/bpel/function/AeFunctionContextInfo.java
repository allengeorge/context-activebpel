//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/function/AeFunctionContextInfo.java,v 1.1 2005/06/08 12:50:2
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
package org.activebpel.rt.bpel.function;

import org.activebpel.rt.util.AeUtil;

/**
 * Wraps the <code>IAeFunctionContext</code> details.  This is an internal 
 * storage construct.
 */
public class AeFunctionContextInfo
{
   /** function context grouping name */
   protected String mName;
   /** namespace to match on */
   protected String mNamespace;
   /** function context impl */
   protected IAeFunctionContext mFunctionContext;
   
   /**
    * Constructor.
    * @param aName User specified name for this grouping.
    * @param aNamespace Namespace to match on. Can be null if a prefix is provided.
    * @param aContext
    */
   public AeFunctionContextInfo( String aName, String aNamespace, IAeFunctionContext aContext )
   {
      mName = aName;
      mNamespace = aNamespace;
      mFunctionContext = aContext;
   }

   /**
    * @return Returns the functionContext.
    */
   public IAeFunctionContext getFunctionContext()
   {
      return mFunctionContext;
   }
   
   /**
    * @return Returns the name.
    */
   public String getName()
   {
      return mName;
   }
   
   /**
    * @return Returns the namespace.
    */
   public String getNamespace()
   {
      return mNamespace;
   }

   /**
    * This impl only compares the name property as there should never be
    * more than one <code>AeFunctionContextInfo</code> registered with the same
    * name.
    * @see java.lang.Object#equals(java.lang.Object)
    */
   public boolean equals(Object aObj)
   {
      if( aObj != null && aObj instanceof AeFunctionContextInfo )
      {
         return AeUtil.compareObjects( ((AeFunctionContextInfo)aObj).getName(), getName() );
      }
      return false;
   }

   /**
    * @return Returns the functionContext classname.
    */
   public String getFunctionContextClassname()
   {
      String className = getFunctionContext().getClass().getName();
      if( getFunctionContext() instanceof AeFunctionContextWrapper )
      {
         className = ((AeFunctionContextWrapper) getFunctionContext()).getDelegate().getClass().getName();
      }
      return className;
   }

   /**
    * @see java.lang.Object#hashCode()
    */
   public int hashCode()
   {
      return getName().hashCode();
   }
}
