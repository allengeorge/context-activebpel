// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpel.server/src/org/activebpel/rt/bpel/server/deploy/AeNewDeploymentInfo.java,v 1.2 2005/06/17 21:51:1
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
package org.activebpel.rt.bpel.server.deploy;

import java.net.URL;

import org.w3c.dom.Document;

/**
 * Wrapper for deployment information, url, metadata, etc.
 */
public class AeNewDeploymentInfo
{
   /** holds onto meta data document, which describes deployment. */
   private Document mMetaData;
   
   /** holds onto the deployment context url. */
   private URL mURL;
   
   /** staging deployment url */
   private URL mTempURL;
   
   /**
    * Accessor for deployment URL.
    */
   public URL getURL()
   {
      return mURL;
   }

   /**
    * Accessor for wsdd document.
    */
   public Document getMetaData()
   {
      return mMetaData;
   }

   /**
    * Setter for wsdd document.
    */
   public void setMetaData(Document aDocument)
   {
      mMetaData = aDocument;
   }

   /**
    * Setter for the deployment url.
    */
   public void setURL(URL aUrl)
   {
      mURL = aUrl;
   }
   
   /**
    * Accessor for the temp deployment url.
    */
   public URL getTempURL()
   {
      return mTempURL;
   }
   
   /**
    * Setter for the temp/staging url.
    * @param aURL
    */
   public void setTempURL( URL aURL )
   {
      mTempURL = aURL;
   }
}
