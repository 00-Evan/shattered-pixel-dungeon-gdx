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
package com.shatteredpixel.shatteredpixeldungeon.ui;

import com.badlogic.gdx.Gdx;
import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Chrome;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Game;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.TouchArea;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class UpdateNotification extends Component {

	//0 means no connection yet, -1 means connection failure.
	private static int latestVersion = 0;

	private static boolean updateAvailable;
	private static boolean latestIsUpdate;
	private static String latestMessage;
	private static String updateURL;

	private NinePatch panel;
	private BitmapText updateMessage;
	private TouchArea touchUpdate;

	private float alpha;

	//initially indev this thing always displayed even when it couldn't find an update.
	//decided that was a little too annoying, but decided to keep the logic around.
	//flip this flag to re-enable that old functionality, which is still in the code below.
	private static boolean quiet = true;

	public UpdateNotification(){
		super();
		if (latestVersion <= 1) {
			Thread thread = new Thread() {
				@Override
				public void run() {
					try {
						int currentVersion = Game.versionCode;

						URL versionInfo = new URL("https://raw.githubusercontent.com/00-Evan/shattered-pixel-dungeon-gdx/master/desktop/VersionInfo.txt");
						BufferedReader inforeader = new BufferedReader(new InputStreamReader(versionInfo.openStream()));

						latestVersion = Integer.parseInt(inforeader.readLine());

						if (latestVersion > currentVersion) {
							updateAvailable = true;
						} else {
							updateAvailable = false;
							updateMessage();
							return;
						}

						//Update vs Patch, where a patch just appends a letter and usually fixes bugs.
						int latestFullRelease = Integer.parseInt(inforeader.readLine());
						latestIsUpdate = (currentVersion < latestFullRelease);

						// Affects visibility. Basically, if we don't have the latest update, grab its message,
						// otherwise grab the latest version's message, this way your told about an update
						// you don't have, even if the latest version is a patch.
						String latestVersionMessage = inforeader.readLine();
						String latestUpdateMessage = inforeader.readLine();
						latestMessage = latestIsUpdate ? latestUpdateMessage : latestVersionMessage;

						updateURL = inforeader.readLine();

						updateMessage();

					} catch (Exception e) {
						latestVersion = -1;
						updateMessage();
					}
				}
			};
			thread.start();
		}
	}

	@Override
	protected void createChildren() {
		panel = Chrome.get(Chrome.Type.TOAST_TR);
		add( panel );

		//updateMessage = new BitmapText("Checking Updates", PixelScene.font1x);
		updateMessage = PixelScene.createText("Checking Updates", 9);
		add(updateMessage);

		touchUpdate = new TouchArea( panel ){
			@Override
			protected void onClick( NoosaInputProcessor.Touch touch ) {
				if (updateAvailable) {
					parent.add(new WndUpdate() );
					Sample.INSTANCE.play( Assets.SND_CLICK );
				}
			}
		};
		add(touchUpdate);

		updateMessage();
	}

	@Override
	protected void layout() {
		panel.x = this.x;
		panel.y = this.y;
		panel.alpha(alpha);
		panel.visible = updateAvailable || !quiet;

		updateMessage.x = panel.x+panel.marginLeft();
		updateMessage.y = panel.y+panel.marginTop();
		updateMessage.measure();
		updateMessage.alpha(alpha);
		updateMessage.visible = updateAvailable || !quiet;

		panel.size( panel.marginHor()+updateMessage.width(), panel.marginVer()+updateMessage.height()-1);
		this.width = panel.width();
		this.height = panel.height();

	}

	private void updateMessage(){
		if (latestVersion == -1){
			updateMessage.text("Connection Failed");
			updateMessage.hardlight( 0xFFCC66 );
			alpha = 1f;
		} else if (latestVersion == 0){
			updateMessage.text("Checking Updates");
			updateMessage.hardlight( 0xFFFFFF );
			alpha = 0.8f;
		} else if (!updateAvailable){
			updateMessage.text("Up to Date!");
			updateMessage.hardlight( 0xFFFFFF );
			alpha = 0.8f;
		} else {
			if (!latestIsUpdate){
				updateMessage.text("Patch Available!");
			} else {
				updateMessage.text("Update Available!");
			}
			updateMessage.hardlight( Window.SHPX_COLOR );
			alpha = 1f;
		}
		layout();
	}

	@Override
	public void update() {
		super.update();

		//connection failed
		if (latestVersion == -1) {
			alpha -= Game.elapsed/4f;

		//up to date
		} else if (!updateAvailable && latestVersion > 0){
			alpha -= Game.elapsed/2f;

		//update available
		} else if (updateAvailable){
			alpha = (float) (0.7f + Math.sin(Game.timeTotal*3)*0.3f);
		}

		layout();
		if (alpha <= 0f)
			parent.remove( this );
	}

	public static class WndUpdate extends WndOptions {

		private static final String TTL_UPD = "An Update is Ready!:";
		private static final String TTL_PTH = "A Patch is Ready!:";

		private static final String MSG_UPD = "%s\n" +
				"\n" +
				"Updates include new content, bugfixes, balance tweaks, and various small improvements.\n" +
				"\n" +
				"Simply download the new executable and run it to start playing on the latest version!";

		private static final String MSG_PTH = "%s\n" +
				"\n" +
				"Patches contain bugfixes, balance tweaks, and occasional bits of new content.\n" +
				"\n" +
				"Simply download the new executable and run it to start playing with the latest patch!";

		private static final String BTN_UPD = "Quit and Download Update";
		private static final String BTN_PTH = "Quit and Download Patch";



		public WndUpdate(){
			super(
					latestIsUpdate ? TTL_UPD : TTL_PTH,
					Messages.format((latestIsUpdate ? MSG_UPD : MSG_PTH), latestMessage),
					latestIsUpdate ? BTN_UPD : BTN_PTH);
		}

		@Override
		protected void onSelect(int index) {
			if (index == 0) {
				Gdx.net.openURI("http://" + updateURL);
				Game.instance.finish();
			}
		}
	}
}

