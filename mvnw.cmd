@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    https://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@REM ----------------------------------------------------------------------------
@REM Maven Start Up Batch script
@REM
@REM Required ENV vars:
@REM ------------------
@REM JAVA_HOME - location of a JDK home dir
@REM
@REM Optional ENV vars
@REM -----------------
@REM M2_HOME - location of maven2's installed home dir
@REM MAVEN_BATCH_ECHO - set to 'on' to enable the echoing of the batch commands
@REM MAVEN_BATCH_PAUSE - set to 'on' to wait for a keystroke before ending
@REM MAVEN_OPTS - parameters passed to the Java VM when running Maven
@REM     e.g. to debug Maven itself, use
@REM set MAVEN_OPTS=-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8000
@REM MAVEN_SKIP_RC - flag to disable loading of mavenrc files
@REM ----------------------------------------------------------------------------

@REM Begin all REM lines with '@REM' to ensure they are removed by the batch processor
@REM when the file is executed (e.g. in IF blocks). This is for batch processing.
@REM
@REM See https://github.com/takari/maven-wrapper for more information.

@IF "%__MVNW_VERSION%"=="" SET "__MVNW_VERSION=3.2.0"
@IF "%__MVNW_VERBOSE%"=="" SET "__MVNW_VERBOSE=0"
@IF NOT "%__MVNW_VERBOSE%"=="0" SET "MAVEN_BATCH_ECHO=on"

@SET "MAVEN_PROJECTBASEDIR=%MAVEN_BASEDIR%"
@IF NOT "%MAVEN_PROJECTBASEDIR%"=="" GOTO endDetectBaseDir

@SET EXEC_DIR=%CD%
@SET "WDIR=%EXEC_DIR%"
@FINDSTR /B /C:":VS" "%WDIR%" >nul 2>&1
@IF NOT ERRORLEVEL 1 GOTO stripVS
@GOTO detectBaseDir

:stripVS
@SET "WDIR=%WDIR:VS=%"
@GOTO detectBaseDir

:detectBaseDir
@IF EXIST "%MAVEN_BASEDIR%\.mvn\jvm.config" GOTO readJvmConfig
@IF EXIST "%WDIR%\.mvn\jvm.config" GOTO readJvmConfig
@SET "MAVEN_PROJECTBASEDIR=%WDIR%"
@GOTO endDetectBaseDir

:readJvmConfig
@SET "MAVEN_PROJECTBASEDIR=%WDIR%"
@FOR /F "usebackq tokens=1* delims=" %%a IN ("%MAVEN_PROJECTBASEDIR%\.mvn\jvm.config") DO (
    @SET "JVM_CONFIG_MAVEN_PROPS=%%a"
    @IF NOT "%%b"=="" (
        @SET "JVM_CONFIG_MAVEN_PROPS=!JVM_CONFIG_MAVEN_PROPS! %%b"
    )
)
@SET JVM_CONFIG_MAVEN_PROPS=
@GOTO endDetectBaseDir

:endDetectBaseDir

SET MAVEN_JAVA_EXE="%JAVA_HOME%\bin\java.exe"
set WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
set WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

set DOWNLOAD_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"

FOR /F "usebackq tokens=1,2 delims==" %%A IN (`"%MAVEN_JAVA_EXE%" -Xms32m -Xmx32m -classpath "%WRAPPER_JAR%" %WRAPPER_LAUNCHER% --property-file "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.properties" %MAVEN_CONFIG% %MAVEN_OPTS% %MAVEN_DEBUG_OPTS% -maven.project_basedir="%MAVEN_PROJECTBASEDIR%"`) DO
    (
        IF "%%A"=="-Dmaven.home" SET "MAVEN_HOME=%%B"
        IF "%%A"=="-Dmaven.multiModuleProjectDirectory" SET "MAVEN_PROJECTBASEDIR=%%B"
    )

:downloadMaven
@IF EXIST "%WRAPPER_JAR%" (
    @IF NOT "%__MVNW_VERBOSE%"=="0" (
        @ECHO Found %WRAPPER_JAR%
    )
) ELSE (
    @IF NOT "%__MVNW_VERBOSE%"=="0" (
        @ECHO Couldn't find %WRAPPER_JAR%, downloading it ...
    )
    @IF NOT EXIST "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper" (
        @MKDIR "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper"
    )
    @powershell -Command "& {"^
        "$progressPreference = 'SilentlyContinue';"^
        "Invoke-WebRequest -Uri %DOWNLOAD_URL% -OutFile '%WRAPPER_JAR%';"^
    "}"
    @IF NOT EXIST "%WRAPPER_JAR%" (
        @ECHO Failed to download %WRAPPER_JAR%
        @EXIT /B 1
    )
)

@REM End of Maven wrapper script
