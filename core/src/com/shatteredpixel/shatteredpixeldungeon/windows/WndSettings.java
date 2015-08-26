/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015  Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2015 Evan Debenham
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
package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.ui.Toolbar;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.watabou.noosa.Camera;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Button;
import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.CheckBox;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;

public class WndSettings extends Window {
	
	private static final String TXT_ZOOM_IN			= "+";
	private static final String TXT_ZOOM_OUT		= "-";
	private static final String TXT_ZOOM_DEFAULT	= "Default Zoom";

	private static final String TXT_SCALE_UP		= "Scale up UI";
	
	private static final String TXT_MUSIC	= "Music";
	
	private static final String TXT_SOUND	= "Sound FX";

	private static final String TXT_BINDINGS	= "Key bindings";
	
	private static final String TXT_BRIGHTNESS	= "Brightness";

	private static final String TXT_QUICKSLOT = "Second QuickSlot";
	
	private static final String TXT_SWITCH_PORT	= "Switch to portrait";
	private static final String TXT_SWITCH_LAND	= "Switch to landscape";

	private static final String TXT_SWITCH_FULL = "Switch to fullscreen";
	private static final String TXT_SWITCH_WIN = "Switch to windowed";

	private static final int WIDTH		= 112;
	private static final int BTN_HEIGHT	= 20;
	private static final int GAP 		= 2;
	
	private RedButton btnZoomOut;
	private RedButton btnZoomIn;
	
	public WndSettings( boolean inGame ) {
		super();
		
		
		if (inGame) {
			int w = BTN_HEIGHT;
			
			btnZoomOut = new RedButton( TXT_ZOOM_OUT ) {
				@Override
				protected void onClick() {
					zoom( Camera.main.zoom - 1 );
				}
			};
			add( btnZoomOut.setRect( 0, 0, w, BTN_HEIGHT) );
			
			// Zoom in
			btnZoomIn = new RedButton( TXT_ZOOM_IN ) {
				@Override
				protected void onClick() {
					zoom( Camera.main.zoom + 1 );
				}
			};
			add( btnZoomIn.setRect( WIDTH - w, 0, w, BTN_HEIGHT) );
			
			// Default zoom
			add( new RedButton( TXT_ZOOM_DEFAULT ) {
				@Override
				protected void onClick() {
					zoom( PixelScene.defaultZoom );
				}
			}.setRect( btnZoomOut.right(), 0, WIDTH - btnZoomIn.width() - btnZoomOut.width(), BTN_HEIGHT ) );
			
			updateEnabled();
			
		} else {
			
			CheckBox btnScaleUp = new CheckBox( TXT_SCALE_UP ) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scaleUp(checked());
				}
			};
			btnScaleUp.setRect( 0, 0, WIDTH, BTN_HEIGHT );
			btnScaleUp.checked( ShatteredPixelDungeon.scaleUp() );
			add( btnScaleUp );
			
		}
		
		CheckBox btnMusic = new CheckBox( TXT_MUSIC ) {
			@Override
			protected void onClick() {
				super.onClick();
				ShatteredPixelDungeon.music(checked());
			}
		};
		btnMusic.setRect( 0, BTN_HEIGHT + GAP, WIDTH, BTN_HEIGHT );
		btnMusic.checked( ShatteredPixelDungeon.music() );
		add( btnMusic );
		
		CheckBox btnSound = new CheckBox( TXT_SOUND ) {
			@Override
			protected void onClick() {
				super.onClick();
				ShatteredPixelDungeon.soundFx(checked());
				Sample.INSTANCE.play( Assets.SND_CLICK );
			}
		};
		btnSound.setRect( 0, btnMusic.bottom() + GAP, WIDTH, BTN_HEIGHT );
		btnSound.checked( ShatteredPixelDungeon.soundFx() );
		add( btnSound );

		Application.ApplicationType type = Gdx.app.getType();

		Button lastBtn = btnSound;
		if (!inGame) {
		/*	if (type == Application.ApplicationType.Android || type == Application.ApplicationType.iOS) {
				RedButton btnOrientation = new RedButton(orientationText()) {
					@Override
					protected void onClick() {
						ShatteredPixelDungeon.landscape(!ShatteredPixelDungeon.landscape());
					}
				};
				btnOrientation.setRect(0, btnSound.bottom() + GAP, WIDTH, BTN_HEIGHT);
				add(btnOrientation);

				lastBtn = btnOrientation;
			} else*/ if (type == Application.ApplicationType.Desktop) {

				RedButton btnResolution = new RedButton(resolutionText()) {
					@Override
					protected void onClick() {
						ShatteredPixelDungeon.fullscreen(!ShatteredPixelDungeon.fullscreen());
					}
				};
				btnResolution.enable( ShatteredPixelDungeon.instance.getPlatformSupport().isFullscreenEnabled() );
				btnResolution.setRect(0, btnSound.bottom() + GAP, WIDTH, BTN_HEIGHT);
				add(btnResolution);

				lastBtn = btnResolution;
			}
		} else {

			CheckBox btnBrightness = new CheckBox( TXT_BRIGHTNESS ) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.brightness(checked());
				}
			};
			btnBrightness.setRect( 0, btnSound.bottom() + GAP, WIDTH, BTN_HEIGHT );
			btnBrightness.checked( ShatteredPixelDungeon.brightness() );
			add( btnBrightness );

			/*CheckBox btnQuickSlot = new CheckBox( TXT_QUICKSLOT ) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.quickSlots(checked() ? 2 : 1);
					Toolbar.QuickSlots = checked() ? 2 : 1;
				}
			};
			btnQuickSlot.setRect( 0, btnBrightness.bottom() + GAP, WIDTH, BTN_HEIGHT );
			btnQuickSlot.checked( ShatteredPixelDungeon.quickSlots() == 2 );
			add( btnQuickSlot );*/

			lastBtn = btnBrightness;
			
		}

		if (type == Application.ApplicationType.Desktop) {

			RedButton btnKeymap = new RedButton(TXT_BINDINGS) {
				@Override
				protected void onClick() {
					parent.add(new WndKeymap());
				}
			};
			btnKeymap.setRect(0, lastBtn.bottom() + GAP, WIDTH, BTN_HEIGHT);
			add(btnKeymap);

			lastBtn = btnKeymap;
		}

		resize(WIDTH, (int) lastBtn.bottom());
	}
	
	private void zoom( float value ) {

		Camera.main.zoom( value );
		ShatteredPixelDungeon.zoom((int) (value - PixelScene.defaultZoom));

		updateEnabled();
	}
	
	private void updateEnabled() {
		float zoom = Camera.main.zoom;
		btnZoomIn.enable( zoom < PixelScene.maxZoom );
		btnZoomOut.enable( zoom > PixelScene.minZoom );
	}
	
	private String orientationText() {
		return ShatteredPixelDungeon.landscape() ? TXT_SWITCH_PORT : TXT_SWITCH_LAND;
	}

	private String resolutionText() {
		return Gdx.graphics.isFullscreen() ? TXT_SWITCH_WIN : TXT_SWITCH_FULL;
	}
}
