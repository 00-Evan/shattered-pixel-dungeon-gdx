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
package com.watabou.pd.desktop;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.IntMap;
import com.shatteredpixel.shatteredpixeldungeon.Preferences;
import com.shatteredpixel.shatteredpixeldungeon.input.GameAction;
import com.shatteredpixel.shatteredpixeldungeon.input.PDInputProcessor;

import java.util.HashMap;
import java.util.Map;

public class DesktopInputProcessor extends PDInputProcessor {
	public static final String GAMEACTION_PREFIX1 = "ACT1_";
	public static final String GAMEACTION_PREFIX2 = "ACT2_";

	private final Map<Integer, GameActionWrapper> keyMappings = new HashMap<>();

	@Override
	public void init() {
		super.init();

		// Load the default mappings...
		// ...ONLY IF IT'S THE FIRST RUN!
		if (Preferences.INSTANCE.getInt( getPrefKey( GameAction.BACK, true ), 0 ) == 0) {
			resetKeyMappings();
		}

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

		keyMappings.clear();

		for (GameAction action : GameAction.values()) {
			KeyPair pair = DEFAULTS.get( action );
			if (pair != null) {
				setKeyMapping( action, pair.code1, pair.code2 );
			} else {
				pair = new KeyPair();
			}
			Preferences.INSTANCE.put( getPrefKey( action, true ), pair.code1 );
			Preferences.INSTANCE.put( getPrefKey( action, false ), pair.code2 );
		}
	}

	@Override
	public GameActionWrapper setKeyMapping( GameAction action, boolean defaultKey, int code ) {
		final GameActionWrapper existingMapping = keyMappings.get(code);
		keyMappings.put(code, new GameActionWrapper(action, defaultKey));
		Preferences.INSTANCE.put( getPrefKey( action, defaultKey ), code);

		if (existingMapping != null && (existingMapping.gameAction != action || existingMapping.defaultKey != defaultKey)) {
			// If some other action was mapped to this key, then we have
			// to remove a record about it from the preferences
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
		Touch touch = pointers.remove(button);
		if(touch != null) {
			eventTouch.dispatch(touch.up());
			return true;
		}

		return false;
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

	private static void putToPrefs( GameAction action, boolean defaultKey, boolean code ) {
		Preferences.INSTANCE.put( getPrefKey( action, defaultKey ), code );
	}

	private static final HashMap<GameAction, KeyPair> DEFAULTS = new HashMap<>();
	static {
		DEFAULTS.put( GameAction.HERO_INFO, new KeyPair( Input.Keys.H ) );
		DEFAULTS.put( GameAction.CATALOGUS, new KeyPair( Input.Keys.C ) );
		DEFAULTS.put( GameAction.JOURNAL, new KeyPair( Input.Keys.J ) );

		DEFAULTS.put( GameAction.REST, new KeyPair( Input.Keys.SPACE, Input.Keys.NUMPAD_5 ) );
		DEFAULTS.put( GameAction.SEARCH, new KeyPair( Input.Keys.S ) );
		DEFAULTS.put( GameAction.RESUME, new KeyPair( Input.Keys.T ) );

		DEFAULTS.put( GameAction.BACKPACK, new KeyPair( Input.Keys.I ) );
		DEFAULTS.put( GameAction.QUICKSLOT_1, new KeyPair( Input.Keys.Q ) );
		DEFAULTS.put( GameAction.QUICKSLOT_2, new KeyPair( Input.Keys.W ) );
		DEFAULTS.put( GameAction.QUICKSLOT_3, new KeyPair( Input.Keys.E ) );
		DEFAULTS.put( GameAction.QUICKSLOT_4, new KeyPair( Input.Keys.R ) );

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

		public KeyPair() {
			code1 = -1;
			code2 = -1;
		}
	}
}
