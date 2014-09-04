package com.watabou.pd.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import com.watabou.pixeldungeon.PixelDungeon;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 640;
		config.height = 865;
		if (SharedLibraryLoader.isMac) {
			config.preferencesDirectory = "Library/Application Support/Pixel Dungeon/";
		} else if (SharedLibraryLoader.isLinux) {
			config.preferencesDirectory = ".watabou/pixel-dungeon/";
		} else if (SharedLibraryLoader.isWindows) {
			config.preferencesDirectory = "Saved Games/";
		}
		new LwjglApplication(new PixelDungeon(config.preferencesDirectory), config);
	}
}
