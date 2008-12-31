// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/ext/IAeExtensionElementDef.java,v 1.1 2006/10/12 20:15:2
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
package org.activebpel.rt.bpel.def.io.ext;

import javax.xml.namespace.QName;

/**
 * Generic interface for all BPEL extension elements.
 * <br />
 * It is expected that readers and writers will cast
 * instances to specific subclasses for serialization/
 * deserialization.
 */
public interface IAeExtensionElementDef
{
   /**
    * Accessor for extension element QName.
    * @return QName
    */
   public QName getElementQName();
   
   /**
    * Mutator for extension element QName;
    * @param aQName
    */
   public void setElementQName( QName aQName );
   
   /**
    * Preserve any comments.
    * @param aComments
    */
   public void setComments( String aComments );
}
