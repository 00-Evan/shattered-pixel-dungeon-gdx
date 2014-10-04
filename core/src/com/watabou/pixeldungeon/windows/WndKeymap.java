package com.watabou.pixeldungeon.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Game;
import com.watabou.noosa.ui.Button;
import com.watabou.noosa.ui.Component;
import com.watabou.pixeldungeon.input.GameAction;
import com.watabou.pixeldungeon.input.PDInputProcessor;
import com.watabou.pixeldungeon.scenes.PixelScene;
import com.watabou.pixeldungeon.ui.ScrollPane;
import com.watabou.pixeldungeon.ui.Window;

import java.util.Map;
import java.util.TreeMap;

public class WndKeymap extends Window {
	private static final int MARGIN = 5;
	private static final float ITEM_HEIGHT = 10;
	public static final String TXT_UNASSIGNED = "<None>";

	private int tempPos = 0;

	private static class KeyPair {
		public int key1, key2;
	}

	public WndKeymap() {

		int maxWidth = Gdx.graphics.getWidth()/4;
		int maxHeight = Gdx.graphics.getHeight()/4;

		resize(maxWidth, maxHeight);

		Component content = new Component();
		ScrollPane list = new ScrollPane(content);
		PDInputProcessor inputProcessor = (PDInputProcessor) Game.instance.getInputProcessor();
		Map<Integer, PDInputProcessor.GameActionWrapper> keyMappings = inputProcessor.getKeyMappings();

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

		content.setSize(maxWidth, tempPos);

		add(list);

		list.setRect(0, 0, width - MARGIN*2, height);
	}

	private void addKey(Component content, int maxWidth, Map.Entry<GameAction, KeyPair> entry) {
		ListItem keyLeft = new ListItem(entry.getKey(), entry.getValue());
		keyLeft.setRect( 0, tempPos, maxWidth, ITEM_HEIGHT );
		tempPos += ITEM_HEIGHT;
		content.add(keyLeft);
	}

	private static class TextButton extends Button<GameAction> {
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
	}

	private static class ListItem extends Component {
		private BitmapText action;
		private TextButton key, key2;

		public ListItem(GameAction gameAction, KeyPair keys) {
			super();

			action.text(gameAction.getDescription());
			action.measure();

			key.setText(keys.key1 > 0 ? Input.Keys.toString(keys.key1) : TXT_UNASSIGNED);
			key2.setText(keys.key2 > 0 ? Input.Keys.toString(keys.key2) : TXT_UNASSIGNED);
		}

		@Override
		protected void createChildren() {
			action = PixelScene.createText(9);
			add(action);

			key = new TextButton();
			add(key);
			key2 = new TextButton();
			add(key2);
		}

		@Override
		protected void layout() {
			final float newY = PixelScene.align(y + (height - key.height()) / 2);
			final float oldH = key.height();
			final float w4 = width / 4;

			key.setPos(w4 * 2, newY);
			key.setSize(w4, oldH);
			key2.setPos(w4 * 3, newY);
			key2.setSize(w4, oldH);

			action.x = MARGIN;
			action.y = key.top();
		}
	}
}
