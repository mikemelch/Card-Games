///////////////////////////////////
//Michael Melchione
//AP Computer Science A
//6-1-12
//VisualBJ
///////////////////////////////////

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VisualBJ extends JFrame implements ActionListener{
	
	JButton hit;
	JButton stay;
	JButton deal;
	JButton doubledown;
	int amount;
	JLabel dc;
	JLabel yc;
	JTextField bettf;
	JButton bet;
	JTextField results;
	JLabel[] dealercards;
	JLabel[] playercards;
	JPanel dealer;
	JPanel buttons;
	JPanel player;
	Deck deck;
	BJHand playerHand;
	BJHand dealerHand;
	JPanel buttons2;
	Icon iconName;
	int i, j;
	boolean win, lose;
	boolean push, down;
	
	public VisualBJ(String name){
		super(name);
		setBackground(new Color(0, 100, 0));
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setSize(500, 345); 	
		hit = new JButton("Hit");
		stay = new JButton("Stay");
		deal = new JButton("Deal");
		doubledown = new JButton("Double Down");
		bettf = new JTextField("-enter amount-");
		bet = new JButton("Bet!");
		dc = new JLabel("Dealer:");
		yc = new JLabel("Player:");
		results = new JTextField(50);
		dealercards = new JLabel[7];
		playercards = new JLabel[7];
		dealer = new JPanel();
		buttons = new JPanel();
		buttons2 = new JPanel();
		player = new JPanel();
		dealer.add(dc);
		player.add(yc);
		i = 2;
		j = 2;
		win = false;
		push = false;
		lose = false;
		iconName = new ImageIcon();
		for(int i = 0; i <= 6; i++){
			dealercards[i] = new JLabel();
			playercards[i] = new JLabel();
			dealer.add(dealercards[i]);
			player.add(playercards[i]);
		}
	
		buttons.add(hit);
		buttons.add(stay);
		buttons.add(deal);
		buttons2.add(bet);
		buttons2.add(bettf);
		buttons2.add(doubledown);
		bet.setActionCommand("Bet");
		bet.addActionListener(this);
		add(dealer);
		add(buttons);
		add(buttons2);
		add(player);
		add(results);
		
		hit.setActionCommand("hit");
		stay.setActionCommand("stay");
		deal.setActionCommand("deal");
		doubledown.setActionCommand("down");
		
		hit.addActionListener(this);
		stay.addActionListener(this);
		deal.addActionListener(this);
		doubledown.addActionListener(this);
		
		hit.setEnabled(false);
		stay.setEnabled(false);
		bet.setEnabled(true);
		bettf.setEnabled(true);
		doubledown.setEnabled(false);
		deal.setEnabled(false);
		
		playerHand = new BJHand();
		dealerHand = new BJHand();
		deck = new Deck();
	}
	
	public void actionPerformed(ActionEvent evt) {
		String event = evt.getActionCommand();
		
		if(event.equals("deal")){
			bettf.setText("      ");
			results.setText(null);
			amount = 0;
			win = false;
			lose = false;
			push = false;
			i = 2;
			j = 2;
			for(int k = 0; k < 7; k++){
				dealercards[k].setIcon(null);
				playercards[k].setIcon(null);
			}
			results.setText("");
			deck.shuffle();

			
			hit.setEnabled(false);
			stay.setEnabled(false);
			deal.setEnabled(false);
			doubledown.setEnabled(false);
			bet.setEnabled(true);
			bettf.setEnabled(true);

		}
		
		else if(event.equals("hit")){
			doubledown.setEnabled(false);
			playerHand.addCard(deck.dealCard());
			iconName = new ImageIcon(playerHand.getCard(i).showVal() + " of " + playerHand.getCard(i).getSuit() + ".gif");
			playercards[i].setIcon(iconName);
			i++;
			if(i == 6)
			{
				results.setText("Five Card Charlie! You win $" + amount + "!");
				win = true;
			}
			
			if(playerHand.BJValue() > 21){
				results.setText("You busted! Dealer wins! You lose $" + amount + "!");
				lose = true;
			}
			
		}
		
		else if(event.equals("stay")){
			doubledown.setEnabled(false);
			iconName = new ImageIcon(dealerHand.getCard(0).showVal() + " of " + dealerHand.getCard(0).getSuit() + ".gif");
			dealercards[0].setIcon(iconName);
			
			while(dealerHand.BJValue() < 17)
			{
				
				dealerHand.addCard(deck.dealCard());
				iconName = new ImageIcon(dealerHand.getCard(j).showVal() + " of " + dealerHand.getCard(j).getSuit() + ".gif");
				dealercards[j].setIcon(iconName);
				j++;
			}
			
			if(dealerHand.BJValue() > 21)
			{
				results.setText("The dealer busted. You win $" + amount + "!");
				win = true;
			}
			else if(dealerHand.BJValue() > playerHand.BJValue())
			{
				results.setText("The dealer has a better hand than you. You lose $" + amount + "!");
				lose = true;
			}
			else if((dealerHand.BJValue() < playerHand.BJValue()))
			{
				results.setText("You have a better hand than the dealer. You win $" + amount + "!");
				win = true;
			}
			else
			{
				results.setText("You and the dealer have the same hand. You push.");
				push = true;
			}
		}
		
		if(event.equals("down")){
			hit.setEnabled(false);
			deal.setEnabled(false);
			stay.setEnabled(false);
			amount = Integer.parseInt(bettf.getText().trim());
			bettf.setEnabled(false);
			bet.setEnabled(false);
			
			doubledown.setEnabled(false);
			playerHand.addCard(deck.dealCard());
			iconName = new ImageIcon(playerHand.getCard(i).showVal() + " of " + playerHand.getCard(i).getSuit() + ".gif");
			playercards[i].setIcon(iconName);
			i++;
			
			if(playerHand.BJValue() > 21){
				results.setText("You busted! Dealer wins!");
				results.setText(results.getText() + "    You lose $" + amount * 2 + "!");
				lose = true;
			}
			else{
				iconName = new ImageIcon(dealerHand.getCard(0).showVal() + " of " + dealerHand.getCard(0).getSuit() + ".gif");
				dealercards[0].setIcon(iconName);
				
				while(dealerHand.BJValue() < 17)
				{
					
					dealerHand.addCard(deck.dealCard());
					iconName = new ImageIcon(dealerHand.getCard(j).showVal() + " of " + dealerHand.getCard(j).getSuit() + ".gif");
					dealercards[j].setIcon(iconName);
					j++;
				}
				
				if(dealerHand.BJValue() > 21)
				{
					results.setText("The dealer busted. You win $" + amount * 2 + "!");
					win = true;
				}
				else if(dealerHand.BJValue() > playerHand.BJValue())
				{
					results.setText("The dealer has a better hand than you. You lose $" + amount * 2 + "!");
					lose = true;
				}
				else if((dealerHand.BJValue() < playerHand.BJValue()))
				{
					results.setText("You have a better hand than the dealer. You win $" + amount * 2 + "!");
					win = true;
				}
				else
				{
					results.setText("You and the dealer have the same hand. You push.");
					push = true;
				}
				
			}
		}

		else if(event.equals("Bet")){
			amount = Integer.parseInt(bettf.getText().trim());
			deck.shuffle();
			if(playerHand.BJValue() == 21){
				results.setText("BLACKJACK! You win! You win $" + amount * 3 + "!");
				win = true;
				hit.setEnabled(false);
				stay.setEnabled(false);
				deal.setEnabled(true);
				return;
			}
			else if(dealerHand.BJValue() == 21){
				results.setText("BLACKJACK! Dealer wins! You lose $" + amount + "!");
				iconName = new ImageIcon(dealerHand.getCard(0).showVal() + " of " + dealerHand.getCard(0).getSuit() + ".gif");
				dealercards[0].setIcon(iconName);
				lose = true;
				hit.setEnabled(false);
				stay.setEnabled(false);
				deal.setEnabled(true);
				return;
			}
			playerHand.clearHand();
			dealerHand.clearHand();	
			playerHand.addCard(deck.dealCard());
			dealerHand.addCard(deck.dealCard());
			playerHand.addCard(deck.dealCard());
			dealerHand.addCard(deck.dealCard());
			iconName = new ImageIcon("b.gif");
			dealercards[0].setIcon(iconName);
			iconName = new ImageIcon(dealerHand.getCard(1).showVal() + " of " + dealerHand.getCard(1).getSuit() + ".gif");
			dealercards[1].setIcon(iconName);
			iconName = new ImageIcon(playerHand.getCard(0).showVal() + " of " + playerHand.getCard(0).getSuit() + ".gif");
			playercards[0].setIcon(iconName);
			iconName = new ImageIcon(playerHand.getCard(1).showVal() + " of " + playerHand.getCard(1).getSuit() + ".gif");
			playercards[1].setIcon(iconName);
			doubledown.setEnabled(true);
			hit.setEnabled(true);
			stay.setEnabled(true);
			
			
			bet.setEnabled(false);
			bettf.setEnabled(false);
			
		}
		
		if((win) || (lose) || (push)){
			hit.setEnabled(false);
			stay.setEnabled(false);
			deal.setEnabled(true);
		}
		
	}
	
	public static void main(String[] args){
		VisualBJ window = new VisualBJ("Blackjack");
		window.show();
	}
}