package com.shatteredpixel.shatteredpixeldungeon.flashcard;

import java.util.Vector;
//import java.util.Random;
import com.watabou.utils.Random;

public class FlashDeck implements IFlashDeck
{
	private Vector<FlashQuestion> questions;
	public FlashDeck(Vector<FlashQuestion> questions)
	{
		this.questions = questions;
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
