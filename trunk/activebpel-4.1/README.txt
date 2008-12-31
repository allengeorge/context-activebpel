                                   README

                             ActiveBPEL(TM) engine
                                Version 4.1
Contents

   * Introduction
   * Dependencies
   * Release Notes
   * Bug Reports and Feedback
   * Documentation
  
Introduction

     Thank you for downloading the latest release of the ActiveBPEL(TM)
     engine. This software and accompanying samples should help you begin
     becoming familiar with BPEL processing. The ActiveBPEL engine software
     supports the BPEL4WS 1.1 specification.

Dependencies

     You need Ant in order to run the targets in the build.xml file. You
     *could* do everything manually, but the rest of this document assumes you
     have Ant. The build.xml file was created with and requires Ant Version
     1.6.1 or higher.
     
     The environment variable CATALINA_HOME must be defined prior to running
     the installation batch file (install.bat | install.sh).

Release Notes

     If you have been using .bpr files to deploy Java Web services to the
     ActiveBPEL install \bpr directory you MUST rename those files to .wsr
     extensions. The ActiveBPEL scanner will continue to deploy these new file
     extensions as Web services.
     
     Each release of the ActiveBPEL engine has the potential to add to the
     persistence layer and may require that you run the database setup even if
     you downloaded and installed a previous version of ActievBPEL. If you
     want, you can install the new version and attempt to start the engine. If
     the database configuration has changed the engine will fail to start and
     an error message will be displayed on the Home page of the console
     stating that the db version does not match.
     
     The persistence_setup.txt file located in the docs directory contains the
     instructions for setting up the ActiveBPEL engine to run with persistence
     turned on for all supported databases.
          
     More detailed release notes are available on the Web site at
     http://www.activebpel.org/download/release_notes.php.
     
Bug Reports and Feedback

      To get help from the ActiveBPEL community and developers or to submit
      feedback go to the ActiveBPEL support forum at
      http://forums.activebpel.org.
      
      To review or report new bugs go to the ActiveBPEL Bug Tracking system at
      http://www.activebpel.org/code/tracker.

Documentation

     Documentation can found on-line at activebpel.org as well as in the docs
     directory you unzipped. Help for the administration consoles can be found
     by using the Help link.

     For additional information, refer to the http://www.activebpel.org site.

Copyright (c) 2004-2007, ActiveBPEL LLC.  All rights reserved.