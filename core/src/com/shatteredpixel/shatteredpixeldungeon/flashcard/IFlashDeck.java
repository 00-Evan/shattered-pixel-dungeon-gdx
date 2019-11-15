package com.shatteredpixel.shatteredpixeldungeon.flashcard;

public interface IFlashDeck
{
	IFlashQuestion getQuestion();
	void addQuestion(FlashQuestion newQuestion);
	public String getDeckName();
	public void setDeckName(String deckName);
	public boolean isActive();
	public void setIsActive(boolean isActive);
}
