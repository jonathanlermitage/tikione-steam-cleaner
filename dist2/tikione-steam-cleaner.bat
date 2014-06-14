@echo off
rem TikiOne Steam Cleaner, a pure Java cleaner for Steam games redistributable packages.
rem Copyright (C) 2008-2014  Jonathan Lermitage
rem 
rem This tool is free software; you can redistribute it and/or modify it under the terms of the
rem GNU Lesser General Public License as published by the Free Software Foundation; either version
rem 2.1 of the License, or (at your option) any later version.
rem 
rem This tool is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
rem without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See
rem the GNU Lesser General Public License for more details.
rem 
rem You should have received a copy of the GNU Lesser General Public License along with this
rem library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
rem Boston, MA 02111-1307 USA

rem ---------------------------------------------------------------------------------------------------------------------------
rem TikiOne Steam Cleaner start-up script.
rem Used to launch TikiOne Steam Cleaner with the bundled JVM (if exists) or the operating system's JVM.
rem ---------------------------------------------------------------------------------------------------------------------------

rem **************************************************
rem Go to the application's dir (fix for the case you start the BAT with elevated privileges)
cd %~dp0%

rem **************************************************
rem Check for patch to apply.
if exist "%cd%\tmp\tikione-steam-cleaner\" goto applyPatch
goto noPatch

:applyPatch
rmdir /S /Q "%cd%\conf\"
rmdir /S /Q "%cd%\lib\"
rmdir /S /Q "%cd%\license\"
move /Y "%cd%\tmp\tikione-steam-cleaner\*.*" "%cd%"
move /Y "%cd%\tmp\tikione-steam-cleaner\conf" "%cd%"
move /Y "%cd%\tmp\tikione-steam-cleaner\lib" "%cd%"
move /Y "%cd%\tmp\tikione-steam-cleaner\license" "%cd%"
rmdir /S /Q "%cd%\tmp\"
goto startProgram

:noPatch
goto startProgram

rem **************************************************
rem Start program.
:startProgram

rem Set global JVM options, to increase the maximum amount of memory (RAM) available to the JVM, to avoid some abnormal exits. It increases
rem the Java Thread Stack size too ("Xss"), to avoid some very rare crash cases (e.g. if you scan a very large folder).
set "JAVA_OPTS1=-Xms32m"
set "JAVA_OPTS2=-Xmx768m"
set "JAVA_OPTS3=-Xss3m"
set "PRG="tikione-steam-cleaner.jar"

if exist "%cd%\jre\bin\javaw.exe" goto start1
if exist "C:\Program Files\Java\jre7\bin\javaw.exe" goto start2
if exist "C:\Program Files (x86)\Java\jre7\bin\javaw.exe" goto start3
if exist "C:\Program Files\Java\jre8\bin\javaw.exe" goto start6
if exist "C:\Program Files (x86)\Java\jre8\bin\javaw.exe" goto start7
if exist "C:\Program Files\Java\jre\bin\javaw.exe" goto start8
if exist "C:\Program Files (x86)\Java\jre\bin\javaw.exe" goto start9

echo launch Java from system PATH
echo start with Java from system path
set "JAVAWEXE_PATH=javaw.exe"
goto startFinal

:start1 
echo start with bundled Java7
set "JAVAWEXE_PATH=%cd%\jre\bin\javaw.exe"
goto startFinal

:start2 
echo start with program files Java 7
set "JAVAWEXE_PATH=C:\Program Files\Java\jre7\bin\javaw.exe"
goto startFinal

:start3 
echo start with program files Java 7
set "JAVAWEXE_PATH=C:\Program Files (x86)\Java\jre7\bin\javaw.exe"
goto startFinal

:start6 
echo start with program files Java 8
set "JAVAWEXE_PATH=C:\Program Files\Java\jre8\bin\javaw.exe"
goto startFinal

:start7 
echo start with program files Java 8
set "JAVAWEXE_PATH=C:\Program Files (x86)\Java\jre8\bin\javaw.exe"
goto startFinal

:start8 
echo start with program files Java
set "JAVAWEXE_PATH=C:\Program Files\Java\jre\bin\javaw.exe"
goto startFinal

:start9 
echo start with program files Java
set "JAVAWEXE_PATH=C:\Program Files (x86)\Java\jre\bin\javaw.exe"
goto startFinal

:startFinal
start "" "%JAVAWEXE_PATH%" "-jar" "%JAVA_OPTS1%" "%JAVA_OPTS2%" "%JAVA_OPTS3%" "%PRG%"
goto end

:end
