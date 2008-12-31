// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/AeStaticAnalysisException.java,v 1.3 2006/07/19 20:07:5
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
package org.activebpel.rt.bpel;

/**
 * This exception indicates some error within the business process that should
 * have been caught during the static analysis of the business process. An 
 * example of this might be an activity trying to access a variable that isn't
 * declared in any enclosing scope or at the process level. 
 */
public class AeStaticAnalysisException extends AeBusinessProcessException
{
   /**
    * Creates a static analysis exception with the given message.
    * 
    * @param aMessage
    */
   public AeStaticAnalysisException(String aMessage)
   {
      super(aMessage);
   }
}
