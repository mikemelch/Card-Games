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
	
	/**
	 * Adds a card to the hand. If the hand is full, the method returns false.
	 * @param currentCard Card to be added to the hand.
	 * @return true on success, false on failure.
	 */
	public boolean addCard(Card currentCard)
	{
        if(hand.size() >= maxSize){
             return false;
        }

		hand.add(currentCard);
        return true;
	}
	
	/**
	 * Return the number of Cards in the Hand.
	 * @return The number of Cards in the Hand.
	 */
	public int getSize()
	{
		return hand.size();
	}
	
	/**
	 * Removes a card from the Hand.
	 * @param cardPosition Position of the Card in the Hand that 
	 * will be removed.
	 */
	public void removeCard(int cardPosition)
	{
		hand.remove(cardPosition - 1);
	}
	
	/**
	 * Removes a card from the Hand.
	 * @param currentCard Card that will be removed.
	 */
	public void removeCard(Card currentCard)
	{
		hand.remove(currentCard);
	}
	
	/**
	 * Empties the Hand of all his Cards.
	 */
	public void clearHand()
	{
		hand.clear();
	}
	
	/**
	 * Gets the Card at the given Position in the Hand.
	 * @param i The position of the Card in the Hand.
	 * @return The Card at the given position in the Hand.
	 */
	public Card getCard(int i)
	{
		return hand.get(i);
	}
	
	/**
	 * Return the cards of the hand in an ArrayList.
	 * @return Cards of the Hand.
	 */
	public ArrayList<Card> getHand(){
		return hand;
	}

	/**
	 * Gets the maximal number of Cards that are allowed in the Hand.
	 * @return the maximal number of Cards that are allowed in the Hand.
	 */
    public int getMaxSize(){
        return maxSize;
    }
}
