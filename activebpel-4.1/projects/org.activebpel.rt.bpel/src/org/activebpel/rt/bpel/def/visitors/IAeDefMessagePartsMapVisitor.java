//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/IAeDefMessagePartsMapVisitor.java,v 1.2 2006/09/15 14:49:5
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
package org.activebpel.rt.bpel.def.visitors; 

import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.def.AeProcessDef;

/**
 * visits each node and inlines the message parts info used for the wsio operation 
 */
public interface IAeDefMessagePartsMapVisitor
{
   /**
    * Traverses the given process definition and assigns message parts maps to
    * web service activities.
    *
    * @param aDef
    * @param aThrowOnErrorsFlag
    * @throws AeBusinessProcessException
    */
   public void assignMessagePartsMaps(AeProcessDef aDef, boolean aThrowOnErrorsFlag) throws AeBusinessProcessException;
}
 
