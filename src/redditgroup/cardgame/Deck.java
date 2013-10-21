package redditgroup.cardgame;
///////////////////////////////////
//Michael Melchione
//AP Computer Science A
//1-6-12
//Card Games Program
///////////////////////////////////

public class Deck {
	private Card [] deck;
	private int dealt;

    public Deck(){
		int i;
		int cardNum = 0;
		
		deck = new Card[52];


        for(Card.Suits s : Card.Suits.values()){
           for(Card.Cards c : Card.Cards.values()){
               deck[cardNum] = new Card(c,s);
               cardNum++;
            }
        }
		
		dealt = 0;
	}
	
    /**
     * Shuffles the cards of the deck.
     */
	public void shuffle(){
		int i;
		int rand;
		Card temp;
		
		for(i = 0; i < deck.length; i++){
			rand = (int)(Math.random() * 52);
			temp = deck[i];
			deck[i] = deck[rand];
			deck[rand] = temp;
		}
		
		dealt = 0;
	}
	
	/**
	 * Deals a Card from the Deck. The dealt card will no more be deal by the 
	 * deck. If no cards are left, a IllegalStateException is thrown.
	 * @return A Card from the deck.
	 */
	public Card dealCard(){
		if(dealt == 52)
			throw new IllegalStateException("No cards left in the deck!!!");
		
		else{
			dealt++;
			return (deck[dealt - 1]);
		}
		
	}
}



class Card{
    private Cards card;
    private Suits suit;
    private String UID;

    public static enum Cards{
        Two(2),
        Three(3),
        Four(4),
        Five(5),
        Six(6),
        Seven(7),
        Eight(8),
        Nine(9),
        Ten(10),
        Jack(11),
        Queen(12),
        King(13),
        Ace(14);

        private final int value;

        Cards(int value){
            this.value = value;
        }

    };


    public static enum Suits{Hearts,Diamonds,Clubs,Spades};


    public Card(Cards card,Suits suit)
    {
        this.card = card;
        this.suit = suit;
        this.UID = Integer.toString(card.ordinal()) + Integer.toString(suit.ordinal());
    }

    /**
     * Return the {@link Suits}' String of the given Card. Possible values:
     * - "Hearts"
     * - "Diamonds"
     * - "Clubs"
     * - "Spades"
     * @return String of the Suit of the Card.
     */
    public String getSuitString()
    {
        return suit.toString();
    }

    /**
     * Return the {@link Suits} of the given Card. 
     * @return Suit of the Card.
     */
    public Suits getSuit(){
        return suit;
    }

    /**
     * Gets the Card Number.
     * @return Card Number.
     */
    public int getCardNumber(){
        return card.ordinal();
    }

    /**
     * Return the relative file location of the image of the Card.
     * @return File location of the card's image.
     */
    public String getFileName(){
        return "Cards/" + card.toString() + " of " + suit.toString() + ".gif";
    }

    public String getUID(){
        return UID;
    }

    public String toString(){
        return card.toString() + " of " + suit.toString();
    }


}
