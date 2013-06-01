package deck;
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
		
		String [] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
		
		for(String s: suits){
			for(i = 1; i <= 13; i++){
				deck[cardNum] = new Card(s, i);
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
