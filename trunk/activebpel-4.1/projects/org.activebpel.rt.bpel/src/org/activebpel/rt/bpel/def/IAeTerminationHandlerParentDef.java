// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/IAeTerminationHandlerParentDef.java,v 1.1 2006/10/12 20:15:2
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
package org.activebpel.rt.bpel.def;

/**
 * BPEL constructs that parent the 'terminationHandler' construct should implement this interface.
 */
public interface IAeTerminationHandlerParentDef
{
   /**
    * Gets the 'terminationHandler' def.
    */
   public AeTerminationHandlerDef getTerminationHandlerDef();
   
   /**
    * Sets the 'terminationHandler' def.
    * 
    * @param aDef
    */
   public void setTerminationHandlerDef(AeTerminationHandlerDef aDef);
}
