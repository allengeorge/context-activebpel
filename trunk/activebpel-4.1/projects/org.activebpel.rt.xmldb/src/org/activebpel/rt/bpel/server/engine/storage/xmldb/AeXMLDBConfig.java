// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.xmldb/src/org/activebpel/rt/bpel/server/engine/storage/xmldb/AeXMLDBConfig.java,v 1.1 2007/08/17 00:40:5
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
package org.activebpel.rt.bpel.server.engine.storage.xmldb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.activebpel.rt.bpel.server.engine.storage.AeStorageConfig;

/**
 * This class encapsulates the XMLDB XQuery statements used by the Active BPEL XMLDB based persistence
 * layer. This class uses a SAX parser to first parse an xml file that contains the XQuery queries.
 * 
 * FIXMEQ (xmldb) parameterize the xmldb config (list of config files to load) and pull that info from engine config (only need 1 config impl)
 */
public class AeXMLDBConfig extends AeStorageConfig
{
   private static final String XQUERY_NAME = "xquery"; //$NON-NLS-1$
   private static final String XQUERY_STATEMENT_NAME = "xquery-statement"; //$NON-NLS-1$

   /**
    * Creates a XMLDB config object.
    */
   public AeXMLDBConfig()
   {
      this(Collections.EMPTY_MAP);
   }

   /**
    * Creates a XMLDB config object with the given map of constant overrides.
    * 
    * @param aConstantOverrides
    */
   public AeXMLDBConfig(Map aConstantOverrides)
   {
      super(XQUERY_STATEMENT_NAME, XQUERY_NAME, aConstantOverrides);
   }

   /**
    * Gets a statement given a key (the name of the statement as configured in the file).
    * 
    * @param aKey A key that references a statement in the config file.
    * @return A statement.
    */
   public String getXQueryStatement(String aKey)
   {
      return (String) getStatement(aKey);
   }

   /**
    * @see org.activebpel.rt.bpel.server.engine.storage.AeStorageConfig#getStatementConfigFilenames()
    */
   protected List getStatementConfigFilenames()
   {
      String fileName = "xmldb-queries.xml"; //$NON-NLS-1$

      List list = new ArrayList();
      list.add(new AeFilenameClassTuple(fileName, AeXMLDBConfig.class));
      
      return list;
   }

   /**
    * Gets the xquery statement element name.
    */
   protected String getStatementElementName()
   {
      return XQUERY_STATEMENT_NAME; 
   }
   
   /**
    * Gets the xquery element name.
    */
   protected String getStatementValueElementName()
   {
      return XQUERY_NAME; 
   }
}
