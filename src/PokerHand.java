import java.util.ArrayList;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class PokerHand extends Hand{
	
	public PokerHand(){
		super();
	}
	
	public int[] PokerValue(){
		int [] pv = new int[7];
		int curr = 1;
		int clubs = 0, spades = 0, hearts = 0, diamonds = 0;
		int s = 0;
		boolean straight = false, flush = false;
		Card temp[] = new Card[hand.size()];
		int n = temp.length;
		
		for(int i = 0; i < hand.size(); i++){
			temp[i] = hand.get(i);
		}
		
		for (int pass=1; pass < n; pass++) { 
	        for (int i=0; i < n-pass; i++) {
	            if (temp[i].getValue() > temp[i+1].getValue()) {
	                Card t = temp[i];  
	                temp[i] = temp[i+1];  
	                temp[i+1] = t;
	            }
	        }
	    }

		for(int i = 0; i < hand.size(); i++){
			if(getCard(i).getSuit().equals("Clubs")){
				clubs++;
			}
			else if(getCard(i).getSuit().equals("Spades")){
				spades++;
			}
			else if(getCard(i).getSuit().equals("Hearts")){
				hearts++;
			}
			else
				diamonds++;
			
		}
		
		if((clubs == 5) || (spades == 5) || (hearts == 5) || (diamonds == 5)){
			flush = true;
			pv[1] = temp[hand.size() - 1].getValue();
			pv[2] = temp[hand.size() - 2].getValue();
			curr += 2;
		}
			
		
		for(int i = 0; i < hand.size() - 1; i++){
			if(temp[i+1].getValue() - temp[i].getValue() == 1){
				s++;
			}	
			else{
				break;
			}
		}	
		
		if(s == 4){
			straight = true;
			pv[1] = temp[hand.size() - 1].getValue();
			pv[2] = temp[hand.size() - 2].getValue();
			curr += 2;
		}
		
		boolean pair = false, twopair = false, three = false, fullhouse = false, fourkind = false;
		for(int i = 1; i <= 13; i++){
			int count = 0;
			for(Card c : hand){
				if(c.getValue() == i){
					count++;
				}
			}
			if(count == 2){
				if(pair){
					twopair = true;
					pv[curr] = i;
					curr++;
				}
				else if(three){
					fullhouse = true;
					pv[curr] = i;
					curr++;
				}
				else{
					pair = true;
					pv[curr] = i;
					curr++;
				}
			}
			else if(count == 3){
				if(pair){
					fullhouse = true;
					pv[curr] = i;
					curr++;
				}
				else{
					three = true;
					pv[curr] = i;
					curr++;
				}
			}
			else if(count == 4){
				fourkind = true;
				pv[curr] = i;
				curr++;
			}
			
		}
		
		if((straight) && (flush)){
			pv[0] = 10;
		}
		else if(fourkind){
			pv[0] = 9;
		}
		else if(fullhouse){
			pv[0] = 8;
		}
		else if(flush){
			pv[0] = 7;
		}
		else if(straight){
			pv[0] = 6;
		}
		else if(three){
			pv[0] = 5;
		}
		else if(twopair){
			pv[0] = 4;
		}
		else if(pair){
			pv[0] = 3;
		}
		else
			pv[0] = 2;
		
		return pv;

	}
	public int getHighCard(){
		Card highcard = new Card("", -1);
		
		for(int i = 0; i < hand.size(); i++){
			if(hand.get(i).getValue() > highcard.getValue()){
				highcard = hand.get(i);
			}
		}
		
		return highcard.getValue();
	}
	
	public String showPokerValue(){
		if(PokerValue()[0] == 10){
			return "Straight Flush";
		}
		else if(PokerValue()[0] == 9){
			return "Four of a Kind";
		}
		else if(PokerValue()[0]== 8){
			return "Full House";
		}
		else if(PokerValue()[0] == 7){
			return "Flush";
		}
		else if(PokerValue()[0] == 6){
			return "Straight";
		}
		else if(PokerValue()[0] == 5){
			return "Three of a Kind";
		}
		else if(PokerValue()[0] == 4){
			return "Two Pair";
		}
		else if(PokerValue()[0] == 3){
			return "Pair";
		}
		else{
			return "Higher Card";
		}
		
	}

	public void dealerExchange(Deck d, JLabel [] dc){
		ArrayList<Card> keepcards = new ArrayList<Card>();
		
		if(PokerValue()[0] == 10 || PokerValue()[0] == 8 || PokerValue()[0] == 7 || PokerValue()[0] == 6){
			return;
		}
		else if(PokerValue()[0] == 9){
			int val = 0;
			if(hand.get(0).getValue() == hand.get(1).getValue()){
				val = hand.get(0).getValue();
			}
				
			if(hand.get(3).getValue() == hand.get(4).getValue()){
				val = hand.get(0).getValue();
			}
			Card c = null;
			
			for(int i = 0; i < 5; i++){
				c = hand.get(i);	
				
				if(c.getValue() == val){
					keepcards.add(c);
				}
				else if(c.getValue() < 9){
					keepcards.add(d.dealCard());
				}
				else{
					keepcards.add(c);
				}
			}
		}
		
		else if(PokerValue()[0] == 5){
			int val = 0;
			if(hand.get(0).getValue() == hand.get(1).getValue()){
				val = hand.get(0).getValue();
			}
				
			else if(hand.get(3).getValue() == hand.get(4).getValue()){
				val = hand.get(0).getValue();
			}
			else if(hand.get(2).getValue() == hand.get(3).getValue()){
				val = hand.get(0).getValue();
			}
			if(hand.get(0).getValue() == hand.get(4).getValue()){
				val = hand.get(0).getValue();
			}
			
			Card c = null;
			
			for(int i = 0; i < 5; i++){
				c = hand.get(i);	
				
				if(c.getValue() == val){
					keepcards.add(c);
				}
				else{
					keepcards.add(d.dealCard());
				}
			}
		}
		
		else if(PokerValue()[0] == 4){
			int val1 = -1;
			int val2 = -1;
			
			for(int i = 0; i < 5; i++){
				Card c = hand.get(i);
				for(int j = 0; j < 5; j++){
					Card a = hand.get(j);
					
					if(c.getValue() == a.getValue() && c != a){
						if(val1 == -1){
							val1 = c.getValue();
						}
						else{
							val2 = c.getValue();
						}
					}
				}
			}
			
			for(int i = 0; i < 5; i++){
				Card b = hand.get(i);
				
				if(b.getValue() == val1 || b.getValue() == val2){
					keepcards.add(b);
				}
				else{
					keepcards.add(d.dealCard());
				}
			}
		}
		
		else if(PokerValue()[0] == 3){
			int val = -1;
			
			for(int i = 0; i < 5; i++){
				Card c = hand.get(i);
				for(int j = 0; j < 5; j++){
					Card a = hand.get(j);
					
					if(c.getValue() == a.getValue() && c != a){
						val = c.getValue();
						break;
					}
				}
			}
			
			for(int i = 0; i < 5; i++){
				Card b = hand.get(i);
				
				if(b.getValue() == val){
					keepcards.add(b);
				}
				else{
					keepcards.add(d.dealCard());
				}
			}
		}
		
		else{
			int clubs = 0, spades = 0, diamonds = 0, hearts = 0;
			int n = hand.size();
			ArrayList<Card> temp = hand;
			
			for (int pass=1; pass < n; pass++) { 
		        for (int i=0; i < n-pass; i++) {
		            if (temp.get(i).getValue() > temp.get(i + 1).getValue()) {
		                Card t = temp.get(i);  
		                temp.set(i, temp.get(i + 1));  
		                temp.set(i + 1, t);
		            }
		        }
		    }
		    
			for(int i = 0; i < 5; i++){
				if(getCard(i).getSuit().equals("Clubs")){
					clubs++;
					if(clubs >= 3){
						for(int j = 0; j < 5; j++){
							if(hand.get(j).getSuit().equals("Clubs")){
								keepcards.add(hand.get(j));
							}
							else
								keepcards.add(d.dealCard());
						}
					}
				}
				else if(getCard(i).getSuit().equals("Spades")){
					spades++;
					if(spades >= 3){
						for(int j = 0; j < 5; j++){
							if(hand.get(j).getSuit().equals("Spades")){
								keepcards.add(hand.get(j));
							}
							else
								keepcards.add(d.dealCard());
						}
					}
				}
				else if(getCard(i).getSuit().equals("Hearts")){
					hearts++;
					if(hearts >= 3){
						
						for(int j = 0; j < 5; j++){
							if(hand.get(j).getSuit().equals("Hearts")){
								keepcards.add(hand.get(j));
							}
							else
								keepcards.add(d.dealCard());
						}
					}
				}
				else{
					diamonds++;
					if(diamonds >= 3){
						for(int j = 0; j < 5; j++){
							if(hand.get(j).getSuit().equals("Diamonds")){
								keepcards.add(hand.get(j));
							}
							else
								keepcards.add(d.dealCard());
						}
					}
				}
				
			}
			if(keepcards.size() == 0){
				int s = 0;
				ArrayList<Card> st = new ArrayList<Card>();
				
				for(int i = 0; i < 4; i++){
					if(temp.get(i + 1).getValue() - temp.get(i).getValue() == 1){
						s++;
						if(!st.contains(temp.get(i))){
							st.add(temp.get(i));
						}
						if(!st.contains(temp.get(i + 1))){
							st.add(temp.get(i + 1));
						}
						
					}

				}	
				
				if(s >= 2 && st.get(st.size() - 1).getValue() - st.get(0).getValue() < 4){
					keepcards = st;
					for(int i = keepcards.size(); i <= 5; i++){
						keepcards.add(d.dealCard());
					}
				}
				
			}
			
			if(keepcards.size() == 0){
				/**
				 * Keep 2 highest values
				 */
				keepcards.add(temp.get(3));
				keepcards.add(temp.get(4));
				
				
				keepcards.add(d.dealCard());
				keepcards.add(d.dealCard());
				keepcards.add(d.dealCard());
			}

		}
		
		
		clearHand();
		for(int i = 0; i < 5; i++){
			hand.add(keepcards.get(i));
		}
		
	}
	
	public int bet(int amount){
		int pv = PokerValue()[0];
		if(amount >= 500){
			if(pv < 4){
				return -1;
			}
			else if(pv < 7){
				return amount;
			}
			else{
				return amount + 100;
			}
		}
		
		else if(amount >= 100){
			if(pv < 3){
				return -1;
			}
			else if(pv < 7){
				return amount + 100;
			}
			else{
				return amount * 2;
			}
		}
		else if(amount >= 10){
			if(pv < 3){
				return amount;
			}
			else if(pv < 7){
				return amount * 2;
			}
			else{
				return amount * 5;
			}
		}
		else{
			if(pv < 3){
				return amount;
			}
			else if(pv < 7){
				return amount + 100;
			}
			else{
				return amount + 700;
			}
		}

	}
	
	public int higherHand(PokerHand dh){
		
		Card temp[] = new Card[hand.size()];
		int n = temp.length;
		
		for(int i = 0; i < hand.size(); i++){
			temp[i] = hand.get(i);
		}
		
		for (int pass=1; pass < n; pass++) { 
	        for (int i=0; i < n-pass; i++) {
	            if (temp[i].getValue() > temp[i+1].getValue()) {
	                Card t = temp[i];  
	                temp[i] = temp[i+1];  
	                temp[i+1] = t;
	            }
	        }
	    }
		
		Card temp2[] = new Card[dh.hand.size()];
		int num = temp2.length;
		
		for(int i = 0; i < dh.hand.size(); i++){
			temp2[i] = dh.hand.get(i);
		}
		
		for (int pass=1; pass < num; pass++) { 
	        for (int i=0; i < num-pass; i++) {
	            if (temp2[i].getValue() > temp2[i+1].getValue()) {
	                Card t = temp2[i];  
	                temp2[i] = temp2[i+1];  
	                temp2[i+1] = t;
	            }
	        }
	    }
		
		for(int i = 0; i < temp.length; i++){
			if(temp[i].getValue() > temp2[i].getValue()){
				return -1;
			}
			else if(temp[i].getValue() < temp2[i].getValue()){
				return 1;
			}
			else{
				continue;
			}
		}
		
		return 0;
	}
}
