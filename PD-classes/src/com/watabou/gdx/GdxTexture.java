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
