This repository is deprecated!
==========

The original [android codebase](https://github.com/00-Evan/shattered-pixel-dungeon) is now multiplatform and supports desktop builds. This repository has stopped receiving updates, and is provided for historical purposes as it was used to build the desktop versions from v0.2.2 to v0.7.5.

If you need assistance converting an existing project to the new codebase, please feel free to email me. If you are considering starting a project, I would strongly advise against using this repository; please use the main one instead.

shattered-pixel-dungeon-gdx
=================

GDX port of [Shattered Pixel Dungeon](https://github.com/00-Evan/shattered-pixel-dungeon) the awesome fork of [Pixel Dungeon](https://github.com/watabou/pixel-dungeon)

Quickstart
----------

Do `./gradlew <task>` to compile and run the project, where `task` is:

* Desktop: `desktop:run`
* Android: `android:installDebug android:run`
* iOS: `launchIosDevice` or `launchIphoneSimulator` or `launchIpadSimulator`
* HTML: `html:superDev` (this doesn't work yet, some classes need to be changed)
* Generate IDEA project: `idea`

For more info about those and other tasks: https://github.com/libgdx/libgdx/wiki/Gradle-on-the-Commandline#running-the-html-project
