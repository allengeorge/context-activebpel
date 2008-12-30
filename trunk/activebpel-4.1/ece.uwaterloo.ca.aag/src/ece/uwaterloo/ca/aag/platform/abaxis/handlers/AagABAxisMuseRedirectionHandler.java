package ece.uwaterloo.ca.aag.platform.abaxis.handlers;

import org.apache.axis.handlers.BasicHandler;
import org.apache.axis.MessageContext;
import org.apache.axis.AxisFault;

/**
 * Created by Allen Ajit George
 * Date: Mar 7, 2008
 * Time: 12:12:11 PM
 */
public class AagABAxisMuseRedirectionHandler extends BasicHandler {
   public void invoke(MessageContext messageContext) throws AxisFault {
      if (messageContext.getTargetService().contains("MuseService")) {
         System.out.println("Trying to get service " + messageContext.getTargetService());

         messageContext.setTargetService("MuseService");
         System.out.println("Target service reset to MuseService");
      }
   }
}
