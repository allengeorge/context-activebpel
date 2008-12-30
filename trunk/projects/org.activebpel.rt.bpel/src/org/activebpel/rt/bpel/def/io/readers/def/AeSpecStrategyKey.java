// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel/src/org/activebpel/rt/bpel/def/io/readers/def/AeSpecStrategyKey.java,v 1.1 2006/07/27 21:01:5
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
package org.activebpel.rt.bpel.def.io.readers.def;

/**
 * A simple key that is used to indicate the strategy to use for the To portion of a 
 * copy operation.
 */
public class AeSpecStrategyKey
{
   /** The name of the strategy. */
   private String mStrategyName;
   /** Optional arguments that will get passed to the strategy when it is created. */
   private Object [] mStrategyArguments;

   /**
    * Constructs a strategy key with the given name.
    * 
    * @param aStrategyName
    */
   public AeSpecStrategyKey(String aStrategyName)
   {
      setStrategyName(aStrategyName);
   }
   
   /**
    * Constructs a strategy key with the given name and arguments.
    * 
    * @param aStrategyName
    * @param aStrategyArguments
    */
   public AeSpecStrategyKey(String aStrategyName, Object [] aStrategyArguments)
   {
      setStrategyName(aStrategyName);
      setStrategyArguments(aStrategyArguments);
   }
   
   /**
    * Returns true if the key contains some arguments.
    */
   public boolean hasArguments()
   {
      return getStrategyArguments() != null;
   }

   /**
    * @return Returns the strategyName.
    */
   public String getStrategyName()
   {
      return mStrategyName;
   }

   /**
    * @param aStrategyName The strategyName to set.
    */
   protected void setStrategyName(String aStrategyName)
   {
      mStrategyName = aStrategyName;
   }

   /**
    * @return Returns the strategyArguments.
    */
   public Object[] getStrategyArguments()
   {
      return mStrategyArguments;
   }

   /**
    * @param aStrategyArguments The strategyArguments to set.
    */
   protected void setStrategyArguments(Object[] aStrategyArguments)
   {
      mStrategyArguments = aStrategyArguments;
   }
}
