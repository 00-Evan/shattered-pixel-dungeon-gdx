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

package com.shatteredpixel.shatteredpixeldungeon.scenes;

import java.io.File;

import javax.swing.JFileChooser;

import com.shatteredpixel.shatteredpixeldungeon.Chrome;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.flashcard.FlashDecks;
import com.shatteredpixel.shatteredpixeldungeon.flashcard.IFlashDeck;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.Archs;
import com.shatteredpixel.shatteredpixeldungeon.ui.ExitButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.StyledButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.ui.Button;

public class FlashDeckScene extends PixelScene {
	
	private static final int SLOT_WIDTH = 120;
	private static final int SLOT_HEIGHT = 30;
	
	@Override
	public void create() {
		super.create();
		
		uiCamera.visible = false;
		
		int w = Camera.main.width;
		int h = Camera.main.height;
		
		Archs archs = new Archs();
		archs.setSize( w, h );
		add( archs );
		
		ExitButton btnExit = new ExitButton();
		btnExit.setPos( w - btnExit.width(), 0 );
		add( btnExit );
		
		RenderedTextBlock title = PixelScene.renderTextBlock( Messages.get(this, "title"), 9);
		title.hardlight(Window.TITLE_COLOR);
		title.setPos(
				(w - title.width()) / 2f,
				(20 - title.height()) / 2f
		);
		align(title);
		add(title);
		
		int slotGap = SPDSettings.landscape() ? 1 : 2;
		
		float yPos = title.bottom() + 5;
		if (SPDSettings.landscape()) yPos += 8;
		
		for (IFlashDeck flashDeck : FlashDecks.getFlashDecks()) {
			FlashDeckButton deckButton = new FlashDeckButton(flashDeck.getDeckName(), flashDeck.isActive());
			deckButton.setRect((w - SLOT_WIDTH) / 2f, yPos, SLOT_WIDTH, SLOT_HEIGHT);
			yPos += SLOT_HEIGHT + slotGap;
			align(deckButton);
			add(deckButton);
		}
		
		if (FlashDecks.getFlashDecks().size() < FlashDecks.MAX_DECKS){
			FlashDeckButton newDeck = new FlashDeckButton();
			newDeck.setRect((w - SLOT_WIDTH) / 2f, yPos, SLOT_WIDTH, SLOT_HEIGHT);
			yPos += SLOT_HEIGHT + slotGap;
			align(newDeck);
			add(newDeck);
		}
		
		fadeIn();
		
	}
	
	@Override
	protected void onBackPressed() {
		ShatteredPixelDungeon.switchNoFade( TitleScene.class );
	}
	
	private class FlashDeckButton extends Button {
		
		private NinePatch bg;
		private String deckName;
		private RenderedTextBlock name;
		private Image deckIconInactive;
		private Image deckIconActive;
		private StyledButton removeDeckButton;

		public FlashDeckButton()
		{
			this.deckName = null;
			set();
		}

		public FlashDeckButton(String deckName, boolean isActive)
		{
			this.deckName = deckName;
			set(isActive);
		}
		
		@Override
		protected void createChildren() {
			super.createChildren();
			
			bg = Chrome.get(Chrome.Type.GEM);
			add( bg);
			
			name = PixelScene.renderTextBlock(9);
			add(name);
		}
		
		private void set() {
			set(false);
		}

		private void set(boolean isActive){
			if (deckName == null){
				name.text( Messages.get(FlashDeckScene.class, "new") );
			} else {
				name.text( deckName );
				
				deckIconActive = Icons.get(Icons.ACTIVE);
				add(deckIconActive);
				
				deckIconInactive = Icons.get(Icons.INACTIVE);
				add(deckIconInactive);
				
				deckIconActive.visible = isActive;
				deckIconInactive.visible = !isActive;
				
				removeDeckButton = new StyledButton(Chrome.Type.GREY_BUTTON, "", 9) {
					@Override
					protected void onClick() {
						super.onClick();
						FlashDecks.removeDeck(deckName);
						FlashDeckScene.this.create();
					}
				};

				Image delIcon = Icons.get(Icons.DELETE);
				removeDeckButton.icon( delIcon );
				removeDeckButton.setSize( delIcon.width, delIcon.height);
				add(removeDeckButton);
			}
			
			layout();
		}
		
		@Override
		protected void layout() {
			super.layout();
			
			bg.x = x;
			bg.y = y;
			bg.size( width, height );
			
			if (deckName != null){
				deckIconActive.x = x+8;
				deckIconActive.y = y + (height - deckIconActive.height())/2f;
				align(deckIconActive);
				deckIconInactive.x = x+8;
				deckIconInactive.y = y + (height - deckIconInactive.height())/2f;
				align(deckIconActive);
				
				name.setPos(
						deckIconActive.x + deckIconActive.width() + 6,
						y + (height - name.height())/2f
				);
				align(name);
				
				float buttonX = this.right() - 8 - removeDeckButton.width();
				float buttonY = this.top() + 8;
				removeDeckButton.setPos(buttonX, buttonY);
			} else {
				name.setPos(
						x + (width - name.width())/2f,
						y + (height - name.height())/2f
				);
				align(name);
			}
		}
		
		@Override
		protected void onClick() {
			if (deckName == null) {
				JFileChooser fc = new JFileChooser();

				int returnVal = fc.showOpenDialog(null);
 
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();

					// TODO: Call Johnathan's importing functions here
					// AFAIK this will only work on desktops, when porting this to Android this SO thread may be useful:
					//		https://stackoverflow.com/questions/7856959/android-file-chooser
				}
			} else {
				deckIconActive.visible = !deckIconActive.visible;
				deckIconInactive.visible = !deckIconActive.visible;
				FlashDecks.setDeckActive(deckName, deckIconActive.visible);
			}
		}
	}
}
