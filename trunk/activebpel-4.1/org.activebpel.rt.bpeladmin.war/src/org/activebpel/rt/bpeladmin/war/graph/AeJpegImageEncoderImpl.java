//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/graph/AeJpegImageEncoderImpl.java,v 1.3 2005/04/20 20:19:4
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

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import org.activebpel.rt.AeException;

/**
 * Implements IAeImageEncoder based on Sun's JPEG Image Encoder class.
 */
public class AeJpegImageEncoderImpl implements IAeImageEncoder
{

   /**
    * Default constructer.
    *
    */
   public AeJpegImageEncoderImpl()
   {
   }

   /**
    * @return the MIME content-type for JPEG images.
    * @see org.activebpel.rt.bpeladmin.war.graph.IAeImageEncoder#getContentType()
    */
   public String getContentType()
   {
      return "image/jpeg";//$NON-NLS-1$
   }

   /**
    * Encodes the image as JPEG stream.
    * @param aImage buffered image
    * @param aOutputStream codec output stream 
    * @see org.activebpel.rt.bpeladmin.war.graph.IAeImageEncoder#encode(java.awt.image.BufferedImage, java.io.OutputStream)
    */
   public void encode(BufferedImage aImage, OutputStream aOutputStream) throws IOException, AeException
   {
      try
      {
         JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(aOutputStream);
         JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(aImage);
         param.setQuality(0.9f, true);
         encoder.setJPEGEncodeParam(param);
         encoder.encode(aImage);
      }
      catch(IOException io)
      {
         throw io;
      }
      catch(Throwable t)
      {
         throw new AeException(t);
      }
      
   }

}
