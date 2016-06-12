@echo off
rem ---------------------------------------------------------------------------------------------------------------------------
rem TikiOne Steam Cleaner start-up script.
rem Used to launch TikiOne Steam Cleaner with the bundled JVM (if exists) or the operating system's JVM.
rem ---------------------------------------------------------------------------------------------------------------------------

cd %~dp0%

set "OPATH=%PATH%"
set "PATH=%cd%\jre\bin\;%~dp0%\jre\bin\"
set "PATH=%PATH%;C:\Program Files (x86)\Java\jre8\bin\"
set "PATH=%PATH%;C:\Program Files (x86)\Java\jre9\bin\"
set "PATH=%PATH%;C:\Program Files (x86)\Java\jre10\bin\"
set "PATH=%PATH%;C:\Program Files (x86)\Java\jre\bin\"
set "PATH=%PATH%;C:\Program Files\Java\jre8\bin\"
set "PATH=%PATH%;C:\Program Files\Java\jre9\bin\"
set "PATH=%PATH%;C:\Program Files\Java\jre10\bin\"
set "PATH=%PATH%;C:\Program Files\Java\jre\bin\"
set "PATH=%PATH%;%OPATH%"

start "" "javaw.exe" "-jar" "-splash:tikione-steam-cleaner.png" "-Xms32m" "-Xmx512m" "tikione-steam-cleaner.jar" "enablePortablemode"
