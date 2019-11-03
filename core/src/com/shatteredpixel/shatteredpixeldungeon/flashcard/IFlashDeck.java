package com.shatteredpixel.shatteredpixeldungeon.flashcard;

public interface IFlashDeck
{
	IFlashQuestion getQuestion();
	void addQuestion(FlashQuestion newQuestion);
}
