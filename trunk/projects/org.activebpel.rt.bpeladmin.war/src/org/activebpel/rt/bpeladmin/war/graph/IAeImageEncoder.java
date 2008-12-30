//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/graph/IAeImageEncoder.java,v 1.1 2005/04/18 18:31:5
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
package org.activebpel.rt.bpeladmin.war.graph;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import org.activebpel.rt.AeException;

/**
 * Interface the defines contracts for classes that are responsible for
 * encoding a buffered image.
 */
public interface IAeImageEncoder
{
   /**
    * Returns the MIME content-type
    * @return mime content type.
    */
   public String getContentType();
   
   /**
    * Encodes the image into the output stream.
    * @param aImage buffered image
    * @param aOutputStream codec output stream
    * @throws IOException
    * @throws AeException
    */
   public void encode(BufferedImage aImage, OutputStream aOutputStream) throws IOException, AeException;
}
