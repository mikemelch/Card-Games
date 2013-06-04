package redditgroup.cardgame;
///////////////////////////////////
//Michael Melchione
//AP Computer Science A
//5-24-12
//VisualHL
///////////////////////////////////


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class VisualHL extends JFrame implements ActionListener{
	
	JButton higher = new JButton("Higher");
	JButton lower = new JButton("Lower");
	JButton newgame = new JButton("New Game");
	
	JTextField numcorrect = new JTextField(5);
	JTextField tf = new JTextField(20);
	
	JLabel label = new JLabel("Number Correct:");
	
	JLabel cardpic;
	Icon iconName;
	
	int correct;
	boolean cont;
	boolean corr = false;
	Container back;
	Deck deck = new Deck();
	Card [] card = new Card[2];	

	
	public VisualHL(String name){
		super(name);
		setBackground(new Color(0, 100, 0));
		setLayout(new FlowLayout());
		setSize(260, 240);
		back = getContentPane();
		back.setBackground(Color.RED);
		iconName = new ImageIcon("b.gif");
		cardpic = new JLabel(iconName);
		cardpic.setIcon(iconName);
		add(cardpic);
		add(higher);
		add(lower);
		add(tf);
		add(label);
		add(numcorrect);
		add(newgame);
		
		higher.setActionCommand("Higher");
		lower.setActionCommand("Lower");
		newgame.setActionCommand("New Game");
		
		higher.addActionListener(this);
		lower.addActionListener(this);
		newgame.addActionListener(this);
	}
	public static void main(String[] args){
		VisualHL window = new VisualHL("HighLow");
		window.show();
	}
	
	public void actionPerformed(ActionEvent evt) {
		
		if(evt.getActionCommand().equals("New Game")){
			deck.shuffle();
			card[0] = deck.dealCard();
			correct = 0;
	
			numcorrect.setText("0");
			tf.setText("");
			iconName = new ImageIcon(card[0].getCardNumber() + " of " + card[0].getSuitString() + ".gif");
			cardpic.setIcon(iconName);
			
			card[1] = deck.dealCard();
			higher.setEnabled(true);
			lower.setEnabled(true);
		}
		
		else if(evt.getActionCommand().equals("Higher")){
			iconName = new ImageIcon(card[1].getCardNumber() + " of " + card[1].getSuitString() + ".gif");
			cardpic.setIcon(iconName);
			
			if((card[0].getCardNumber() < card[1].getCardNumber()))
			{
				tf.setText("\nYour prediction was correct.");
				corr = true;
			}
			
			else if((card[0].getCardNumber() > card[1].getCardNumber()))
			{
				tf.setText("\nYour prediction was incorrect.");
				corr = false;
			}
			
			else
			{
				tf.setText("\nThe card value is the same. Sorry, you lose!");
				corr = false;
			}
			
			if(corr){
				correct++;
				numcorrect.setText(correct + "");
				card[0] = card[1];
				card[1] = deck.dealCard();
			}
			else{
				higher.setEnabled(false);
				lower.setEnabled(false);
			}
			
		}
		
		else if(evt.getActionCommand().equals("Lower")){
			iconName = new ImageIcon(card[1].getCardNumber() + " of " + card[1].getSuitString() + ".gif");
			cardpic.setIcon(iconName);
			
			if((card[0].getCardNumber() > card[1].getCardNumber()))
			{
				tf.setText("\nYour prediction was correct.");
				corr = true;
			}
			
			else if((card[0].getCardNumber() < card[1].getCardNumber()))
			{
				tf.setText("\nYour prediction was incorrect.");
				corr = false;
			}
			
			else
			{
				tf.setText("\nThe card value is the same. Sorry, you lose!");
				corr = false;
			}
			
			if(corr){
				correct++;
				numcorrect.setText(correct + "");
				card[0] = card[1];
				card[1] = deck.dealCard();
			}
			else{
				higher.setEnabled(false);
				lower.setEnabled(false);
			}
		}

		
		
	}
}
