package com.watabou.pd.desktop;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.IntMap;
import com.watabou.pixeldungeon.Preferences;
import com.watabou.pixeldungeon.input.GameAction;
import com.watabou.pixeldungeon.input.PDInputProcessor;

import java.util.HashMap;
import java.util.Map;

public class DesktopInputProcessor extends PDInputProcessor {
	public static final String GAMEACTION_PREFIX = "ACT_";
	public static final String GAMEACTION_PREFIX2 = "ACT2_";

	private static final class GameActionWrapper {
		public final boolean defaultKey;
		public final GameAction gameAction;

		private GameActionWrapper(GameAction gameAction, boolean defaultKey) {
			this.defaultKey = defaultKey;
			this.gameAction = gameAction;
		}
	}

	private final Map<Integer, GameActionWrapper> keyMapping = new HashMap<>();

	@Override
	public void init() {
		super.init();

		// Load the default mappings
//		loadKeyMapping(GameAction.BACK, Input.Keys.ESCAPE);
		loadKeyMapping(GameAction.BACKPACK, Input.Keys.I);
		loadKeyMapping(GameAction.CATALOGUS, Input.Keys.C);
		loadKeyMapping(GameAction.CELL_INFO, Input.Keys.V);
		loadKeyMapping(GameAction.HERO_INFO, Input.Keys.H);
		loadKeyMapping(GameAction.JOURNAL, Input.Keys.J);
//		loadKeyMapping(GameAction.MENU, Input.Keys.F5);
		loadKeyMapping(GameAction.QUICKSLOT, Input.Keys.Q);
		loadKeyMapping(GameAction.REST, Input.Keys.SPACE);
		loadKeyMapping(GameAction.RESUME, Input.Keys.R);
		loadKeyMapping(GameAction.SEARCH, Input.Keys.S);
		loadKeyMapping(GameAction.TAG_ATTACK, Input.Keys.A);
		loadKeyMapping(GameAction.TAG_DANGER, Input.Keys.TAB);
		loadKeyMapping(GameAction.WAIT, Input.Keys.ENTER);

		loadKeyMapping(GameAction.ZOOM_DEFAULT, Input.Keys.SLASH);
		loadKeyMapping(GameAction.ZOOM_IN, Input.Keys.PLUS, Input.Keys.EQUALS);
		loadKeyMapping(GameAction.ZOOM_OUT, Input.Keys.MINUS);

		loadKeyMapping(GameAction.MOVE_UP, Input.Keys.UP, Input.Keys.NUMPAD_8);
		loadKeyMapping(GameAction.MOVE_DOWN, Input.Keys.DOWN, Input.Keys.NUMPAD_2);
		loadKeyMapping(GameAction.MOVE_LEFT, Input.Keys.LEFT, Input.Keys.NUMPAD_4);
		loadKeyMapping(GameAction.MOVE_RIGHT, Input.Keys.RIGHT, Input.Keys.NUMPAD_6);

		loadKeyMapping(GameAction.MOVE_TOP_LEFT, Input.Keys.NUMPAD_7);
		loadKeyMapping(GameAction.MOVE_TOP_RIGHT, Input.Keys.NUMPAD_9);
		loadKeyMapping(GameAction.MOVE_BOTTOM_LEFT, Input.Keys.NUMPAD_1);
		loadKeyMapping(GameAction.MOVE_BOTTOM_RIGHT, Input.Keys.NUMPAD_3);
	}

	private void loadKeyMapping(GameAction action, int defaultKey) {
		loadKeyMapping(action, defaultKey, null);
	}

	private void loadKeyMapping(GameAction action, int defaultKey1, Integer defaultKey2) {
		int mapping = Preferences.INSTANCE.getInt(GAMEACTION_PREFIX + action, -1);
		keyMapping.put(mapping > 0 ? mapping : defaultKey1, new GameActionWrapper(action, true));

		int mapping2 = Preferences.INSTANCE.getInt(GAMEACTION_PREFIX2 + action, -1);
		if (mapping2 > 0 || defaultKey2 != null) {
			keyMapping.put(mapping2 > 0 ? mapping2 : defaultKey2, new GameActionWrapper(action, false));
		}
	}

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
		if (keyMapping.containsKey(keycode))
			return keyMapping.get(keycode).gameAction;
		return GameAction.UNKNOWN;
	}
}
