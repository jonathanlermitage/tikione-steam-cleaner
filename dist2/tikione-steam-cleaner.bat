@echo off
rem TikiOne Steam Cleaner, a pure Java cleaner for Steam games redistributable packages.
rem Copyright (C) 2008-2015  Jonathan Lermitage
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

cd %~dp0%

set "PATH=%PATH%;%cd%\jre\bin\;%~dp0%\jre\bin\"
set "PATH=%PATH%;C:\Program Files\Java\jre\bin\"
set "PATH=%PATH%;C:\Program Files\Java\jre7\bin\"
set "PATH=%PATH%;C:\Program Files\Java\jre8\bin\"
set "PATH=%PATH%;C:\Program Files\Java\jre9\bin\"
set "PATH=%PATH%;C:\Program Files\Java\jre10\bin\"
set "PATH=%PATH%;C:\Program Files (x86)\Java\jre\bin\"
set "PATH=%PATH%;C:\Program Files (x86)\Java\jre7\bin\"
set "PATH=%PATH%;C:\Program Files (x86)\Java\jre8\bin\"
set "PATH=%PATH%;C:\Program Files (x86)\Java\jre9\bin\"
set "PATH=%PATH%;C:\Program Files (x86)\Java\jre10\bin\"

start "" "javaw.exe" "-jar" "-Xms32m" "-Xmx512m" "tikione-steam-cleaner.jar"
