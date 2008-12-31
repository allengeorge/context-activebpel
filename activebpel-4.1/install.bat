@ECHO OFF
SETLOCAL
IF NOT "%1" == "" SET CATALINA_HOME="%1"
IF NOT "%CATALINA_HOME%" == "" GOTO IS_TC_VALID

ECHO Installation requires the CATALINA_HOME environment variable to be set to the location of your Tomcat installation. Please set the environment variable and run install.bat again.
GOTO END


:IS_TC_VALID
IF EXIST "%CATALINA_HOME%\server\lib\catalina.jar" GOTO CHECK_DIRS
GOTO INVALID_TC


:CHECK_DIRS
FOR %%A IN ( "%CATALINA_HOME%\webapps" "%CATALINA_HOME%\shared" "%CATALINA_HOME%\shared\lib" "%CATALINA_HOME%\shared\classes" ) DO IF NOT EXIST %%A md %%A

:REMOVE_OLD
FOR %%B IN ( BpelAdmin active-bpel BpelAdminHelp ) DO IF EXIST %CATALINA_HOME%\webapps\%%B.war del %CATALINA_HOME%\webapps\%%B.war 
FOR %%B IN ( BpelAdmin active-bpel BpelAdminHelp ) DO IF EXIST %CATALINA_HOME%\webapps\%%B rmdir /s /q %CATALINA_HOME%\webapps\%%B 
IF EXIST %CATALINA_HOME%\work rmdir /s /q %CATALINA_HOME%\work
IF EXIST %CATALINA_HOME%\bpr\work rmdir /s /q %CATALINA_HOME%\bpr\work

:INSTALL
ECHO.
ECHO.

rem Remove prior installation of castor jar if it exists
IF EXIST %CATALINA_HOME%\shared\lib\castor-0.9.6-xml.jar del %CATALINA_HOME%\shared\lib\castor-0.9.6-xml.jar
IF EXIST %CATALINA_HOME%\shared\lib\bcprov-jdk13-128.jar del %CATALINA_HOME%\shared\lib\bcprov-jdk13-128.jar
IF EXIST %CATALINA_HOME%\shared\lib\activeio-core-3.0-beta3.jar del %CATALINA_HOME%\shared\lib\activeio-core-3.0-beta3.jar
IF EXIST %CATALINA_HOME%\shared\lib\activemq-console-4.0.jar del %CATALINA_HOME%\shared\lib\activemq-console-4.0.jar

ECHO Creating BPR Directory
md "%CATALINA_HOME%"\bpr > NUL

ECHO Copying Libraries...
COPY /V dist\ae_rt.jar "%CATALINA_HOME%"\shared\lib\ > NUL
COPY /V dist\ae_rtaxis.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V dist\ae_rtaxisbpel.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V dist\ae_rtbpel.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V dist\ae_rtbpelsvr.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V dist\ae_rtexpr.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V dist\ae_rtexprbsf.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V dist\ae_tamino.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V dist\ae_axisweb.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V dist\ae_rtaxisadmin.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V dist\ae_wsio.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\axis.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\activation.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\castor-1.0-xml.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\commons-codec-1.3.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\commons-dbutils-1.0.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\commons-discovery.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\commons-httpclient-3.0-rc3.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\commons-logging.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\commons-fileupload-1.0.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\commonj-twm.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\log4j.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\jaxen-1.1-beta-8.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\jaxrpc.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\js-1.6R1.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\jython.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\mail.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\resolver.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\saaj.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\saxon8.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\saxon8-dom.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\TaminoAPI4J.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\TaminoJCA.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\wsdl4j.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\xercesImpl.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\xalan.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\xml-apis.jar "%CATALINA_HOME%"\shared\lib > NUL
COPY /V lib\xmlParserAPIs.jar "%CATALINA_HOME%"\shared\lib > NUL


ECHO Copying Configuration files...
COPY /V dist\conf\*.wsdd "%CATALINA_HOME%"\shared\classes    > NUL
COPY /V dist\conf\aeEngineConfig.xml "%CATALINA_HOME%"\bpr    > NUL

ECHO Deploying Web-Applications... 
COPY /V dist\*.war "%CATALINA_HOME%"\webapps  > NUL
COPY /V dist\activebpel\*.war "%CATALINA_HOME%"\webapps  > NUL

ECHO Deploying Remote Debugging BPR...
COPY /V dist\*.wsr "%CATALINA_HOME%"\bpr    > NUL


ECHO.
ECHO.


ECHO Congratulations, you have just installed ActiveBPEL.
ECHO Access http://localhost:8080/BpelAdmin to test and administer your installation.
GOTO END

:INVALID_TC
ECHO This does not appear to be a valid Tomcat installation.  Please verify your installation of tomcat and your CATALINA_HOME environment variable.

:END