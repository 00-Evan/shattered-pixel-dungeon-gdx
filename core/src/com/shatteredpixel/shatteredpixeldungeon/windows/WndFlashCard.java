package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.Chrome;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.flashcard.FlashDecks;
import com.shatteredpixel.shatteredpixeldungeon.flashcard.IFlashQuestion;
import com.watabou.noosa.Game;

public abstract class WndFlashCard extends Window {
  protected static final float BUTTON_HEIGHT = 16;
  protected static final int MARGIN_HORIZONTAL = 2;
  protected static final int MARGIN_VERTICAL = 2;
  protected static final int MARGIN_BETWEEN = 2;
  protected IFlashQuestion question;

  private static final int WIDTH_P = 125;
  private static final int WIDTH_L = 160;

  private RenderedTextBlock tb1;
  private RenderedTextBlock tb2;
  private RenderedTextBlock tb3;

  private float delay;

  public WndFlashCard(IFlashQuestion question) {
    super(0, 0, Chrome.get(Chrome.Type.SCROLL));
    if (question != null) {
      this.question = question;
    } else {
      this.question = FlashDecks.getQuestion();
    }

    final int WIDTH = SPDSettings.landscape() ? WIDTH_L - MARGIN_HORIZONTAL * 2 : WIDTH_P - MARGIN_HORIZONTAL * 2;
    int y = MARGIN_VERTICAL;

    String intro = Messages.get(this, "intro");
    tb1 = PixelScene.renderTextBlock(intro, 6);
    GLog.w(intro);
    tb1.maxWidth(WIDTH);
    tb1.invert();
    tb1.setPos(y, 2);
    add(tb1);

    y += tb1.height() + MARGIN_BETWEEN;

    tb2 = PixelScene.renderTextBlock(this.getText(), 12);
    tb2.maxWidth(WIDTH);
    tb2.invert();
    int centeredAnswerPos = (WIDTH - (int) tb2.width()) / 2;
    tb2.setPos(centeredAnswerPos, y);
    add(tb2);

    y += tb2.height() + MARGIN_BETWEEN;

    tb3 = PixelScene.renderTextBlock(Messages.get(this, "outro"), 6);
    tb3.maxWidth(WIDTH);
    tb3.invert();
    tb3.setPos(2, y);
    add(tb3);

    y += tb3.height() + MARGIN_BETWEEN;
    y += this.renderInputs(y, WIDTH);
    int finalHeight = (int) Math.min(y, 180);
    resize(WIDTH + MARGIN_HORIZONTAL * 2, finalHeight);
  }

  @Override
  public void update() {
    super.update();

    if (delay > 0 && (delay -= Game.elapsed) <= 0) {
      shadow.visible = true;
      chrome.visible = true;
      tb1.visible = true;
      tb2.visible = true;
      tb3.visible = true;
    }
  }
  protected void printOutro()
  {
    GLog.w(Messages.get(this, "outro"));
  }

  protected abstract String getText();

  protected abstract int renderInputs(final int height, final int width);
}