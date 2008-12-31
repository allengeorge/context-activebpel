//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/attachment/AePairSerializer.java,v 1.1 2007/04/23 23:38:2
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
package org.activebpel.rt.bpel.server.engine.storage.attachment;

import java.util.Iterator;
import java.util.Map;

import org.activebpel.rt.bpel.impl.fastdom.AeFastDocument;
import org.activebpel.rt.bpel.impl.fastdom.AeFastElement;
import org.activebpel.rt.bpel.impl.fastdom.AeFastText;

/**
 * Utility class to convert between a java.util.Map containing name/value pairs and an AeFastDocument
 * Restrictions on the map: key and value are assumed to be Strings
 */
public class AePairSerializer
{

   /** xml tag name for name pairs  */
   protected static final String ROOT_ELEMENT = "pairs"; //$NON-NLS-1$

   /** xml tag name for a single name pair */
   protected static final String PAIR_ELEMENT = "pair"; //$NON-NLS-1$

   /** Attribute name of a pair */
   protected static final String NAME_ATTRIBUTE = "name"; //$NON-NLS-1$

   /**
    * Convert a map of name pairs into a fast document xml representation
    * @param aNamePair
    * @return
    */
   public static AeFastDocument serialize(Map aNamePair)
   {
      AeFastElement root = new AeFastElement(ROOT_ELEMENT);

      for (Iterator pairs = aNamePair.entrySet().iterator(); pairs.hasNext();)
      {
         Map.Entry pair = (Map.Entry)pairs.next();
         AeFastElement pairElement = new AeFastElement(PAIR_ELEMENT);
         pairElement.setAttribute(NAME_ATTRIBUTE, (String)pair.getKey());
         AeFastText value = new AeFastText((String)pair.getValue());
         pairElement.appendChild(value);
         root.appendChild(pairElement);
      }
      AeFastDocument document = new AeFastDocument();
      document.setRootElement(root);
      return document;
   }
}
