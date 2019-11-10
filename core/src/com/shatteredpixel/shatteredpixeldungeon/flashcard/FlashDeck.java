package com.shatteredpixel.shatteredpixeldungeon.flashcard;

import java.util.Vector;
import com.watabou.utils.Random;

public class FlashDeck implements IFlashDeck
{
	private Vector<FlashQuestion> questions;
	private boolean isActive;
	private String deckName;

	public FlashDeck(Vector<FlashQuestion> questions, String deckName)
	{
		this.questions = questions;
		this.deckName = deckName;
	}

	public static FlashDeck getTestDeck() {
		Vector<FlashQuestion> multQuestions = new Vector<FlashQuestion>();
		for (int i = 1; i < 5; i++) {
			for (int j = 1; j < 5; j++) {
				String q = Integer.toString(i) + " * " + Integer.toString(j);
				String a = Integer.toString(i*j);
				multQuestions.add(new FlashQuestion(q, a));
			}
		}
		return new FlashDeck(multQuestions, "Times Tables");
	}

	public String getDeckName() {
		return deckName;
	}

	public void setDeckName(String deckName) {
		this.deckName = deckName;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void addQuestion(FlashQuestion newQuestion) {
		questions.add(newQuestion);
	}

	//this function could definitely be made faster if it ends up slowing things down
	public FlashQuestion getQuestion() {
		int totalWeight = 0;
		for (int i = 0; i < questions.size(); i++) {
			totalWeight += questions.get(i).getWeight();
		}
		int rand = Random.Int(totalWeight);
		for (int i = 0; i < questions.size(); i++) {
			rand -= questions.get(i).getWeight();
			if (rand <= 0) {
				return questions.get(i);
			}
		}
		//this should never happen, but I put it here just in case.
		return questions.get(0);
	}
}
