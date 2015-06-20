[![Stories in Backlog](https://badge.waffle.io/jonathanlermitage/tikione-steam-cleaner.png?label=backlog&title=Backlog)](https://waffle.io/jonathanlermitage/tikione-steam-cleaner)
[![Stories in Ready](https://badge.waffle.io/jonathanlermitage/tikione-steam-cleaner.png?label=ready&title=Ready)](https://waffle.io/jonathanlermitage/tikione-steam-cleaner)
[![Stories in In progress](https://badge.waffle.io/jonathanlermitage/tikione-steam-cleaner.png?label=in%20progress&title=In%20progress)](https://waffle.io/jonathanlermitage/tikione-steam-cleaner)

## TikiOne Steam Cleaner - The best companion of Steam users

Tikione Steam Cleaner is an open source and free software written in Java7 and helps you to find and remove all games's redistribuable packages downloaded by Steam (http://store.steampowered.com). For MS Windows only.

TikiOne Steam Cleaner is built with [NetBeans 8](http://netbeans.org) and the latest version of Oracle JDK7.

![Screenshot](http://lermitage.biz/files/steamcleaner_2.png)

### How to download TikiOne Steam Cleaner installer

TikiOne Steam Cleaner installer is hosted on:

* [Sourceforge](http://sourceforge.net/projects/tikione/), click [here](http://sourceforge.net/projects/tikione/files/latest/download) to download the latest version
* [GitHub](https://github.com/jonathanlermitage/tikione-steam-cleaner/releases)
 
*Tip*: software comes with a standalone version on Java. Standalone means it won't be registered on your system, so you won't be exposed to web vulnerabilities. Standalone Java is used by Steam Cleaner only. Also, if you already installed Java (version 7, 8 or better), you can delete the standalone version: simply go to the Steam Cleaner installation directory (something like `C:\Program Files (x86)\TikiOne Steam Cleaner\`) and delete the `jre` folder. Once it's done, Steam Cleaner will use your Java installation.

*Information about Sourceforge*: you probably heard about the fact that Sourceforge modified installer files of GIMP and Firefox projects to include adware and junkware. This is due to the fact that Sourceforge takes ownership of abandoned projects (they haven't been updated since a very long time on the Sourceforge platform). Sourgeforge decision is very disputable. Meanwhile, maintained projects are not modified; TikiOne Steam Cleaner is one of them: I maintain binaries and they will NEVER come with adware or junkware. The day I stop Steam Cleaner development, I will simply delete my Sourceforge account and project files.

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

### Author
* Jonathan Lermitage (<jonathan.lermitage@gmail.com>)

### Contact

You can ask questions and share ideas by email (<jonathan.lermitage@gmail.com>), discuss via [Twitter](https://twitter.com/JLermitage) (JLermitage) or [Reddit](https://www.reddit.com/r/gaming/comments/3ahunc/tikione_steam_cleaner_will_support_gog_and_origin/) (jlermitage).

### Contributors
* Dmitry Bolotov (Дмитрий Болотов): Russian and Ukrainian translations
* Boris Klein: German translation
* Ulli Kunz: German translation
* Zsolt Brechler: Hungarian translation
* Piotr Swat: Polish translation
* Pedro Henrique Viegas Diniz: Portuguese translation
* Petr Kudlička: redist detection improvements
* Brian Huqueriza: redist detection improvements
* Members of the [CanardPC forum](http://forum.canardpc.com), for their support and cheerfulness

### License

LGPL License
