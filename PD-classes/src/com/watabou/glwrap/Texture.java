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

package com.watabou.glwrap;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.utils.BufferUtils;
import com.watabou.gdx.GdxTexture;

public class Texture {

	public boolean premultiplied = false;
	public GdxTexture bitmap;

	protected Texture(GdxTexture bitmap) {
		this.bitmap = bitmap;
	}

	public static void activate( int index ) {
		Gdx.gl.glActiveTexture( GL20.GL_TEXTURE0 + index );
	}
	
	public void bind() {
		bitmap.bind();
	}
	
	public void filter( TextureFilter minMode, TextureFilter maxMode ) {
		bitmap.setFilter(minMode, maxMode);
	}
	
	public void wrap( TextureWrap u, TextureWrap v ) {
		bitmap.setWrap(u, v);
	}
	
	public void delete() {
		bitmap.dispose();
	}
	
	public void bitmap( GdxTexture bitmap ) {
		this.bitmap = bitmap;

		premultiplied = true;
	}
	
	public void pixels( int w, int h, int[] pixels ) {

		// FIXME
		bind();
		
		IntBuffer imageBuffer = ByteBuffer.
			allocateDirect( w * h * 4 ).
			order( ByteOrder.nativeOrder() ).
			asIntBuffer();
		imageBuffer.put( pixels );
		imageBuffer.position( 0 );
		
		Gdx.gl.glTexImage2D(
			GL20.GL_TEXTURE_2D,
			0,
			GL20.GL_RGBA,
			w,
			h,
			0,
			GL20.GL_RGBA,
			GL20.GL_UNSIGNED_BYTE,
			imageBuffer );
	}
	
	public void pixels( int w, int h, byte[] pixels ) {
		
		bind();
		
		ByteBuffer imageBuffer = ByteBuffer.
			allocateDirect( w * h ).
			order( ByteOrder.nativeOrder() );
		imageBuffer.put( pixels );
		imageBuffer.position( 0 );
		
		Gdx.gl.glPixelStorei( GL20.GL_UNPACK_ALIGNMENT, 1 );

		Gdx.gl.glTexImage2D(
			GL20.GL_TEXTURE_2D,
			0,
			GL20.GL_ALPHA,
			w,
			h,
			0,
			GL20.GL_ALPHA,
			GL20.GL_UNSIGNED_BYTE,
			imageBuffer );
	}
	
	// If getConfig returns null (unsupported format?), GLUtils.texImage2D works
	// incorrectly. In this case we need to load pixels manually
	public void handMade( GdxTexture bitmap, boolean recode ) {

		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		
		int[] pixels = new int[w * h];
		// FIXME
//		bitmap.getPixels( pixels, 0, w, 0, 0, w, h );

		// recode - components reordering is needed
		if (recode) {
			for (int i=0; i < pixels.length; i++) {
				int color = pixels[i];
				int ag = color & 0xFF00FF00;
				int r = (color >> 16) & 0xFF;
				int b = color & 0xFF;
				pixels[i] = ag | (b << 16) | r;
			}
		}
		
		pixels( w, h, pixels );
		
		premultiplied = false;
	}
	
	public static Texture create( GdxTexture bmp ) {
		return new Texture(bmp);
	}
	
	public static Texture create( int width, int height, int[] pixels ) {
		// FIXME
		Texture tex = new Texture(null);
		tex.pixels( width, height, pixels );
		
		return tex;
	}
	
	public static Texture create( int width, int height, byte[] pixels ) {
		// FIXME
		Texture tex = new Texture(null);
		tex.pixels( width, height, pixels );
		
		return tex;
	}
}
