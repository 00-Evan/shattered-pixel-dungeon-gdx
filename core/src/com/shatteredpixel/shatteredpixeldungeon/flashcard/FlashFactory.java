package com.shatteredpixel.shatteredpixeldungeon.flashcard;

public class FlashFactory
{
	public static IFlashQuestion newFlashQuestion(String question, String answer)
	{
		return new FlashQuestion(question,answer);
	}
	public static IFlashQuestion newFlashQuestion(String question, String answer)
	{
		return null;
	}
	public static IFlashDeck newFlashDeck(IFlashQuestion[] questions)
	{
		return null;
	}
	public static IFlashDeckDAO getFlashDeckDAO()
	{
		return null;
	}
	public static IFlashWindow getFlashWindow()
	{
		return null;
	}
	public static IMysticalCard getMysticalCard()
	{
		return null;
	}
}
