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

package com.watabou.gltextures;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.watabou.gdx.GdxTexture;
import com.watabou.utils.RectF;

import com.watabou.glwrap.Texture;

public class SmartTexture extends Texture {

	public int width;
	public int height;
	
	public TextureFilter fModeMin;
	public TextureFilter fModeMax;
	
	public TextureWrap wModeH;
	public TextureWrap wModeV;
	
	public Atlas atlas;
	
	public SmartTexture( GdxTexture bitmap ) {
		this( bitmap, TextureFilter.Nearest, TextureWrap.ClampToEdge );
	}

	public SmartTexture( GdxTexture bitmap, TextureFilter filtering, TextureWrap wrapping ) {
		
		super(bitmap);
		width = bitmap.getWidth();
		height = bitmap.getHeight();
		
		filter( filtering, filtering );
		wrap( wrapping, wrapping );
		
	}
	
	@Override
	public void filter(TextureFilter minMode, TextureFilter maxMode) {
		super.filter( fModeMin = minMode, fModeMax = maxMode);
	}
	
	@Override
	public void wrap( TextureWrap u, TextureWrap v ) {
		super.wrap( wModeH = u, wModeV = v );
	}
	
	@Override
	public void bitmap( GdxTexture bitmap ) {
		bitmap( bitmap, false );
	}
	
	public void bitmap( GdxTexture bitmap, boolean premultiplied ) {
		if (premultiplied) {
			super.bitmap( bitmap );
		} else {
			handMade( bitmap, true );
		}
		
		this.bitmap = bitmap;
		width = bitmap.getWidth();
		height = bitmap.getHeight();
	}
	
	public void reload() {
		// FIXME: Not sure if we need to do anything here
//		id = new SmartTexture( bitmap ).id;
		filter( fModeMin, fModeMax );
		wrap( wModeH, wModeV );
	}
	
	@Override
	public void delete() {
		
		super.delete();
		
		bitmap.dispose();
		bitmap = null;
	}
	
	public RectF uvRect( int left, int top, int right, int bottom ) {
		return new RectF(
			(float)left		/ width,
			(float)top		/ height,
			(float)right	/ width,
			(float)bottom	/ height );
	}
}
