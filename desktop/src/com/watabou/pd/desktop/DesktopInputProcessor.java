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
		resetToDefaults();

		for (GameAction action : GameAction.values()) {
			loadKeyMapping( action );
		}
	}

	private void loadKeyMapping( GameAction action ) {
		/*
		 * Right now we store key mapping preferences on the default game preferences file/registry/whatever. It will probably
		 * be a good idea to have a separate one for this
		 */
		int mapping1 = Preferences.INSTANCE.getInt( getPrefKey( action, true ), -1 );
		if (mapping1 > 0) {
			System.out.println( Input.Keys.toString( mapping1 ));
			keyMappings.put( mapping1, new GameActionWrapper( action, true ) );
		}

		int mapping2 = Preferences.INSTANCE.getInt( getPrefKey( action, false ), -1 );
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
		resetToDefaults();

		for (GameAction action : GameAction.values()) {
			Preferences.INSTANCE.put( getPrefKey( action, true ), -1 );
			Preferences.INSTANCE.put( getPrefKey( action, false ), -1 );
		}
		// No need to save default bindings, cause next time
		// they will be loaded anyway
	}

	private void resetToDefaults() {

		keyMappings.clear();

		for (GameAction action : DEFAULTS.keySet()) {
			KeyPair pair = DEFAULTS.get( action );
			setKeyMapping( action, pair.code1, pair.code2 );
		}
	}

	@Override
	public GameActionWrapper setKeyMapping(GameAction action, boolean defaultKey, int code) {
		final GameActionWrapper existingMapping = keyMappings.get(code);
		keyMappings.put(code, new GameActionWrapper(action, defaultKey));
		Preferences.INSTANCE.put( getPrefKey( action, defaultKey ), code);

		// Return a "replaced" object only if it's not the same action and default key that we are remapping
	/*	return existingMapping != null && (existingMapping.gameAction != action || existingMapping.defaultKey != defaultKey)
				? existingMapping
				: null;*/
		if (existingMapping != null && (existingMapping.gameAction != action || existingMapping.defaultKey != defaultKey)) {
			Preferences.INSTANCE.put( getPrefKey( existingMapping.gameAction, existingMapping.defaultKey ), -1);
			return existingMapping;
		} else {
			return null;
		}
	}

	public void setKeyMapping( GameAction action, int code1, int code2 ) {
		if (code1 > 0) {
			keyMappings.put( code1, new GameActionWrapper( action, true ) );
		} else {
			keyMappings.remove( code1 );
		}
		if (code2 > 0) {
			keyMappings.put( code2, new GameActionWrapper( action, false ) );
		} else {
			keyMappings.remove( code2 );
		}
	}

	public void setKeyMapping( GameAction action, int code ) {
		setKeyMapping( action, code, 0 );
	}

	@Override
	public GameActionWrapper removeKeyMapping(GameAction action, boolean defaultKey, int code) {
		final GameActionWrapper result = keyMappings.remove(code);
		Preferences.INSTANCE.put( getPrefKey( action, defaultKey ), -1);
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

	private static String getPrefKey( GameAction action, boolean defaultKey ) {
		return (defaultKey ? GAMEACTION_PREFIX1 : GAMEACTION_PREFIX2) + action;
	}

	private static final HashMap<GameAction, KeyPair> DEFAULTS = new HashMap<>();
	static {
		DEFAULTS.put( GameAction.HERO_INFO, new KeyPair( Input.Keys.H ) );
		DEFAULTS.put( GameAction.CATALOGUS, new KeyPair( Input.Keys.C ) );
		DEFAULTS.put( GameAction.JOURNAL, new KeyPair( Input.Keys.J ) );

		DEFAULTS.put( GameAction.REST, new KeyPair( Input.Keys.SPACE ) );
		DEFAULTS.put( GameAction.SEARCH, new KeyPair( Input.Keys.S ) );
		DEFAULTS.put( GameAction.CELL_INFO, new KeyPair( Input.Keys.V ) );
		DEFAULTS.put(GameAction.RESUME, new KeyPair( Input.Keys.R ) );

		DEFAULTS.put( GameAction.BACKPACK, new KeyPair( Input.Keys.I ) );
		DEFAULTS.put( GameAction.QUICKSLOT, new KeyPair( Input.Keys.Q ) );

		DEFAULTS.put( GameAction.TAG_ATTACK, new KeyPair( Input.Keys.A ) );
		DEFAULTS.put( GameAction.TAG_DANGER, new KeyPair( Input.Keys.TAB ) );

		DEFAULTS.put( GameAction.ZOOM_IN, new KeyPair( Input.Keys.PLUS, Input.Keys.EQUALS ) );
		DEFAULTS.put( GameAction.ZOOM_OUT, new KeyPair( Input.Keys.MINUS ) );
		DEFAULTS.put( GameAction.ZOOM_DEFAULT, new KeyPair( Input.Keys.SLASH ) );

		DEFAULTS.put( GameAction.MOVE_UP, new KeyPair( Input.Keys.UP, Input.Keys.NUMPAD_8 ) );
		DEFAULTS.put( GameAction.MOVE_DOWN, new KeyPair( Input.Keys.DOWN, Input.Keys.NUMPAD_2 ) );
		DEFAULTS.put( GameAction.MOVE_LEFT, new KeyPair( Input.Keys.LEFT, Input.Keys.NUMPAD_4 ) );
		DEFAULTS.put( GameAction.MOVE_RIGHT, new KeyPair( Input.Keys.RIGHT, Input.Keys.NUMPAD_6 ) );

		DEFAULTS.put( GameAction.MOVE_TOP_LEFT, new KeyPair( Input.Keys.NUMPAD_7 ) );
		DEFAULTS.put( GameAction.MOVE_TOP_RIGHT, new KeyPair( Input.Keys.NUMPAD_9 ) );
		DEFAULTS.put( GameAction.MOVE_BOTTOM_LEFT, new KeyPair( Input.Keys.NUMPAD_1 ) );
		DEFAULTS.put( GameAction.MOVE_BOTTOM_RIGHT, new KeyPair( Input.Keys.NUMPAD_3 ) );

		DEFAULTS.put( GameAction.OPERATE, new KeyPair( Input.Keys.ENTER ) );
	};

	private static class KeyPair {

		public int code1;
		public int code2;

		public KeyPair( int code1, int code2 ) {
			this.code1 = code1;
			this.code2 = code2;
		}

		public KeyPair( int code ) {
			this( code, -1 );
		}
	}
}
