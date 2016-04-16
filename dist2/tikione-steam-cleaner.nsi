Name "TikiOne Steam Cleaner"
OutFile "TikiOne Steam Cleaner Setup.exe"
InstallDir "$PROGRAMFILES\TikiOne Steam Cleaner"
InstallDirRegKey HKLM "Software\TikiOneSteamCleaner" "Install_Dir"
RequestExecutionLevel admin

Page Directory
Page InstFiles
UninstPage uninstConfirm
UninstPage instfiles

Section "install"
    setOutPath $INSTDIR
    delete "$INSTDIR\CHANGELOG.HTML"
    delete "$INSTDIR\news.txt"
    delete "$INSTDIR\tikione-steam-cleaner.bat"
    delete "$INSTDIR\tikione-steam-cleaner.ico"
    delete "$INSTDIR\tikione-steam-cleaner.jar"
    delete "$INSTDIR\tikione-steam-cleaner.png"
    rmDir /r "$INSTDIR\conf"
    rmDir /r "$INSTDIR\jre"
    rmDir /r "$INSTDIR\lib"
    rmDir /r "$INSTDIR\license"
    rmDir /r "$INSTDIR\log"
    file news.txt
    file tikione-steam-cleaner.bat
    file tikione-steam-cleaner.ico
    file tikione-steam-cleaner.jar
    file tikione-steam-cleaner.png
    file /r conf
    file /r jre
    file /r lib
    file /r license
    createDirectory "$SMPROGRAMS\TikiOne Steam Cleaner"
    createShortCut "$SMPROGRAMS\TikiOne Steam Cleaner\TikiOne Steam Cleaner.lnk" "$INSTDIR\tikione-steam-cleaner.bat" "" "$INSTDIR\tikione-steam-cleaner.ico"
    WriteRegStr HKLM "Software\TikiOneSteamCleaner" "Install_Dir" "$\"$INSTDIR\uninstall.exe$\""
    WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\TikiOneSteamCleaner" "DisplayName" "TikiOne Steam Cleaner"
    WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\TikiOneSteamCleaner" "UninstallString" "$\"$INSTDIR\uninstall.exe$\""
    WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\TikiOneSteamCleaner" "Publisher" "Jonathan Lermitage"
    WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\TikiOneSteamCleaner" "NoModify" 1
    WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\TikiOneSteamCleaner" "NoRepair" 1
    WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\TikiOneSteamCleaner" "QuietUninstallString" "$\"$INSTDIR\uninstall.exe$\" /S"
    writeUninstaller "$INSTDIR\uninstall.exe"
    createShortCut "$SMPROGRAMS\TikiOne Steam Cleaner\Uninstall.lnk" "$INSTDIR\uninstall.exe"
SectionEnd

Section "uninstall"
    DeleteRegKey HKLM "SOFTWARE\TikiOneSteamCleaner"
    DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\TikiOneSteamCleaner"
    delete "$INSTDIR\news.txt"
    delete "$INSTDIR\tikione-steam-cleaner.bat"
    delete "$INSTDIR\tikione-steam-cleaner.ico"
    delete "$INSTDIR\tikione-steam-cleaner.jar"
    delete "$INSTDIR\tikione-steam-cleaner.png"
    rmDir /r "$INSTDIR\conf"
    rmDir /r "$INSTDIR\jre"
    rmDir /r "$INSTDIR\lib"
    rmDir /r "$INSTDIR\license"
    rmDir /r "$INSTDIR\log"
    delete "$SMPROGRAMS\TikiOne Steam Cleaner\TikiOne Steam Cleaner.lnk"
    delete "$SMPROGRAMS\TikiOne Steam Cleaner\Uninstall.lnk"
    rmDir "$SMPROGRAMS\TikiOne Steam Cleaner"
    delete "$INSTDIR\uninstall.exe"
    rmDir $INSTDIR
SectionEnd
