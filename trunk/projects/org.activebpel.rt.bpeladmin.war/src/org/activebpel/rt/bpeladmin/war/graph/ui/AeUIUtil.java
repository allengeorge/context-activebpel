//$Header: /Development/AEDevelopment/projects/org.activebpel.rt.bpeladmin.war/src/org/activebpel/rt/bpeladmin/war/graph/ui/AeUIUtil.java,v 1.2 2005/04/22 18:29:3
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
package org.activebpel.rt.bpeladmin.war.graph.ui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.activebpel.rt.AeException;

/**
 * Utility methods used for the UI components.
 */
public class AeUIUtil
{
   /** AWT component to act as a media observer */
   private static Component sImageObserver = new Container();
   /** Media tracker used when loading images */
   private static MediaTracker sMediaTracker = new MediaTracker(sImageObserver);
   
   private AeUIUtil()
   {
   }

   /**
    * Loads and returns the image given its filename.
    * @param aFilename image filename
    * @return image
    * @throws IOException
    */
   public static Image loadImage(String aFilename) throws IOException {
      File file = new File(aFilename);
      return loadImage(file.toURL());
   }      

   /**
    * Loads and returns the image given class and the resource path. 
    * @param aClass class from which this resource is to be loaded.
    * @param aResourceName path or name of resource.
    * @return image
    * @throws IOException
    */
   public static Image loadImage(Class aClass, String aResourceName) throws IOException  {
      return loadImage( aClass.getResource(aResourceName) );
   }      
   
   /**
    * Loads the image given its URL. This method will block until the image has been loaded.
    * @param aURL url of the image
    * @return image
    * @throws IOException
    */
   public static Image loadImage(URL aURL) throws IOException  {
      try
      {
         Toolkit toolkit = sImageObserver.getToolkit();
         Image image = toolkit.getImage(aURL);         
         waitUntilReady(image);
         return image;
      }
      catch(Throwable t)
      {
         AeException.logError(t,t.getLocalizedMessage());
         throw new IOException(t.getLocalizedMessage());
      }      
   }
   
   /**
    * Returns the inputstream to the given resource.
    * @param aClass resource class 
    * @param aResourceName name of resource
    * @return inputstream to the resource
    * @throws IOException
    */
   public static InputStream getImageInputStream(Class aClass, String aResourceName) throws IOException
   {
         return aClass.getResourceAsStream(aResourceName);
   }
   
   /**
    * Returns a filtered image given the original image and the image filter.
    * @param aImage original source image
    * @param aImageFilter image filter to apply
    * @return filtered image
    * @throws AeException
    */
   public static Image createFilteredImage(Image aImage, ImageFilter aImageFilter) throws AeException
   {
      try
      {
         ImageProducer producer = new FilteredImageSource(aImage.getSource(), aImageFilter);
         Image image = sImageObserver.createImage(producer);
         waitUntilReady(image);
         return image;
      }
      catch(Throwable t)
      {
         throw new AeException(t);
      }
   }
   
   /** 
    * @return image observer component used by the media tracker.
    */
   public static Component getImageObserver()
   {
      return sImageObserver;
   }
   
   /**
    * Adds the image to the media tracker and blocks until the image is ready (produced).
    * @param aImage image to add to the media tracker.
    */
   public static void waitUntilReady(Image aImage)
   {
      if (aImage != null)
      {
         try 
         { 
            int id = aImage.hashCode();
            sMediaTracker.addImage(aImage, id);
            sMediaTracker.waitForID(id);
         } 
         catch (Exception e) 
         {
            // ignore errors image didn't load, handled by display
         }
      }
   }
}
