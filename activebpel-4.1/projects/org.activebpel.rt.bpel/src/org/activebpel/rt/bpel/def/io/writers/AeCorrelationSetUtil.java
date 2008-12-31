// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/writers/AeCorrelationSetUtil.java,v 1.5 2006/06/26 16:50:4
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
package org.activebpel.rt.bpel.def.io.writers;

import java.util.Iterator;
import java.util.StringTokenizer;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.def.AeCorrelationSetDef;
import org.activebpel.rt.util.AeUtil;
import org.activebpel.rt.util.AeXmlUtil;
import org.w3c.dom.Element;

/**
 * Utility class for reading and writing correlationSet properties.
 */
public class AeCorrelationSetUtil
{
   /**
    * Default c'tor.
    */
   private AeCorrelationSetUtil()
   {
      super();
   }
   
   /**
    * Parses and sets the properties of this object.
    *
    * @param aDef
    * @param aProperties
    * @param aElement
    */
   public static void addProperties(AeCorrelationSetDef aDef, String aProperties, Element aElement)
   {
      if (aProperties != null)
      {
         StringTokenizer tok = new StringTokenizer(aProperties, " "); //$NON-NLS-1$

         // Loop through all correlation properties. If property is qualified by
         // a namespace use different constructor for property
         for (int i = 0; tok.hasMoreTokens(); i++)
         {
            String corr = tok.nextToken();
            if (!AeXmlUtil.hasColon(corr))
            {
               // no prefix specified, use null namespace.
               aDef.addProperty(new QName(null, corr));
            }
            else
            {
               String prefix     = AeXmlUtil.extractPrefix(corr);
               String localPart  = AeXmlUtil.extractLocalPart(corr);
               aDef.addProperty( new QName(
                  AeXmlUtil.getNamespaceForPrefix(aElement, prefix), localPart) );
            }
         }
      }
   }

   /**
    * Formats and returns the properties attribute value.
    * @return String the properties.
    */
   public static String formatProperties(AeCorrelationSetDef aDef, Element aElement)
   {
      boolean first = true;
      StringBuffer props = new StringBuffer();
      for (Iterator it=aDef.getPropertiesList(); it.hasNext(); )
      {
         QName qname = (QName)it.next();
         if (!first)
            props.append(" "); //$NON-NLS-1$
         first = false;
         props.append(getStringForQName(aElement, qname));
      }
      return props.toString();
   }

   /**
    * A helper method which converts the given QName object to a QName string of
    * the format "prefix:localPart". The prefix is determined by walking the given
    * element ancestors looking for the declared namespace.
    * @param aElement
    * @param aQName the QName object
    * @return String the QName string.
    */
   private static String getStringForQName(Element aElement, QName aQName)
   {
      String prop = aQName.getLocalPart();
      String prefix = AeXmlUtil.getPrefixForNamespace(aElement, aQName.getNamespaceURI());
      if (! AeUtil.isNullOrEmpty(prefix))
         prop = prefix + ":" + prop; //$NON-NLS-1$
      
      return prop;
   }
}
