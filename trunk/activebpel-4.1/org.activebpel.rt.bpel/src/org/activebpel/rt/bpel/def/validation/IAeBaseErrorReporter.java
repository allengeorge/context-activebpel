// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/IAeBaseErrorReporter.java,v 1.3 2005/02/10 16:34:1
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
package org.activebpel.rt.bpel.def.validation;

/**
 * Interface for reporting errors and warnings found during the analysis of a
 * deployment plan or bpel process.
 */
public interface IAeBaseErrorReporter
{
   // TODO (MF)  the methods below suggest that a key is passed in when in reality
   //            the (info|error|warning)code is actually a message format pattern.
   //            should change this as part of a general i18n effort.
   
   /**
    * Add information to the collection of info for a given node.
    * 
    * @param aInfoCode The information code of the info to add.
    * @param aArgs Object array containing substitution args for '{n}'s.
    * @param aNode The node to which the information refers.
    */
   public abstract void addInfo(String aInfoCode, Object[] aArgs, Object aNode);


   /**
    * Add an error code to the collection of errors for a given node.
    * 
    * @param aErrorCode The error code of the error to add.
    * @param aArgs Object array containing substitution args for '{n}'s.
    * @param aNode The node on which the error occurred.
    */
   public abstract void addError(String aErrorCode, Object[] aArgs, Object aNode);

   /**
    * Add a warning code to the collection of warnings for a given node.
    * 
    * @param aWarnCode The warning code of the warning to add.
    * @param aArgs Object array containing substitution args for '{n}'s.
    * @param aNode The node on which the warning occurred.
    */
   public abstract void addWarning(String aWarnCode, Object[] aArgs, Object aNode);

   /**
    * Returns true if any errors were encountered.
    */
   public abstract boolean hasErrors();

   /**
    * Returns true if any warnings were encountered.
    */
   public abstract boolean hasWarnings();


}
