/*
 * Pixel Dungeon
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
package com.shatteredpixel.shatteredpixeldungeon.ui;

import com.watabou.noosa.Game;
import com.watabou.noosa.Gizmo;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Button;
import com.watabou.noosa.ui.Component;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.DungeonTilemap;
import com.shatteredpixel.shatteredpixeldungeon.input.GameAction;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.scenes.CellSelector;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndCatalogus;

public class Toolbar extends Component {

	private Tool btnWait;
	private Tool btnSearch;
	private Tool btnInfo;
	private Tool btnResume;
	private Tool btnInventory;
	private Tool btnQuick;
	private Tool btnQuick2;

	public static int QuickSlots;

	private PickedUpItem pickedUp;

	private boolean lastEnabled = true;

	public Toolbar() {
		super();

		QuickSlots = ShatteredPixelDungeon.quickSlots();

		height = btnInventory.height();
	}

	@Override
	protected void createChildren() {
		
		add( btnWait = new Tool( 0, 7, 20, 24, GameAction.REST ) {
			
			@Override
			protected void onClick() {
                restOneTurn();
			}
			protected boolean onLongClick() {
				restFull();
				return true;
			}

			private void restOneTurn() {
				Dungeon.hero.rest( false );
			}

			private void restFull() {
				Dungeon.hero.rest( true );
			}
		} );
		
		add( btnSearch = new Tool( 20, 7, 20, 24, GameAction.SEARCH ) {
			@Override
			protected void onClick() {
				doSearch();
			}

			private void doSearch() {
				Dungeon.hero.search( true );
			}
		} );
		
		add( btnInfo = new Tool( 40, 7, 21, 24, GameAction.CELL_INFO) {
			@Override
			protected void onClick() {
				getCellInfo();
			}

			private void getCellInfo() {
				GameScene.selectCell(informer);
			}
		} );

		/*
		add( btnResume = new Tool( 61, 7, 21, 24, GameAction.RESUME ) {
			@Override
			protected void onClick() {
				resume();
			}

			private void resume() {
				Dungeon.hero.resume();
			}
		} );
		*/
		
		add( btnInventory = new Tool( 82, 7, 23, 24, GameAction.BACKPACK ) {
			private GoldIndicator gold;
			@Override
			protected void onClick() {
                showBackpack();
			}

            @Override
			protected boolean onLongClick() {
				showCatalogus();
				return true;
			}

			private void showBackpack() {
				GameScene.show(new WndBag(Dungeon.hero.belongings.backpack, null, WndBag.Mode.ALL, null));
			}
			private void showCatalogus() {
				GameScene.show(new WndCatalogus());
			}

			@Override
			protected void createChildren() {
				super.createChildren();
				gold = new GoldIndicator();
				add(gold);
			};
			@Override
			protected void layout() {
				super.layout();
				gold.fill(this);
			};
		} );

		add( btnQuick = new QuickslotTool( 105, 7, 22, 24, 0) );

		btnQuick2 = new QuickslotTool( 105, 7, 22, 24, 1);

		add( pickedUp = new PickedUpItem() );
	}

	@Override
	protected void layout() {
		btnWait.setPos(x, y);
		btnSearch.setPos( btnWait.right(), y );
		btnInfo.setPos( btnSearch.right(), y );
		//btnResume.setPos( btnInfo.right(), y );
		btnQuick.setPos( width - btnQuick.width(), y );
		btnQuick2.setPos( btnQuick.left() - btnQuick2.width(), y );
		if (QuickSlots == 2){
			add(btnQuick2);
			btnQuick2.visible = btnQuick2.active = true;
			btnInventory.setPos( btnQuick2.left() - btnInventory.width(), y );
		} else {
			remove(btnQuick2);
			btnQuick2.visible = btnQuick2.active = false;
			btnInventory.setPos( btnQuick.left() - btnInventory.width(), y );
		}
	}

	@Override
	public void update() {
		super.update();

		if (lastEnabled != Dungeon.hero.ready) {
			lastEnabled = Dungeon.hero.ready;

			for (Gizmo tool : members) {
				if (tool instanceof Tool) {
					((Tool)tool).enable( lastEnabled );
				}
			}
		}

		//btnResume.visible = Dungeon.hero.lastAction != null;

		if (!Dungeon.hero.isAlive()) {
			btnInventory.enable( true );
		}

		//If we have 2 slots, and 2nd one isn't visible, or we have 1, and 2nd one is visible...
		if ((QuickSlots == 1) == btnQuick2.visible){
			layout();
		}
	}

	public void pickup( Item item ) {
		pickedUp.reset( item,
				btnInventory.centerX(),
				btnInventory.centerY() );
	}

	private static CellSelector.Listener informer = new CellSelector.Listener() {
		@Override
		public void onSelect( Integer cell ) {
            GameScene.examineCell( cell );
		}	
		@Override
		public String prompt() {
			return "Select a cell to examine";
		}
	};
	
	private static class Tool extends Button<GameAction> {
		
		private static final int BGCOLOR = 0x7B8073;

		private Image base;
		
		public Tool(int x, int y, int width, int height, GameAction hotKey ) {
			super();

			base.frame( x, y, width, height );

			this.width = width;
			this.height = height;

            this.hotKey = hotKey;
		}
		
		@Override
		protected void createChildren() {
			super.createChildren();

			base = new Image( Assets.TOOLBAR );
			add( base );
		}

		@Override
		protected void layout() {
			super.layout();

			base.x = x;
			base.y = y;
		}

		@Override
		protected void onTouchDown() {
			base.brightness( 1.4f );
		}

		@Override
		protected void onTouchUp() {
			if (active) {
				base.resetColor();
			} else {
				base.tint( BGCOLOR, 0.7f );
			}
		}

		public void enable( boolean value ) {
			if (value != active) {
				if (value) {
					base.resetColor();
				} else {
					base.tint( BGCOLOR, 0.7f );
				}
				active = value;
			}
		}
	}

	private static class QuickslotTool extends Tool {

		private QuickSlotButton slot;

		public QuickslotTool( int x, int y, int width, int height, int slotNum) {
			super(x, y, width, height, GameAction.QUICKSLOT);

			slot = new QuickSlotButton( slotNum );
			add(slot);
		}

		@Override
		protected void layout() {
			super.layout();
			slot.setRect( x + 1, y + 2, width - 2, height - 2 );
		}

		@Override
		public void enable( boolean value ) {
			super.enable( value );
			slot.enable( value );
		}
	}

	private static class PickedUpItem extends ItemSprite {

		private static final float DISTANCE = DungeonTilemap.SIZE;
		private static final float DURATION = 0.2f;

		private float dstX;
		private float dstY;
		private float left;

		public PickedUpItem() {
			super();

			originToCenter();

			active =
					visible =
							false;
		}

		public void reset( Item item, float dstX, float dstY ) {
			view( item.image(), item.glowing() );

			active =
					visible =
							true;

			this.dstX = dstX - ItemSprite.SIZE / 2;
			this.dstY = dstY - ItemSprite.SIZE / 2;
			left = DURATION;

			x = this.dstX - DISTANCE;
			y = this.dstY - DISTANCE;
			alpha( 1 );
		}

		@Override
		public void update() {
			super.update();

			if ((left -= Game.elapsed) <= 0) {

				visible =
						active =
								false;

			} else {
				float p = left / DURATION;
				scale.set( (float)Math.sqrt( p ) );
				float offset = DISTANCE * p;
				x = dstX - offset;
				y = dstY - offset;
			}
		}
	}
}
