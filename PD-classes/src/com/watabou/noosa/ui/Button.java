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
package com.watabou.noosa.ui;

import com.watabou.input.NoosaInputProcessor;
import com.watabou.noosa.Game;
import com.watabou.noosa.TouchArea;

public class Button<T> extends Component {

	public static float longClick = 1f;
	
	protected TouchArea hotArea;

	protected boolean pressed;
	protected float pressTime;
	
	protected boolean processed;

	public T hotKey = null;

	@Override
	protected void createChildren() {
		hotArea = new TouchArea<T>( 0, 0, 0, 0 ) {
			@Override
			protected void onTouchDown(NoosaInputProcessor.Touch touch) {
				pressed = true;
				pressTime = 0;
				processed = false;
				Button.this.onTouchDown();
			};
			@Override
			protected void onTouchUp(NoosaInputProcessor.Touch touch) {
				pressed = false;
				Button.this.onTouchUp();
			};
			@Override
			protected void onClick( NoosaInputProcessor.Touch touch ) {
				if (!processed) {
					Button.this.onClick();
				}
			};
			@Override
			public boolean onKeyDown(NoosaInputProcessor.Key<T> key) {
				return Button.this.onKeyDown(key);
			}
			@Override
			public boolean onKeyUp(NoosaInputProcessor.Key<T> key) {
				return Button.this.onKeyUp(key);
			}
		};
		add( hotArea );
	}
	
	@Override
	public void update() {
		super.update();
		
		hotArea.active = visible;
		
		if (pressed) {
			if ((pressTime += Game.elapsed) >= longClick) {
				pressed = false;
				if (onLongClick()) {

					hotArea.reset();
					processed = true;
					onTouchUp();
					
					Game.vibrate( 50 );
				}
			}
		}
	}
	
	protected void onTouchDown() {};
	protected void onTouchUp() {};
	protected void onClick() {};

    protected boolean onLongClick() {
        return false;
    };

	protected boolean onKeyDown(NoosaInputProcessor.Key<T> key) {
		return false;
	}
	protected boolean onKeyUp(NoosaInputProcessor.Key<T> key) {
		if (active && hotKey != null && key.action.equals(hotKey)) {
            if (NoosaInputProcessor.modifier) {
                return onLongClick();
            } else {
                onClick();
                return true;
            }
        } else {
            return false;
        }
	}
	
	@Override
	protected void layout() {
		hotArea.x = x;
		hotArea.y = y;
		hotArea.width = width;
		hotArea.height = height;
	}
}
