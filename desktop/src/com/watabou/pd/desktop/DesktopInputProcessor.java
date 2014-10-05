package com.watabou.pd.desktop;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.IntMap;
import com.watabou.pixeldungeon.Preferences;
import com.watabou.pixeldungeon.input.GameAction;
import com.watabou.pixeldungeon.input.PDInputProcessor;

import java.util.HashMap;
import java.util.Map;

public class DesktopInputProcessor extends PDInputProcessor {
	public static final String GAMEACTION_PREFIX1 = "ACT1_";
	public static final String GAMEACTION_PREFIX2 = "ACT2_";

	private final Map<Integer, GameActionWrapper> keyMappings = new HashMap<>();

	@Override
	public void init() {
		super.init();

		// Load the default mappings
		resetKeyMappings();

		loadKeyMapping( GameAction.HERO_INFO );
		loadKeyMapping( GameAction.CATALOGUS );
		loadKeyMapping( GameAction.JOURNAL );

		loadKeyMapping( GameAction.REST );
		loadKeyMapping( GameAction.SEARCH );
		loadKeyMapping( GameAction.CELL_INFO );
		loadKeyMapping( GameAction.RESUME );

		loadKeyMapping( GameAction.BACKPACK );
		loadKeyMapping( GameAction.QUICKSLOT );

		loadKeyMapping( GameAction.TAG_ATTACK );
		loadKeyMapping( GameAction.TAG_DANGER );

		loadKeyMapping( GameAction.ZOOM_IN );
		loadKeyMapping( GameAction.ZOOM_OUT );
		loadKeyMapping( GameAction.ZOOM_DEFAULT );

		loadKeyMapping( GameAction.MOVE_UP );
		loadKeyMapping( GameAction.MOVE_DOWN );
		loadKeyMapping( GameAction.MOVE_LEFT );
		loadKeyMapping( GameAction.MOVE_RIGHT );

		loadKeyMapping( GameAction.MOVE_TOP_LEFT );
		loadKeyMapping( GameAction.MOVE_TOP_RIGHT );
		loadKeyMapping( GameAction.MOVE_BOTTOM_LEFT );
		loadKeyMapping( GameAction.MOVE_BOTTOM_RIGHT );

		loadKeyMapping( GameAction.OPERATE );
	}

	private void loadKeyMapping( GameAction action ) {
		/*
		 * Right now we store key mapping preferences on the default game preferences file/registry/whatever. It will probably
		 * be a good idea to have a separate one for this
		 */
		int mapping1 = Preferences.INSTANCE.getInt( GAMEACTION_PREFIX1 + action, -1 );
		if (mapping1 > 0) {
			keyMappings.put( mapping1, new GameActionWrapper( action, true ) );
		}

		int mapping2 = Preferences.INSTANCE.getInt (GAMEACTION_PREFIX2 + action, -1 );
		if (mapping2 > 0) {
			keyMappings.put( mapping2, new GameActionWrapper( action, false ) );
		}
	}

	@Override
	public Map<Integer, GameActionWrapper> getKeyMappings() {
		return keyMappings;
	}

	@Override
	public void resetKeyMappings() {
		setKeyMapping( GameAction.HERO_INFO, Input.Keys.H );
		setKeyMapping( GameAction.CATALOGUS, Input.Keys.C );
		setKeyMapping( GameAction.JOURNAL, Input.Keys.J );

		setKeyMapping( GameAction.REST, Input.Keys.SPACE );
		setKeyMapping( GameAction.SEARCH, Input.Keys.S );
		setKeyMapping( GameAction.CELL_INFO, Input.Keys.V );
		setKeyMapping (GameAction.RESUME, Input.Keys.R );

		setKeyMapping( GameAction.BACKPACK, Input.Keys.I );
		setKeyMapping( GameAction.QUICKSLOT, Input.Keys.Q );

		setKeyMapping( GameAction.TAG_ATTACK, Input.Keys.A );
		setKeyMapping( GameAction.TAG_DANGER, Input.Keys.TAB );

		setKeyMapping( GameAction.ZOOM_IN, Input.Keys.PLUS, Input.Keys.EQUALS );
		setKeyMapping( GameAction.ZOOM_OUT, Input.Keys.MINUS );
		setKeyMapping( GameAction.ZOOM_DEFAULT, Input.Keys.SLASH );

		setKeyMapping( GameAction.MOVE_UP, Input.Keys.UP, Input.Keys.NUMPAD_8 );
		setKeyMapping( GameAction.MOVE_DOWN, Input.Keys.DOWN, Input.Keys.NUMPAD_2 );
		setKeyMapping( GameAction.MOVE_LEFT, Input.Keys.LEFT, Input.Keys.NUMPAD_4 );
		setKeyMapping( GameAction.MOVE_RIGHT, Input.Keys.RIGHT, Input.Keys.NUMPAD_6 );

		setKeyMapping( GameAction.MOVE_TOP_LEFT, Input.Keys.NUMPAD_7 );
		setKeyMapping( GameAction.MOVE_TOP_RIGHT, Input.Keys.NUMPAD_9 );
		setKeyMapping( GameAction.MOVE_BOTTOM_LEFT, Input.Keys.NUMPAD_1 );
		setKeyMapping( GameAction.MOVE_BOTTOM_RIGHT, Input.Keys.NUMPAD_3 );

		setKeyMapping( GameAction.OPERATE, Input.Keys.ENTER );
	}

	@Override
	public GameActionWrapper setKeyMapping(GameAction action, boolean defaultKey, int code) {
		final GameActionWrapper existingMapping = keyMappings.get(code);
		keyMappings.put(code, new GameActionWrapper(action, defaultKey));
		Preferences.INSTANCE.put((defaultKey ? GAMEACTION_PREFIX1 : GAMEACTION_PREFIX2) + action, code);

		// Return a "replaced" object only if it's not the same action and default key that we are remapping
		return existingMapping != null && (existingMapping.gameAction != action || existingMapping.defaultKey != defaultKey)
				? existingMapping
				: null;
	}

	public void setKeyMapping( GameAction action, int code1, int code2 ) {
		if (code1 > 0) {
			setKeyMapping( action, true, code1 );
		} else {
			removeKeyMapping( action, true, code1 );
		}
		if (code2 > 0) {
			setKeyMapping( action, false, code2 );
		} else {
			removeKeyMapping( action, false, code2 );
		}
	}

	public void setKeyMapping( GameAction action, int code ) {
		setKeyMapping( action, code, 0 );
	}

	@Override
	public GameActionWrapper removeKeyMapping(GameAction action, boolean defaultKey, int code) {
		final GameActionWrapper result = keyMappings.remove(code);
		Preferences.INSTANCE.put((defaultKey ? GAMEACTION_PREFIX1 : GAMEACTION_PREFIX2) + action, -1);
		return result;
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
		if (keyMappings.containsKey(keycode))
			return keyMappings.get(keycode).gameAction;
		return GameAction.UNKNOWN;
	}
}
