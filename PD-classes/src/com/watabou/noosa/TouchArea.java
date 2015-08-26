/*
 * Copyright (C) 2012-2015  Oleg Dolya
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

import com.watabou.input.NoosaInputProcessor;
import com.watabou.utils.Signal;

public class TouchArea<T> extends Visual implements Signal.Listener<NoosaInputProcessor.Touch> {
	
	// Its target can be toucharea itself
	public final Visual target;
	
	protected NoosaInputProcessor.Touch touch = null;

	private Signal.Listener<NoosaInputProcessor.Key<T>> keyListener = new Signal.Listener<NoosaInputProcessor.Key<T>>() {
		@Override
		public void onSignal(NoosaInputProcessor.Key<T> key) {
			final boolean handled;

			if (key.pressed) {
				handled = onKeyDown(key);
			} else {
				handled = onKeyUp(key);
			}

			if (handled) {
				Game.instance.getInputProcessor().cancelKeyEvent();
			}
		}
	};

	private Signal.Listener<NoosaInputProcessor.PDMouseEvent> mouseListener = new Signal.Listener<NoosaInputProcessor.PDMouseEvent>() {
		@Override
		public void onSignal(NoosaInputProcessor.PDMouseEvent event) {
			final boolean handled;

			handled = onMouseScroll(event.scroll);

			if (handled) {
				Game.instance.getInputProcessor().cancelMouseEvent();
			}
		}
	};

	public boolean onMouseScroll(int scroll) {
		return false;
	}

	public boolean onKeyDown(NoosaInputProcessor.Key<T> key) {
		return false;
	}

	public boolean onKeyUp(NoosaInputProcessor.Key<T> key) {
		return false;
	}

	public TouchArea( Visual target ) {
		super( 0, 0, 0, 0 );
		this.target = target;

		setupListeners();
	}

	public TouchArea( float x, float y, float width, float height ) {
		super(x, y, width, height);
		this.target = this;

		visible = false;

		setupListeners();
	}

	private void setupListeners() {
		NoosaInputProcessor<T> ip = Game.instance.<T>getInputProcessor();
		ip.addTouchListener(this);
		ip.addKeyListener(keyListener);
		ip.addMouseListener(mouseListener);
	}

	@Override
	public void onSignal( NoosaInputProcessor.Touch touch ) {
		
		if (!isActive()) {
			return;
		}
		
		boolean hit = touch != null && target.overlapsScreenPoint( (int)touch.start.x, (int)touch.start.y );
		
		if (hit) {

			Game.instance.getInputProcessor().cancelTouchEvent();
			
			if (touch.down) {
				
				if (this.touch == null) {
					this.touch = touch;
				}
				onTouchDown( touch );
				
			} else {
				
				onTouchUp( touch );
				
				if (this.touch == touch) {
					this.touch = null;
					onClick( touch );
				}

			}
			
		} else {
			
			if (touch == null && this.touch != null) {
				onDrag( this.touch );
			}
			
			else if (this.touch != null && touch != null && !touch.down) {
				onTouchUp( touch );
				this.touch = null;
			}
			
		}
	}
	
	protected void onTouchDown( NoosaInputProcessor.Touch touch ) {
	}
	
	protected void onTouchUp( NoosaInputProcessor.Touch touch ) {
	}
	
	protected void onClick( NoosaInputProcessor.Touch touch ) {
	}
	
	protected void onDrag( NoosaInputProcessor.Touch touch ) {
	}
	
	public void reset() {
		touch = null;
	}
	
	@Override
	public void destroy() {
		NoosaInputProcessor<T> ip = Game.instance.<T>getInputProcessor();
		ip.removeMouseListener(mouseListener);
		ip.removeKeyListener(keyListener);
		ip.removeTouchListener(this);
		super.destroy();
	}
}
