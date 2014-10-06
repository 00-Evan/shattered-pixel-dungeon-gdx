package com.watabou.pd.android;

import android.content.pm.PackageManager;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.input.GameAction;
import com.watabou.utils.PDPlatformSupport;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		String version;
		try {
			version = getPackageManager().getPackageInfo( getPackageName(), 0 ).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			version = "???";
		}
		initialize(new PixelDungeon(new PDPlatformSupport<GameAction>(version, null, new AndroidInputProcessor())), config);
	}
}
