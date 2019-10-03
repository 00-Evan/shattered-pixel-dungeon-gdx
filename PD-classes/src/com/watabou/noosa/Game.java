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

package com.watabou.noosa;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;
import com.watabou.glscripts.Script;
import com.watabou.gltextures.TextureCache;
import com.watabou.glwrap.Blending;
import com.watabou.glwrap.Vertexbuffer;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PDPlatformSupport;
import com.watabou.utils.Reflection;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Game<GameActionType> implements ApplicationListener {

	public static Game instance;
	
	// Actual size of the screen
	public static int width;
	public static int height;
	
	// Density: mdpi=1, hdpi=1.5, xhdpi=2...
	public static float density = 1;
	
	public static String version;
	public static int versionCode;


	private final String basePath;
	private final NoosaInputProcessor<GameActionType> inputProcessor;
	private final PDPlatformSupport platformSupport;

	// Current scene
	protected Scene scene;
	// New scene we are going to switch to
	protected Scene requestedScene;
	// true if scene switch is requested
	protected boolean requestedReset = true;
	// callback to perform logic during scene change
	protected SceneChangeCallback onChange;
	// New scene class
	protected static Class<? extends Scene> sceneClass;
	
	public static float timeScale = 1f;
	public static float elapsed = 0f;
	public static float timeTotal = 0f;
	public static long realTime = 0;

	public Game( Class<? extends Scene> c, PDPlatformSupport<GameActionType> platformSupport ) {
		super();
		sceneClass = c;
		this.platformSupport = platformSupport;
		this.inputProcessor = platformSupport.getInputProcessor();
		this.basePath = platformSupport.getBasePath();
	}

	@Override
	public void create() {
		instance = this;
		
		density = Gdx.graphics.getDensity();
		this.inputProcessor.init();
		Gdx.input.setInputProcessor(this.inputProcessor);

		// TODO: Is this right?
		onSurfaceCreated();
	}
	
	@Override
	public void resume() {
		Music.INSTANCE.resume();
		Sample.INSTANCE.resume();
	}
	
	@Override
	public void pause() {
		if (scene != null) {
			scene.onPause();
		}
		
		Script.reset();
		
		Music.INSTANCE.pause();
		Sample.INSTANCE.pause();
	}
	
	@Override
	public void dispose() {
		destroyGame();
		
		Music.INSTANCE.mute();
		Sample.INSTANCE.reset();
	}

	@Override
	public void render() {
		
		if (width == 0 || height == 0) {
			return;
		}

		NoosaScript.get().resetCamera();
		NoosaScriptNoLighting.get().resetCamera();
		Gdx.gl.glDisable( GL20.GL_SCISSOR_TEST );
		Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT );
		draw();

		Gdx.gl.glFlush();

		step();
	}

	@Override
	public void resize( int width, int height ) {
		Gdx.gl.glViewport( 0, 0, width, height );

		if (width != Game.width || height != Game.height) {
			Game.width = width;
			Game.height = height;

			Scene sc = scene();
			if (sc != null) {
				TextureCache.reload();
				Camera.reset();
				switchScene(sc.getClass());
			}
		}
	}

	public void onSurfaceCreated() {
		Blending.useDefault();
		
		//refreshes texture and vertex data stored on the gpu
		TextureCache.reload();
		RenderedText.reloadCache();
		Vertexbuffer.refreshAllBuffers();
	}
	
	protected void destroyGame() {
		if (scene != null) {
			scene.destroy();
			scene = null;
		}
		
		//instance = null;
	}
	
	public static void resetScene() {
		switchScene( instance.sceneClass );
	}

	public static void switchScene(Class<? extends Scene> c) {
		switchScene(c, null);
	}
	
	public static void switchScene(Class<? extends Scene> c, SceneChangeCallback callback) {
		instance.sceneClass = c;
		instance.requestedReset = true;
		instance.onChange = callback;
	}
	
	public static Scene scene() {
		return instance.scene;
	}
	
	protected void step() {
		
		if (requestedReset) {
			requestedReset = false;
			
			requestedScene = Reflection.newInstance(sceneClass);
			if (requestedScene != null){
				switchScene();
			}
			
		}
		
		update();
	}
	
	protected void draw() {
		if (scene != null) scene.draw();
	}
	
	protected void switchScene() {

		Camera.reset();
		
		if (scene != null) {
			scene.destroy();
		}
		scene = requestedScene;
		if (onChange != null) onChange.beforeCreate();
		scene.create();
		if (onChange != null) onChange.afterCreate();
		onChange = null;
		
		Game.elapsed = 0f;
		Game.timeScale = 1f;
	}
	
	protected void update() {
		Game.elapsed = Game.timeScale * Gdx.graphics.getDeltaTime();
		Game.timeTotal += Game.elapsed;
		
		Game.realTime = TimeUtils.millis();
		
		scene.update();
		Camera.updateAll();
	}
	
	public static void vibrate( int milliseconds ) {
		Gdx.input.vibrate(milliseconds);
	}

	public File getFilesDir() {
		return Gdx.files.external(basePath).file();
	}

	public boolean deleteFile(String fileName) {
		final FileHandle fh = Gdx.files.external(basePath != null ? basePath + fileName : fileName);
		return fh.exists() && fh.delete();
	}

	public InputStream openFileInput(String fileName) throws IOException {
		final FileHandle fh = Gdx.files.external(basePath != null ? basePath + fileName : fileName);
		if (!fh.exists())
			throw new IOException("File " + fileName + " doesn't exist");
		return fh.read();
	}

	public OutputStream openFileOutput(String fileName) {
		final FileHandle fh = Gdx.files.external(basePath != null ? basePath + fileName : fileName);
		return fh.write(false);
	}

	public void finish() {
		Gdx.app.exit();
	}

	public NoosaInputProcessor<GameActionType> getInputProcessor() {
		return inputProcessor;
	}

	public PDPlatformSupport getPlatformSupport() {
		return platformSupport;
	}

	public static void reportException( Throwable tr ) {
		if (instance != null) instance.logException(tr);
	}

	protected void logException( Throwable tr ){
		Gdx.app.error("PD", tr.getMessage(), tr);
	}

	public interface SceneChangeCallback {
		void beforeCreate();
		void afterCreate();
	}
}
