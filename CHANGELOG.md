## TikiOne Steam Cleaner Change Log

### 3.0.5 (2017/07/10) JAVA8
* updated Traditional Chinese (zh-hk) translation. Thx "tskonetwo".
* improved weight of application (a ~1MB library was needed at compile time only).
* updated the bundled JVM to Java 8u131.

### 3.0.4 (2017/02/06) JAVA8
* updated Italian (it) translation. Thx "xDarkWolf".
* improved handling of "Penumbra" games. Thx "DickTektiv".
* updated the bundled JVM to Java 8u121.

### 3.0.3 (2016/12/30) JAVA8
* added Traditional Chinese (zh-hk) translation. Thx "tsk12".
* internal: code rework and cleanup.

### 3.0.2 (2016/10/09) JAVA8
* improved redist detection: patterns are now case-insensitive.
* fixed some redist patterns.
* reworked download link to updates.
* updated Portuguese (pt) translation. Thx "poutros".
* added Italian (it) translation. Thx Davide Crucitti.
* moved to MIT license.

### 3.0.1 (2016/08/12) JAVA8
* added Steam download folder protection. You can now safely run Steam Cleaner while downloading games.
* fixed GOG folders protection. You can now safely run GOG while downloading games.
* updated the bundled JVM to Java 8u102.

### 3.0.0 (2016/06/26) JAVA8
* you can now use additional remote lists of redist patterns. See "Options/General" dialog and [readme.txt](https://github.com/jonathanlermitage/tikione-steam-cleaner/blob/master/dist2/readme.txt) file.
* some cleanup in UI.
* increased the default maximum depth of search (5->6).
* fixed program startup on some Windows installations.
* internal: some code cleanup
* internal: migration from Ant to Maven build system (I'm also working on a migration from NetBeans to IntelliJ)
* updated the bundled JVM to Java 8u92.

### 2.9.2 (2016/02/13) JAVA8
* added Simplified Chinese (zh-cn) translation. Thx "wbsdty331".
* some cleanup in dialogs.
* updated the bundled JVM to Java 8u73.
* new license.

### 2.9.1 (2015/09/19) JAVA8
* removed links to Kickstarter and Patreon campaigns.

### 2.9.0 (2015/08/03) JAVA8
* added links to Patreon and Kickstarter campaigns.

### 2.4.6 (2015/07/19) JAVA8
* there are now four editions of TikiOne Steam Cleaner: regular installer with and without bundled JVM, portable edition with and without bundled JVM.
* updater now offer link to download TikiOne Steam Cleaner from GitHub.com instead of Sourceforge.net.
* updater now detects which edition to download.
* updated German (de) translation. Thx Hauwertlhaufn.
* internal: removed some debug log messages.
* updated the bundled JVM to Java 8u51.

### 2.4.5 (2015/07/12) JAVA8
* updated German (de) translation. Thx Hauwertlhaufn.
* updated the bundled JVM to Java 8u45.
* there are now two editions of TikiOne Steam Cleaner: regular, with the installer, and a portable edition that don't left config files in %userprofile% folder.

### 2.4.4 (2015/06/21) JAVA7
* updated the list of redistributable packages to support GOG (GalaxyClient) and Origin platforms.
* updated the experimental list of redistributable packages to support Help, Support and EULA files (non-redist but still removable).
* greatly updated the exclude list to prevent many programs corruption.
* updated Spanish (es) translation. Thx "ZoSH".

### 2.4.3 (2015/05/23) JAVA7
* updated the list of redistributable packages (recently, many games seem to store their redist in a single "_CommonRedist" subdir).
* fixed UE3 redist detection.

### 2.4.2 (2015/04/18) JAVA7
* UI improvement: you can sort results.
* UI improvement: you can see estimated total size of selected elements to delete.
* some minor bugfixes.
* updated the bundled JVM to Java 7u80.

### 2.4.1 (2015/03/28) JAVA7
* fixed support for custom folders.
* fixed a bug with Steam path (you couldn't launch scan even if the Steam path was filled).
* fixed threading issues that lead to incomplete scans.
* changed UI fonts to support Chinese locales.
* the list of redist patterns is now in a separate configuration file. Starting from this release, you won't loose your preferences each program's update.

### 2.4.0 (2014/11/15) JAVA7
* fixed configuration corruption after application upgrade (some users reported problems with after a 2.3.2 upgrade, that could be fixed by deleting the "%USERPROFILE%/.tikione/" directory).
* removed GooglePlus shortcut.

### 2.3.2 (2014/09/13) JAVA 7
* fixed the Steam folder detection to support the latest Steam application version. Thx to the CanardPC forum members.
* updated the bundled JVM to Java 7u67.

### 2.3.1 (2014/06/27) JAVA 7
* updated the list of redistributable packages. Thx Brian Huqueriza.
* updated the launcher code.
* updated the bundled JVM to Java 8u5.

### 2.3.0 (2013/10/27) JAVA 7
* greatly updated the list of redistributable packages. Thx Maxim Polulyakh.

### 2.2.0 (2013/06/20) JAVA 7
* internal: TikiOne Steam Cleaner now downloads updater file and versioning info from Sourceforge.net instead of Tikione.fr.

### 2.1.5 (2013/04/15) JAVA 7
* the TikiOne Steam Cleaner Facebook account is now disused (Facebook wants me to give them my phone number, WTF?). I'll now use my personnal Facebook account. Facebook link updated.
* internal: moved Log4j configuration file to user dir.
* internal: minor code refactorings.
* internal: improved Javadocs.

### 2.1.4 (2013/04/10) JAVA 7
* fixed a minor regression in the main panel's UI.

### 2.1.3 (2013/04/10) JAVA 7
* updated the Windows installer: you can now use it to upgrade any existing installation (you don't have to uninstall it first).
* updated Hungarian (hu) translation. Thx Zsolt Brechler.
* added shortcuts to the Twitter and Reddit webpages of TikiOne Steam Cleaner.

### 2.1.2 (2013/04/09) JAVA 7
* updated Russian (ru) and Ukrainian (ua) translations. Thx Dmitry Bolotov (Дмитрий Болотов).
* internal: removed CPU detection and forced the number or threads to 4 (no impact on mono-core CPU, and good performance on multi-core).
* internal: removed all debug log messages.
* internal: reduced the size of log file.

### 2.1.1 (2013/03/27) JAVA 7
* added a shortcut to the GitHub repository of TikiOne Steam Cleaner.
* minor corrections in French (fr) and Russian (ru) translations.
* internal: some code optimizations.
* internal: removed some debug log messages.

### 2.1.0 (2013/03/26) JAVA 7
* improved a part of the search engine with multi-threading. Results analysis may run faster on multi-core processors.
* added a button to abort the search.
* updated German (de) translation. Thx Boris Klein for his great contribution.
* renamed the logfile "steamcleaner_messages.log" to avoid collision with other TikiOne products.
* internal: some code optimization in the search engine.
* internal: some code cleanup in the search engine.

### 2.0.4 (2013/03/21) JAVA 7
* updated the the startup (.bat) script.
* updated encoding of Russian and Ukrainian translation files from Windows-1251 to UTF-8.
* merged the TikiOne Steam Cleaner and TikiOne NIO projects.

### 2.0.3 (2013/03/19) JAVA 7
* added Ukrainian (ua) translation. Thx Dmitry Bolotov (Дмитрий Болотов) for his great contribution.
* updated Russian (ru) translation. Thx Dmitry Bolotov (Дмитрий Болотов).

### 2.0.2 (2013/03/16) JAVA 7
* added Polish (pl) translation. Thx Piotr Swat for his great contribution.
* improved translation files.

### 2.0.1 (2013/03/07) JAVA 7 this is a highly recommended update !
* removed the "/config/config.vdf" file parsing support (too many bugs with some Steam installations). It will fix a bug that forced the program to include the entire "C:\" drive to the search path.
* increased the default maximum depth of search (3->5).
* updated the logging engine: log messages are now stored in the "%USERPROFILE%/.tikione/log/messages.log" file.
* added dependency to the TikiOne NIO library (v0.0.1). It will provide the multi-threaded optimizations in the future 2.1.0 version.
* updated Portuguese (pt) translation. Thx Pedro Henrique Viegas Diniz.
* updated Russian (ru) translation. Thx Dmitry Bolotov (Дмитрий Болотов).

### 2.0.0 (2013/03/06) JAVA 7
* added a Windows installer based on Nullsoft Scriptable Install System (NSIS) 2.46.5-Unicode.
* For future 2.x.x releases, please uninstall previous version first; do not try to upgrade an existing NSIS based installation (I'll try to make it possible in a future release).
* fixed a regression bug in the "/config/config.vdf" file parsing (results were ignored since a few TikiOne releases).
* added Portuguese (pt) translation. Thx Pedro Henrique Viegas Diniz for his great contribution.
* updated Russian (ru) translation. Thx Dmitry Bolotov (Дмитрий Болотов) for his great contribution.

### 1.4.13 (2013/03/03) JAVA 7
* fixed a bug case when trying to delete a folder that has already been deleted.
* some code reworked.

### 1.4.12 (2013/02/28) JAVA 7
* updated Russian (ru) translation. Thx Dmitry Bolotov (Дмитрий Болотов).

### 1.4.11 (2013/02/27) JAVA 7
* updated the search engine to support a static list of folders to exclude from the search path. See the /conf/tikione-steam-cleaner_dangerous-items.ini file for details.
* updated internationalization: the user interface is now fully internationalizable.
* minor UI improvements.

### 1.4.10 (2013/02/22) JAVA 7
* updated Hungarian (hu) translation. Thx Zsolt Brechler.
* updated the logging engine (custom->log4j-1.2.17).
* information messages and errors are now always appended to logs.
* minor UI improvements.

### 1.4.9 (2013/02/14) JAVA 7
* updated dependency to the TikiOne INI library (v2.0.2->v2.0.3).
* updated Russian (ru) translation. Thx Dmitry Bolotov (Дмитрий Болотов).
* minor UI improvements.

### 1.4.8 (2013/02/13) JAVA 7 this is a highly recommended update !
* fixed the the startup (.bat) script to allow elevation of privileges (right click / Run as administrator).
* added the ability to define a list of additional custom folders to the search path.
* Nota: the "C:\Windows" folder is automatically excluded from the search path. You still can add it manually (or any of its subfolders), but it won't be analysed.
* updated translation files: new content is now internationalizable (added French, English and Spanish new words only, we need your help to complete the other translations).
* fixed calculation of the space saved after cleaning (files which have not been removed due to errors were not properly managed).
* fixed a bug in the folder deletion process that could lead to a crash of the application.
* minor speed improvements of the search engine.
* minor UI improvements.

### 1.4.7 (2013/02/09) JAVA 7
* updated Russian (ru) translation. Thx Dmitry Bolotov (Дмитрий Болотов).

### 1.4.6 (2013/02/08) JAVA 7
* updated the update engine to better manage TikiOne's version numbers.
* added a button to show the latest changelog file.
* internal: some code cleanup.

### 1.4.5 (2013/02/05) JAVA 7
* added Russian (ru) translation. Thx Dmitry Bolotov (Дмитрий Болотов) for his great contribution.
* updated the update engine to better manage TikiOne's version numbers.
* added logs to indicate which folders have been scanned during research.
* minor improvements of the internationalization engine (custom encoding support).
* minor UI improvements.

### 1.4.4 (2013/01/31) JAVA 7
* updated the search engine: fixed a bug in the "/config/config.vdf" file parsing (search was case sensitive).

### 1.4.3 (2013/01/30) JAVA 7
* removed the confirmation dialog of selected redistributable packages deletion (this confirmation sounded useless).
* updated the search engine: the old and new engines have been merged to solve come compatibility issues.
* updated the application configuration management: config files are now stored in the "%USERPROFILE%/.tikione/" directory. So, your preferences are preserved when you install a new version of TikiOne Steam Cleaner.
* updated the update mechanism: TikiOne Steam Cleaner can now download and install updates automatically (simply check for updates and use the download button).
* If an error occurs, you will be redirected to the SourceForge.net website to download new version by hand.
* fixed a case that generated unnecessary error/warning logs.
* minor performance optimizations of the search engine.
* minor UI improvements.

### 1.4.2 (2013/01/27) JAVA 7
* fixed a crach case where a folder registered by "/config/config.vdf" doesn't exist.

### 1.4.1 (2013/01/26) JAVA 7
* fixed the startup (.bat) script: it should avoid some rare craches.
* updated the search engine in order to detect custom installation directories. This is the main new feature of the future TikiOne Steam Cleaner 2.0.0 version.
* warning: due to the new search engine, the program still asks for the Steam base directory, but the "/config/config.vdf" has to be present too (in a normal Steam installation, it is here).
* Info: the "/config/config.vdf" indicates where all your games are installed.
* updated the error dialogs (to warn about the possible "/config/config.vdf" file absence).

### 1.4.0 (2013/01/17) JAVA 7
* warning: TikiOne Steam Cleaner now runs with Java 7 (or the upcoming version 8). You can not run it with Java 5 nor Java 6.
* information: the 1.4.x branch will prepare the future 2.0.0 version of TikiOne Steam Cleaner (which will include support for the new Steam features).
* fixed the startup (.bat) script: it should detect more Java installations.
* replaced the main menu by a toolbar.
* internal: general code cleanup.
* removed support of VDF files (this feature sounded useless and not efficient enough).

### 1.3.5 (2012/11/10) JAVA 6
* updated German (de) translation. Thx Ulli Kunz for his great contribution.

### 1.3.4 (2012/10/28) JAVA 6
* added Hungarian (hu) translation. Thx Zsolt Brechler for his great contribution.

### 1.3.3 (2012/08/21) JAVA 6
* updated the configuration file to detect BattleEye Anti-Cheat Engine installers.
* updated the program's launcher to detect more Java installations.

### 1.3.2 (2012/07/31) JAVA 6
* updated the configuration file to improve installers detection (DirectX folder for the Worms Reloaded game).

### 1.3.1 (2012/07/25) JAVA 6
* updated the program's logger to provide more detailed error messages.

### 1.3.0 (2012/05/15) JAVA 6
* the program now asks for the Steam base directory, not the "SteamApps" directory.
* updated the configuration file to improve installers detection (DirectX, DotNET, AMD Dual-Core Optimizer and patterns I have classified as experimental). Thx Petr Kudlička for his great contribution.
* updated the detection of possible Steam base directories.
* updated the English and French translations.
* added a PayPal button for donations.
* added an option to enable experimental installers detection. This feature is deactivated by default.
* added an option to check for program updates at startup. This feature is activated by default.
* added Spanish (es) translation, based on Google Trad and Microsoft Translator.
* added German (de) translation, based on Google Trad and Microsoft Translator.
* added icons in some menu items and buttons.
* added the possibility to save to a (log) file the errors that the program encounters. This feature is activated by default.
* internal: re-activated the debugging informations from compiled Java class files, to make log informations more accurate.
* important code cleanup and refactoring.
* reworked UI (User Interface).
* information: replaced the executable file (generated by Bat-To-Exe-Converter v1.6) by a windows batch file (BAT): some antivirus softwares reported it as a malware (this is a false-positive alert, probably due to unreliable heuristics).
* Avira Antivir has announced a fix for this false-positive alert in a future update of its virus definitions database.
* You still can download and use the executable file from the 1.2.2 version of TikiOne Steam Cleaner. I'll provide my own executable file in a future release.
* the start-up script now increases the maximum amount of memory that the program can use, to avoid some abnormal exits. Available if you use the bundled JRE only (available in the "tikione-steam-cleaner-1.3.0 (with JRE).zip" package download).
* A future version of the start-up script (or executable file, if available) will apply the same parameters to the system's JRE (if you use the default package download: "tikione-steam-cleaner-1.3.0.zip").

### 1.2.2 (2012/04/29) JAVA 6
* at first launch, the default language is now selected according to the operating system's settings.

### 1.2.1 (2012/04/21) JAVA 6
* fixed the log files name (the month's number was wrong).
* minor performance optimizations.
* internal: removed the debugging informations from compiled Java class files, to reduce the application size.

### 1.2.0 (2012/04/18) JAVA 6 this is a highly recommended update !
* updated the configuration file to improve installers detection ("dependencies" directories) (1.1.5 update 1 integration).
* updated the configuration file to make installers detection safer (it will avoid false-positive directories detections) (1.1.5 update 1 integration).
* added the possibility to verify redistributable packages files validity thanks to the VDF games files (it will avoid false-positive directories detections too). This feature is experimental and not activated by default.
* added the possibility to save to a (log) file the list of redistributable packages you have deleted. This feature is activated by default.
* updated the French translation.
* minor performance optimizations.
* updated dependency to the TikiOne INI library (v2.0.1->v2.0.2).

### 1.1.5 (2012/03/29) JAVA 6
* some UI enhancements.
* updated the French translation of "About TikiOne Steam Cleaner" window's title (1.1.4 translation update 1 integration).

### 1.1.4 (2012/03/29) JAVA 6
* fixed a bug: close the application while a search task is running hides the main window but it doesn't end the search task. The search task is now automatically aborted.
* added support for language translations.
* added French translation (fr).
* the application configuration design allows you to add new translations easily: edit or add /conf/i18n/*.ini files.
* Don't hesitate to contact me for additional translations (you can find my email at the end of this page).
* updated (decreased by 2) the default max depth of folders scanning to significantly improve search speed.
* added an option to configure max depth of folders scanning.

### 1.1.3 (2012/03/19) JAVA 6
* updated the configuration file to improve installers detection ("prerequisite(s)" directories).

### 1.1.2 (2012/03/10) JAVA 6
* some UI enhancements.
* updated the configuration file to improve Visual C++ Redistributable Package installers detection.

### 1.1.1 (2012/03/04) JAVA 6
* reduced the application size by 25%.

### 1.1.0 (2012/03/01) JAVA 6
* warning: TikiOne Steam Cleaner now runs with Java 6 (or better). You can not run it with Java 5.
* added the possibility to check for new version of TikiOne Steam Cleaner.
* added an estimation of the amount of disk space you have saved by deleting selected redistributable packages.
* code cleanup and some documentation added in the Java source code (Javadoc).
* updated the configuration file to improve installers detection ("installer(s)" directories).
* fixed a case where the "Search" button is disabled if no redistributable package found.

### 1.0.8 (2012/02/01) JAVA 5
* fixed another case where user could search for packages to remove, edit the Steam path (via the "..." button), and launch redistributable packages deletion: packages base path used the new and wrong Steam path.

### 1.0.7 (2012/01/31) JAVA 5
* added an information message when Steam is correctly detected and there is (no) redistributable package to remove.
* added the ability to remember when a redistributable package has been unselected in the list, in order to set its default state to unselected too for later searches.
* added a progress bar to show advance of research.
* improved user interface reactivity during research.
* fixed another language error: replaced "Redistribuable" by "Redistributable" in Visual C++ Redistributable Package installer description.

### 1.0.6 (2012/01/30) JAVA 5
* fixed a little language error in button names: replaced "Redistribuable" by "Redistributable".

### 1.0.5 (2012/01/30) JAVA 5
* updated the program and configuration file to be case insensitive when examining redistributable package names. This will improve detection of every redistributable package.
* updated the configuration file to improve GameSpy Arcade installers detection.
* some code optimizations.

### 1.0.4 (2012/01/29) JAVA 5
* fixed a case where user could search for packages to remove, edit the Steam path, and launch redistributable packages deletion: packages base path used the new and wrong Steam path.
* Deletion buttons ("Remove selected items..." and "Generate a Windows Batch...") are now disabled when Steam path is modified: you have to initiate a new search in order to re-enable them.
* updated the configuration file to fix DirectX installers detection.

### 1.0.3 (2012/01/29) JAVA 5
* updated the configuration file to rename the Visual C++ redist description ("Vivual" to "Visual").
* fixed size information for files smaller than 1 MB.

### 1.0.2 (2012/01/28) JAVA 5
* updated the configuration file to remove the "install(s)" and ""installer(s)" directories detection.
* fixed the version shown into the window title.
* renamed the "Reload" button title to "Search".
* added a button ("...") to search Steam folder manually.

### 1.0.1 (2012/01/28) JAVA 5
* updated the configuration file to fix .NET 4.0 Framework installers detection.

### 1.0.0 (2012/01/28) JAVA 5
* first public release.
