@echo off
cd %~dp0%
set "7z=%~dp0%\ext\7z.exe"
set "BUILD_BASEDIR=%TMP%\tikione_tmp_build_dir_1\"
set "BUILD_APPDIR=%BUILD_BASEDIR%\tikione-steam-cleaner\"
set "SRC_DIST=%~dp0%\..\dist\"
set "SRC_DIST2=%~dp0%\..\dist2\"


:: cleanup build dirs
echo.
echo will delete %BUILD_BASEDIR%
pause
rmdir /S /Q %BUILD_BASEDIR%
mkdir %BUILD_BASEDIR%
mkdir %BUILD_APPDIR%


:: prepare and zip app
echo.
echo will build app
pause


:: prepare ans zip app (NoJRE)
echo.
echo will build app (NoJRE)
pause


:: final cleanup
echo.
echo will delete %BUILD_BASEDIR%
pause
rmdir /S /Q %BUILD_BASEDIR%
