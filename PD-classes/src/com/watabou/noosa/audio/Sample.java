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

package com.watabou.noosa.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;

public enum Sample {
	
	INSTANCE;

	protected HashMap<Object, Sound> ids = new HashMap<Object, Sound>();
	
	private boolean enabled = true;
	
	public void reset() {

		for (Sound sound : ids.values()) {
			sound.dispose();
		}

		ids.clear();
	}
	
	public void pause() {
		for (Sound sound : ids.values()) {
			sound.pause();
		}
	}
	
	public void resume() {
		for (Sound sound : ids.values()) {
			sound.resume();
		}
	}
	
	public void load( String... assets ) {
		
		for (int i=0; i < assets.length; i++) {
			
			String asset = assets[i];
			
			if (!ids.containsKey( asset )) {
				ids.put(asset, Gdx.audio.newSound(Gdx.files.internal(asset)));
			}
			
		}
	}
	
	public void unload( Object src ) {
		
		if (ids.containsKey( src )) {
			ids.get( src ).dispose();
			ids.remove( src );
		}
	}
	
	public long play(Object id) {
		return play( id, 1, 1, 1 );
	}
	
	public long play(Object id, float volume) {
		return play( id, volume, volume, 1 );
	}
	
	public long play(Object id, float leftVolume, float rightVolume, float rate) {
		if (enabled && ids.containsKey( id )) {
			Sound sound = ids.get(id);
			return sound.play(leftVolume, rate, 0);
		} else {
			return -1;
		}
	}
	
	public void enable( boolean value ) {
		enabled = value;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
}
