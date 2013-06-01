package hand;
///////////////////////////////////
//Michael Melchione
//AP Computer Science A
//1-6-12
//Card Games Program
///////////////////////////////////


import java.util.ArrayList;

import deck.Card;

public class BJHand extends Hand
{
	public BJHand()
	{
		super();
	}
	
	public int BJValue()
	{
		int i, value = 0;
		Card temp;
		int cardVal;
		boolean ace = false;
		
		for(i = 0; i < getSize(); i++)
		{
			temp = getCard(i);
			cardVal = temp.getValue();
			
			if(cardVal > 10)
				cardVal = 10;
			
			else if(cardVal == 1)
				ace = true;
			
			value += cardVal;
		}
		
		if((ace == true) && ((value + 10) <= 21))
			value += 10;
		
		return value;
	}
}
