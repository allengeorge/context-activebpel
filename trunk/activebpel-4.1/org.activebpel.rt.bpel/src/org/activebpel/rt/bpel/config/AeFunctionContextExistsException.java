//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/config/AeFunctionContextExistsException.java,v 1.1 2007/05/04 18:35:1
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
package org.activebpel.rt.bpel.config;

import org.activebpel.rt.AeException;

/**
 * Generated if there is an existing exception with a matching name, prefix or namespace.
 */
public class AeFunctionContextExistsException extends AeException
{
   // error type constants
   public static final int DUPLICATE_NAME = 1;
   public static final int DUPLICATE_PREFIX_OR_NAMESPACE = 2;
   
   /** error type */
   protected int mType;
   
   /**
    * Constructor.
    */
   public AeFunctionContextExistsException( int aType )
   {
      super();
      mType = aType;
   }
   
   /**
    * Return true of the exception was triggered because of a duplicate name.
    */
   public boolean isDuplicateName()
   {
      return mType == DUPLICATE_NAME;
   }
   
   /**
    * Return true if the exception was triggered because of a duplicate namespace or prefix.
    */
   public boolean isDuplicatePrefixOrNamespace()
   {
      return mType == DUPLICATE_PREFIX_OR_NAMESPACE;
   }
}
