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

public class Framebuffer {

	public static final int COLOR	= GL20.GL_COLOR_ATTACHMENT0;
	public static final int DEPTH	= GL20.GL_DEPTH_ATTACHMENT;
	public static final int STENCIL	= GL20.GL_STENCIL_ATTACHMENT;
	
	public static final Framebuffer	system	= new Framebuffer( 0 );
	
	private int id;
	
	public Framebuffer() {
		IntBuffer buf = BufferUtils.newIntBuffer(1);
		Gdx.gl.glGenBuffers( 1, buf );
		id = buf.get();
	}
	
	private Framebuffer( int n ) {
		
	}
	
	public void bind() {
		Gdx.gl.glBindFramebuffer( GL20.GL_FRAMEBUFFER, id );
	}
	
	public void delete() {
		IntBuffer buf = BufferUtils.newIntBuffer(1);
		buf.put(id);
		Gdx.gl.glDeleteFramebuffers( 1, buf );
	}
	
	/*public void attach( int point, Texture tex ) {
		bind();
		Gdx.gl.glFramebufferTexture2D( GL20.GL_FRAMEBUFFER, point, GL20.GL_TEXTURE_2D, tex.id, 0 );
	}
	
	public void attach( int point, Renderbuffer buffer ) {
		bind();
		Gdx.gl.glFramebufferRenderbuffer( GL20.GL_RENDERBUFFER, point, GL20.GL_TEXTURE_2D, buffer.id() );
	}*/
	
	public boolean status() {
		bind();
		return Gdx.gl.glCheckFramebufferStatus( GL20.GL_FRAMEBUFFER ) == GL20.GL_FRAMEBUFFER_COMPLETE;
	}
}
