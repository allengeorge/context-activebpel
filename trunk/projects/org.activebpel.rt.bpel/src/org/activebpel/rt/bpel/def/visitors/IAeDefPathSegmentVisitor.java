//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/IAeDefPathSegmentVisitor.java,v 1.1 2006/08/29 21:03:2
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

/**
 * A non-traversing visitor (double-dispatch only) that produces the appropriate
 * value for a def's segment path. In some cases, a def's segment path will differ
 * between bpel versions. For example, the AeActivityIfDef will produce a "switch"
 * segment for bpws 1.1 and "if" for ws-bpel. 
 */
public interface IAeDefPathSegmentVisitor extends IAeDefVisitor
{
   /**
    * Getter for the last path generated
    */
   public String getPathSegment();
   
   /**
    * Setter for the path
    * @param aPathSegment
    */
   public void setPathSegment(String aPathSegment);
}
 
