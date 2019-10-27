package com.shatteredpixel.shatteredpixeldungeon.flashcard;

public class FlashQuestion implements IFlashQuestion
{
	private String question;
	private String answer;

	//the question's weight determines its likelihood of being drawn next from the deck
	private int weight;
	FlashQuestion(String question, String answer)
	{
		this.question = question;
		this.answer = answer;
		this.weight = 10;
	}
	public String getQuestion() {return question;}
	public String getAnswer() {return answer;}
	public int getWeight() {return weight;}
	public int increaseWeight() {
		weight += 10;
		return weight;
	}
	public int decreaseWeight() {
		weight /= 2;
		if (weight < 1) {
			weight = 1;
		}
		return weight;
	}

}
