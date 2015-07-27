[![Stories in Backlog](https://badge.waffle.io/jonathanlermitage/tikione-steam-cleaner.png?label=backlog&title=Backlog)](https://waffle.io/jonathanlermitage/tikione-steam-cleaner)
[![Stories in Ready](https://badge.waffle.io/jonathanlermitage/tikione-steam-cleaner.png?label=ready&title=Ready)](https://waffle.io/jonathanlermitage/tikione-steam-cleaner)
[![Stories in In progress](https://badge.waffle.io/jonathanlermitage/tikione-steam-cleaner.png?label=in%20progress&title=In%20progress)](https://waffle.io/jonathanlermitage/tikione-steam-cleaner)

# TikiOne Steam Cleaner

_The best companion of Steam users - Download version 2.4.6 [here](https://github.com/jonathanlermitage/tikione-steam-cleaner/releases)_

Tikione Steam Cleaner is an open source and free software written in Java 8 and helps you to find and remove all games's redistribuable packages downloaded by Steam (http://store.steampowered.com). For MS Windows only.

GOG (GalaxyClient) and Origin support is under development.

TikiOne Steam Cleaner is built with [NetBeans](http://netbeans.org) and the latest version of Oracle JDK8.

![Screenshot](http://lermitage.biz/files/steamcleaner_2.png)

## Download installer

TikiOne Steam Cleaner installer is hosted on [GitHub releases](https://github.com/jonathanlermitage/tikione-steam-cleaner/releases).

**Information about Sourceforge**: up to version 2.4.6, TikiOne Steam Cleaner was hosted on SourceForge. I have decided to leave this website to prefer GitHub.

## Build, test and package

To build the project:

* load the project with NetBeans and a Java 8 compatible JDK (I use the latest version of NetBeans and Oracle JDK8)
* dependencies should be automatically loaded from the ``./dependencies/`` folder.
* set the working directory to the "dist2" folder. It contains additional configuration files used by base application.

You can now build and run the project.

To package the application, simply merge the "dist" and "dist2" folders.

To bundle a JVM (version 8 or better), copy it into a "jre" folder in the "dist2" folder and launch the NSIS script: it will package TikiOne steam Cleaner with the provided JVM into an EXE installer based on NSIS-Unicode (Nullsoft Scriptable Install System, Unicode version: I use version 2.46-5 from [Google Code](http://code.google.com/p/unsis/downloads/list)).

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
* Petr Kudlička: redist detection improvements
* Brian Huqueriza: redist detection improvements
* Antti Mieskolainen: redist detection improvements, GOG and Origin support
* Members of the [CanardPC forum](http://forum.canardpc.com), for their support and cheerfulness

## History

I'm working on this software since Janurary 2012. Here is the full [changelog](https://github.com/jonathanlermitage/tikione-steam-cleaner/blob/master/CHANGELOG.md).

## License

LGPL License

## Donations

You can help me with a PayPal donation ([TikiOne, jonathan@lermitage.biz](http://sourceforge.net/p/tikione/donate/)). I'll buy beer and chocolate.
