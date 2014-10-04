/*
 * Pixel Dungeon
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
package com.watabou.pixeldungeon.scenes;

import com.badlogic.gdx.Input;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.noosa.TouchArea;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.DungeonTilemap;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.input.GameAction;
import com.watabou.pixeldungeon.input.PDInputProcessor;
import com.watabou.utils.GameMath;
import com.watabou.utils.Point;
import com.watabou.utils.PointF;

public class CellSelector extends TouchArea<GameAction> {
	public Listener listener = null;
	
	public boolean enabled;

    private float mouseZoom;
	
	private float dragThreshold;
	
	public CellSelector( DungeonTilemap map ) {
		super( map );
		camera = map.camera();

        mouseZoom = camera.zoom;
		
		dragThreshold = PixelScene.defaultZoom * DungeonTilemap.SIZE / 2;
	}
	
	@Override
	protected void onClick( NoosaInputProcessor.Touch touch ) {
		if (dragging) {
			
			dragging = false;
			
		} else {
			
			select( ((DungeonTilemap)target).screenToTile( 
				(int)touch.current.x, 
				(int)touch.current.y ) );
		}
	}

	@Override
	public boolean onKeyDown(NoosaInputProcessor.Key<GameAction> key) {

        switch (key.action) {
        case ZOOM_IN:
            zoom( camera.zoom + 1 );
            return true;
        case ZOOM_OUT:
            zoom( camera.zoom - 1 );
            return true;
        case ZOOM_DEFAULT:
            zoom( PixelScene.defaultZoom );
            return true;
        }

		boolean handled = true;
		int x = 0, y = 0;
		switch (key.action) {
			case MOVE_UP:
				y = -1;
				break;
			case MOVE_DOWN:
				y = 1;
				break;
			case MOVE_LEFT:
				x = -1;
				break;
			case MOVE_RIGHT:
				x = 1;
				break;
			case MOVE_TOP_LEFT:
				x = -1;
				y = -1;
				break;
			case MOVE_TOP_RIGHT:
				x = 1;
				y = -1;
				break;
			case MOVE_BOTTOM_LEFT:
				x = -1;
				y = 1;
				break;
			case MOVE_BOTTOM_RIGHT:
				x = 1;
				y = 1;
				break;
			case WAIT:
				break;
			default:
				handled = false;
				break;
		}

		if (handled) {
			Point point = DungeonTilemap.tileToPoint(Dungeon.hero.pos);
			point.x += x;
			point.y += y;
			select(DungeonTilemap.pointToTile(point));
		}

		return handled;
	}

    @Override
    public boolean onKeyUp( PDInputProcessor.Key<GameAction> key ) {
        switch (key.code) {
		case PDInputProcessor.MODIFIER_KEY:
			mouseZoom = zoom( Math.round( mouseZoom ) );
			return true;
		default:
			return false;
        }
    }

    private float zoom( float value ) {

        value = GameMath.gate( PixelScene.minZoom, value, PixelScene.maxZoom );
        PixelDungeon.zoom((int) (value - PixelScene.defaultZoom));
        camera.zoom( value );

        return value;
    }

	public void select( int cell ) {
		if (enabled && listener != null && cell != -1) {
			
			listener.onSelect( cell );
			GameScene.ready();
			
		} else {
			
			GameScene.cancel();
			
		}
	}
	
	private boolean pinching = false;
	private NoosaInputProcessor.Touch another;
	private float startZoom;
	private float startSpan;
	
	@Override
	protected void onTouchDown( NoosaInputProcessor.Touch t ) {

		if (t != touch && another == null) {
					
			if (!touch.down) {
				touch = t;
				onTouchDown( t );
				return;
			}
			
			pinching = true;
			
			another = t;
			startSpan = PointF.distance( touch.current, another.current );
			startZoom = camera.zoom;

			dragging = false;
		}
	}
	
	@Override
	protected void onTouchUp( NoosaInputProcessor.Touch t ) {
		if (pinching && (t == touch || t == another)) {
			
			pinching = false;
			
			int zoom = Math.round( camera.zoom );
			camera.zoom( zoom );
			PixelDungeon.zoom( (int)(zoom - PixelScene.defaultZoom) );
			
			dragging = true;
			if (t == touch) {
				touch = another;
			}
			another = null;
			lastPos.set( touch.current );
		}
	}	
	
	private boolean dragging = false;
	private PointF lastPos = new PointF();

	@Override
	public boolean onMouseScroll(int scroll) {
        mouseZoom -= scroll / 3f;
        if (PDInputProcessor.modifier) {
            mouseZoom = zoom( mouseZoom );
        } else {
            zoom( Math.round( mouseZoom ) );
			mouseZoom = GameMath.gate( PixelScene.minZoom, mouseZoom, PixelScene.maxZoom );
        }
        return true;
	}

	@Override
	protected void onDrag( NoosaInputProcessor.Touch t ) {
		 
		camera.target = null;

		if (pinching) {

			float curSpan = PointF.distance( touch.current, another.current );
			camera.zoom( GameMath.gate( 
				PixelScene.minZoom, 
				startZoom * curSpan / startSpan, 
				PixelScene.maxZoom ) );

		} else {
		
			if (!dragging && PointF.distance( t.current, t.start ) > dragThreshold) {
				
				dragging = true;
				lastPos.set( t.current );
				
			} else if (dragging) {
				camera.scroll.offset( PointF.diff( lastPos, t.current ).invScale( camera.zoom ) );
				lastPos.set( t.current );	
			}	
		}
		
	}	
	
	public void cancel() {
		
		if (listener != null) {
			listener.onSelect(null);
		}
		
		GameScene.ready();
	}

	public interface Listener {
		void onSelect( Integer cell );
		String prompt();
	}
}
