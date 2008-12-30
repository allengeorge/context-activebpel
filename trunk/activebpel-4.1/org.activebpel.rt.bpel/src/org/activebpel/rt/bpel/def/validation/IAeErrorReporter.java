// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/validation/IAeErrorReporter.java,v 1.7 2006/12/14 22:41:4
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

import org.activebpel.rt.bpel.def.AeProcessDef;


/**
 * Specification for error reporting classes.
 */
public interface IAeErrorReporter extends IAeBaseErrorReporter
{
   /** Set the root definition node for error reporting. */
   public void setRootDefNode( AeProcessDef aRootDefNode );
   
   /**
    * Remove the issues for a given node.  This is for cases when a node is found to be 'not used', etc.
    * 
    * @param aNode The node whose issues should be removed.
    */
   public abstract void removeIssues( Object aNode );

   /**
    * Report the errors collected by this object.
    */
   public abstract void reportErrors();

   /**
    * Report the warnings collected by this object.
    */
   public abstract void reportWarnings();

   /**
    * Report the information collected by this object.
    */
   public abstract void reportInfo();

   /**
    * Report all errors, warnings and information collected by this object.
    */
   public abstract void reportAll();

   /**
    * Returns true if we are processing validations errors for the BPEL.
    */
   public abstract boolean isProcessErrors();

   /**
    * Returns true if we are processing validations warnings for the BPEL.
    */
   public abstract boolean isProcessWarnings();

   /**
    * Returns true if we are processing validations info for the BPEL.
    */
   public abstract boolean isProcessInfos();
}
