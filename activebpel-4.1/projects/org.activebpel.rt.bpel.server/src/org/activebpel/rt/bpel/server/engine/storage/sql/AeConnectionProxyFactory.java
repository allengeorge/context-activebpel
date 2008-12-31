//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/engine/storage/sql/AeConnectionProxyFactory.java,v 1.1 2005/03/23 17:32:0
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
package org.activebpel.rt.bpel.server.engine.storage.sql;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * Generic factory for wrapping JDBC connections in their proxy instances.
 */
public class AeConnectionProxyFactory
{
   /**
    * Wrap the <code>Connection</code> in the <code>InvocationHandler</code>
    * instance.
    * @param aConn
    * @param aHandler
    */
   public static Connection getConnectionProxy( Connection aConn, InvocationHandler aHandler )
   {
      return (Connection) Proxy.newProxyInstance(
            aConn.getClass().getClassLoader(),
            new Class[] { Connection.class },
            aHandler );
   }
}
