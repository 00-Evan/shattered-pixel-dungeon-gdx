package com.shatteredpixel.shatteredpixeldungeon.flashcard;

import java.util.Collections;
import java.util.List;
import java.util.Vector;
import com.watabou.utils.Random;

public class FlashDecks
{
	public static final int MAX_DECKS = 8;
	private static Vector<IFlashDeck> decks = new Vector<IFlashDeck>();

	public static void addDeck(IFlashDeck newDeck) {
		decks.add(newDeck);
	}

	public static List<IFlashDeck> getFlashDecks() {
		if (decks.isEmpty()) {
			FlashDecks.addDeck(FlashDeck.getTestDeck());
			// More dummy data for the import UI
			FlashDeck deck1 = new FlashDeck(new Vector<IFlashQuestion>(), "Japanese");
			FlashDeck deck2 = new FlashDeck(new Vector<IFlashQuestion>(), "Pokemon Types");
			FlashDeck deck3 = new FlashDeck(new Vector<IFlashQuestion>(), "Trig Identities");
			deck1.setIsActive(true);
			deck2.setIsActive(false);
			deck3.setIsActive(true);
			FlashDecks.addDeck(deck1);
			FlashDecks.addDeck(deck2);
			FlashDecks.addDeck(deck3);
		}
		return Collections.unmodifiableList(decks);
	}

	public static List<IFlashDeck> getActiveDecks() {
		if (decks.isEmpty()) {
			FlashDecks.addDeck(FlashDeck.getTestDeck());
		}
		Vector<IFlashDeck> activeDecks = new Vector<IFlashDeck>();
		for (int i = 0; i < decks.size(); i++) {
			if (decks.get(i).isActive()) {
				activeDecks.add(decks.get(i));
			}
		}
		if (activeDecks.isEmpty()) {
			activeDecks.add(decks.get(0));
			decks.get(0).setIsActive(true);
		}
		return Collections.unmodifiableList(activeDecks);
	}

	//todo someday: give decks their own weights
	public static IFlashQuestion getQuestion() {
		//random card from random deck
		return FlashDecks.getActiveDecks().get(Random.Int(FlashDecks.getActiveDecks().size())).getQuestion();
	}

	public static int getCount() {
		return decks.size();
	}

	public static boolean isDeckActive(String deckName) {
		for (IFlashDeck deck : decks) {
			if (deck.getDeckName().equals(deckName)) {
				return deck.isActive();
			}
		}

		return false;
	}

	public static boolean setDeckActive(String deckName, boolean shouldBeActive) {
		for (IFlashDeck deck : decks) {
			if (deck.getDeckName().equals(deckName)) {
				deck.setIsActive(shouldBeActive);;
				return true;
			}
		}

		return false;
	}

	public static boolean removeDeck(String deckName) {
		for (IFlashDeck deck : decks) {
			if (deck.getDeckName().equals(deckName)) {
				decks.remove(deck);
				return true;
			}
		}

		return false;
	}
}
