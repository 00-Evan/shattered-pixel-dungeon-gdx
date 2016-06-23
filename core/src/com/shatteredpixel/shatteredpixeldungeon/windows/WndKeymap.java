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
package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.badlogic.gdx.Input;
import com.shatteredpixel.shatteredpixeldungeon.input.GameAction;
import com.shatteredpixel.shatteredpixeldungeon.input.PDInputProcessor;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.ScrollPane;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Game;
import com.watabou.noosa.ui.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class WndKeymap extends Window {
	private static final int MARGIN = 0;
	private static final float ITEM_HEIGHT = 12;
	private static final int BTN_HEIGHT	= 20;

	public static final String TXT_UNASSIGNED = "<None>";

	private Component listContent;

	private int tempPos = 0;

	private final Map<GameAction, ListItem> items = new HashMap<>(32);

	private static class KeyPair {
		public int key1, key2;
	}

	public WndKeymap() {

		int ww = Math.min( 160, PixelScene.uiCamera.width - 16 );
		int wh = PixelScene.uiCamera.height - 24;

		resize( ww, wh );

		RedButton btnReset = new RedButton( "Reset To Defaults" ) {
			@Override
			protected void onClick() {
				resetToDefaults();
				populateList();
			}
		};
		btnReset.setRect( 0, height - BTN_HEIGHT, width, BTN_HEIGHT );
		add( btnReset );

		listContent = new Component();
		final ScrollPane list = new ScrollPane(listContent) {
			@Override
			public void onClick( float x, float y ) {
				for (ListItem item : items.values()) {
					if (item.onClick( x, y )) {
						break;
					}
				}
			}
		};

		populateList();

		add(list);

		list.setRect(0, 0, width, btnReset.top() );
	}

	private void populateList() {

		listContent.clear();

		tempPos = 0;

		final PDInputProcessor inputProcessor = (PDInputProcessor) Game.instance.getInputProcessor();
		final Map<Integer, PDInputProcessor.GameActionWrapper> keyMappings = inputProcessor.getKeyMappings();

		final Map<GameAction, KeyPair> mappings = new TreeMap<>();

		for (GameAction action : GameAction.values()) {
			if (action.getDescription() != null) {
				mappings.put( action, new KeyPair() );
			}
		}

		for (Map.Entry<Integer, PDInputProcessor.GameActionWrapper> entry : keyMappings.entrySet()) {
			final Integer key = entry.getKey();
			final PDInputProcessor.GameActionWrapper value = entry.getValue();

			final GameAction action = value.gameAction;
			final KeyPair keyPair = mappings.get(action);
		/*	if (keyPair == null) {
				mappings.put(action, keyPair = new KeyPair());
			}*/
			if (value.defaultKey) {
				keyPair.key1 = key;
			} else {
				keyPair.key2 = key;
			}
		}
		for (Map.Entry<GameAction, KeyPair> entry : mappings.entrySet()) {
			addKey(listContent, width, entry);
		}

		listContent.setSize( 0, tempPos);
	}

	private void addKey(Component content, int maxWidth, Map.Entry<GameAction, KeyPair> entry) {
		final GameAction action = entry.getKey();
		final ListItem keyItem = new ListItem(action, entry.getValue());
		keyItem.setRect(0, tempPos, maxWidth, ITEM_HEIGHT);
		tempPos += ITEM_HEIGHT;
		content.add(keyItem);

		items.put(action, keyItem);
	}

	private void resetToDefaults() {
		((PDInputProcessor)Game.instance.getInputProcessor()).resetKeyMappings();
	}

	private class ListItem extends Component {

		private static final int BOUND		= TITLE_COLOR;
		private static final int NOT_BOUND	= 0xCACFC2;

		private final GameAction gameAction;
		private final KeyPair keys;

		private BitmapText action;
		private BitmapText key1;
		private BitmapText key2;

		public ListItem( GameAction gameAction, KeyPair keys ) {
			super();

			this.gameAction = gameAction;
			this.keys = keys;

			action.text(gameAction.getDescription());
			action.measure();

			reconfigureKeysText();
		}

		@Override
		protected void createChildren() {
			action = PixelScene.createText( 9 );
			add( action );

			key1 = PixelScene.createText( 9 );
			add( key1 );

			key2 = PixelScene.createText( 9 );
			add( key2 );
		}

		@Override
		protected void layout() {
			final float ty = y + (height - action.baseLine()) / 2;
			final float w4 = width / 4;

			action.x = MARGIN;
			action.y = ty;

			key1.x = w4 * 2 + (w4 - key1.width()) / 2;
			key1.y = ty;

			key2.x = w4 * 3 + (w4 - key2.width()) / 2;
			key2.y = ty;
		}

		private void reconfigureKeysText() {
			if (keys.key1 > 0) {
				key1.text( Input.Keys.toString( keys.key1 ) );
				key1.hardlight( BOUND );
			} else {
				key1.text( TXT_UNASSIGNED );
				key1.hardlight( NOT_BOUND );
			}
			key1.measure();

			if (keys.key2 > 0) {
				key2.text( Input.Keys.toString( keys.key2 ) );
				key2.hardlight( BOUND );
			} else {
				key2.text( TXT_UNASSIGNED );
				key2.hardlight( NOT_BOUND );
			}
			key2.measure();

			layout();
		}

		public int getKey(boolean defaultKey) {
			if (defaultKey) {
				return this.keys.key1;
			} else {
				return this.keys.key2;
			}
		}

		public void setKey(boolean defaultKey, int keycode) {
			if (defaultKey) {
				this.keys.key1 = keycode;
			} else {
				this.keys.key2 = keycode;
			}
			reconfigureKeysText();
		}

		public boolean onClick( float x, float y ) {

			if (x < this.x || x > this.x + width || y < this.y || y > this.y + height) {
				return false;
			}

			final boolean defaultKey = x < width * 3 / 4;
			final String message =
				"Press a key for \"" + gameAction.getDescription() + "\", or press " +
				Input.Keys.toString(PDInputProcessor.MODIFIER_KEY) + " to remove the binding.";

			Game.scene().add( new WndMessage( message ) {
				@Override
				protected void onKeyDown( NoosaInputProcessor.Key key ) {

					final PDInputProcessor inputProcessor = (PDInputProcessor) Game.instance.getInputProcessor();
					final Map<Integer, PDInputProcessor.GameActionWrapper> keyMappings = inputProcessor.getKeyMappings();

					int oldKeycode = getKey(defaultKey);
					inputProcessor.removeKeyMapping( gameAction, defaultKey, oldKeycode);

					// Don't allow the modifier key to be replaced. TODO: This should probably be improved
					if (key.code == PDInputProcessor.MODIFIER_KEY) {
						setKey(defaultKey, 0);
					} else {
						setKey(defaultKey, key.code);

						final PDInputProcessor.GameActionWrapper replacedAction = inputProcessor.setKeyMapping( gameAction, defaultKey, key.code);
						if (replacedAction != null) {
							final ListItem replacedItem = items.get(replacedAction.gameAction);
							replacedItem.setKey(replacedAction.defaultKey, 0);
						}
					}
					hide();
				}
			} );

			return true;
		}
	}
}
