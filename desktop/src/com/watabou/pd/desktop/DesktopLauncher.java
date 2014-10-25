package com.watabou.pd.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglPreferences;
import com.badlogic.gdx.utils.SharedLibraryLoader;
import com.watabou.input.NoosaInputProcessor;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.Preferences;
import com.watabou.utils.PDPlatformSupport;

import org.lwjgl.opengl.Display;

public class DesktopLauncher {
	public static void main (String[] arg) {
		String version = DesktopLauncher.class.getPackage().getSpecificationVersion();
		if (version == null) {
			version = "???";
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
		com.badlogic.gdx.Preferences prefs = new LwjglPreferences(Preferences.FILE_NAME, config.preferencesDirectory);

		boolean isFullscreen = prefs.getBoolean(Preferences.KEY_WINDOW_FULLSCREEN, false);
		config.fullscreen = isFullscreen;
		if (!isFullscreen) {
			config.width = prefs.getInteger(Preferences.KEY_WINDOW_WIDTH, Preferences.DEFAULT_WINDOW_WIDTH);
			config.height = prefs.getInteger(Preferences.KEY_WINDOW_HEIGHT, Preferences.DEFAULT_WINDOW_HEIGHT);
		}

		config.addIcon( "ic_launcher_128.png", Files.FileType.Internal );
		config.addIcon( "ic_launcher_32.png", Files.FileType.Internal );
		config.addIcon( "ic_launcher_16.png", Files.FileType.Internal );

		// TODO: It have to be pulled from build.gradle, but I don't know how it can be done
		config.title = "Shattered Pixel Dungeon";

		new LwjglApplication(new ShatteredPixelDungeon(
				new DesktopSupport(version, config.preferencesDirectory, new DesktopInputProcessor())
		), config);
	}

	private static class DesktopSupport extends PDPlatformSupport {
		public DesktopSupport( String version, String basePath, NoosaInputProcessor inputProcessor ) {
			super( version, basePath, inputProcessor );
		}

		@Override
		public boolean isFullscreenEnabled() {
		//	return Display.getPixelScaleFactor() == 1f;
            return !SharedLibraryLoader.isMac;
		}
	}
}
