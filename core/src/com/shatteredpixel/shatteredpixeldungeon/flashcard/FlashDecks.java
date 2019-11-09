package com.shatteredpixel.shatteredpixeldungeon.flashcard;

import java.util.Vector;
import com.watabou.utils.Random;

public class FlashDecks
{
	private static Vector<FlashDeck> decks = new Vector<FlashDeck>();

	public static void addDeck(FlashDeck newDeck) {
		decks.add(newDeck);
	}

	public static Vector<FlashDeck> getFlashDecks() {
		if (decks.isEmpty()) {
			FlashDecks.addDeck(FlashDeck.getTestDeck());
		}
		return decks;
	}

	public static Vector<FlashDeck> getActiveDecks() {
		if (decks.isEmpty()) {
			FlashDecks.addDeck(FlashDeck.getTestDeck());
		}
		Vector<FlashDeck> activeDecks = new Vector<FlashDeck>();
		for (int i = 0; i < decks.size(); i++) {
			if (decks.get(i).isActive()) {
				activeDecks.add(decks.get(i));
			}
		}
		if (activeDecks.isEmpty()) {
			activeDecks.add(decks.get(0));
			decks.get(0).setIsActive(true);
		}
		return activeDecks;
	}

	//todo someday: give decks their own weights
	public static FlashQuestion getQuestion() {
		//random card from random deck
		return FlashDecks.getActiveDecks().get(Random.Int(FlashDecks.getActiveDecks().size())).getQuestion();
	}
}
