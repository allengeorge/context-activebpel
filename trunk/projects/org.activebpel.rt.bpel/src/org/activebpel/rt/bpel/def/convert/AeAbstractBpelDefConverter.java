// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/convert/AeAbstractBpelDefConverter.java,v 1.3 2006/12/14 22:40:2
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
package org.activebpel.rt.bpel.def.convert;

import org.activebpel.rt.bpel.def.AeProcessDef;

/**
 * An abstract base class that contains some common functionality for all BPEL Def converters.
 */
public abstract class AeAbstractBpelDefConverter implements IAeBpelDefConverter
{
   /** The process def we are converting. */
   private AeProcessDef mProcessDef;

   /**
    * Default c'tor.
    */
   public AeAbstractBpelDefConverter()
   {
   }

   /**
    * @see org.activebpel.rt.bpel.def.convert.IAeBpelDefConverter#convert(org.activebpel.rt.bpel.def.AeProcessDef)
    */
   public void convert(AeProcessDef aProcessDef)
   {
      setProcessDef(aProcessDef);
      convertProcessNamespace();
      doConversion();
   }
   
   /**
    * Converts the namespace.
    */
   protected void convertProcessNamespace()
   {
      String newNamespace = getNewBpelNamespace();
      String preferredPrefix = getNewBpelNamespacePrefix();
      getProcessDef().setNamespace(newNamespace);
      getProcessDef().addNamespace("", newNamespace); //$NON-NLS-1$
      getProcessDef().allocateNamespace(preferredPrefix, newNamespace);      
   }

   /**
    * Returns the BPEL namespace of the target format (the BPEL version we are converting TO).
    */
   protected abstract String getNewBpelNamespace();

   /**
    * Returns the <strong>preferred</strong> BPEL namespace prefix for the BPEL namespace 
    * we are converting TO.  For example, if we are converting to WS-BPEL 2.0, this would 
    * return "bpel".  If we are converting to BPEL4WS 1.1, this would return "bpws".
    */
   protected abstract String getNewBpelNamespacePrefix();
   
   /**
    * Called to actually do the conversion steps that will be specific to a particular type of
    * conversion (e.g. from 1.1 to 2.0).
    */
   protected abstract void doConversion();

   /**
    * @return Returns the processDef.
    */
   protected AeProcessDef getProcessDef()
   {
      return mProcessDef;
   }

   /**
    * @param aProcessDef The processDef to set.
    */
   protected void setProcessDef(AeProcessDef aProcessDef)
   {
      mProcessDef = aProcessDef;
   }
}
