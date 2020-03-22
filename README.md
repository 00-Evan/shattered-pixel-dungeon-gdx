# This Codebase is Being Deprecated!

The original [android codebase](https://github.com/00-Evan/shattered-pixel-dungeon) is now multiplatform and supports desktop builds. As a result, support for this repository is ending.

~~This repo will receive 0.8.0 and any immediate patches, but will not receive any updates afterward. As a result I would strongly advise against forking this repository. You should fork the original repository if you want to receive updates from upstream.~~ After further consideration, due to the size of 0.8.0 and the significant amount of divergent desktop code I have implemented into it, I have decided to not port it to this codebase. 0.7.5e is the final update this repository will receive. 

If you need assistance converting an existing project to the new codebase, please feel free to email me. If you are considering starting a project, I would strongly advise against using this repository; please use the main one instead. The desktop builds the main repository produces are suitable for debugging at the moment, and once 0.8.0 is released in early April the main repository will be able to produce production-ready desktop builds.

If you are a regular user you have nothing to worry about. You should be able to seamlessly switch to desktop builds from the original codebase once they become available.

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
