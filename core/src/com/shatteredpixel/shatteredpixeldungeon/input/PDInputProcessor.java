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
