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

import com.watabou.input.PDInputProcessor;
import com.watabou.utils.Signal;

public class TouchArea extends Visual implements Signal.Listener<PDInputProcessor.Touch> {
	
	// Its target can be toucharea itself
	public Visual target;
	
	protected PDInputProcessor.Touch touch = null;

	private Signal.Listener<PDInputProcessor.Key> keyListener = new Signal.Listener<PDInputProcessor.Key>() {
		@Override
		public void onSignal(PDInputProcessor.Key key) {
			final boolean handled;

			if (key.pressed) {
				handled = onKeyDown(key);
			} else {
				handled = onKeyUp(key);
			}

			if (handled) {
				PDInputProcessor.eventKey.cancel();
			}
		}
	};

	public boolean onKeyDown(PDInputProcessor.Key key) {
		return false;
	}

	public boolean onKeyUp(PDInputProcessor.Key key) {
		return false;
	}

	public TouchArea( Visual target ) {
		super( 0, 0, 0, 0 );
		this.target = target;

		PDInputProcessor.eventTouch.add( this );
		PDInputProcessor.eventKey.add( keyListener );
	}
	
	public TouchArea( float x, float y, float width, float height ) {
		super( x, y, width, height );
		this.target = this;
		
		visible = false;

		PDInputProcessor.eventTouch.add( this );
		PDInputProcessor.eventKey.add( keyListener );
	}

	@Override
	public void onSignal( PDInputProcessor.Touch touch ) {
		
		if (!isActive()) {
			return;
		}
		
		boolean hit = touch != null && target.overlapsScreenPoint( (int)touch.start.x, (int)touch.start.y );
		
		if (hit) {

			PDInputProcessor.eventTouch.cancel();
			
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
	
	protected void onTouchDown( PDInputProcessor.Touch touch ) {
	}
	
	protected void onTouchUp( PDInputProcessor.Touch touch ) {
	}
	
	protected void onClick( PDInputProcessor.Touch touch ) {
	}
	
	protected void onDrag( PDInputProcessor.Touch touch ) {
	}
	
	public void reset() {
		touch = null;
	}
	
	@Override
	public void destroy() {
		PDInputProcessor.eventTouch.remove( this );
		PDInputProcessor.eventKey.remove( keyListener );
		super.destroy();
	}
}
