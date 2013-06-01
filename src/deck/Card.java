package deck;
///////////////////////////////////
//Michael Melchione
//AP Computer Science A
//1-6-12
//Card Games Program
///////////////////////////////////

public class Card 
{
	private String suit;
	private int value;
	
	public Card()
	{
		suit = "suit";
		value = 0;
	}
	
	public Card(String s, int v)
	{
		suit = s;
		value = v;
	}

	public String getSuit()
	{
		return suit;
	}
	
	public int getValue()
	{
		return value;
	}

	
	public String showVal()
	{
		switch(value)
		{
			case 1:
				return "Ace";
			case 2:
				return "Two";
			case 3:
				return "Three";
			case 4:
				return "Four";
			case 5:
				return "Five";
			case 6:
				return "Six";
			case 7:
				return "Seven";
			case 8:
				return "Eight";
			case 9:
				return "Nine";
			case 10:
				return "Ten";
			case 11:
				return "Jack";
			case 12:
				return "Queen";
			case 13:
				return "King";
			default:
				return "opps";
		}
	}
}
