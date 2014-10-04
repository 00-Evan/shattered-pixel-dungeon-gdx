package com.watabou.pd.desktop;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.IntMap;
import com.watabou.pixeldungeon.Preferences;
import com.watabou.pixeldungeon.input.GameAction;
import com.watabou.pixeldungeon.input.PDInputProcessor;

public class DesktopInputProcessor extends PDInputProcessor {
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Touch touch = new Touch(screenX, screenY);
		pointers.put(button, touch);
		eventTouch.dispatch(touch);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		eventTouch.dispatch(pointers.remove(button).up());
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		for (IntMap.Entry<Touch> entry : pointers) {
			entry.value.update(screenX, screenY);
		}
		eventTouch.dispatch(null);
		return true;
	}

	@Override
	protected GameAction keycodeToGameAction(int keycode) {
		final GameAction defaultResult = super.keycodeToGameAction(keycode);
		if (defaultResult != null) {
			return defaultResult;
		}
		String enumValue = Preferences.INSTANCE.getString(Integer.toString(keycode), null);
		if (enumValue != null) {
			try {
				return GameAction.valueOf(enumValue);
			} catch (Exception ignored) {}
		}
		switch (keycode) {
			case Input.Keys.SPACE:
				return GameAction.REST;
			case Input.Keys.A:
				return GameAction.TAG_ATTACK;
			case Input.Keys.C:
				return GameAction.CATALOGUS;
			case Input.Keys.H:
				return GameAction.HERO_INFO;
			case Input.Keys.TAB:
				return GameAction.TAG_DANGER;
			case Input.Keys.I:
				return GameAction.BACKPACK;
			case Input.Keys.J:
				return GameAction.JOURNAL;
			case Input.Keys.Q:
				return GameAction.QUICKSLOT;
			case Input.Keys.R:
				return GameAction.RESUME;
			case Input.Keys.S:
				return GameAction.SEARCH;
			case Input.Keys.V:
				return GameAction.INFO;

			case Input.Keys.PLUS:
			case Input.Keys.EQUALS:
				return GameAction.ZOOM_IN;
			case Input.Keys.MINUS:
				return GameAction.ZOOM_OUT;
			case Input.Keys.SLASH:
				return GameAction.ZOOM_DEFAULT;

			case Input.Keys.UP:
			case Input.Keys.NUMPAD_8:
				return GameAction.MOVE_UP;
			case Input.Keys.DOWN:
			case Input.Keys.NUMPAD_2:
				return GameAction.MOVE_DOWN;
			case Input.Keys.LEFT:
			case Input.Keys.NUMPAD_4:
				return GameAction.MOVE_LEFT;
			case Input.Keys.RIGHT:
			case Input.Keys.NUMPAD_6:
				return GameAction.MOVE_RIGHT;
			case Input.Keys.NUMPAD_7:
				return GameAction.MOVE_TOP_LEFT;
			case Input.Keys.NUMPAD_9:
				return GameAction.MOVE_TOP_RIGHT;
			case Input.Keys.NUMPAD_1:
				return GameAction.MOVE_BOTTOM_LEFT;
			case Input.Keys.NUMPAD_3:
				return GameAction.MOVE_BOTTOM_RIGHT;
			case Input.Keys.ENTER:
				return GameAction.WAIT;
		}
		return null;
	}
}
