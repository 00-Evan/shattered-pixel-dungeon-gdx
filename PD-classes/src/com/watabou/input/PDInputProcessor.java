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

package com.watabou.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.watabou.utils.PointF;
import com.watabou.utils.Signal;

import java.util.HashMap;

public class PDInputProcessor implements InputProcessor {
	public static Signal<Key> eventKey = new Signal<>(true);
	public static Signal<Touch> eventTouch = new Signal<>(true);
	public static HashMap<Integer, Touch> pointers = new HashMap<>();

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.VOLUME_DOWN || keycode == Input.Keys.VOLUME_UP) {
			return false;
		}
		eventKey.dispatch(new Key(keycode, true));
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Input.Keys.VOLUME_DOWN || keycode == Input.Keys.VOLUME_UP) {
			return false;
		}
		eventKey.dispatch(new Key(keycode, false));
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Touch touch = new Touch(screenX, screenY, pointer);
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
		pointers.get(pointer).update(screenX, screenY, pointer);
		eventTouch.dispatch(null);
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	public static class Key {

		public int code;
		public boolean pressed;

		public Key(int code, boolean pressed) {
			this.code = code;
			this.pressed = pressed;
		}
	}

	public static class Touch {

		public PointF start;
		public PointF current;
		public boolean down;

		public Touch(int x, int y, int index) {
			start = new PointF(x, y);
			current = new PointF(x, y);

			down = true;
		}

		public void update(int x, int y, int index) {
			current.set(x, y);
		}

		public Touch up() {
			down = false;
			return this;
		}
	}
}
