// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.tamino/src/org/activebpel/rt/bpel/server/engine/storage/tamino/AeTaminoConfig.java,v 1.7 2007/08/17 00:57:3
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
package org.activebpel.rt.bpel.server.engine.storage.tamino;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.bpel.server.engine.storage.xmldb.AeXMLDBConfig;

/**
 * This class encapsulates the Tamino XQuery statements used by the Active BPEL Tamino based persistence
 * layer. This class uses a SAX parser to first parse an xml file that contains the XQuery queries.
 */
public class AeTaminoConfig extends AeXMLDBConfig
{
   /**
    * Creates a Tamino config object.
    */
   public AeTaminoConfig()
   {
      this(Collections.EMPTY_MAP);
   }

   /**
    * Creates a Tamino config object with the given map of constant overrides.
    * 
    * @param aConstantOverrides
    */
   public AeTaminoConfig(Map aConstantOverrides)
   {
      super(aConstantOverrides);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.AeStorageConfig#getStatementConfigFilenames()
    */
   protected List getStatementConfigFilenames()
   {
      String fileName = "tamino-queries.xml"; //$NON-NLS-1$

      List list = super.getStatementConfigFilenames();
      list.add(new AeFilenameClassTuple(fileName, AeTaminoConfig.class));
      
      return list;
   }
}
