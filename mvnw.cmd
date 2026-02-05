@echo off
setlocal
set "MAVEN_PROJECTBASEDIR=%~dp0"
set "MAVEN_WRAPPER_URL=https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"
set "MAVEN_WRAPPER_JAR=%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar"

if not exist "%MAVEN_WRAPPER_JAR%" (
  echo Downloading Maven Wrapper...
  powershell -Command "& {[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -Uri '%MAVEN_WRAPPER_URL%' -OutFile '%MAVEN_WRAPPER_JAR%' -UseBasicParsing}"
)

set "MAVEN_OPTS=-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%"
set "WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain"
"%JAVA_HOME%\bin\java" %MAVEN_OPTS% -classpath "%MAVEN_WRAPPER_JAR%" "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" %WRAPPER_LAUNCHER% %*
endlocal & exit /b %ERRORLEVEL%
