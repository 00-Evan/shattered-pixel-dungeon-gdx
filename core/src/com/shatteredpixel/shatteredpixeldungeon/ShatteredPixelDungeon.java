/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2016 Evan Debenham
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
package com.shatteredpixel.shatteredpixeldungeon;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.shatteredpixel.shatteredpixeldungeon.input.GameAction;
import com.shatteredpixel.shatteredpixeldungeon.messages.Languages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.WelcomeScene;
import com.watabou.noosa.Game;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.PDPlatformSupport;

import java.util.Locale;

public class ShatteredPixelDungeon extends Game<GameAction> {
	
	//variable constants for specific older versions of shattered, used for data conversion
	//versions older than v0.4.3c are no longer supported, and data from them is ignored
	public static final int v0_4_3c = 148;
	
	public static final int v0_5_0b = 157;
	
	public static final int v0_6_0b = 185;
	
	public static final int v0_6_1  = 205;
	
	public static final int v0_6_2  = 222;
	
	public ShatteredPixelDungeon(final PDPlatformSupport<GameAction> platformSupport) {
		super(WelcomeScene.class, platformSupport);
		
		Game.version = platformSupport.getVersion();
		Game.versionCode = platformSupport.getVersionCode();
		
		//v0.6.0
		com.watabou.utils.Bundle.addAlias(
				com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special.MassGraveRoom.Bones.class,
				"com.shatteredpixel.shatteredpixeldungeon.levels.painters.MassGravePainter$Bones" );
		com.watabou.utils.Bundle.addAlias(
				com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard.RitualSiteRoom.RitualMarker.class,
				"com.shatteredpixel.shatteredpixeldungeon.levels.painters.RitualSitePainter$RitualMarker" );
		com.watabou.utils.Bundle.addAlias(
				com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special.WeakFloorRoom.HiddenWell.class,
				"com.shatteredpixel.shatteredpixeldungeon.levels.painters.WeakFloorPainter$HiddenWell" );
		com.watabou.utils.Bundle.addAlias(
				com.shatteredpixel.shatteredpixeldungeon.levels.rooms.Room.class,
				"com.shatteredpixel.shatteredpixeldungeon.levels.Room" );
		com.watabou.utils.Bundle.addAlias(
				com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.Shortsword.class,
				"com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.NewShortsword" );
		
		//v0.6.0a
		com.watabou.utils.Bundle.addAlias(
				com.shatteredpixel.shatteredpixeldungeon.items.food.SmallRation.class,
				"com.shatteredpixel.shatteredpixeldungeon.items.food.OverpricedRation" );
		
		//v0.6.2
		com.watabou.utils.Bundle.addAlias(
				com.shatteredpixel.shatteredpixeldungeon.levels.rooms.secret.RatKingRoom.class,
				"com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special.RatKingRoom" );
		com.watabou.utils.Bundle.addAlias(
				com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard.PlantsRoom.class,
				"com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard.GardenRoom" );
		com.watabou.utils.Bundle.addAlias(
				com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special.GardenRoom.class,
				"com.shatteredpixel.shatteredpixeldungeon.levels.rooms.special.FoliageRoom" );
		
		com.watabou.utils.Bundle.addAlias(
				com.shatteredpixel.shatteredpixeldungeon.levels.traps.WornDartTrap.class,
				"com.shatteredpixel.shatteredpixeldungeon.levels.traps.WornTrap" );
		com.watabou.utils.Bundle.addAlias(
				com.shatteredpixel.shatteredpixeldungeon.levels.traps.PoisonDartTrap.class,
				"com.shatteredpixel.shatteredpixeldungeon.levels.traps.PoisonTrap" );
		com.watabou.utils.Bundle.addAlias(
				com.shatteredpixel.shatteredpixeldungeon.levels.traps.ShockingTrap.class,
				"com.shatteredpixel.shatteredpixeldungeon.levels.traps.ParalyticTrap" );
		com.watabou.utils.Bundle.addAlias(
				com.shatteredpixel.shatteredpixeldungeon.levels.traps.ShockingTrap.class,
				"com.shatteredpixel.shatteredpixeldungeon.levels.traps.LightningTrap" );
		com.watabou.utils.Bundle.addAlias(
				com.shatteredpixel.shatteredpixeldungeon.levels.traps.GrippingTrap.class,
				"com.shatteredpixel.shatteredpixeldungeon.levels.traps.SpearTrap" );
		com.watabou.utils.Bundle.addAlias(
				com.shatteredpixel.shatteredpixeldungeon.levels.traps.BurningTrap.class,
				"com.shatteredpixel.shatteredpixeldungeon.levels.traps.FireTrap" );
		
		com.watabou.utils.Bundle.addAlias(
				com.shatteredpixel.shatteredpixeldungeon.actors.buffs.BlobImmunity.class,
				"com.shatteredpixel.shatteredpixeldungeon.actors.buffs.GasesImmunity" );
		
		com.watabou.utils.Bundle.exceptionReporter =
				new com.watabou.utils.Bundle.BundleExceptionCallback() {
					@Override
					public void call(Throwable t) {
						ShatteredPixelDungeon.reportException(t);
					}
				};
		
	}

	@SuppressWarnings("deprecation")
	@Override
	public void create() {
		super.create();

		boolean landscape = Gdx.graphics.getWidth() > Gdx.graphics.getHeight();

		final Preferences prefs = Preferences.INSTANCE;
		if (prefs.getBoolean(Preferences.KEY_LANDSCAPE, false) != landscape) {
			landscape(!landscape);
		}
		fullscreen( prefs.getBoolean(Preferences.KEY_WINDOW_FULLSCREEN, false) );
		
		Music.INSTANCE.enable( music() );
		Music.INSTANCE.volume( musicVol()/10f );
		Sample.INSTANCE.enable( soundFx() );
		Sample.INSTANCE.volume( SFXVol()/10f );

		Sample.INSTANCE.load(
				Assets.SND_CLICK,
				Assets.SND_BADGE,
				Assets.SND_GOLD,

				Assets.SND_STEP,
				Assets.SND_WATER,
				Assets.SND_OPEN,
				Assets.SND_UNLOCK,
				Assets.SND_ITEM,
				Assets.SND_DEWDROP,
				Assets.SND_HIT,
				Assets.SND_MISS,

				Assets.SND_DESCEND,
				Assets.SND_EAT,
				Assets.SND_READ,
				Assets.SND_LULLABY,
				Assets.SND_DRINK,
				Assets.SND_SHATTER,
				Assets.SND_ZAP,
				Assets.SND_LIGHTNING,
				Assets.SND_LEVELUP,
				Assets.SND_DEATH,
				Assets.SND_CHALLENGE,
				Assets.SND_CURSED,
				Assets.SND_EVOKE,
				Assets.SND_TRAP,
				Assets.SND_TOMB,
				Assets.SND_ALERT,
				Assets.SND_MELD,
				Assets.SND_BOSS,
				Assets.SND_BLAST,
				Assets.SND_PLANT,
				Assets.SND_RAY,
				Assets.SND_BEACON,
				Assets.SND_TELEPORT,
				Assets.SND_CHARMS,
				Assets.SND_MASTERY,
				Assets.SND_PUFF,
				Assets.SND_ROCKS,
				Assets.SND_BURNING,
				Assets.SND_FALLING,
				Assets.SND_GHOST,
				Assets.SND_SECRET,
				Assets.SND_BONES,
				Assets.SND_BEE,
				Assets.SND_DEGRADE,
				Assets.SND_MIMIC );

		if (classicFont()) {
			RenderedText.setFont("pixelfont.ttf");
		} else {
			RenderedText.setFont( null );
		}
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

		Graphics.DisplayMode mode = Gdx.graphics.getDisplayMode();
		boolean maximized = width >= mode.width || height >= mode.height;

		if (!maximized && !fullscreen()) {
			final Preferences prefs = Preferences.INSTANCE;
			prefs.put(Preferences.KEY_WINDOW_WIDTH, width);
			prefs.put(Preferences.KEY_WINDOW_HEIGHT, height);
		}
	}
	/*
	 * ---> Prefernces
	 */
	
	public static void landscape( boolean value ) {
		// FIXME
//		Game.instance.setRequestedOrientation( value ?
//			ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE :
//			ActivityInfo.SCREEN_ORIENTATION_PORTRAIT );
//		Preferences.INSTANCE.put( Preferences.KEY_LANDSCAPE, value );
	}
	
	public static boolean landscape() {
		return width > height;
	}

	public static void fullscreen(boolean value) {
		final Preferences prefs = Preferences.INSTANCE;
		if (value) {
			prefs.put(Preferences.KEY_WINDOW_FULLSCREEN, true);

			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		} else {
			int w = prefs.getInt(Preferences.KEY_WINDOW_WIDTH, Preferences.DEFAULT_WINDOW_WIDTH);
			int h = prefs.getInt(Preferences.KEY_WINDOW_HEIGHT, Preferences.DEFAULT_WINDOW_HEIGHT);
			prefs.put(Preferences.KEY_WINDOW_FULLSCREEN, false);
			Gdx.graphics.setWindowedMode(w, h);
		}
	}

	public static boolean fullscreen() {
		return Gdx.graphics.isFullscreen();
	}
	
	public static void scale( int value ) {
		Preferences.INSTANCE.put( Preferences.KEY_SCALE, value );
	}
	
	public static int scale() {
		return Preferences.INSTANCE.getInt( Preferences.KEY_SCALE, 0 );
	}

	public static void zoom( int value ) {
		Preferences.INSTANCE.put( Preferences.KEY_ZOOM, value );
	}
	
	public static int zoom() {
		return Preferences.INSTANCE.getInt( Preferences.KEY_ZOOM, 0 );
	}
	
	public static void music( boolean value ) {
		Music.INSTANCE.enable( value );
		Preferences.INSTANCE.put( Preferences.KEY_MUSIC, value );
	}
	
	public static boolean music() {
		return Preferences.INSTANCE.getBoolean( Preferences.KEY_MUSIC, true );
	}

	public static void musicVol( int value ){
		Preferences.INSTANCE.put( Preferences.KEY_MUSIC_VOL, value );
	}

	public static int musicVol(){
		return Preferences.INSTANCE.getInt( Preferences.KEY_MUSIC_VOL, 10, 0, 10 );
	}
	
	public static void soundFx( boolean value ) {
		Sample.INSTANCE.enable( value );
		Preferences.INSTANCE.put( Preferences.KEY_SOUND_FX, value );
	}
	
	public static boolean soundFx() {
		return Preferences.INSTANCE.getBoolean( Preferences.KEY_SOUND_FX, true );
	}

	public static void SFXVol( int value ) {
		Preferences.INSTANCE.put( Preferences.KEY_SFX_VOL, value );
	}

	public static int SFXVol() {
		return Preferences.INSTANCE.getInt( Preferences.KEY_SFX_VOL, 10, 0, 10 );
	}
	
	public static void brightness( int value ) {
		Preferences.INSTANCE.put( Preferences.KEY_BRIGHTNESS, value );
		GameScene.updateFog();
	}
	
	public static int brightness() {
		return Preferences.INSTANCE.getInt( Preferences.KEY_BRIGHTNESS, 0, -2, 2 );
	}

	public static void visualGrid( int value ){
		Preferences.INSTANCE.put( Preferences.KEY_GRID, value );
		GameScene.updateMap();
	}

	public static int visualGrid() {
		return Preferences.INSTANCE.getInt( Preferences.KEY_GRID, 0, -1, 3 );
	}

	public static void language(Languages lang) {
		Preferences.INSTANCE.put( Preferences.KEY_LANG, lang.code());
	}

	public static Languages language() {
		//multi-language does not currently work
		return Languages.ENGLISH;
	}

	public static void classicFont(boolean classic){
		Preferences.INSTANCE.put(Preferences.KEY_CLASSICFONT, classic);
		if (classic) {
			RenderedText.setFont("pixelfont.ttf");
		} else {
			RenderedText.setFont( null );
		}
	}

	public static boolean classicFont(){
		return Preferences.INSTANCE.getBoolean(Preferences.KEY_CLASSICFONT,
				(language() != Languages.KOREAN && language() != Languages.CHINESE));
	}

	public static void lastClass( int value ) {
		Preferences.INSTANCE.put( Preferences.KEY_LAST_CLASS, value );
	}
	
	public static int lastClass() {
		return Preferences.INSTANCE.getInt( Preferences.KEY_LAST_CLASS, 0, 0, 3 );
	}

	public static void challenges( int value ) {
		Preferences.INSTANCE.put( Preferences.KEY_CHALLENGES, value );
	}

	public static int challenges() {
		return Preferences.INSTANCE.getInt( Preferences.KEY_CHALLENGES, 0, 0, Challenges.MAX_VALUE );
	}

	public static void quickSlots( int value ){ Preferences.INSTANCE.put( Preferences.KEY_QUICKSLOTS, value ); }

	public static int quickSlots(){
		if (Gdx.app.getType() == Application.ApplicationType.Desktop){
			return 4;
		} else {
			return Preferences.INSTANCE.getInt(Preferences.KEY_QUICKSLOTS, 4, 0, 4);
		}
	}

	public static void flipToolbar( boolean value) {
		Preferences.INSTANCE.put(Preferences.KEY_FLIPTOOLBAR, value );
	}

	public static boolean flipToolbar(){ return Preferences.INSTANCE.getBoolean(Preferences.KEY_FLIPTOOLBAR, false); }

	public static void flipTags( boolean value) {
		Preferences.INSTANCE.put(Preferences.KEY_FLIPTAGS, value );
	}

	public static boolean flipTags(){ return Preferences.INSTANCE.getBoolean(Preferences.KEY_FLIPTAGS, false); }

	public static void toolbarMode( String value ) {
		Preferences.INSTANCE.put( Preferences.KEY_BARMODE, value );
	}

	public static String toolbarMode() {
		return Preferences.INSTANCE.getString(Preferences.KEY_BARMODE, !landscape() ? "SPLIT" : "GROUP");
	}
	
	public static void intro( boolean value ) {
		Preferences.INSTANCE.put( Preferences.KEY_INTRO, value );
	}
	
	public static boolean intro() {
		return Preferences.INSTANCE.getBoolean( Preferences.KEY_INTRO, true );
	}

	public static void version( int value)  {
		Preferences.INSTANCE.put( Preferences.KEY_VERSION, value );
	}

	public static int version() {
		return Preferences.INSTANCE.getInt( Preferences.KEY_VERSION, 0 );
	}

	public static void switchNoFade( Class<? extends PixelScene> c ) {
		PixelScene.noFade = true;
		switchScene( c );
	}

	public static void switchNoFade(Class<? extends PixelScene> c, SceneChangeCallback callback) {
		PixelScene.noFade = true;
		switchScene( c, callback );
	}

	/*
	 * <--- Preferences
	 */
	
	public static void reportException( Throwable tr ) {
		Gdx.app.error("PD", tr.getMessage(), tr);
	}
}