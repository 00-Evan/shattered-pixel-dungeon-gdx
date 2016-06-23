/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.BufferUtils;

import java.nio.IntBuffer;

public class Renderbuffer {

	public static final int RGBA8		= GL20.GL_RGBA;	// ?
	public static final int DEPTH16		= GL20.GL_DEPTH_COMPONENT16;
	public static final int STENCIL8	= GL20.GL_STENCIL_INDEX8;
	
	private int id;
	
	public Renderbuffer() {
		IntBuffer buf = BufferUtils.newIntBuffer(1);
		Gdx.gl.glGenRenderbuffers( 1, buf );
		id = buf.get();
	}
	
	public int id() {
		return id;
	}
	
	public void bind() {
		Gdx.gl.glBindRenderbuffer( GL20.GL_RENDERBUFFER, id );
	}
	
	public void delete() {
		IntBuffer buf = BufferUtils.newIntBuffer(1);
		buf.put(id);
		Gdx.gl.glDeleteRenderbuffers( 1, buf );
	}
	
	public void storage( int format, int width, int height ) {
		Gdx.gl.glRenderbufferStorage( GL20.GL_RENDERBUFFER, format , width, height );
	}
}
