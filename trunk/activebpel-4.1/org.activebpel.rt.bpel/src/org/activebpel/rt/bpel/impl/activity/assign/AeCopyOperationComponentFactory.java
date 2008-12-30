//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/impl/activity/assign/AeCopyOperationComponentFactory.java,v 1.4 2006/07/27 21:01:5
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
package org.activebpel.rt.bpel.impl.activity.assign; 

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import org.activebpel.rt.AeException;
import org.activebpel.rt.bpel.AeBusinessProcessException;
import org.activebpel.rt.bpel.AeMessages;
import org.activebpel.rt.bpel.def.activity.support.AeVarDef;
import org.activebpel.rt.bpel.def.io.readers.def.AeSpecStrategyKey;

/**
 * Base class for creating copy operation impl objects like specific
 * versions of a <from> or <to> variant
 */
public abstract class AeCopyOperationComponentFactory
{
   /**  */
   private Map mMap = new HashMap();

   /**
    * Inits the map
    */
   protected AeCopyOperationComponentFactory()
   {
      initMap();
   }

   /**
    * The map of strategy names to Class is constructed here
    */
   protected abstract void initMap();

   /**
    * Getter for the map
    */
   protected Map getMap()
   {
      return mMap;
   }

   /**
    * Factory method does a new instance on the class, passing the def in as a param
    * @param aDef
    * @throws AeBusinessProcessException
    */
   protected Object create(AeVarDef aDef)
   {
      AeSpecStrategyKey strategy = aDef.getStrategyKey();
      String strategyName = strategy.getStrategyName();
      Class clazz = (Class) getMap().get(strategyName);
      try
      {
         if (strategy.hasArguments())
         {
            Object [] arguments = strategy.getStrategyArguments();
            Class [] classes = new Class[arguments.length];
            for (int i = 0; i < arguments.length; i++)
               classes[i] = arguments[i].getClass();

            Constructor cons = clazz.getConstructor(classes);
            return cons.newInstance(arguments);
         }
         else
         {
            Constructor cons = clazz.getConstructor(new Class[] { aDef.getClass() });
            return cons.newInstance(new Object[] { aDef });
         }
      }
      catch (Throwable t)
      {
         Object[] args = {strategyName, aDef.getLocationPath()};
         String message = AeMessages.format("AeCopyOperationComponentFactory.ErrorCreatingStrategy", args); //$NON-NLS-1$
         AeException.logError(t, message);
         throw new InternalError(message);
      }
   }

}
 
