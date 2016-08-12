:fire: Translation update: help wanted  
See [ini files in dist2/conf/i18n](https://github.com/jonathanlermitage/tikione-steam-cleaner/tree/master/dist2/conf/i18n)
```
[W_OPTIONS]
notice.registerRemoteRedistDefFiles=<translation here>
notice.downloadRemoteRedistDefFiles=<translation here>
download.errormsg.remoteRedistDefFiles=<translation here>
download.warningbox.remoteRedistDefFiles=<translation here>
download.warningbox.title.remoteRedistDefFiles=<translation here>
download.complete=<translation here>
```

# TikiOne Steam Cleaner

_The best companion of Steam users - [Download latest version **here**](https://github.com/jonathanlermitage/tikione-steam-cleaner/releases)_

Tikione Steam Cleaner is an open source and free software written in Java 8 and helps you to find and remove all games's redistribuable packages downloaded by **Steam** (http://store.steampowered.com). For MS Windows only.

**GOG** (GalaxyClient) and Electronic Arts **Origin** are supported.

![Screenshot](https://raw.githubusercontent.com/jonathanlermitage/tikione-steam-cleaner/master/tikione-steam-cleaner-banner.png)

## Download installer

TikiOne Steam Cleaner installer is hosted on [GitHub releases](https://github.com/jonathanlermitage/tikione-steam-cleaner/releases).

## Build, test and package

*(Saturday, April 16, 2016 Warning: I just migrated from Ant to Maven build system)*

TikiOne Steam Cleaner is currently built with [NetBeans](http://netbeans.org), Maven and the latest version of Oracle JDK8.

To build the project:

* install TikiOne INI dependency in your Maven local repo: launch ``.dependencies/install-maven-artifacts/tikione-ini-3.0.0.bat``
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
* "ZoSH": Spanish translation
* "wbsdty331": Simplified Chinese translation.
* Petr Kudlička: redist detection improvements
* Brian Huqueriza: redist detection improvements
* "snowman": redist detection improvements, GOG and Origin support
* "[voltagex](https://github.com/voltagex)": Steam and GOG protection improvements
* "[mariosumd](https://github.com/mariosumd)": Steam and GOG protection improvements
* Members of the [CanardPC forum](http://forum.canardpc.com), for their support and cheerfulness

## History

I'm working on this software since Janurary 2012. Here is the full [changelog](https://github.com/jonathanlermitage/tikione-steam-cleaner/blob/master/CHANGELOG.md).
