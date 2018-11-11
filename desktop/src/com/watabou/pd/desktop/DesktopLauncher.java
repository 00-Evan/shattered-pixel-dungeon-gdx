/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.watabou.pd.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglPreferences;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.utils.PDPlatformSupport;

public class DesktopLauncher {
	public static void main (String[] arg) {
		String version = DesktopLauncher.class.getPackage().getSpecificationVersion();
		if (version == null) {
			version = "0.7.0c";
		}

		int versionCode;
		try {
			versionCode = Integer.parseInt(DesktopLauncher.class.getPackage().getImplementationVersion());
		} catch (NumberFormatException e) {
			versionCode = 311;
		}

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		if (SharedLibraryLoader.isMac) {
			config.preferencesDirectory = "Library/Application Support/Shattered Pixel Dungeon/";
		} else if (SharedLibraryLoader.isLinux) {
			config.preferencesDirectory = ".shatteredpixel/shattered-pixel-dungeon/";
		} else if (SharedLibraryLoader.isWindows) {
			String winVer = System.getProperties().getProperty("os.name");
			if (winVer.contains("XP")) {
				config.preferencesDirectory = "Application Data/.shatteredpixel/Shattered Pixel Dungeon/";
			} else {
				config.preferencesDirectory = "AppData/Roaming/.shatteredpixel/Shattered Pixel Dungeon/";
			}
		}
		// FIXME: This is a hack to get access to the preferences before we have an application setup
		com.badlogic.gdx.Preferences prefs = new LwjglPreferences(SPDSettings.FILE_NAME, config.preferencesDirectory);

		boolean isFullscreen = prefs.getBoolean(SPDSettings.KEY_WINDOW_FULLSCREEN, false);
		//config.fullscreen = isFullscreen;
		if (!isFullscreen) {
			config.width = prefs.getInteger(SPDSettings.KEY_WINDOW_WIDTH, SPDSettings.DEFAULT_WINDOW_WIDTH);
			config.height = prefs.getInteger(SPDSettings.KEY_WINDOW_HEIGHT, SPDSettings.DEFAULT_WINDOW_HEIGHT);
		}

		config.addIcon( "ic_launcher_128.png", Files.FileType.Internal );
		config.addIcon( "ic_launcher_32.png", Files.FileType.Internal );
		config.addIcon( "ic_launcher_16.png", Files.FileType.Internal );

		// TODO: It have to be pulled from build.gradle, but I don't know how it can be done
		config.title = "Shattered Pixel Dungeon";

		new LwjglApplication(new ShatteredPixelDungeon(
				new DesktopSupport(version, versionCode, config.preferencesDirectory, new DesktopInputProcessor())
		), config);
	}

	private static class DesktopSupport extends PDPlatformSupport {
		public DesktopSupport( String version, int versionCode, String basePath, NoosaInputProcessor inputProcessor ) {
			super( version, versionCode, basePath, inputProcessor );
		}

		@Override
		public boolean isFullscreenEnabled() {
		//	return Display.getPixelScaleFactor() == 1f;
			return !SharedLibraryLoader.isMac;
		}
	}
}
