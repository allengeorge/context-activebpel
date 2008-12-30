// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/admin/rdebug/client/IAeEventHandlerService.java,v 1.1 2004/12/02 00:01:4
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
package org.activebpel.rt.bpel.server.admin.rdebug.client;

import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

/**
 * The interface which defines the remote debug service.
 */
public interface IAeEventHandlerService extends Service
{
   /**
    * Returns the address of the remote debug service.
    */
   public String getRemoteDebugAddress();

   /**
    * Returns the interface for the web service methods of the remote debugger. 
    * @throws ServiceException
    */
   public IAeEventHandler getRemoteDebugService() throws ServiceException;
}
