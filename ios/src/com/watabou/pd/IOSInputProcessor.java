/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
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
package com.watabou.pd;

import com.shatteredpixel.shatteredpixeldungeon.input.PDInputProcessor;

public class IOSInputProcessor extends PDInputProcessor {
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Touch touch = new Touch(screenX, screenY);
		pointers.put(pointer, touch);
		eventTouch.dispatch(touch);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		eventTouch.dispatch(pointers.remove(pointer).up());
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		pointers.get(pointer).update(screenX, screenY);
		eventTouch.dispatch(null);
		return true;
	}
}
