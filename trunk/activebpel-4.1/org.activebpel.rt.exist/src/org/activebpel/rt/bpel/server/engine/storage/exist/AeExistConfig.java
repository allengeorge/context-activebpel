// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.exist/src/org/activebpel/rt/bpel/server/engine/storage/exist/AeExistConfig.java,v 1.2 2007/08/17 00:59:5
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
package org.activebpel.rt.bpel.server.engine.storage.exist;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBConfig;

/**
 * This class encapsulates the Exist XQuery statements used by the Active BPEL Exist based persistence
 * layer. This class uses a SAX parser to first parse an xml file that contains the XQuery queries.
 */
public class AeExistConfig extends AeXMLDBConfig
{
   /**
    * Creates a Exist config object.
    */
   public AeExistConfig()
   {
      this(Collections.EMPTY_MAP);
   }

   /**
    * Creates a Exist config object with the given map of constant overrides.
    * 
    * @param aConstantOverrides
    */
   public AeExistConfig(Map aConstantOverrides)
   {
      super(aConstantOverrides);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.AeStorageConfig#getStatementConfigFilenames()
    */
   protected List getStatementConfigFilenames()
   {
      String fileName = "exist-queries.xml"; //$NON-NLS-1$

      List list = super.getStatementConfigFilenames();
      list.add(new AeFilenameClassTuple(fileName, AeExistConfig.class));
      
      return list;
   }
}
