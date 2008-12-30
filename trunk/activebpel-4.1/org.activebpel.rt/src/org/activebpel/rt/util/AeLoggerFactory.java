//$Header: /Development/AEDevelopment/projects/org.activebpel.rt/src/org/activebpel/rt/util/AeLoggerFactory.java,v 1.2 2006/04/10 21:02:2
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
package org.activebpel.rt.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Factory class for creating a logger
 */
public class AeLoggerFactory
{
   /**
    * Creates a logger with an initial level of warning. Adds a default console
    * handler to the logger if the logger does not already have a handler.
    *
    * @param aName logger name
    * @return logger
    */
   public static Logger createLogger(String aName)
   {
      Logger logger = Logger.getLogger(aName);
      logger.setLevel(Level.WARNING);      

      synchronized (logger)
      {
         if (!hasHandler(logger))
         {
            logger.addHandler(new AeConsoleLogHandler());
         }
      }

      return logger;
   }

   /**
    * Returns <code>true</code> if and only if the given logger has at least one
    * handler.
    */
   protected static boolean hasHandler(Logger aLogger)
   {
      if (aLogger.getHandlers().length > 0)
      {
         return true;
      }

      // If the logger is sending its output to its parent, then check the
      // parent for handlers.
      if (aLogger.getUseParentHandlers())
      {
         Logger parent = aLogger.getParent();
         
         if ((parent != null) && (parent != aLogger))
         {
            return hasHandler(parent);
         }
      }

      // Couldn't find a handler.
      return false;
   }
}
