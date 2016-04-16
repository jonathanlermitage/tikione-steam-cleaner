*:fire: Thursday, January 21, 2016: I will continue to include contributions (usually translations) and provide bugfixes. Fell free to submit ideas and provide feedback. Don't expect new major features or native builds, this is a side project now.*

# TikiOne Steam Cleaner

_The best companion of Steam users - [Download latest version **here**](https://github.com/jonathanlermitage/tikione-steam-cleaner/releases)_

Tikione Steam Cleaner is an open source and free software written in Java 8 and helps you to find and remove all games's redistribuable packages downloaded by **Steam** (http://store.steampowered.com). For MS Windows only.

**GOG** (GalaxyClient) and Electronic Arts **Origin** are supported.

![Screenshot](https://raw.githubusercontent.com/jonathanlermitage/tikione-steam-cleaner/master/src/fr/tikione/steam/cleaner/gui/tikione-steam-cleaner-banner.png)

## Download installer

TikiOne Steam Cleaner installer is hosted on [GitHub releases](https://github.com/jonathanlermitage/tikione-steam-cleaner/releases).

**Information about Sourceforge**: up to version 2.4.6, TikiOne Steam Cleaner was hosted on SourceForge. I have decided to leave this website to prefer GitHub. Starting from next version, I'll use GitHub only.

## Build, test and package

*(Saturday, April 16, 2016 Warning: I just migrated from Ant to Maven build system)*

TikiOne Steam Cleaner is currently built with [NetBeans](http://netbeans.org) and the latest version of Oracle JDK8 (nota: I am working on an [IntelliJ IDEA](https://www.jetbrains.com/idea/) port).

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

## Contact

You can ask questions, share ideas or insult me by email (<jonathan.lermitage@gmail.com>) or discuss via [Twitter](https://twitter.com/JLermitage).

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
* Antti Mieskolainen: redist detection improvements, GOG and Origin support
* Members of the [CanardPC forum](http://forum.canardpc.com), for their support and cheerfulness

## History

I'm working on this software since Janurary 2012. Here is the full [changelog](https://github.com/jonathanlermitage/tikione-steam-cleaner/blob/master/CHANGELOG.md).

## License

LGPL License with FRA-FN exception

## Donations

You can help me with a PayPal donation ([TikiOne, jonathan@lermitage.biz](http://sourceforge.net/p/tikione/donate/)).
