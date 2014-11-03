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
