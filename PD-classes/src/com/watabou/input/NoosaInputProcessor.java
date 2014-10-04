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
import com.badlogic.gdx.utils.IntMap;
import com.watabou.utils.PointF;
import com.watabou.utils.Signal;

public abstract class NoosaInputProcessor<T> implements InputProcessor {
	protected Signal<Key<T>> eventKey = new Signal<>(true);
	protected Signal<Touch> eventTouch = new Signal<>(true);
	protected Signal<PDMouseEvent> eventMouse = new Signal<>(true);
	protected IntMap<Touch> pointers = new IntMap<>();
	
	public static final int MODIFIER_KEY    = Input.Keys.CONTROL_LEFT;
	
	public static boolean modifier = false;

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		
		case Input.Keys.VOLUME_DOWN:
		case Input.Keys.VOLUME_UP:
			return false;
			
		case MODIFIER_KEY:
			modifier = true;
			
		default:
			eventKey.dispatch( new Key<>(keycode, keycodeToGameAction(keycode), true ) );
			return true;
		}
	}

	protected abstract T keycodeToGameAction(int keycode);

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		
		case Input.Keys.VOLUME_DOWN:
		case Input.Keys.VOLUME_UP:
			return false;
			
		case MODIFIER_KEY:
			modifier = false;
			
		default:
			eventKey.dispatch( new Key<>(keycode, keycodeToGameAction(keycode), false ) );
			return true;
		}
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		eventMouse.dispatch(new PDMouseEvent(amount));
		return true;
	}
	
	public void addKeyListener(Signal.Listener<Key<T>> listener) {
		eventKey.add(listener);
	}

	public void removeKeyListener(Signal.Listener<Key<T>> listener) {
		eventKey.remove(listener);
	}
	
	public void addTouchListener(Signal.Listener<Touch> listener) {
		eventTouch.add(listener);
	}

	public void removeTouchListener(Signal.Listener<Touch> listener) {
		eventTouch.remove(listener);
	}
	
	public void addMouseListener(Signal.Listener<PDMouseEvent> listener) {
		eventMouse.add(listener);
	}

	public void removeMouseListener(Signal.Listener<PDMouseEvent> listener) {
		eventMouse.remove(listener);
	}

	public void cancelKeyEvent() {
		eventKey.cancel();
	}

	public void cancelTouchEvent() {
		eventTouch.cancel();
	}

	public void cancelMouseEvent() {
		eventMouse.cancel();
	}

	public void removeAllKeyEvent() {
		eventKey.removeAll();
	}

	public void removeAllTouchEvent() {
		eventTouch.removeAll();
	}

	public void removeAllMouseEvent() {
	eventMouse.removeAll();
	}

	public static class PDMouseEvent {
		// TODO: This should probably contain the position of the mouse as well to be used by 'mouseMoved'
		public final int scroll;

		public PDMouseEvent(int scroll) {
			this.scroll = scroll;
		}
	}

	public static class Key<T> {
		// FIXME: This is only here to support reading the key from PD-Classes, but that should also be abstracted and this removed
		public final int code;
		public final T action;
		public final boolean pressed;

		public Key(int code, T action, boolean pressed) {
			this.code = code;
			this.action = action;
			this.pressed = pressed;
		}
	}

	public static class Touch {

		public PointF start;
		public PointF current;
		public boolean down;

		public Touch(int x, int y) {
			start = new PointF(x, y);
			current = new PointF(x, y);

			down = true;
		}

		public void update(int x, int y) {
			current.set(x, y);
		}

		public Touch up() {
			down = false;
			return this;
		}
	}
}
