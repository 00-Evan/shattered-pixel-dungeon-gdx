/*
 * Copyright (C) 2012-2015  Oleg Dolya
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

import com.watabou.gdx.GdxTexture;

import java.util.HashMap;

public class BitmapCache {

	private static final String DEFAULT	= "__default";
	
	private static HashMap<String,Layer> layers = new HashMap<String, BitmapCache.Layer>();
	
	public static GdxTexture get( String assetName ) {
		return get( DEFAULT, assetName );
	}
	
	public static GdxTexture get( String layerName, String assetName ) {
		
		Layer layer;
		if (!layers.containsKey( layerName )) {
			layer = new Layer();
			layers.put( layerName, layer );
		} else {
			layer = layers.get( layerName );
		}
		
		if (layer.containsKey( assetName )) {
			return layer.get( assetName );
		} else {
			GdxTexture bmp = new GdxTexture( assetName );
			layer.put( assetName, bmp );
			return bmp;
		}
	}

	public static void clear( String layerName ) {
		if (layers.containsKey( layerName )) {
			layers.get( layerName ).clear();
			layers.remove(layerName);
		}
	}
	
	public static void clear() {
		for (Layer layer:layers.values()) {
			layer.clear();
		}
		layers.clear();
	}
	
	@SuppressWarnings("serial")
	private static class Layer extends HashMap<Object,GdxTexture> {
		
		@Override
		public void clear() {
			for (GdxTexture bmp:values()) {
				bmp.dispose();
			}
			super.clear();
		}
	}
}
