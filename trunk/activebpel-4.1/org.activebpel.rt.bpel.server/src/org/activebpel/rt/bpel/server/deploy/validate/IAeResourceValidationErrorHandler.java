//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/validate/IAeResourceValidationErrorHandler.java,v 1.1 2005/11/01 14:39:2
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
package org.activebpel.rt.bpel.server.deploy.validate;

import org.w3c.dom.Node;


/**
 * This interface defines methods that will be called by a validator when it is
 * validating a resource (PDD, PDEF, etc...) file/document.
 */
public interface IAeResourceValidationErrorHandler
{
   /**
    * Called when the validator finds a fatal error and cannot continue.
    * 
    * @param aMessage
    */
   public void fatalError(String aMessage);
   
   /**
    * Called when a warning is found while parsing the resource.
    * 
    * @param aMessage
    * @param aLineNumber
    */
   public void parseWarning(String aMessage, int aLineNumber);

   /**
    * Called when an error is found while parsing the resource.
    * 
    * @param aMessage
    * @param aLineNumber
    */
   public void parseError(String aMessage, int aLineNumber);

   /**
    * Called when a fatal error is found while parsing the resource.
    * 
    * @param aMessage
    * @param aLineNumber
    */
   public void parseFatalError(String aMessage, int aLineNumber);

   /**
    * Called when the validator discovers an error when performing "additional validation".  This
    * additional validation tries to find problems that cannot be caught by the resource's schema.
    * 
    * @param aMessage
    * @param aNode
    */
   public void contentError(String aMessage, Node aNode);
   
   /**
    * Called when the validator discovers a warning when performing "additional validation".  This
    * additional validation tries to find problems that cannot be caught by the resource's schema.
    * 
    * @param aMessage
    * @param aNode
    */
   public void contentWarning(String aMessage, Node aNode);
}
