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

package com.watabou.gdx;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;

public class GdxTexture extends Texture {
	public GdxTexture(String internalPath) {
		super(internalPath);
	}

	public GdxTexture(FileHandle file) {
		super(file);
	}

	public GdxTexture(FileHandle file, boolean useMipMaps) {
		super(file, useMipMaps);
	}

	public GdxTexture(FileHandle file, Pixmap.Format format, boolean useMipMaps) {
		super(file, format, useMipMaps);
	}

	public GdxTexture(Pixmap pixmap) {
		super(pixmap);
	}

	public GdxTexture(Pixmap pixmap, boolean useMipMaps) {
		super(pixmap, useMipMaps);
	}

	public GdxTexture(Pixmap pixmap, Pixmap.Format format, boolean useMipMaps) {
		super(pixmap, format, useMipMaps);
	}

	public GdxTexture(int width, int height, Pixmap.Format format) {
		super(width, height, format);
	}

	public GdxTexture(TextureData data) {
		super(data);
	}
}
