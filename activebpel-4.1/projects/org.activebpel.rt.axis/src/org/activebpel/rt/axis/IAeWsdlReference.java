//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis/src/org/activebpel/rt/axis/IAeWsdlReference.java,v 1.3 2006/09/07 15:19:5
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
package org.activebpel.rt.axis;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.def.AePartnerLinkDef;
import org.activebpel.rt.bpel.server.catalog.IAeCatalogListener;
import org.activebpel.rt.wsdl.def.AeBPELExtendedWSDLDef;
import org.apache.axis.description.OperationDesc;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.List;

/**
 * Container class for wsdl properties that are accessed via <code>AeMutableServiceDesc</code>.
 * Will update its properties if the underlying wsdl is replaced.
 */
public interface IAeWsdlReference extends IAeCatalogListener
{
   /**
    * Set the initial state of this object.  This method should be called before
    * the wsdl reference is made available to the <code>AeServiceDesc</code>.
    * @throws Exception
    */
   public void init() throws AeException;
   
   /**
    * Getter for the port type <code>QName</code>.
    */
   public QName getPortTypeQName();
   
   /**
    * Getter for the partner link definition object.
    */
   public AePartnerLinkDef getPartnerLinkDef();
   
   /**
    * Return the list of all available <code>OperationDesc</code> objects.
    */
   public ArrayList getOperations();
   
   /**
    * Remove the given operation.
    * @param aOperation
    */
   public void removeOperationDesc(OperationDesc aOperation);
   
   /**
    * Get all overloaded operations by name.
    * @param aMethodName
    * @return null for no match, or an array of OperationDesc objects
    */
   public OperationDesc[] getOperationsByName(String aMethodName);
   
   /**
    * Return an operation matching the given method name.  Note that if we
    * have multiple overloads for this method, we will return the first one.
    * @return null for no match
    */
   public OperationDesc getOperationByName(String methodName);

   /**
    * Return the list of allowed method names.
    */
   public List getAllowedMethods();
   
   /**
    * Getter for the current global wsdl def mapped to this wsdl reference.
    */
   public AeBPELExtendedWSDLDef getWsdlDef();
   
}
