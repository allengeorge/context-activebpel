package ece.uwaterloo.ca.aag.platform.abaxis;

/**
 * Created by Allen Ajit George
 * Date: Mar 12, 2008
 * Time: 9:59:16 PM
 *
 * Holds Muse-related service paths and webapp names that the ActiveBPEL handlers use when creating WS-N consumers, performing
 * a subscribe etc.
 *
 * FIXME Ideally many of these parameters should be in the aeEngineConfig.xml file
 */
public interface IAagABAxisConstants {
   /**
    * This is the path of the MuseService that's hosted in tandem with ActiveBPEL (i.e. under the ActiveBPEL Axis1.4 stack)
    * The name of this path _must be_ the same name as the service@name in ae-server-config.wsdd. This file is copied from
    * ${active-bpel-src}/projects/org.activebpel.rt.axis.bpel/support/shared/classes/ae-server-config.wsdd in the source tree
    */
   public static final String MUSE_SERVICE_PATH = "MuseService";

   /**
    * Name of the singleton Muse WS-Resource web service that creates BPELConsumer instances
    * All processes should first invoke BPELConsumerFactory to get an EPR to a BPELConsumer before doing WS-N operations
    */
   public static final String BPEL_CONSUMER_FACTORY_APP = "BPELConsumerFactory";

   /**
    * Name of the Muse WS-Resource web service that acts as a WS-N consumer and is associated with an ActiveBPEL process
    */
   public static final String BPEL_CONSUMER_APP = "BPELConsumer";

   /**
    * Key for storing the base invocation URL in the Axis/servlet extended message context object.
    */
   public static final String AAG_ENGINE_INVOCATION_URL = "AAG_ENGINE_INVOCATION_URL";
}
