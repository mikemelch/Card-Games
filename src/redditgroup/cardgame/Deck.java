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

    public static enum Cards{
        TWO(2),
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
    }

    public String getSuitString()
    {
        return suit.toString();
    }

    public Suits getSuit(){
        return suit;
    }

    public int getCardNumber(){
        return card.ordinal();
    }

    public String getFileName(){
        return "Cards/" + card.toString() + " of " + suit.toString() + ".gif";
    }


}
