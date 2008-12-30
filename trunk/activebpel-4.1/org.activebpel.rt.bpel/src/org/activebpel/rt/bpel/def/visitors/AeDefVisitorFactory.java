//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/visitors/AeDefVisitorFactory.java,v 1.4 2006/09/27 18:23:4
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
package org.activebpel.rt.bpel.def.visitors; 

import org.activebpel.rt.bpel.def.IAeBPELConstants;

/**
 * Factory for creating def visitors 
 */
public abstract class AeDefVisitorFactory implements IAeDefVisitorFactory
{
   /** impl for BPEL4WS 1.1 */
   private static final IAeDefVisitorFactory BPWS_FACTORY = new AeBPWSDefVisitorFactory();
   /** impl for WS-BPEL 2.0 */
   private static final IAeDefVisitorFactory WSBPEL20_FACTORY = new AeWSBPELDefVisitorFactory();
   /** impl for WS-BPEL 2.0 Abstract Processes */
   private static final IAeDefVisitorFactory WSBPEL20_ABSTRACT_PROCESS_FACTORY = new AeWSBPELAbstractProcessDefVisitorFactory();
   
   /**
    * Factory method returns a factory specific to the namespace
    * @param aNamespace
    */
   public static IAeDefVisitorFactory getInstance(String aNamespace)
   {
      if (IAeBPELConstants.BPWS_NAMESPACE_URI.equals(aNamespace))
      {
         return BPWS_FACTORY;
      }
      else if (IAeBPELConstants.WSBPEL_2_0_ABSTRACT_NAMESPACE_URI.equals(aNamespace))
      {
         return WSBPEL20_ABSTRACT_PROCESS_FACTORY;
      }
      else
      {
         return WSBPEL20_FACTORY;
      }
   }
   
   /**
    * protected ctor to force static getInstance() usage 
    */
   protected AeDefVisitorFactory()
   {
      super();
   }
   
   /**
    * @see org.activebpel.rt.bpel.def.visitors.IAeDefVisitorFactory#createDefPathVisitor()
    */
   public IAeDefPathVisitor createDefPathVisitor()
   {
      return new AeDefPathVisitor(createDefPathSegmentVisitor());
   }
   
}
 
