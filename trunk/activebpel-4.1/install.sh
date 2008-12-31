#!/bin/sh

installdir=`dirname $0`
cd $installdir


if [ -z "$CATALINA_HOME" ]
then
    echo
    echo "Installation requires the CATALINA_HOME environment variable to be set to the location of your Tomcat installation. Please set the environment variable and run install.sh again."
    exit
fi

if [ ! -f "$CATALINA_HOME/server/lib/catalina.jar" ]
then
    echo
    echo "This does not appear to be a valid Tomcat installation.  Please verify your installation of tomcat and your CATALINA_HOME environment variable."
    exit
fi

for dir in "$CATALINA_HOME/shared" "$CATALINA_HOME/shared/lib" "$CATALINA_HOME/shared/classes" "$CATALINA_HOME/webapps"
do
      if [ ! -d "$dir" ]
      then
     echo Creating $dir
     `mkdir "$dir"`
      fi
done

for dir in "$CATALINA_HOME/webapps/active-bpel" "$CATALINA_HOME/webapps/BpelAdmin" "$CATALINA_HOME/webapps/bpel_example_client_page" "$CATALINA_HOME/webapps/BpelAdminHelp"
do
      if [ -d "$dir" ]
      then
      `rm -rf "$dir"`
      `rm -rf "$dir".war`
      fi

      shortname=`basename "$dir"`

      if [ -d "$CATALINA_HOME/work" ]
      then
     `find "$CATALINA_HOME/work" -name $shortname -print | xargs rm -rf` > nul
      fi
done



if [ -d "$CATALINA_HOME/bpr/work" ]
then
     `rm -rf "$CATALINA_HOME/bpr/work"`
fi

if [ ! -d "$CATALINA_HOME/bpr" ]
then
       echo Creating BPR Directory
      `mkdir "$CATALINA_HOME/bpr"`
fi

if [ -f "$CATALINA_HOME/shared/lib/castor-0.9.6-xml.jar" ]
then
       `rm -rf "$CATALINA_HOME/shared/lib/castor-0.9.6-xml.jar"`
fi

if [ -f "$CATALINA_HOME/shared/lib/bcprov-jdk13-128.jar" ]
then
       `rm -rf "$CATALINA_HOME/shared/lib/bcprov-jdk13-128.jar"`
fi

if [ -f "$CATALINA_HOME/shared/lib/activeio-core-3.0-beta3.jar" ]
then
       `rm -rf "$CATALINA_HOME/shared/lib/activeio-core-3.0-beta3.jar"`
fi

if [ -f "$CATALINA_HOME/shared/lib/activemq-console-4.0.jar" ]
then
       `rm -rf "$CATALINA_HOME/shared/lib/activemq-console-4.0.jar"`
fi


echo "Copying Libraries..."
                cp -rf dist/ae_rt.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf dist/ae_rtaxis.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf dist/ae_rtaxisbpel.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf dist/ae_rtbpel.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf dist/ae_rtbpelsvr.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf dist/ae_rtexprbsf.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf dist/ae_rtexpr.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf dist/ae_axisweb.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf dist/ae_rtaxisadmin.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf dist/ae_wsio.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf dist/ae_tamino.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/axis.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/activation.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/castor-1.0-xml.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/commons-codec-1.3.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/commons-dbutils-1.0.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/commons-discovery.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/commons-httpclient-3.0-rc3.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/commons-logging.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/commonj-twm.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/commons-fileupload-1.0.jar "$CATALINA_HOME"/shared/lib > /dev/null                
                cp -rf lib/log4j.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/jaxen-1.1-beta-8.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/jaxrpc.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/js-1.6R1.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/jython.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/mail.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/resolver.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/saaj.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/saxon8.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/saxon8-dom.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/TaminoAPI4J.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/TaminoJCA.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/wsdl4j.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/xalan.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/xercesImpl.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/xml-apis.jar "$CATALINA_HOME"/shared/lib > /dev/null
                cp -rf lib/xmlParserAPIs.jar "$CATALINA_HOME"/shared/lib > /dev/null

echo "Copying Configuration files..."
cp -rf dist/conf/*.wsdd "$CATALINA_HOME"/shared/classes  > /dev/null
cp -rf dist/conf/aeEngineConfig.xml "$CATALINA_HOME"/bpr  > /dev/null

echo "Copying Web-Applications..."
cp -rf dist/*.war "$CATALINA_HOME"/webapps  > /dev/null
cp -rf dist/activebpel/*.war "$CATALINA_HOME"/webapps  > /dev/null

echo "Copying Remote Debug WSR..."
cp -rf dist/*.wsr "$CATALINA_HOME"/bpr  > /dev/null

echo
echo

echo "Congratulations, you have just installed ActiveBPEL."
echo "Access http://localhost:8080/BpelAdmin to test and administer your installation."

