package redditgroup.cardgame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Client extends JFrame implements ActionListener{
	JPanel bj;
	JPanel p;
	JPanel lab;
	JPanel hl;
	JPanel tex;
	public Client(String title){
		super(title);
		setBackground(new Color(0, 100, 0));
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		setSize(350, 250);
		
		bj = new JPanel();
		p = new JPanel();
		lab = new JPanel();
		hl = new JPanel();
		tex = new JPanel();
		
		JLabel label = new JLabel("Which table would you like to play?");
		lab.add(label);
		JButton blackjack = new JButton("BlackJack");
		bj.add(blackjack);
		JButton poker = new JButton("Poker");
		p.add(poker);
		JButton highlow = new JButton("HighLow");
		hl.add(highlow);
		JButton texas = new JButton("Texas Holdem");
		tex.add(texas);
		add(lab);
		add(bj);
		add(p);
		add(tex);
		add(hl);
		
		blackjack.addActionListener(this);
		poker.addActionListener(this);
		highlow.addActionListener(this);
		texas.addActionListener(this);
		texas.setActionCommand("texas");
		
		setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public static void main(String args[]){
		Client c = new Client("Casino");
		c.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent evt) {
		String event = evt.getActionCommand();
		
		if(event.equals("BlackJack")){
			VisualBJ.main(null);
			setVisible(false);
		}
		else if(event.equals("texas")){
			TH.main(null);
			setVisible(false);
		}
		else if(event.equals("Poker")){
			Poker.main(null);
			setVisible(false);
		}
		else if(event.equals("HighLow")){
			VisualHL.main(null);
			setVisible(false);
			
		}
		
	}
}
