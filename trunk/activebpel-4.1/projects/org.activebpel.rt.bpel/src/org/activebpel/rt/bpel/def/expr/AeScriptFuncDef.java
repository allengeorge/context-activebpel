//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/expr/AeScriptFuncDef.java,v 1.5 2007/06/29 22:24:1
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
package org.activebpel.rt.bpel.def.expr;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

/**
 * This class represents a single function in an expression.  Each function can be described 
 * as the name of the function and a list of arguments to the function.
 */
public class AeScriptFuncDef
{
   /** This is the value of an arg when it is a nested expression. */
   public static final Object __EXPRESSION__ = new Object() {
      public String toString()
      {
         return "__EXP__"; //$NON-NLS-1$
      }
   };

   /** The function's namespace. */
   private String mNamespace;
   /** The function name. */
   private String mName;
   /** The list of arguments passed to the function. */
   private List mArgs;
   /** The parent function if this is a nested function. */
   private AeScriptFuncDef mParent;

   /**
    * Constructs a function from the function QName.  The argument list will be
    * initialized to the empty list.  Arguments can be added later.
    * 
    * @param aNamespace
    * @param aName
    */
   public AeScriptFuncDef(String aNamespace, String aName)
   {
      setNamespace(aNamespace);
      setName(aName);
      setArgs(new ArrayList());
   }
   
   /**
    * Form of the constructor that takes a QName.
    * 
    * @param aQName
    */
   public AeScriptFuncDef(QName aQName)
   {
      this(aQName.getNamespaceURI(), aQName.getLocalPart());
   }
   
   /**
    * Returns the function's argument found at the given index.  Returns null if there is no argument
    * at the given index.
    * 
    * @param aArgIdx
    */
   public Object getArgument(int aArgIdx)
   {
      if (aArgIdx >= getArgs().size())
      {
         return null;
      }
      return getArgs().get(aArgIdx);
   }
   
   /**
    * Returns the function's argument found at the given index.  Only returns a value if the type of
    * the argument is a String.  If it is some other type, or there is no argument at the given 
    * index, this method returns null.
    * 
    * @param aArgIdx
    */
   public String getStringArgument(int aArgIdx)
   {
      Object rval = getArgument(aArgIdx);
      if (rval != null && rval instanceof String)
      {
         return (String) rval;
      }
      else
      {
         return null;
      }
   }

   /**
    * Returns the function's argument found at the given index.  Only returns a value if the type of
    * the argument is an expression object.  If it is some other type, or there is no argument at the given 
    * index, this method returns null.
    * 
    * @param aArgIdx
    */
   private Object getExpressionArgument(int aArgIdx)
   {
      Object rval = getArgument(aArgIdx);

      if (rval == __EXPRESSION__)
      {
         return rval;
      }
      else
      {
         return null;
      }
   }
   
   /**
    * Returns true if the argument at the given index exists and is a String Literal.
    * 
    * @param aArgIdx
    */
   public boolean isStringArgument(int aArgIdx)
   {
      return getStringArgument(aArgIdx) != null;
   }

   /**
    * Returns true if the argument at the given index exists and is an expression.
    * 
    * @param aArgIdx
    */
   public boolean isExpressionArgument(int aArgIdx)
   {
      return getExpressionArgument(aArgIdx) != null;
   }
   
   /**
    * @return Returns the args.
    */
   public List getArgs()
   {
      return mArgs;
   }

   /**
    * @param aArgs The args to set.
    */
   public void setArgs(List aArgs)
   {
      mArgs = aArgs;
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
   protected void setName(String aName)
   {
      mName = aName;
   }

   /**
    * @see java.lang.Object#toString()
    */
   public String toString()
   {
      return MessageFormat.format("{0}(args: {1})", new Object[] { getName(), getArgs().toString() }); //$NON-NLS-1$
   }
   
   /**
    * @return Returns the parent.
    */
   public AeScriptFuncDef getParent()
   {
      return mParent;
   }

   /**
    * @param aParent The parent to set.
    */
   public void setParent(AeScriptFuncDef aParent)
   {
      mParent = aParent;
   }
   
   /**
    * @return Returns the namespace.
    */
   public String getNamespace()
   {
      return mNamespace;
   }
   
   /**
    * @param aNamespace The namespace to set.
    */
   public void setNamespace(String aNamespace)
   {
      mNamespace = aNamespace;
   }
   
   /**
    * Gets the function's fully qualified name.
    */
   public QName getQName()
   {
      return new QName(getNamespace(), getName());
   }
}
