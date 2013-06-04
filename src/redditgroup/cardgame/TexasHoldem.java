package redditgroup.cardgame;
/*
 * Texas Holdem Program
 * 
 * Created by Michael Melchione
 */



import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


import javax.swing.*;


public class TexasHoldem extends JFrame implements ActionListener{

    JButton deal;
	JButton call;
	JButton fold;
	JLabel dc;
	JLabel yc;
	JTextField results;
	JLabel[] dealercards;
	JLabel[] playercards;
	JLabel[] communitycards;
	JPanel dealer;
	JPanel buttons, buttons2;
	JPanel player;
	JPanel com;
	JLabel pot;
	JLabel cc;
	Deck deck;
	PokerHand playerHand;
	PokerHand dealerHand;
	Icon iconName;
	ArrayList<Card> keepcards, community;
	JTextField bettf;
	JButton betb;
	int amount;
	int card;
	int dealbet;
	int playerbet;
	
	public TexasHoldem(String name){
		super(name);
        super.setDefaultCloseOperation(EXIT_ON_CLOSE); //You don't know what you got til it's gone.
		setBackground(new Color(0, 100, 0));
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setSize(650, 425);
		dealbet = 0;
		playerbet = 0;
		deal = new JButton("Deal");
		call = new JButton("Call");
		fold = new JButton("Fold");
		dc = new JLabel("Dealer:");
		cc = new JLabel("Community Cards:");
		yc = new JLabel("Player:");
		bettf = new JTextField("-enter amount-");
		betb = new JButton("Bet!");
		pot = new JLabel("Pot: 0");
		amount = 0;
		card = 2;
		results = new JTextField(50);
		dealercards = new JLabel[2];
		playercards = new JLabel[2];
		communitycards = new JLabel[5];
		community = new ArrayList<Card>();
		dealer = new JPanel();
		com = new JPanel();
		buttons = new JPanel();
		buttons2 = new JPanel();
		player = new JPanel();
        cc.setVisible(false);
        dc.setVisible(false);
		com.add(cc);
		dealer.add(dc);
		player.add(yc);
		iconName = new ImageIcon();
		for(int i = 0; i < 2; i++){
			dealercards[i] = new JLabel();
			playercards[i] = new JLabel();
			dealer.add(dealercards[i]);
			player.add(playercards[i]);
		}
		for(int i = 0; i < 5; i++){
			communitycards[i] = new JLabel();
			com.add(communitycards[i]);
		}

	
		buttons.add(deal);	
		buttons.add(pot);	
		buttons.add(betb);
		buttons.add(bettf);
		buttons.add(call);
		buttons.add(fold);
		bettf.setEnabled(false);
		call.setEnabled(false);
		fold.setEnabled(false);
		betb.setEnabled(false);
		
		add(dealer);
		add(com);
		add(buttons);
		add(player);
		
		
		deal.setActionCommand("deal");
		deal.addActionListener(this);
		betb.setActionCommand("Bet!");
		betb.addActionListener(this);
		fold.addActionListener(this);
		call.addActionListener(this);
		fold.setActionCommand("fold");
		call.setActionCommand("call");
		
		add(buttons2);
		add(results);
		playerHand = new PokerHand();
		dealerHand = new PokerHand();
		deck = new Deck();
	}


	public void actionPerformed(ActionEvent evt) {
		String event = evt.getActionCommand();
        cc.setVisible(true);
		if(event.equals("deal")){
			amount = 0;
			card = 2;
			fold.setEnabled(true);
			pot.setText("Pot: 0");
			betb.setEnabled(true);
			deck.shuffle();
			deal.setEnabled(false);

            cc.setVisible(true);
            dc.setVisible(true);

			for(int k = 0; k < 2; k++){
				dealercards[k].setIcon(null);
				playercards[k].setIcon(null);
			}
			for(int k = 0; k < 5; k++){
				communitycards[k].setIcon(null);
			}
			results.setText("");
			playerHand.clearHand();
			dealerHand.clearHand();	
			
			/**
			 * Deals new cards
			 */
			for(int i = 0; i < 2; i++){
				playerHand.addCard(deck.dealCard());
				dealerHand.addCard(deck.dealCard());
			}
			
			community.add(deck.dealCard());
			community.add(deck.dealCard());
			community.add(deck.dealCard());
			deck.dealCard();
			community.add(deck.dealCard());
			deck.dealCard();
			community.add(deck.dealCard());
			
			
			
			for(int i = 0; i < 5; i++){
				iconName = new ImageIcon("Cards/b.gif");
				communitycards[i].setIcon(iconName);
				
			}
			
			/**
			 * Sets JLabels to pictures.gif
			 */
			for(int i = 0; i < 2; i++){
				iconName = new ImageIcon("Cards/b.gif");
				dealercards[i].setIcon(iconName);
				iconName = new ImageIcon(playerHand.getCard(i).getFileName());
				playercards[i].setIcon(iconName);
			}
			bettf.setEnabled(true);

		}
		
		else if(event.equals("Bet!")){

            try{
			    playerbet = Integer.parseInt(bettf.getText().trim());
            }
            catch(NumberFormatException e){
                results.setText("Please enter a number");
                return;
            }

			if(playerbet < 0){
				bettf.setText("No cheating!");
				return;
			}
			
			amount += playerbet;
			pot.setText("Pot: $" + amount);
			
			dealbet = dealerHand.bet(playerbet);
			
			if(dealbet == -1){
				results.setText("The dealer folded. You win $" + amount + "!");
				pot.setText("Pot: $0");
				betb.setEnabled(false);	
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
				betb.setEnabled(false);	
				bettf.setEnabled(false);
				return;
			}
			else{
				results.setText("The dealer called.");
				amount += dealbet;
				pot.setText("Pot: $" + amount);
			}


			if(card == 2){
				for(int i = 0; i < 3; i++){
					iconName = new ImageIcon(community.get(i).getFileName());
					communitycards[i].setIcon(iconName);
					playerHand.addCard(community.get(i));
					dealerHand.addCard(community.get(i));
				}
				card++;

			}
			
			else if(card < 5){
				iconName = new ImageIcon(community.get(card).getFileName());
				communitycards[card].setIcon(iconName);
				playerHand.addCard(community.get(card));
				dealerHand.addCard(community.get(card));
				card++;
			}
		
			else{
				betb.setEnabled(false);	
				bettf.setEnabled(false);
				
				iconName = new ImageIcon(dealerHand.getCard(0).getFileName());
				dealercards[0].setIcon(iconName);
				
				iconName = new ImageIcon(dealerHand.getCard(1).getFileName());
				dealercards[1].setIcon(iconName);
				
				if(playerHand.PokerValue()[0] == dealerHand.PokerValue()[0]){
					if(playerHand.PokerValue()[1] > dealerHand.PokerValue()[1]){
						results.setText("You win $" + amount + " with a " + playerHand.showPokerValue());
					}
					else if(playerHand.PokerValue()[1] < dealerHand.PokerValue()[1]){
						results.setText("You lose $" + amount + " to a " + dealerHand.showPokerValue());
					}
				}
				else if(playerHand.PokerValue()[0] > dealerHand.PokerValue()[0]){
					results.setText("You win $" + amount + " with a " + playerHand.showPokerValue());
				}
				else if(playerHand.PokerValue()[0] < dealerHand.PokerValue()[0]){
					results.setText("You lose $" + amount + " to a " + dealerHand.showPokerValue());
				}
		
				deal.setEnabled(true);
			}
			
			bettf.setText("             ");
			
		}
			
		else if(event.equals("fold")){

			results.setText("You have folded. You lose $" + amount + "!");
			betb.setEnabled(false);	
			bettf.setEnabled(false);
			fold.setEnabled(false);
			call.setEnabled(false);
			deal.setEnabled(true);
			pot.setText("Pot: $0");
			return;
		}
		
		else if(event.equals("call")){
			amount += (dealbet - playerbet);
			pot.setText("Pot: $" + amount);
			call.setEnabled(false);
			fold.setEnabled(false);
			
			if(card == 2){
				for(int i = 0; i < 3; i++){
					iconName = new ImageIcon(community.get(i).getFileName());
					communitycards[i].setIcon(iconName);
					playerHand.addCard(community.get(i));
					dealerHand.addCard(community.get(i));
				}
				card++;

				
				bettf.setText("             ");
				betb.setEnabled(true);
				bettf.setEnabled(true);
			}
			
			else if(card < 5){
				iconName = new ImageIcon(community.get(card).getFileName());
				communitycards[card].setIcon(iconName);
				playerHand.addCard(community.get(card));
				dealerHand.addCard(community.get(card));
				card++;
				
				bettf.setText("             ");
				betb.setEnabled(true);
				bettf.setEnabled(true);
			}
		
			else{
				betb.setEnabled(false);	
				bettf.setEnabled(false);
				iconName = new ImageIcon(dealerHand.getCard(0).getFileName());
				dealercards[0].setIcon(iconName);
				
				iconName = new ImageIcon(dealerHand.getCard(1).getFileName());
				dealercards[1].setIcon(iconName);
				
				if(playerHand.PokerValue()[0] == dealerHand.PokerValue()[0]){
					if(playerHand.PokerValue()[1] > dealerHand.PokerValue()[1]){
						results.setText("You win $" + amount + " with a " + playerHand.showPokerValue());
					}
					else if(playerHand.PokerValue()[1] < dealerHand.PokerValue()[1]){
						results.setText("You lose $" + amount + " to a " + dealerHand.showPokerValue());
					}
				}
				else if(playerHand.PokerValue()[0] > dealerHand.PokerValue()[0]){
					results.setText("You win $" + amount + " with a " + playerHand.showPokerValue());
				}
				else if(playerHand.PokerValue()[0] < dealerHand.PokerValue()[0]){
					results.setText("You lose $" + amount + " to a " + dealerHand.showPokerValue());
				}
		
				deal.setEnabled(true);
				bettf.setText("             ");
			}

		}

	}


	
	
	public static void main(String[] args){
		TexasHoldem window = new TexasHoldem("Texas Holdem");
		window.show();
	}
	
}

