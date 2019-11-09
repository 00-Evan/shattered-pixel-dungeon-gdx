package com.shatteredpixel.shatteredpixeldungeon.flashcard;

public interface IFlashQuestion
{
	String getQuestion();
	String getAnswer();
	int getWeight();
	int increaseWeight();
	int decreaseWeight();
}
