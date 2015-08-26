package com.shatteredpixel.shatteredpixeldungeon.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.watabou.utils.PDPlatformSupport;

public class HtmlLauncher extends GwtApplication {

		@Override
		public GwtApplicationConfiguration getConfig () {
				return new GwtApplicationConfiguration(480, 320);
		}

		@Override
		public ApplicationListener getApplicationListener () {
			String version = "???";
			int versionCode = 0;
			return new ShatteredPixelDungeon(new PDPlatformSupport(version, versionCode, null, null));
		}
}
