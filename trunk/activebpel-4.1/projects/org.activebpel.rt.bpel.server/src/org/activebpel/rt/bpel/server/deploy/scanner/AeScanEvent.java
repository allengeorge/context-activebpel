// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/scanner/AeScanEvent.java,v 1.2 2005/02/04 21:43:0
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
package org.activebpel.rt.bpel.server.deploy.scanner;

import java.net.URL;

/**
 * Event fired by the <code>AeDirectoryScanner</code> to indicate
 * that there has been a change in the watch list.
 */
public class AeScanEvent
{
   /** File has been removed constant.*/
   public static final int REMOVAL = 0;
   /** File has been added constant.*/
   public static final int ADDITION = 1;
   
   /** changed file url */
   private URL mURL;
   /** change type (either REMOVAL or ADDITION) */
   private int mType;
   /** Arbitrary user data included in the event. */
   private Object mUserData;

   /**
    * Constructor.
    * @param aURL The file url.
    * @param aType The change type. See constants.
    * @param aUserData Any data to include in the event.
    */
   public AeScanEvent( URL aURL, int aType, Object aUserData )
   {
      mURL = aURL;
      mType = aType;
      mUserData = aUserData;
   }
   
   /**
    * Accessor for the file url.
    */
   public URL getURL()
   {
      return mURL;
   }
   
   /**
    * Return true if this is a remove event.
    */
   public boolean isRemoveEvent()
   {
      return mType == REMOVAL;
   }
   
   /**
    * Return true if this is a add event.
    */
   public boolean isAddEvent()
   {
      return mType == ADDITION;
   }

   /**
    * Returns the user data that was attached to this event when it was created.
    */
   public Object getUserData()
   {
      return mUserData;
   }
}
