//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/admin/server/AeActiveBpelDeployBPRSkeleton.java,v 1.3 2007/01/29 23:04:4
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
package org.activebpel.rt.axis.bpel.admin.server; 

import java.rmi.RemoteException;

import org.activebpel.rt.axis.bpel.admin.types.AesDeployBprType;
import org.activebpel.rt.axis.bpel.admin.types.AesStringResponseType;

/**
 * Server binding for deploying bprs. Subclass of the full remote debug class that only exposes the deployBpr method.
 */
public class AeActiveBpelDeployBPRSkeleton extends AeActiveBpelAdminSkeleton
{
   /**
    * @see org.activebpel.rt.axis.bpel.admin.server.AeActiveBpelAdminSkeleton#deployBpr(org.activebpel.rt.axis.bpel.admin.types.AesDeployBprType)
    */
   public AesStringResponseType deployBpr(AesDeployBprType input) throws RemoteException
   {
      return super.deployBpr(input);
   }
}
