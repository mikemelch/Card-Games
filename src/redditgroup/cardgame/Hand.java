package redditgroup.cardgame;
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
    private int maxSize; //Maximum size of the hand.

	public Hand(int maxSize)
	{
		hand = new ArrayList<Card>();
        this.maxSize = maxSize;
	}
	
	public boolean addCard(Card temp)
	{
        if(hand.size() >= maxSize){
             return false;
        }

		hand.add(temp);
        return true;
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
	
	public ArrayList<Card> getHand(){
		return hand;
	}

    public int getMaxSize(){
        return maxSize;
    }
}
