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

package com.watabou.noosa;

import com.badlogic.gdx.Input;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.utils.Signal;

public class Scene extends Group {
	
	private Signal.Listener<NoosaInputProcessor.Key> keyListener;
	
	public void create() {
		Game.instance.getInputProcessor().addKeyListener(keyListener = new Signal.Listener<NoosaInputProcessor.Key>() {
			@Override
			public void onSignal(NoosaInputProcessor.Key key) {
				if (Game.instance != null && key.pressed) {
					switch (key.code) {
						case Input.Keys.BACK:
						case Input.Keys.ESCAPE:
							onBackPressed();
							break;
						case Input.Keys.MENU:
						case Input.Keys.F5:
							onMenuPressed();
							break;
					}
				}
			}
		});
	}
	
	@Override
	public void destroy() {
		Game.instance.getInputProcessor().removeKeyListener(keyListener);
		super.destroy();
	}
	
	public void pause() {
		
	}
	
	public void resume() {
		
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	@Override
	public Camera camera() {
		return Camera.main;
	}
	
	protected void onBackPressed() {
		Game.instance.finish();
	}
	
	protected void onMenuPressed() {
		
	}

}
