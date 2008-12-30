// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/IAeProcessPlan.java,v 1.11 2006/06/26 16:50:3
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
package org.activebpel.rt.bpel.impl;

import java.util.Collection;

import javax.xml.namespace.QName;

import org.activebpel.rt.bpel.IAeContextWSDLProvider;
import org.activebpel.rt.bpel.def.AePartnerLinkDefKey;
import org.activebpel.rt.bpel.def.AePartnerLinkOpKey;
import org.activebpel.rt.bpel.def.AeProcessDef;

/**
 * Describes some basic characteristics of a process that we need in order to
 * for the engine to dispatch a message to a specific process (or possibly create
 * a process instance).
 */
public interface IAeProcessPlan extends IAeContextWSDLProvider
{
   /**
    * Returns a flag indicating if this activity should create the process instance.
    * @param aPartnerLinkOpKey the partner link and operation key
    * @return boolean flag indicating if the operation is capable of creating the process
    */
   public boolean isCreateInstance(AePartnerLinkOpKey aPartnerLinkOpKey);

   /**
    * Returns a List of correlation properties for the given partnerLink, portType and operation.
    * If no list has been set an empty collection will be returned.
    * @param aPartnerLinkOpKey the partner link and operation key
    */
   public Collection getCorrelatedPropertyNames(AePartnerLinkOpKey aPartnerLinkOpKey);

   /**
    * Gets the process definition.
    */
   public AeProcessDef getProcessDef();

   /**
    * Return the port type for the my role and partner link.
    *
    * @param aPartnerLinkKey
    */
   public QName getMyRolePortType(AePartnerLinkDefKey aPartnerLinkKey);

   /**
    * Return true if the process should be suspended if it encounters
    * an uncaught fault.
    */
   public boolean isSuspendProcessOnUncaughtFaultEnabled();
}
