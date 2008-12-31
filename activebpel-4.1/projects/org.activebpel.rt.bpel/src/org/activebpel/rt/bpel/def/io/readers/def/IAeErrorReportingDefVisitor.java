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
package org.activebpel.rt.bpel.def.io.readers.def; 

import java.util.List;

import org.activebpel.rt.bpel.def.visitors.IAeDefVisitor;

/**
 * Simple extension to the def visitor interface to introduce error reporting capabilities.
 */
public interface IAeErrorReportingDefVisitor extends IAeDefVisitor
{
   /**
    * Returns true if there are errors encountered during the parse. These errors are
    * significant enough to not add definition object to the process def.
    * 
    * One example of an error like this would be encountering multiple child
    * activities when only one is allowed.
    */
   public boolean hasErrors();
   
   /**
    * Getter for the errors list.
    */
   public List getErrors();
}
 
