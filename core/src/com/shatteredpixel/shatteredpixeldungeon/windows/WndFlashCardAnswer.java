package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.flashcard.IFlashQuestion;
import com.watabou.noosa.Game;

public class WndFlashCardAnswer extends WndFlashCard {
  WndFlashCardAnswer(final Class<? extends Item> item, IFlashQuestion question) {
    super(item, question);
  }

  @Override
  public String getText() {
    return question.getAnswer();
  }

  @Override
  public int renderInputs(final int height, final int width) {
    RedButton fail = new RedButton("Failure...", 8) {
      @Override
      protected void onClick() {
        hide();
        onQuestionFail();
      }
    };

    RedButton success = new RedButton("Success!", 8) {
      @Override
      protected void onClick() {
        hide();
        onQuestionSuccess();
      }
    };

    fail.setRect(MARGIN_HORIZONTAL, height, (width - MARGIN_BETWEEN) / 2, BUTTON_HEIGHT);
    add(fail);

    success.setRect(MARGIN_HORIZONTAL + fail.width() + (MARGIN_BETWEEN / 2), height, (width - MARGIN_BETWEEN) / 2,
        BUTTON_HEIGHT);

    add(success);
    return (int) BUTTON_HEIGHT + MARGIN_VERTICAL;
  }

  private void onQuestionFail() {
    question.increaseWeight();
    GameScene.show(new WndFlashCardQuestion(item, null));
  }

  private void onQuestionSuccess() {
    try {
      question.decreaseWeight();
      Item selectedItem = (Item) item.getDeclaredConstructor().newInstance();
      selectedItem.collect();
      Game.scene().addToFront(new WndItem(null, selectedItem, true));
    } catch (Exception e) {
      GLog.n(e.getMessage());
    }
  }
}
