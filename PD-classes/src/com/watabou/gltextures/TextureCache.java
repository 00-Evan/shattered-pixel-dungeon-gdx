/*
 * Copyright (C) 2012-2014  Oleg Dolya
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

package com.watabou.gltextures;

import com.badlogic.gdx.graphics.Pixmap;
import com.watabou.gdx.GdxTexture;
import com.watabou.glwrap.Texture;

import java.util.HashMap;

public class TextureCache {

	private static HashMap<Object,SmartTexture> all = new HashMap<Object, SmartTexture>();
	
	public static SmartTexture createSolid( int color ) {
		String key = "1x1:" + color;
		
		if (all.containsKey( key )) {
			
			return all.get( key );
			
		} else {

			final Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
			// In the rest of the code ARGB is used
			pixmap.setColor( (color << 8) | (color >>> 24) );
			pixmap.fill();
			GdxTexture bmp = new GdxTexture( pixmap );

			SmartTexture tx = new SmartTexture( bmp );
			all.put(key, tx);

			return tx;
		}
	}

	public static void add( Object key, SmartTexture tx ) {
		all.put( key, tx );
	}

	public static SmartTexture get( Object src ) {
		
		if (all.containsKey( src )) {
			
			return all.get( src );
			
		} else if (src instanceof SmartTexture) {
			
			return (SmartTexture)src;
			
		} else {

			SmartTexture tx = new SmartTexture( getBitmap( src ) );
			all.put( src, tx );
			return tx;
		}
		
	}
	
	public static void clear() {
		
		for (Texture txt:all.values()) {
			txt.delete();
		}
		all.clear();
		
	}
	
	public static void reload() {
		for (SmartTexture tx:all.values()) {
			tx.reload();
		}		
	}
	
	public static GdxTexture getBitmap( Object src ) {
		
		try {
			if (src instanceof Integer){

				// FIXME
				throw new UnsupportedOperationException();
				
			} else if (src instanceof String) {
				
				return new GdxTexture((String)src);
				
			} else if (src instanceof GdxTexture) {
				
				return (GdxTexture)src;
				
			} else {
				
				return null;
				
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
			
		}
	}
	
	public static boolean contains( Object key ) {
		return all.containsKey( key );
	}
	
}
