//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/web/upload/IAeFileUploader.java,v 1.1 2007/08/13 19:37:0
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
package org.activebpel.rt.bpeladmin.war.web.upload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface for client view of file uploads from the web perspective.
 */
public interface IAeFileUploader
{

   // constants for query string params
   public static final String THRESHOLD_BYTES = "threshold-size"; //$NON-NLS-1$

   public static final String MAX_SIZE = "max-size"; //$NON-NLS-1$

   /**
    * Return true if the request contains multipart content.
    * @param aRequest
    */
   public boolean isMultiPartContent( HttpServletRequest aRequest );
   
   /**
    * Handle the details of parsing the upload data from the request. 
    * @param aRequest
    * @param aResponse
    */
   public void uploadFile( HttpServletRequest aRequest, HttpServletResponse aResponse );
   
}
