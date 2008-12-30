// $Header: /Development/AEDevelopment/projects/org.activebpel.rt.axis/src/org/activebpel/rt/axis/AeCatalogServlet.java,v 1.6 2007/08/13 22:28:2
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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activebpel.rt.AeException;
import org.activebpel.rt.util.AeUtil;

/**
 * Servlet designed to provide the WSDL which is part of a BPR deployment.
 */
public class AeCatalogServlet extends HttpServlet
{
   /**
    * Handle POST request for WSDL catalog entry. 
    * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
    */
   public void doPost(HttpServletRequest aRequest, HttpServletResponse aResponse)
       throws ServletException, IOException
   {
      doGet(aRequest, aResponse);
   }
   
   /**
    * Handle GET request for WSDL catalog entry. 
    * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
    */
   public void doGet(HttpServletRequest aRequest, HttpServletResponse aResponse) throws ServletException, IOException
   {
      String pathInfo = AeUtil.getSafeString(aRequest.getPathInfo()).trim();
      String requestURL = aRequest.getRequestURL().toString();
      String entry = AeUtil.getSafeString(aRequest.getParameter("location")).trim(); //$NON-NLS-1$
      // first check to see if requested resource is specified as part of the query string using param name 'location'.
      if (entry.length() > 1)
      {
         // E.g entry: project:/a/b/c
         entry = entry.trim();
         // Build requestURL so that it is in the expected (legacy) format:
         // as http://host:port/active-bpel/catalog/project:/a/b/c
         
         // First get the current request url and strip out extra path info (if any)
         if (pathInfo.length() > 0 && requestURL.endsWith(pathInfo))
         {
            requestURL = requestURL.substring(0, requestURL.length() - aRequest.getPathInfo().length());
         }         
         // append entry info
         requestURL = requestURL + "/" + entry; //$NON-NLS-1$
      }
      else if (pathInfo.length() > 1)
      {
         // use entry based on extra path info.
         // Eg: http://host:8080/active-bpel/catalog/project:/a/b/c
         // PathInfo: /project:/a/b/c
         // entry: project:/a/b/c
         // requestURL = http://host:8080/active-bpel/catalog/project:/a/b/c
         entry = pathInfo.substring(1);
      }
      else
      {
         aResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, AeMessages.getString("AeCatalogServlet.1")); //$NON-NLS-1$
         return;         
      }      
      
      boolean isWsdl = entry.toLowerCase().endsWith(".wsdl"); //$NON-NLS-1$            
      String output = null;
      try
      {
         if (isWsdl)
         {
            // get wsdl file
            output = AeCatalogHelper.getCatalogWsdl(entry, requestURL);
         }
         else
         {
            // all others: schema/xsd, xsl.
            output = AeCatalogHelper.getCatalogSchema(entry, requestURL);
         }
      }
      catch(Exception e)
      {
         aResponse.setContentType("text/plain"); //$NON-NLS-1$
         aResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
         AeException.printError(aResponse.getWriter(), e );    
         return;
      }
            
      if (output != null)
      {
         // make sure that the charset is UTF-8.
         aResponse.setContentType("application/xml; charset=utf-8"); //$NON-NLS-1$
         aResponse.setStatus(HttpServletResponse.SC_OK);
         aResponse.getWriter().println(output);         
      }
      else
      {
         // todo (PJ) send redirect if protocol is http, https or file and host is not this server instance.
         String msg = AeMessages.format("AeCatalogServlet.RESOURCE_NOT_FOUND", entry); //$NON-NLS-1$
         aResponse.setContentType("text/plain"); //$NON-NLS-1$
         aResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
         aResponse.getWriter().println(msg);         
      }
   }
}
