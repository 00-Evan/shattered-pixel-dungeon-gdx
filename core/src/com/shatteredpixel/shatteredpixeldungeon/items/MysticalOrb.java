package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.Potion;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfExperience;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfFrost;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHaste;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfInvisibility;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLevitation;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfLiquidFlame;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfMindVision;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfParalyticGas;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfPurity;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfStrength;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfToxicGas;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.Scroll;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfIdentify;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfLullaby;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRage;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRecharging;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfRetribution;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTeleportation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTerror;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfTransmutation;
import com.shatteredpixel.shatteredpixeldungeon.items.scrolls.ScrollOfUpgrade;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.ui.IconButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.windows.IconTitle;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndFlashCardQuestion;
import com.watabou.noosa.Image;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Arrays;
import java.util.List;

public class MysticalOrb extends Item {
	private static final String AC_USE_AS_POTION = "POTION";
	private static final String AC_USE_AS_SCROLL = "SCROLL";

	{
		image = ItemSpriteSheet.MYSTICAL_ORB;
		stackable = true;
		defaultAction = AC_USE_AS_POTION;
		bones = true;
	}

	@Override
	public ArrayList<String> actions(Hero hero) {
		ArrayList<String> actions = super.actions(hero);
		actions.add(AC_USE_AS_POTION);
		actions.add(AC_USE_AS_SCROLL);
		return actions;
	}

	@Override
	public void execute(Hero hero, String action) {
		super.execute(hero, action);

		if (action.equals(AC_USE_AS_POTION) || action.equals(AC_USE_AS_SCROLL)) {
			detach(hero.belongings.backpack);
			GameScene.show(new WndSelectEffect(action));
		}
	}

	@Override
	public boolean isUpgradable() {
		return false;
	}

	@Override
	public boolean isIdentified() {
		return true;
	}

	@Override
	public int price() {
		return 100 * quantity;
	}

	List<Class<? extends Item>> potionClasses = Arrays.asList(PotionOfExperience.class, PotionOfFrost.class,
			PotionOfHaste.class, PotionOfHealing.class, PotionOfInvisibility.class, PotionOfLevitation.class,
			PotionOfLiquidFlame.class, PotionOfMindVision.class, PotionOfParalyticGas.class, PotionOfPurity.class,
			PotionOfToxicGas.class);

	List<Class<? extends Item>> scrollClasses = Arrays.asList(ScrollOfIdentify.class, ScrollOfLullaby.class,
			ScrollOfMagicMapping.class, ScrollOfMirrorImage.class, ScrollOfRetribution.class, ScrollOfRage.class,
			ScrollOfRecharging.class, ScrollOfRemoveCurse.class, ScrollOfTeleportation.class, ScrollOfTerror.class,
			ScrollOfTransmutation.class);

	HashMap<Class<? extends Item>, Integer> imageMap= new HashMap<Class<? extends Item>, Integer>() {{
		put(PotionOfExperience.class,0);
		put(PotionOfFrost.class,1);
		put(PotionOfHaste.class,2);
		put(PotionOfHealing.class,3);
		put(PotionOfInvisibility.class,4);
		put(PotionOfLevitation.class,5);
		put(PotionOfLiquidFlame.class,6);
		put(PotionOfMindVision.class,7);
		put(PotionOfParalyticGas.class,8);
		put(PotionOfPurity.class,9);
		//put(PotionOfStrength.class,10);
		put(PotionOfToxicGas.class,11);
		put(ScrollOfIdentify.class,0);
		put(ScrollOfLullaby.class,1);
		put(ScrollOfMagicMapping.class,2);
		put(ScrollOfMirrorImage.class,3);
		put(ScrollOfRetribution.class,4);
		put(ScrollOfRage.class,5);
		put(ScrollOfRecharging.class,6);
		put(ScrollOfRemoveCurse.class,7);
		put(ScrollOfTeleportation.class,8);
		put(ScrollOfTerror.class,9);
		put(ScrollOfTransmutation.class,10);
		//put(ScrollOfUpgrade.class,11);
	 }};
	

	static Class<? extends Item> curSelection = null;

	public class WndSelectEffect extends Window {
		private static final int WIDTH = 120;
		private static final int BTN_SIZE = 20;

		public WndSelectEffect(String action) {
			IconTitle titlebar = new IconTitle();
			titlebar.icon(new ItemSprite(ItemSpriteSheet.MYSTICAL_ORB, null));
			titlebar.label(Messages.get(MysticalOrb.class, "name"));
			titlebar.setRect(0, 0, WIDTH, 0);
			add(titlebar);

			RenderedTextBlock text = PixelScene.renderTextBlock(6);
			text.text(Messages.get(this, action + "_text"));
			text.setPos(0, titlebar.bottom());
			text.maxWidth(WIDTH);
			add(text);

			final RedButton choose = new RedButton("") {
				@Override
				protected void onClick() {
					super.onClick();
					GameScene.show(new WndFlashCardQuestion(curSelection, null));
					hide();
				}
			};

			choose.visible = false;
			choose.icon(new ItemSprite(new MysticalOrb()));
			choose.enable(false);
			choose.setRect(0, 95, WIDTH, 20);
			add(choose);

			List<Class<? extends Item>> classList = action.equals(AC_USE_AS_POTION) ? potionClasses : scrollClasses;
			float left = (WIDTH - BTN_SIZE * ((classList.size() + 1) / 2)) / 2f;
			float top = text.bottom() + 5;
			int row = action.equals(AC_USE_AS_POTION) ? 0 : 16;
			int placed = 0;
			
			HashSet<Class<? extends Potion>> knownPotions = Potion.getKnown();
			HashSet<Class<? extends Scroll>> knownScrolls = Scroll.getKnown();
			for (int i = 0; i < classList.size(); ++i) {
				final Class<? extends Item> itemClass = classList.get(i);
				if (knownPotions.contains(itemClass) || knownScrolls.contains(itemClass))
				{
					IconButton btn = new IconButton() {
						@Override
						protected void onClick() {
							curSelection = itemClass;
							choose.visible = true;
							choose.text(Messages.get(curSelection, "name"));
							choose.enable(true);
							super.onClick();
						}
					};
					Image im = new Image(Assets.CONS_ICONS, 7 * imageMap.get(itemClass), row, 7, 8);
					im.scale.set(2f);
					btn.icon(im);
					btn.setRect(left + placed * BTN_SIZE, top, BTN_SIZE, BTN_SIZE);
					add(btn);
	
					++placed;
					if (placed == ((classList.size() + 1) / 2)) {
						placed = 0;
						if (classList.size() % 2 == 1) {
							left += BTN_SIZE / 2f;
						}
						top += BTN_SIZE;
					}
				}
			}

			resize(WIDTH, 115);
		}

		@Override
		public void onBackPressed() {
			super.onBackPressed();
			new MysticalOrb().collect();
		}
	}
}
