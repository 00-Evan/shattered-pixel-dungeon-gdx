package com.watabou.pixeldungeon.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Game;
import com.watabou.noosa.ui.Component;
import com.watabou.pixeldungeon.input.GameAction;
import com.watabou.pixeldungeon.input.PDInputProcessor;
import com.watabou.pixeldungeon.scenes.PixelScene;
import com.watabou.pixeldungeon.ui.ScrollPane;
import com.watabou.pixeldungeon.ui.Window;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class WndKeymap extends Window {
	private static final int MARGIN = 5;
	private static final float ITEM_HEIGHT = 10;
	public static final String TXT_UNASSIGNED = "<None>";

	private int tempPos = 0;

	private final List<GameAction> actions = new ArrayList<>();
	private final Map<GameAction, ListItem> items = new HashMap<>(32);

	private static class KeyPair {
		public int key1, key2;
	}

	public WndKeymap() {
		final int maxWidth = Gdx.graphics.getWidth()/4;
		final int maxHeight = Gdx.graphics.getHeight()/4;

		resize(maxWidth, maxHeight);

		final PDInputProcessor inputProcessor = (PDInputProcessor) Game.instance.getInputProcessor();
		final Map<Integer, PDInputProcessor.GameActionWrapper> keyMappings = inputProcessor.getKeyMappings();

		final Component content = new Component();
		final ScrollPane list = new ScrollPane(content) {
			@Override
			public void onClick( float x, float y ) {
				// FIXME: This is obviously error-prone
				final GameAction action = actions.get((int) (y / ITEM_HEIGHT));
				if (action == null)
					return;

				final ListItem item = items.get(action);
				final boolean defaultKey = x < width * 3 / 4;

				Game.scene().add( new WndMessage( "Press a key for the action \"" + item.gameAction.getDescription() + "\"," +
						" or press " + Input.Keys.toString(PDInputProcessor.MODIFIER_KEY) + " to remove the mapping" )
				{
					@Override
					protected void onKeyDown( NoosaInputProcessor.Key key ) {
						int oldKeycode = item.getKey(defaultKey);
						inputProcessor.removeKeyMapping(action, defaultKey, oldKeycode);

						// Don't allow the modifier key to be replaced. TODO: This should probably be improved
						if (key.code == PDInputProcessor.MODIFIER_KEY) {
							item.setKey(defaultKey, 0);
						} else {
							item.setKey(defaultKey, key.code);

							final PDInputProcessor.GameActionWrapper replacedAction = inputProcessor.setKeyMapping(action, defaultKey, key.code);
							if (replacedAction != null) {
								final ListItem replacedItem = items.get(replacedAction.gameAction);
								replacedItem.setKey(replacedAction.defaultKey, 0);
							}
						}
						hide();
					}
				});
			}
		};

		final Map<GameAction, KeyPair> mappings = new TreeMap<>();
		for (Map.Entry<Integer, PDInputProcessor.GameActionWrapper> entry : keyMappings.entrySet()) {
			final Integer key = entry.getKey();
			final PDInputProcessor.GameActionWrapper value = entry.getValue();

			final GameAction action = value.gameAction;
			KeyPair keyPair = mappings.get(action);
			if (keyPair == null) {
				mappings.put(action, keyPair = new KeyPair());
			}
			if (value.defaultKey) {
				keyPair.key1 = key;
			} else {
				keyPair.key2 = key;
			}
		}
		for (Map.Entry<GameAction, KeyPair> entry : mappings.entrySet()) {
			addKey(content, maxWidth, entry);
		}

		content.setSize( 0, tempPos);

		add(list);

		list.setRect(0, 0, width, height);
	}

	private void addKey(Component content, int maxWidth, Map.Entry<GameAction, KeyPair> entry) {
		final GameAction action = entry.getKey();
		final ListItem keyItem = new ListItem(action, entry.getValue());
		keyItem.setRect(0, tempPos, maxWidth, ITEM_HEIGHT);
		tempPos += ITEM_HEIGHT;
		content.add(keyItem);

		actions.add(action);
		items.put(action, keyItem);
	}

	private static class ListItem extends Component {
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
			key1.hardlight( TITLE_COLOR );
			add( key1 );

			key2 = PixelScene.createText( 9 );
			key2.hardlight( TITLE_COLOR );
			add( key2 );
		}

		@Override
		protected void layout() {
			final float ty = PixelScene.align( y + (height - action.baseLine()) / 2 );
			final float w4 = width / 4;

			action.x = MARGIN;
			action.y = ty;

			key1.x = PixelScene.align( w4 * 2 + (w4 - key1.width()) / 2 );
			key1.y = y;

			key2.x = PixelScene.align( w4 * 3 + (w4 - key2.width()) / 2 );
			key2.y = y;
		}

		private void reconfigureKeysText() {
			key1.text(keys.key1 > 0 ? Input.Keys.toString(keys.key1) : TXT_UNASSIGNED);
			key1.measure();

			key2.text(keys.key2 > 0 ? Input.Keys.toString(keys.key2) : TXT_UNASSIGNED);
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
	}
}
