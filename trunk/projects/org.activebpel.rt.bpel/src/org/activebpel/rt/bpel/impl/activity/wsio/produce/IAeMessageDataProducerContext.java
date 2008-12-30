// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/wsio/produce/IAeMessageDataProducerContext.java,v 1.1 2006/08/03 23:33:0
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
package org.activebpel.rt.bpel.impl.activity.wsio.produce;

import java.util.Iterator;

import org.activebpel.rt.bpel.IAeVariable;
import org.activebpel.rt.bpel.impl.AeAbstractBpelObject;
import org.activebpel.rt.message.AeMessagePartsMap;

/**
 * Defines the interface for a message data producer to interact with a BPEL
 * implementation object.
 */
public interface IAeMessageDataProducerContext
{
   /**
    * Returns the BPEL implementation object.
    */
   public AeAbstractBpelObject getBpelObject();
   
   /**
    * Returns the message parts map that defines the web service interaction.
    */
   public AeMessagePartsMap getMessagePartsMap();
   
   /**
    * Returns an iterator over the <code>toPart</code> definitions if this is a
    * <code>toParts</code> interaction.
    */
   public Iterator getToPartDefs();

   /**
    * Returns the variable that contains the data if this is a variable
    * interaction.
    */
   public IAeVariable getVariable();
}
