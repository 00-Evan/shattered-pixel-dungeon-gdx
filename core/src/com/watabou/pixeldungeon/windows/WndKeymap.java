package com.watabou.pixeldungeon.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Game;
import com.watabou.noosa.Gizmo;
import com.watabou.noosa.ui.Button;
import com.watabou.noosa.ui.Component;
import com.watabou.pixeldungeon.input.GameAction;
import com.watabou.pixeldungeon.input.PDInputProcessor;
import com.watabou.pixeldungeon.scenes.PixelScene;
import com.watabou.pixeldungeon.ui.Icons;
import com.watabou.pixeldungeon.ui.ScrollPane;
import com.watabou.pixeldungeon.ui.Window;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class WndKeymap extends Window {
	private static final int MARGIN = 5;
	private static final float ITEM_HEIGHT = 10;
	public static final String TXT_UNASSIGNED = "<None>";

	private int tempPos = 0;

	private ArrayList<ListItem> items = new ArrayList<ListItem>();

	private static class KeyPair {
		public int key1, key2;
	}

	public WndKeymap() {

		int maxWidth = Gdx.graphics.getWidth()/4;
		int maxHeight = Gdx.graphics.getHeight()/4;

		resize(maxWidth, maxHeight);

		PDInputProcessor inputProcessor = (PDInputProcessor) Game.instance.getInputProcessor();
		final Map<Integer, PDInputProcessor.GameActionWrapper> keyMappings = inputProcessor.getKeyMappings();

		Component content = new Component();
		ScrollPane list = new ScrollPane(content) {
			@Override
			public void onClick( float x, float y ) {

				final ListItem item = items.get((int) (y / ITEM_HEIGHT));
				final GameAction action = item.gameAction;
				final KeyPair keys = item.keys;
				final boolean defaultKey = x < width * 3 / 4;

				Game.scene().add( new WndMessage( "Press a key for the action \"" + item.gameAction.getDescription() + "\"" ) {
					@Override
					protected void onKeyDown( NoosaInputProcessor.Key key ) {
						keyMappings.put(key.code, new PDInputProcessor.GameActionWrapper(action, defaultKey));
						if (defaultKey) {
							keys.key1 = key.code;
						} else {
							keys.key2 = key.code;
						}
						item.setValues( action, keys );
						hide();
					}
				});
			}
		};

		Map<GameAction, KeyPair> mappings = new TreeMap<>();
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
		ListItem keyLeft = new ListItem(entry.getKey(), entry.getValue());
		keyLeft.setRect( 0, tempPos, maxWidth, ITEM_HEIGHT );
		tempPos += ITEM_HEIGHT;
		content.add(keyLeft);

		items.add( keyLeft );
	}

/*	private static class TextButton extends Button<GameAction> {
		protected BitmapText text;

		public TextButton() {
			super();
		}

		public void setText(String label) {
			text.text( label );
			text.measure();
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			text = PixelScene.createText( 9 );
			text.hardlight(TITLE_COLOR);
			add( text );
		}
		@Override
		protected void layout() {

			super.layout();

			text.x = x + (int) (width - text.width()) / 2;
			text.y = y + (int) (height - text.baseLine()) / 2;
		}

		@Override
		protected void onClick() {
			super.onClick();

			System.out.println("Clicked");
		}
	}*/

	private static class ListItem extends Component {

		public GameAction gameAction;
		public KeyPair keys;

		private BitmapText action;
		private BitmapText key1;
		private BitmapText key2;

		public ListItem( GameAction gameAction, KeyPair keys ) {
			super();
			setValues( gameAction, keys );
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

			action.x = 0;
			action.y = ty;

			key1.x = PixelScene.align( w4 * 2 + (w4 - key1.width()) / 2 );
			key1.y = y;

			key2.x = PixelScene.align( w4 * 3 + (w4 - key2.width()) / 2 );
			key2.y = y;
		}

		public void setValues( GameAction gameAction, KeyPair keys ) {
			this.gameAction = gameAction;
			this.keys = keys;

			action.text(gameAction.getDescription());
			action.measure();

			key1.text( keys.key1 > 0 ? Input.Keys.toString( keys.key1 ) : TXT_UNASSIGNED );
			key1.measure();

			key2.text(keys.key2 > 0 ? Input.Keys.toString(keys.key2) : TXT_UNASSIGNED);
			key2.measure();
		}
	}
}
