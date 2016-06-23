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

package com.watabou.utils;

public class RectF {
	public final float left;
	public final float right;
	public final float top;
	public final float bottom;

	public RectF(float left, float top, float right, float bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}

	public RectF(RectF other) {
		this.left = other.left;
		this.right = other.right;
		this.top = other.top;
		this.bottom = other.bottom;
	}

	public float width() {
		return right - left;
	}

	public float height() {
		return bottom - top;
	}

	public RectF offset(float dx, float dy) {
		return new RectF(left + dx, top + dy, right + dx, bottom + dy);
	}
}
