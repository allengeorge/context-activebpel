// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/AeService.java,v 1.4 2007/08/02 19:54:2
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
package ece.uwaterloo.ca.aag.platform.abaxis.client;

import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Service;

/**
 * This is a simple extension of the Axis Service object.
 */
public class AagABAxisService extends Service
{
   /** cached copy of the AxisClient to avoid creating one-off instances for each invoke */
   private static AxisClient sClient = null;

   /**
    * Overrides the base class to return a cached instance of the client.
    *
    * @see org.apache.axis.client.Service#getAxisClient()
    */
   protected AxisClient getAxisClient()
   {
      if (sClient == null)
      {
         synchronized(AagABAxisService.class)
         {
            if (sClient == null)
            {
               sClient = super.getAxisClient();
            }
         }
      }
      return sClient;
   }
}