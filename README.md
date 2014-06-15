##### ``>> Project Status >>`` Finally, the project is still alive!

## TikiOne Steam Cleaner - The best companion of Steam users

Tikione Steam Cleaner is an open source and free software written in Java7 and helps you to find and remove all games's redistribuable packages downloaded by Steam (http://store.steampowered.com). For MS Windows only.

TikiOne Steam Cleaner is built with [NetBeans 8.0](http://netbeans.org) and the latest version of Oracle JDK7.

![Screenshot](http://netbeanscolors.org/files/steamcleaner_2.png)

### How to build, test and package TikiOne Steam Cleaner

To build the project:

* load the project with NetBeans and a Java7 compatible JDK (I use the latest version of NetBeans and Oracle JDK7)
* provide missing dependencies:
 * [TikiOne INI 3.0.0](http://sourceforge.net/projects/tikione/files/tikione-ini/)
 * [Apache Commons IO 2.4](http://commons.apache.org/proper/commons-io/)
 * [Apache log4j™ 1.2.17](http://logging.apache.org/log4j/1.2/)
* set the working directory to the "dist2" folder. It contains additional configuration files used by base application.

You can now build and run the project.

To package the application, simply merge the "dist" and "dist2" folders.

To bundle a JVM, copy it into a "jre" folder in the "dist2" folder and launch the NSIS script: it will package TikiOne steam Cleaner with the provided JVM into an EXE installer based on NSIS-Unicode (Nullsoft Scriptable Install System, Unicode version: I use version 2.46-5 from [Google Code](http://code.google.com/p/unsis/downloads/list)).

Public distributions are currently hosted by Sourceforge.net. See <http://sourceforge.net/projects/tikione/> for details and downloads.

### Author
* Jonathan Lermitage (<jonathan.lermitage@gmail.com>)

### Contributors
* Dmitry Bolotov (Дмитрий Болотов): Russian and Ukrainian translations
* Boris Klein: German translation
* Ulli Kunz: German translation
* Zsolt Brechler: Hungarian translation
* Piotr Swat: Polish translation
* Pedro Henrique Viegas Diniz: Portuguese translation
* Petr Kudlička: redist detection improvements

### License

LGPL License
