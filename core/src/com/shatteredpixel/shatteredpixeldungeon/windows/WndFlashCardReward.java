package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;

import com.shatteredpixel.shatteredpixeldungeon.items.MysticalOrb;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

public class WndFlashCardReward extends Window {

    private static final String AC_USE_AS_POTION = "POTION";
	private static final String AC_USE_AS_SCROLL = "SCROLL";

    private static final int WIDTH = 120;
    private static final int BTN_HEIGHT = 20;
    private static final float GAP = 2;

    public static WndFlashCardReward instance;

    public WndFlashCardReward() {

        super();

        instance = this;

        IconTitle titlebar = new IconTitle();
        MysticalOrb orb;
        try {
            orb = (MysticalOrb) MysticalOrb.class.getDeclaredConstructor().newInstance();
            titlebar.icon(new ItemSprite(orb));
            titlebar.label( Messages.titleCase(orb.name()) );
            orb = null;
            titlebar.setRect( 0, 0, WIDTH, 0 );
		    add( titlebar );
        } catch (Exception e) {
               GLog.n(e.getMessage());
        }
        String rewardMessage = Messages.get(this, "message");
        RenderedTextBlock message = PixelScene.renderTextBlock( "", 6 );
        GLog.i( rewardMessage );
		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add( message );
		
		RedButton btnScroll = new RedButton( Messages.get(this, "Scroll") ) {
			@Override
			protected void onClick() {
				hide();
				GameScene.show(new WndSelectEffect(AC_USE_AS_SCROLL));
			}
		};
		btnScroll.setRect( 0, message.top() + message.height() + GAP, WIDTH, BTN_HEIGHT );
		add( btnScroll );
		
		RedButton btnPotion = new RedButton( Messages.get(this, "Potion") ) {
			@Override
			protected void onClick() {
				hide();
				GameScene.show(new WndSelectEffect(AC_USE_AS_POTION));
			}
		};
		btnPotion.setRect( 0, btnScroll.bottom() + GAP, WIDTH, BTN_HEIGHT );
		add( btnPotion );
		
		resize( WIDTH, (int)btnPotion.bottom() );
	}
	
	@Override
	public void destroy() {
		super.destroy();
		instance = null;
	}
	
	@Override
	public void onBackPressed() {
	}
}
