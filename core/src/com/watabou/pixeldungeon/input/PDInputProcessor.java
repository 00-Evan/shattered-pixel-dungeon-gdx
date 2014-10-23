package com.shatteredpixel.shatteredpixeldungeon.input;

import com.badlogic.gdx.Input;
import com.watabou.input.NoosaInputProcessor;

import java.util.Map;

public abstract class PDInputProcessor extends NoosaInputProcessor<GameAction> {
	public static final class GameActionWrapper {
		public final boolean defaultKey;
		public final GameAction gameAction;

		public GameActionWrapper(GameAction gameAction, boolean defaultKey) {
			this.defaultKey = defaultKey;
			this.gameAction = gameAction;
		}
	}

	public Map<Integer, GameActionWrapper> getKeyMappings() {
		return null;
	}

	public void resetKeyMappings() {
	}

	public GameActionWrapper setKeyMapping(GameAction action, boolean defaultKey, int code) {
		return null;
	}

	public GameActionWrapper removeKeyMapping(GameAction action, boolean defaultKey, int code) {
		return null;
	}

	@Override
	protected GameAction keycodeToGameAction(int keycode) {
		switch (keycode) {
			case Input.Keys.BACK:
			case Input.Keys.ESCAPE:
				return GameAction.BACK;
			
		}
		return null;
	}
}
