package com.shatteredpixel.shatteredpixeldungeon.flashcard;

public class FlashQuestion implements IFlashQuestion
{
	private String question;
	private String answer;
	FlashQuestion(String question, String answer)
	{
		this.question = question;
		this.answer = answer;
	}
	String getQuestion() {return question;}
	String getAnswer() {return answer;}
}