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

package com.shatteredpixel.shatteredpixeldungeon.ui;

import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;

//FIXME: currently this just wraps HighlightedText, correct this when RenderedText is fixed
public class RenderedTextMultiline extends HighlightedText {

	private int maxWidth = Integer.MAX_VALUE;

	private String text;

	public int nLines;

	private float zoom;
	private int color = -1;

	public RenderedTextMultiline(int size){
		super(size);
	}

	public RenderedTextMultiline(String text, int size){
		super(size);
		text(text);
	}

	public void text(String text){
		text(text, maxWidth);
	}

	public void text(String text, int maxWidth){
		this.text = text;
		this.maxWidth = maxWidth;
		super.text(text, maxWidth);
		PixelScene.align(normal);
		PixelScene.align(highlighted);
		nLines = normal.nLines;
	}

	public String text(){
		return text;
	}

	public void maxWidth(int maxWidth){
		if (this.maxWidth != maxWidth){
			this.maxWidth = maxWidth;
			text(text, maxWidth);
		}
	}

	public int maxWidth(){
		return maxWidth;
	}

	public void zoom(float zoom){
		this.zoom = zoom;
		normal.scale.set(zoom);
		highlighted.scale.set(zoom);
	}

	public void hardlight(int color){
		this.color = color;
		setColor(color, color);
	}

	public void invert(){
		normal.ra = highlighted.ra = 0.77f;
		normal.ga = highlighted.ga = 0.73f;
		normal.ba = highlighted.ba = 0.62f;
		normal.rm = highlighted.rm = -0.77f;
		normal.gm = highlighted.gm = -0.73f;
		normal.bm = highlighted.bm = -0.62f;
	}

}
