package com.watabou.pixeldungeon.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.watabou.input.NoosaInputProcessor;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Component;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.Journal;
import com.watabou.pixeldungeon.input.GameAction;
import com.watabou.pixeldungeon.input.PDInputProcessor;
import com.watabou.pixeldungeon.scenes.PixelScene;
import com.watabou.pixeldungeon.ui.Icons;
import com.watabou.pixeldungeon.ui.ScrollPane;
import com.watabou.pixeldungeon.ui.Window;

import java.util.HashMap;
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

		ScrollPane list = new ScrollPane(content);
		add(list);

		list.setRect(0, 0, width - MARGIN*2, height);
	}

	private void addKey(Component content, int maxWidth, Map.Entry<GameAction, KeyPair> entry) {
		ListItem keyLeft = new ListItem(entry.getKey(), entry.getValue());
		keyLeft.setRect( 0, tempPos, maxWidth, ITEM_HEIGHT );
		tempPos += ITEM_HEIGHT;
		content.add(keyLeft);
	}

	private static class ListItem extends Component {
		private BitmapText action;
		private BitmapText key;
		private BitmapText key2;

		public ListItem(GameAction gameAction, KeyPair keys) {
			super();

			action.text(gameAction.getDescription());
			action.measure();

			key.text(keys.key1 > 0 ? Input.Keys.toString(keys.key1) : TXT_UNASSIGNED);
			key.measure();

			key2.text(keys.key2 > 0 ? Input.Keys.toString(keys.key2) : TXT_UNASSIGNED);
			key2.measure();

			this.key.hardlight(TITLE_COLOR);
			this.key2.hardlight(TITLE_COLOR);
		}

		@Override
		protected void createChildren() {
			action = PixelScene.createText(9);
			add(action);

			key = new BitmapText(PixelScene.font1x);
			add(key);
			key2 = new BitmapText(PixelScene.font1x);
			add(key2);
		}

		@Override
		protected void layout() {
			key.x = width/4*2;
			key2.x = width/4*3;
			key.y = key2.y = PixelScene.align(y + (height - key.height()) / 2);

			action.x = MARGIN;
			action.y = PixelScene.align(key.y + key.baseLine() - action.baseLine());
		}
	}
}
