//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/function/AeFunctionContextContainer.java,v 1.5 2006/09/18 20:04:1
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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.IAeBPELConstants;
import org.activebpel.rt.bpel.impl.function.AeInvalidFunctionContextException;
import org.activebpel.rt.util.AeUtil;

/**
 * Container for custom function contexts.
 */
public class AeFunctionContextContainer
{
   /** Internal storage for mapping namespace to function contexts. */
   private Map mNamespaceToFunctionContext;
   /** Locator for creating function contexts. */
   private IAeFunctionContextLocator mLocator;
   /** bpel expression function context */
   private IAeFunctionContext mBpelFunctionContext;
   /** bpel 2.0 expression function context */
   private IAeFunctionContext mBpel20FunctionContext;
   /** bpel extension function context */
   private IAeFunctionContext mBpelExtFunctionContext;

   /**
    * Constructor.
    * @param aLocator
    */
   public AeFunctionContextContainer( IAeFunctionContextLocator aLocator )
   {
      mLocator = aLocator;
      mNamespaceToFunctionContext = new HashMap();
   }

   /**
    * Return all of the custom <code>AeFunctionContextInfo</code> objects registered
    * with the container.
    */
   public Collection getFunctionContexts()
   {
      return mNamespaceToFunctionContext.values();
   }

   /**
    * Gets a list of all the function context namespaces.
    */
   public Collection getFunctionContextNamespaces()
   {
      List list = new LinkedList(mNamespaceToFunctionContext.keySet());
      list.add(IAeBPELConstants.BPWS_NAMESPACE_URI);
      list.add(IAeBPELConstants.WSBPEL_2_0_NAMESPACE_URI);
      list.add(IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI);
      return list;
   }

   /**
    * Remove the context with the given name from the container.
    * 
    * @param aName
    */
   public void remove( String aName )
   {
      for (Iterator iter = mNamespaceToFunctionContext.entrySet().iterator(); iter.hasNext(); )
      {
         Map.Entry entry = (Map.Entry) iter.next();
         AeFunctionContextInfo info = (AeFunctionContextInfo) entry.getValue();
         if (aName.equals(info.getName()))
         {
            iter.remove();
         }
      }
   }

   /**
    * Find <code>IAeFunctionContext</code> based on the given namespace.
    * 
    * @param aNamespace
    */
   public IAeFunctionContext getFunctionContext(String aNamespace)
   {
      IAeFunctionContext found = null;

      // TODO (EPW) treat these contexts the same as custom function contexts.
      if (IAeBPELConstants.BPWS_NAMESPACE_URI.equals(aNamespace))
      {
         found = getBpelContext();
      }
      else if (IAeBPELConstants.WSBPEL_2_0_NAMESPACE_URI.equals(aNamespace))
      {
         found = getBpel20Context();
      }
      else if (IAeBPELConstants.ABX_FUNCTIONS_NAMESPACE_URI.equals(aNamespace))
      {
         found = getBpelExtContext();
      }
      else
      {
         AeFunctionContextInfo info = (AeFunctionContextInfo) mNamespaceToFunctionContext.get(aNamespace);
         if (info != null)
         {
            found = info.getFunctionContext();
         }
      }
      
      return found;
   }
   
   /**
    * Clear out all registered custom function contexts. 
    */
   public void clearCustomFunctions()
   {
      mNamespaceToFunctionContext.clear();
   }

   /**
    * Add the function context to the container.
    * 
    * @param aName The user specified name for the grouping
    * @param aNamespace Namespace to match on.
    * @param aClassName IAeExpressionFunctionContext impl class name.
    * @throws AeException Throw if there is a problem finding/creating the <code>IAeFunctionContext</code> impl.
    */
   public void addFunctionContext(String aName, String aNamespace, String aClassName) throws AeException
   {
      if (AeUtil.isNullOrEmpty(aClassName))
      {
         throw new AeException(AeMessages.getString("AeFunctionContextContainer.ERROR_0") + aNamespace); //$NON-NLS-1$
      }
      if (AeUtil.isNullOrEmpty(aNamespace))
      {
         throw new AeException(AeMessages.getString("AeFunctionContextContainer.FUNCTION_CONTEXT_WITHOUT_NAMESPACE_ERROR")); //$NON-NLS-1$
      }

      IAeFunctionContext functionContext = loadFunctionContext(aNamespace, null, aClassName);
      addFunctionContext(aName, aNamespace, functionContext);
   }
   
   /**
    * Add the function context to the container.
    * 
    * @param aName The user specified name for the grouping
    * @param aNamespace Namespace to match on.
    * @param aContext
    */
   public void addFunctionContext( String aName, String aNamespace, IAeFunctionContext aContext )
   {
      AeFunctionContextInfo context = new AeFunctionContextInfo(aName, aNamespace, aContext);
      store(context);
   }
   
   /**
    * Load the <code>IAeFunctionContext</code>.
    * 
    * @param aNamespace
    * @param aLocation
    * @param aClassName
    * @throws AeInvalidFunctionContextException
    */
   public IAeFunctionContext loadFunctionContext(String aNamespace, String aLocation, String aClassName) throws AeInvalidFunctionContextException
   {
      return getLocator().locate(aNamespace, aLocation, aClassName);
   }

   /**
    * Store the <code>AeFunctionContextInfo</code> in the container.
    * 
    * @param aContextInfo
    */
   protected void store(AeFunctionContextInfo aContextInfo)
   {
      mNamespaceToFunctionContext.put(aContextInfo.getNamespace(), aContextInfo);
   }
   
   /**
    * Accessor for <code>IAeFunctionContextLocator</code>.
    */
   protected IAeFunctionContextLocator getLocator()
   {
      return mLocator;
   }

   /**
    * @return Returns the bpelContext.
    */
   protected IAeFunctionContext getBpelContext()
   {
      return mBpelFunctionContext;
   }

   /**
    * @return Returns the bpelContext.
    */
   protected IAeFunctionContext getBpel20Context()
   {
      return mBpel20FunctionContext;
   }

   /**
    * Setter for bpel function context.
    */
   public void setBpelContext(IAeFunctionContext aContext)
   {
      mBpelFunctionContext = aContext;
   }

   /**
    * Setter for bpel function context.
    */
   public void setBpel20Context(IAeFunctionContext aContext)
   {
      mBpel20FunctionContext = aContext;
   }

   /**
    * @return Returns the bpelExtContext.
    */
   protected IAeFunctionContext getBpelExtContext()
   {
      return  mBpelExtFunctionContext;
   }

   /**
    * Setter for bpel extension function context info.
    */
   public void setBpelExtContext(IAeFunctionContext aContext)
   {
      mBpelExtFunctionContext = aContext;
   }
}
