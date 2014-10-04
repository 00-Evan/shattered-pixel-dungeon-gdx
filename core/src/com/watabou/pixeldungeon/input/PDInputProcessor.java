package com.watabou.pixeldungeon.input;

import com.badlogic.gdx.Input;
import com.watabou.input.NoosaInputProcessor;

public abstract class PDInputProcessor extends NoosaInputProcessor<GameAction> {
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
