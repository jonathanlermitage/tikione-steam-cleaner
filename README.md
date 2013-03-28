# TikiOne Steam Cleaner - the best companion of Steam users

Tikione Steam Cleaner is an open source and free software written in Java7 and helps you to find and remove all games's
redistribuable packages downloaded by Steam (http://store.steampowered.com).

TikiOne Steam Cleaner is built with NetBeans 7.3 (http://netbeans.org/) and the latest version of Oracle JDK7.

## How to build, test and package TikiOne Steam Cleaner

To build the project:

* load the project with NetBeans (7.1 or better) and a Java7 compatible JDK (I use Oracle JDK7)
* provide missing dependencies:
 * TikiOne INI 2.0.3, available at <http://sourceforge.net/projects/tikione/files/tikione-ini/>
 * Apache Commons IO 2.4
 * Apache Log4J 1.2.17
* set the working directory to the "dist2" folder. It contains additional configuration files used by base application.

You can now build and run the project.

To package the application, simply merge the "dist" and "dist2" folders.

To bundle a JVM, copy it into a "jre" folder in the "dist2" folder and launch the NSIS script: it will package TikiOne
steam Cleaner with the provided JVM into an EXE installer based on NSIS-Unicode (Nullsoft Scriptable Install System,
Unicode version: I use version 2.46-5 from <http://code.google.com/p/unsis/downloads/list>).

## Author
* Jonathan Lermitage (<jonathan.lermitage@entreprise38.org>)

## Contributors
* Dmitry Bolotov (Дмитрий Болотов): Russian and Ukrainian translations
* Boris Klein: German translation
* Ulli Kunz: German translation
* Zsolt Brechler: Hungarian translation
* Piotr Swat: Polish translation
* Pedro Henrique Viegas Diniz: Portuguese translation
* Petr Kudlička: redist detection improvements

## LICENSE

LGPL License