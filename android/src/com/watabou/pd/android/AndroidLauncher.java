package com.watabou.pd.android;

import android.content.pm.PackageManager;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.input.GameAction;
import com.watabou.utils.PDPlatformSupport;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		String version;
		int versionCode;
		try {
			version = getPackageManager().getPackageInfo( getPackageName(), 0 ).versionName;
			versionCode = getPackageManager.getPackageInfo( getPackageName(), 0  ).versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			version = "???";
			versionCode = 0;
		}
		initialize(new ShatteredPixelDungeon(new PDPlatformSupport<GameAction>(version, versionCode, null, new AndroidInputProcessor())), config);
	}
}
