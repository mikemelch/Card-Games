///////////////////////////////////
//Michael Melchione
//AP Computer Science A
//1-12-12
//BlackJack Program
///////////////////////////////////

import java.util.ArrayList;

public class Hand 
{
	protected ArrayList<Card> hand;

	public Hand()
	{
		hand = new ArrayList<Card>();
	}
	
	public void addCard(Card temp)
	{
		hand.add(temp);
	}
	
	public int getSize()
	{
		return hand.size();
	}
	
	public void removeCard(int position)
	{
		hand.remove(position - 1);
	}
	
	public void removeCard(Card temp)
	{
		hand.remove(temp);
	}
	
	public void clearHand()
	{
		hand.clear();
	}
	
	public Card getCard(int i)
	{
		return hand.get(i);
	}
}
