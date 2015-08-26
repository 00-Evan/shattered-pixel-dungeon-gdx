/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2015 Evan Debenham
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
package com.watabou.pd;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.watabou.utils.PDPlatformSupport;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSBundle;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.uikit.UIApplication;

public class IOSLauncher extends IOSApplication.Delegate {
	@Override
	protected IOSApplication createApplication() {
		IOSApplicationConfiguration config = new IOSApplicationConfiguration();
		final String version = NSBundle.getMainBundle().getInfoDictionaryObject("CFBundleShortVersionString").toString();
		final int versionCode = Integer.parseInt(NSBundle.getMainBundle().getInfoDictionaryObject("CFBundleVersion").toString());
		return new IOSApplication(new ShatteredPixelDungeon(new PDPlatformSupport(version, versionCode, null, new IOSInputProcessor())), config);
	}

	public static void main(String[] argv) {
		NSAutoreleasePool pool = new NSAutoreleasePool();
		UIApplication.main(argv, null, IOSLauncher.class);
		pool.close();
	}
}
