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
package org.activebpel.rt.axis.bpel;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.xml.rpc.encoding.Serializer;

import org.apache.axis.Constants;
import org.apache.axis.encoding.SerializerFactory;

// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis.bpel/src/org/activebpel/rt/axis/bpel/AeRPCEncodedSerializerFactory.java,v 1.3 2006/09/07 15:21:07 ewittmann Exp $
/////////////////////////////////////////////////////////////////////////////
//               PROPRIETARY RIGHTS STATEMENT
// The contents of this file represent confidential information that is the
// proprietary property of Active Endpoints, Inc.  Viewing or use of
// this information is prohibited without the express written consent of
// Active Endpoints, Inc. Removal of this PROPRIETARY RIGHTS STATEMENT
// is strictly forbidden. Copyright (c) 2002-2004 All rights reserved.
/////////////////////////////////////////////////////////////////////////////

/**
 * Factory for creating rpc-encoded serializers 
 */
public class AeRPCEncodedSerializerFactory implements SerializerFactory
{
   /** Supported mechanisms is singleton list for axis only deserialization */
   private static final List SUPPORTED_MECHANISMS = Collections.singletonList(Constants.AXIS_SAX);

   /** context contains the Schemas needed to serialize the Document correctly */
   private IAeTypesContext mTypesContext;
   
   /** The cached serializer in case we're called multiple times */
   private AeRPCEncodedSerializer mSerializer;
   
   /**
    * Constructor
    * @param aTypesContext
    */
   public AeRPCEncodedSerializerFactory(IAeTypesContext aTypesContext)
   {
      mTypesContext = aTypesContext;
   }
   
   /**
    * Creates the factory with an existing serializer all set.
    * @param aSerializer
    */
   public AeRPCEncodedSerializerFactory(AeRPCEncodedSerializer aSerializer)
   {
      mSerializer = aSerializer;
   }
   
   /**
    * @see javax.xml.rpc.encoding.SerializerFactory#getSerializerAs(java.lang.String)
    */
   public Serializer getSerializerAs(String mechanismType)
   {
      if (mSerializer == null)
         mSerializer = new AeRPCEncodedSerializer(mTypesContext);
      return mSerializer;
   }

   /**
    * @see javax.xml.rpc.encoding.SerializerFactory#getSupportedMechanismTypes()
    */
   public Iterator getSupportedMechanismTypes()
   {
      return SUPPORTED_MECHANISMS.iterator();
   }

}
