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

public enum Music {
	
	INSTANCE;
	
	private com.badlogic.gdx.audio.Music player;
	
	private String lastPlayed;
	private boolean lastLooping;
	
	private boolean enabled = true;
	
	public void play( String assetName, boolean looping ) {
		
		if (isPlaying() && lastPlayed.equals( assetName )) {
			return;
		}
		
		stop();
		
		lastPlayed = assetName;
		lastLooping = looping;
		
		if (!enabled || assetName == null) {
			return;
		}
		
		player = Gdx.audio.newMusic(Gdx.files.internal(assetName));
		player.setLooping(looping);

		player.play();
	}
	
	public void mute() {
		lastPlayed = null;
		stop();
	}

	public void pause() {
		if (player != null) {
			player.pause();
		}
	}
	
	public void resume() {
		if (player != null) {
			player.play();
		}
	}
	
	public void stop() {
		if (player != null) {
			player.stop();
			player.dispose();
			player = null;
		}
	}
	
	public void volume( float value ) {
		if (player != null) {
			player.setVolume( value );
		}
	}
	
	public boolean isPlaying() {
		return player != null && player.isPlaying();
	}
	
	public void enable( boolean value ) {
		enabled = value;
		if (isPlaying() && !value) {
			stop();
		} else
		if (!isPlaying() && value) {
			play( lastPlayed, lastLooping );
		}
	}
	
	public boolean isEnabled() {
		return enabled;
	}
}
