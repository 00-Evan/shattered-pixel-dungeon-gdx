/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2017 Evan Debenham
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

package com.watabou.utils;

import com.badlogic.gdx.Gdx;
import com.watabou.noosa.Game;

public class DeviceCompat {
	
	public static boolean supportsFullScreen(){
		return Game.instance.getPlatformSupport().isFullscreenEnabled();
	}
	
	public static boolean legacyDevice(){
		return false;
	}
	
	public static boolean supportsPlayServices(){
		return false;
	}
	
	public static boolean usesISO_8859_1(){
		return false;
	}
	
	public static void openURI( String URI ){
		Gdx.net.openURI( URI );
	}
	
	public static void log( String tag, String message ){
		Gdx.app.log( tag, message );
	}

}
