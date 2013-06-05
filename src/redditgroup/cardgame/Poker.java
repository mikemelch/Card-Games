package redditgroup.cardgame;
/*
 * 
 * 
 * Poker Program
 * 
 * 
 */


import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.util.Iterator;



public class Poker extends JFrame implements ActionListener{
    static int cardkeys = 0;
	JButton[] keep = new JButton[5];
	JButton deal;
	JLabel dc;
	JLabel yc;
	JLabel pot;
	JTextField results;
	JLabel[] dealercards;
	JLabel[] playercards;
	JButton bet;
	boolean exchanged;
	JTextField bettf;
	JButton call;
	int playerbet;
	int dealbet;
	int amount;
	JButton fold;
	JPanel dealer;
	JPanel buttons, buttons2;
	JPanel player;
	JPanel ex;
	Deck deck;
	PokerHand playerHand;
	PokerHand dealerHand;
	Icon iconName;
	//ArrayList<Card> keepcards;
    Map<String,Card> keepcards;
	JButton exchange;
	
	public Poker(String name){
		super(name);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(new Color(0, 100, 0));
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setSize(600, 380);
		keepcards = new HashMap<String, Card>();
		deal = new JButton("Deal");
		dc = new JLabel("Dealer:");
		yc = new JLabel("Player:");
		bet = new JButton("Bet!");
		bettf = new JTextField("-enter amount-");
		call = new JButton("Call");
		fold = new JButton("Fold");
		pot = new JLabel("Pot: 0");
		amount = 0;
		dealbet = 0;
		playerbet = 0;
		results = new JTextField(50);
		dealercards = new JLabel[7];
		playercards = new JLabel[7];
		exchange = new JButton("Exchange");
		exchange.setEnabled(false);
		exchanged = false;
		ex = new JPanel();
		dealer = new JPanel();
		buttons = new JPanel();
		buttons2 = new JPanel();
		player = new JPanel();
		dealer.add(dc);
		player.add(yc);
		iconName = new ImageIcon();
		for(int i = 0; i <= 6; i++){
			dealercards[i] = new JLabel();
			playercards[i] = new JLabel();
			dealer.add(dealercards[i]);
			player.add(playercards[i]);
		}
	
		ex.add(exchange);
		buttons.add(deal);
		buttons.add(pot);
		buttons.add(bet);
		buttons.add(bettf);
		buttons.add(call);
		buttons.add(fold);
		add(dealer);
		add(buttons);
		add(player);
		
		
		
		deal.setActionCommand("deal");
		deal.addActionListener(this);
		
		for(int i = 0; i < 5; i++){
			keep[i] = new JButton("Keep");
			keep[i].setEnabled(false);		
			keep[i].addActionListener(this);
			keep[i].setPreferredSize(new Dimension(65, 30));
		}
		buttons2.add(Box.createRigidArea(new Dimension(40,0)));
		buttons2.add(keep[0]);
		buttons2.add(Box.createRigidArea(new Dimension(0,0)));
		buttons2.add(keep[1]);
		buttons2.add(Box.createRigidArea(new Dimension(0,0)));
		buttons2.add(keep[2]);
		buttons2.add(Box.createRigidArea(new Dimension(0,0)));
		buttons2.add(keep[3]);
		buttons2.add(Box.createRigidArea(new Dimension(3,0)));
		buttons2.add(keep[4]);
		buttons2.add(Box.createRigidArea(new Dimension(5,0)));

		keep[0].setActionCommand("keep0");
		keep[1].setActionCommand("keep1");
		keep[2].setActionCommand("keep2");
		keep[3].setActionCommand("keep3");
		keep[4].setActionCommand("keep4");
		exchange.setActionCommand("exchange");
		exchange.addActionListener(this);
		
		call.setActionCommand("Call");
		fold.setActionCommand("Fold");
		bet.setActionCommand("Bet!");
		call.addActionListener(this);
		fold.addActionListener(this);
		bet.addActionListener(this);
		
		call.setEnabled(false);
		fold.setEnabled(false);
		bet.setEnabled(false);
		bettf.setEnabled(false);
		
		add(buttons2);
		add(ex);
		add(results);
		playerHand = new PokerHand();
		dealerHand = new PokerHand();
		deck = new Deck();
	}


	public void actionPerformed(ActionEvent evt) {
		String event = evt.getActionCommand();

		if(event.equals("deal")){
			pot.setText("Pot: 0");
			amount = 0;
			bettf.setText("       ");
			deck.shuffle();
			exchanged = false;
			call.setEnabled(false);
			fold.setEnabled(true);
			bet.setEnabled(true);
			bettf.setEnabled(true);
			deal.setEnabled(false);
			for(int k = 0; k < 7; k++){
				dealercards[k].setIcon(null);
				playercards[k].setIcon(null);
			}
			results.setText("");
			playerHand.clearHand();
			dealerHand.clearHand();	
			
			/**
			 * Deals new cards
			 */
			for(int i = 0; i < 5; i++){
				playerHand.addCard(deck.dealCard());
				dealerHand.addCard(deck.dealCard());
			}
			
			keepcards.clear();
			
			/**
			 * Sets JLabels to pictures.gif
			 */
			for(int i = 0; i < 5; i++){
				keep[i].setEnabled(false);
				iconName = new ImageIcon("b.gif");
				dealercards[i].setIcon(iconName);
                player.add(dealercards[i]);
                repaint();
				iconName = new ImageIcon(playerHand.getCard(i).getFileName());
				playercards[i].setIcon(iconName);
			}

		}


		/**
		 * On each click of a "Keep" Button:
		 * 1. Disabled
		 * 2. Added to keepcards ArrayList
		 * 3. Checks if exchanged can be enabled 
		 */
		
		else if(event.equals("keep0")){
            results.setText(null);
            //keep[1].setEnabled(false);

            if(keep[0].getText().equalsIgnoreCase("Trade")) {
                keep[0].setText("Keep");
                keepcards.remove(playerHand.getCard(0).getUID());
            }
            else{
                keep[0].setText("Trade");
                keepcards.put(playerHand.getCard(0).getUID(),playerHand.getCard(0));
            }

            if(keepcards.size() > 1){
                exchange.setEnabled(true);
            }
            else
                exchange.setEnabled(false);
		}
		else if(event.equals("keep1")){
			results.setText(null);
			//keep[1].setEnabled(false);

            if(keep[1].getText().equalsIgnoreCase("Trade")) {
                keep[1].setText("Keep");
                keepcards.remove(playerHand.getCard(1).getUID());
            }
            else{
                keep[1].setText("Trade");
                keepcards.put(playerHand.getCard(1).getUID(),playerHand.getCard(1));
            }

            if(keepcards.size() > 1){
				exchange.setEnabled(true);
			}
            else
                exchange.setEnabled(false);
		}
		else if(event.equals("keep2")){
            results.setText(null);
            //keep[1].setEnabled(false);

            if(keep[2].getText().equalsIgnoreCase("Trade")) {
                keep[2].setText("Keep");
                keepcards.remove(playerHand.getCard(2).getUID());
            }
            else{
                keep[2].setText("Trade");
                keepcards.put(playerHand.getCard(2).getUID(),playerHand.getCard(2));
            }

            if(keepcards.size() > 1){
                exchange.setEnabled(true);
            }
            else
                exchange.setEnabled(false);
		}
		else if(event.equals("keep3")){
            results.setText(null);
            //keep[1].setEnabled(false);

            if(keep[3].getText().equalsIgnoreCase("Trade")) {
                keep[3].setText("Keep");
                keepcards.remove(playerHand.getCard(3).getUID());
            }
            else{
                keep[3].setText("Trade");
                keepcards.put(playerHand.getCard(3).getUID(),playerHand.getCard(3));
            }

            if(keepcards.size() > 1){
                exchange.setEnabled(true);
            }
            else
                exchange.setEnabled(false);
		}
		else if(event.equals("keep4")){
            results.setText(null);
            //keep[1].setEnabled(false);

            if(keep[4].getText().equalsIgnoreCase("Trade")) {
                keep[4].setText("Keep");
                keepcards.remove(playerHand.getCard(4).getUID());
            }
            else{
                keep[4].setText("Trade");
                keepcards.put(playerHand.getCard(4).getUID(),playerHand.getCard(4));
            }

            if(keepcards.size() > 1){
                exchange.setEnabled(true);
            }
            else
                exchange.setEnabled(false);
		}
		
		/**
		 * Exchanges cards that weren't kept for new ones
		 */
		else if(event.equals("exchange")){
            //Set up an iterator for the hashmap
            Iterator iter = keepcards.entrySet().iterator();

            exchanged = true;
			for(int i = 0; i < 5; i++){
				keep[i].setEnabled(false);
			}
			exchange.setEnabled(false);
			playerHand.clearHand();
			bettf.setEnabled(true);
			bet.setEnabled(true);

			/**
			 * Keeps cards from the keepcards ArrayList back to the hand
			 */
            int count = 1;
			for(Map.Entry<String,Card> entry : keepcards.entrySet()){
				playerHand.addCard(entry.getValue());
			}

            //Add remaining cards
            while(playerHand.addCard(deck.dealCard()));

			/**
			 * GUI changing of pictures
			 */
			for(int i = 0; i < 5; i++){
				iconName = new ImageIcon(playerHand.getCard(i).getFileName());
				playercards[i].setIcon(iconName);
			}

			
			dealerHand.dealerExchange(deck, dealercards);
			
			
		}
		
		else if(event.equals("Fold")){

			results.setText("You have folded. You lose $" + amount + "!");
			bet.setEnabled(false);	
			bettf.setEnabled(false);
			fold.setEnabled(false);
			call.setEnabled(false);
			exchange.setEnabled(false);
			for(int i = 0; i < 5; i++){
				keep[i].setEnabled(false);
			}
			deal.setEnabled(true);
			pot.setText("Pot: $0");
			return;
		}
		
		else if(event.equals("Bet!")){

            try{
			    playerbet = Integer.parseInt(bettf.getText().trim());
            }
            catch(NumberFormatException e){
                results.setText("Please enter a number");
                return;
            }

			bettf.setText("       ");
			if(playerbet < 0){
                results.setText("No cheating!");
				return;
			}

			amount += playerbet;
			pot.setText("Pot: $" + amount);
			
			dealbet = dealerHand.bet(playerbet);
			
			if(dealbet == -1){
				results.setText("The dealer folded. You win $" + amount + "!");
				pot.setText("Pot: $0");
				bet.setEnabled(false);	
				bettf.setEnabled(false);
				fold.setEnabled(false);
				call.setEnabled(false);
				deal.setEnabled(true);
				return;
			}
			else if(dealbet > playerbet){
				results.setText("The dealer has raised you $" + (dealbet - playerbet) + "!");
				amount += dealbet;
				pot.setText("Pot: $" + amount);
				call.setEnabled(true);
				fold.setEnabled(true);
				bet.setEnabled(false);	
				bettf.setEnabled(false);
				return;
			}
			else{
				bet.setEnabled(false);	
				bettf.setEnabled(false);
				results.setText("The dealer called.");
				amount += dealbet;
				pot.setText("Pot: $" + amount);
			}
			
			if(exchanged){
				for(int i = 0; i < dealerHand.getHand().size(); i++){
					Icon iconName = new ImageIcon(dealerHand.getHand().get(i).getFileName());
					dealercards[i].setIcon(iconName);
				}
				fold.setEnabled(false);
				if(playerHand.PokerValue()[0] > dealerHand.PokerValue()[0]){
					results.setText("You win $" + amount + " with a " + playerHand.showPokerValue());
				}
				else if(playerHand.PokerValue()[0] < dealerHand.PokerValue()[0]){
					results.setText("You lose $" + amount + " to a " + dealerHand.showPokerValue());
				}
				else if(playerHand.PokerValue()[0] == 2 && dealerHand.PokerValue()[0] == 2){
					
					int h = playerHand.higherHand(dealerHand);
					
					if(h == -1){
						results.setText("You win $" + amount + " with a " + playerHand.showPokerValue());
					}
					else if(h == 1){
						results.setText("You lose $" + amount + " to a " + dealerHand.showPokerValue());
					}
				}
				else{
					for(int i = 1; i < 3; i++){
						if(playerHand.PokerValue()[i] > dealerHand.PokerValue()[i]){
							results.setText("You win $" + amount + " with a " + playerHand.showPokerValue());
							return;
						}
						else if(playerHand.PokerValue()[i] < dealerHand.PokerValue()[i]){
							results.setText("You lose $" + amount + " to a " + dealerHand.showPokerValue());
							return;
						}
					}
					
					results.setText("You and the dealer tie with a " + dealerHand.showPokerValue() + ". You get your money back");
				}
				
				deal.setEnabled(true);

			}
			
			else{
				
				for(int i = 0; i < 5; i++){
					keep[i].setEnabled(true);
				}
			}
		}
		
		else if(event.equals("Call")){
			amount += (dealbet - playerbet);
			pot.setText("Pot: $" + amount);
			call.setEnabled(false);
			fold.setEnabled(false);
			
			if(exchanged){
				for(int i = 0; i < dealerHand.getHand().size(); i++){
					Icon iconName = new ImageIcon(dealerHand.getHand().get(i).getFileName());
					dealercards[i].setIcon(iconName);
				}
				fold.setEnabled(false);
				if(playerHand.PokerValue()[0] > dealerHand.PokerValue()[0]){
					results.setText("You win $" + amount + " with a " + playerHand.showPokerValue());
				}
				else if(playerHand.PokerValue()[0] < dealerHand.PokerValue()[0]){
					results.setText("You lose $" + amount + " to a " + dealerHand.showPokerValue());
				}
				else if(playerHand.PokerValue()[0] == 2 && dealerHand.PokerValue()[0] == 2){
					
					int h = playerHand.higherHand(dealerHand);
					
					if(h == -1){
						results.setText("You win $" + amount + " with a " + playerHand.showPokerValue());
					}
					else if(h == 1){
						results.setText("You lose $" + amount + " to a " + dealerHand.showPokerValue());
					}
				}
				else{
					for(int i = 1; i < 3; i++){
						if(playerHand.PokerValue()[i] > dealerHand.PokerValue()[i]){
							results.setText("You win $" + amount + " with a " + playerHand.showPokerValue());
							deal.setEnabled(true);
							return;
						}
						else if(playerHand.PokerValue()[i] < dealerHand.PokerValue()[i]){
							results.setText("You lose $" + amount + " to a " + dealerHand.showPokerValue());
							deal.setEnabled(true);
							return;
						}
					}
					
					results.setText("You and the dealer tie with a " + dealerHand.showPokerValue() + ". You get your money back");
				}
				
				deal.setEnabled(true);

			}
			else{
				for(int i = 0; i < 5; i++){
					keep[i].setEnabled(true);
				}
				
			}
			
		}


//        class cardID{
//            private Card.Suits suit;
//            private Card.Cards card;
//        }

			
	}
	
	
	public static void main(String[] args){
		Poker window = new Poker("Poker");
		window.show();
	}
	
}

