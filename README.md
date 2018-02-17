[![Github All Releases](https://img.shields.io/github/downloads/jonathanlermitage/tikione-steam-cleaner/total.svg)](https://github.com/jonathanlermitage/tikione-steam-cleaner/releases) [![SourceForge](https://img.shields.io/sourceforge/dt/tikione.svg)](https://sourceforge.net/projects/tikione/) [![license](https://img.shields.io/github/license/jonathanlermitage/tikione-steam-cleaner.svg)](https://github.com/jonathanlermitage/tikione-steam-cleaner/blob/master/LICENSE.txt)

:cat: **To fix font rendering on hdpi screens, please try JRE9 (Java 9 JVM), it seems to work.** I'll bundle JRE9 with 3.0.8 release. Current version is 3.0.7.

# TikiOne Steam Cleaner

_The best companion of Steam users - [Download latest version **here**](https://github.com/jonathanlermitage/tikione-steam-cleaner/releases)_

Tikione Steam Cleaner is an open source and free software written in Java 8 and helps you to find and remove all games's redistribuable packages downloaded by **Steam** (http://store.steampowered.com). For MS Windows only.

**GOG** (GalaxyClient) and Electronic Arts **Origin** are supported.

![Screenshot](https://raw.githubusercontent.com/jonathanlermitage/tikione-steam-cleaner/master/tikione-steam-cleaner-banner.png)

## Download installer or ZIP package

TikiOne Steam Cleaner installers and ZIP packages are hosted on [GitHub releases](https://github.com/jonathanlermitage/tikione-steam-cleaner/releases).

## Build, test and package

*(Saturday, April 16, 2016 Warning: I just migrated from Ant to Maven build system)*

TikiOne Steam Cleaner is currently built with [NetBeans](http://netbeans.org), Maven and the latest version of Oracle JDK8.

To build the project:

* load the project with NetBeans and a Java 8 compatible JDK (I use the latest version of NetBeans and Oracle JDK8)
* set the working directory to the "dist2" folder. It contains additional configuration files used by base application. Also, the build output targets this directory.

You can now build and run the project.

The packaged application is in the "dist2" folder.

To bundle a JVM (version 8 or better), copy it as a "jre" subfolder in the "dist2" directory and launch the NSIS script: it will package TikiOne steam Cleaner with the provided JVM into an EXE installer based on NSIS-Unicode (Nullsoft Scriptable Install System, Unicode version: I use version 2.46-5 from [Google Code](http://code.google.com/p/unsis/downloads/list)).  
Nota: since Google Code is shutting down, I have uploaded [latest NSIS version here](https://github.com/jonathanlermitage/tikione-steam-cleaner/tree/master/dependencies/NSIS).

## Author
* Jonathan Lermitage (<jonathan.lermitage@gmail.com>)

## Contributors
* Dmitry Bolotov (Дмитрий Болотов): Russian and Ukrainian translations
* Boris Klein: German translation
* Ulli Kunz: German translation
* Hauwertlhaufn: German translation
* Zsolt Brechler: Hungarian translation
* Piotr Swat: Polish translation
* Pedro Henrique Viegas Diniz: Portuguese translation
* "[poutros](https://github.com/poutros)": Portuguese  translation
* "ZoSH": Spanish translation
* "wbsdty331": Simplified Chinese translation
* "[tsk12](https://github.com/tsk12)": Traditional Chinese translation
* "[tskonetwo](https://github.com/tskonetwo)": Traditional Chinese translation
* Davide Crucitti: Italian translation
* "[xDarkWolf](https://github.com/xDarkWolf)": Italian translation
* "[gizmo3399](https://github.com/gizmo3399)": Dutch translation
* Petr Kudlička: redist detection improvements
* Brian Huqueriza: redist detection improvements
* "snowman": redist detection improvements, GOG and Origin support
* "[voltagex](https://github.com/voltagex)": Steam and GOG protection improvements
* "[mariosumd](https://github.com/mariosumd)": Steam and GOG protection improvements
* Members of the [CanardPC forum](http://forum.canardpc.com), for their support and cheerfulness

## History

I'm working on this software since Janurary 2012. Here is the full [changelog](https://github.com/jonathanlermitage/tikione-steam-cleaner/blob/master/CHANGELOG.md).  

## License

MIT License. In other words, you can do what you want: this project is entirely OpenSource, Free and Gratis.

## Alternative

Andrew Sampson is the developper of an excellent alternative based on Microsoft *.NET* technology. Check his repository: [Codeusa/SteamCleaner](https://github.com/Codeusa/SteamCleaner).  
He is also the author of the famous *Borderless Gaming* software: [Codeusa/Borderless-Gaming](https://github.com/Codeusa/Borderless-Gaming).

## Security

You may think that *Java* is not secure. I won't blame you, but please keep in mind that if the *Java _plugin_* (which is dead now) was a giant security hole, the *Java _VM_* (that runs TikiOne Steam Cleaner) is secure and is used by many major companies to run servers (for Google, Amazon...), desktop applications (like JetBrains IDEs), TVs, IoT, Android smartphones, etc. In other words, Java is everywhere and it works fine ;-)

## Tools

I maintain TikiOne Steam Cleaner thanks to these products:

|Apache libraries (Apache Log4j and Apache Commons IO)|
|:--|
|[![Apache](https://raw.githubusercontent.com/jonathanlermitage/tikione-steam-cleaner/master/misc/logo_apache.png)](https://www.apache.org)|

|Apache Maven build tool|
|:--|
|[![Maven](https://raw.githubusercontent.com/jonathanlermitage/tikione-steam-cleaner/master/misc/logo_maven.png)](https://maven.apache.org)|

|Oracle JRE and JDK|
|:--|
|[![JDK](https://raw.githubusercontent.com/jonathanlermitage/tikione-steam-cleaner/master/misc/logo_java.png)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)|

|Currently developed with Oracle NetBeans IDE|
|:--|
|[![NetBeans](https://raw.githubusercontent.com/jonathanlermitage/tikione-steam-cleaner/master/misc/logo_netbeans.png)](https://netbeans.org)|

|Migrating to JetBrains IntelliJ IDEA IDE|
|:--|
|[![IntelliJ](https://raw.githubusercontent.com/jonathanlermitage/tikione-steam-cleaner/master/misc/logo_intellij.png)](https://www.jetbrains.com/idea/)|
