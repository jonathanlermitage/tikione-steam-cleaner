# TikiOne Steam Cleaner

_The best companion of Steam users - [Download latest version **here**](https://github.com/jonathanlermitage/tikione-steam-cleaner/releases)_

Tikione Steam Cleaner is an open source and free software written in Java 8 and helps you to find and remove all games's redistribuable packages downloaded by **Steam** (http://store.steampowered.com). For MS Windows only.

**GOG** (GalaxyClient) and Electronic Arts **Origin** support under development.

![Screenshot](https://raw.githubusercontent.com/jonathanlermitage/tikione-steam-cleaner/master/src/fr/tikione/steam/cleaner/gui/tikione-steam-cleaner-banner.png)

## Project updates (Monday, July 27, 2015)

I'll try to port TikiOne Steam Cleaner to native code. That means you'll no longer need Java, and the program will be smaller (from ~60MB to ~3MB) and faster.

To proceed, I will use a development tool I master: WinDev. Unfortunately, this is a commercial product and I cannot afford a licence (it's about €2000).  
That's why I am launching [Kickstarter](https://www.kickstarter.com/projects/313629631/tikione-steam-cleaner) and [Patreon](https://www.patreon.com/user?u=942297) campaigns. I really hope you will support me, otherwise... I simply won't be able to maintain TikiOne Steam Cleaner.

Do not hesitate to share ideas by email (<jonathan.lermitage@gmail.com>) or via [Twitter](https://twitter.com/JLermitage). Thx!

## Download installer

TikiOne Steam Cleaner installer is hosted on [GitHub releases](https://github.com/jonathanlermitage/tikione-steam-cleaner/releases).

**Information about Sourceforge**: up to version 2.4.6, TikiOne Steam Cleaner was hosted on SourceForge. I have decided to leave this website to prefer GitHub. Starting from next version, I'll use GitHub only.

## Build, test and package

TikiOne Steam Cleaner is built with [NetBeans](http://netbeans.org) and the latest version of Oracle JDK8.

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

## Donators
* [Rami M. M.](https://www.patreon.com/raimu)

If you donate via **Patreon** ($5+) but don't see your name here, please send me an email or contact me via Patreon directly. I'll add your name and external links if asked (links to your Facebook, Twitter, Google+, LinkedIn profile, a weblog or anything else).  
Thank you very much for your support.

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

You can help me with a PayPal donation ([TikiOne, jonathan@lermitage.biz](http://sourceforge.net/p/tikione/donate/)).
