package redditgroup.cardgame;
/*
 * Texas Holdem Program
 * 
 * Created by Michael Melchione
 */



import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;


import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

@SuppressWarnings("serial")
class TH extends JFrame { 
	JPanel dealerPanel;
	JPanel playerPanel;
	JPanel communityPanel;
	JPanel buttonsPanel;
	JPanel log;
	
	Deck deck;
	
	JLabel console;
	
	JLabel[] dealerCards;
	JLabel[] playerCards;
	JLabel[] communityCards;
	
	ImageIcon background;
	
	Button deal;
	Button call;
	Button fold;
	Button bet;
	JTextField betAmount;
	
	PokerHand player;
	PokerHand dealer;
	PokerHand community;
	
	Border sunkIn;
	
	int pot = 0;
	int playerBet = 0;
	int dealerBet = -1;
	
	public TH(String name) { 
		super(name);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.setLayout(null);
		setBackground(new Color(0, 100, 0));
		
		player = new PokerHand();
		dealer = new PokerHand();
		community = new PokerHand();
		
		deck = new Deck();
		
		background = new ImageIcon("Cards/b.gif");
		
		sunkIn = BorderFactory.createBevelBorder(BevelBorder.LOWERED); //Create a border so it can be used in all components
		
		CreateDealerComposite(); 
		CreateCommunityComposite();
		CreateButtonsComposite();
		CreatePlayerComposite();
		
		final JLabel dealerLabel = new JLabel();
		dealerLabel.setText("Dealer Cards");
		dealerLabel.setHorizontalAlignment(JLabel.CENTER);
		dealerLabel.setBounds(5, 5, dealerPanel.getWidth(), 20);
		
		final JLabel communityLabel = new JLabel();
		communityLabel.setText("Community Cards");
		communityLabel.setHorizontalAlignment(JLabel.CENTER);
		communityLabel.setBounds(dealerLabel.getWidth() + 5, 5, communityPanel.getWidth(), 20);
		
		final JLabel playerLabel = new JLabel();
		playerLabel.setText("Player Cards");
		playerLabel.setHorizontalAlignment(JLabel.CENTER);
		playerLabel.setBounds(5, buttonsPanel.getBounds().y + buttonsPanel.getHeight() + 5, buttonsPanel.getWidth(), 25);
		
		console = new JLabel();
		console.setHorizontalAlignment(JLabel.CENTER);
		console.setBorder(sunkIn);
		console.setBounds(5, playerPanel.getBounds().y + playerPanel.getHeight() + 5, buttonsPanel.getWidth(), 25);
		console.setBackground(Color.white);
		console.setText("- Welcome to Texas Hold'em!!! -");
		
		add(dealerLabel);
		add(dealerPanel);
		add(communityLabel);
		add(communityPanel);
		add(buttonsPanel);
		add(playerPanel);
		add(playerLabel);
		add(console);
	
	}
	
	private void CreatePlayerComposite() { 
		
		playerCards = new JLabel[2];
		
		playerPanel = new JPanel();
		playerPanel.setLayout(null);
		playerPanel.setBorder(sunkIn);
		
		playerPanel.setBounds(205, 217, 161, 107);
		
		System.out.println("Player Panel Bounds: " + playerPanel.getBounds());
		
	}
	
	private void CreateButtonsComposite() { 
		buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 5));
		buttonsPanel.setBorder(sunkIn);
		
		deal = new Button("Deal");
		deal.setActionCommand("deal");
		deal.addActionListener(new ButtonListener());
		
		call = new Button("Call");
		call.setActionCommand("call");
		call.addActionListener(new ButtonListener());
		call.setEnabled(false);
		
		fold = new Button("Fold");
		fold.setActionCommand("fold");
		fold.addActionListener(new ButtonListener());
		fold.setEnabled(false);
		
		bet = new Button("Bet");
		bet.setActionCommand("bet");
		bet.addActionListener(new ButtonListener());
		bet.setEnabled(false);
		
		betAmount = new JTextField("-Enter Bet Amount-");
		betAmount.setHorizontalAlignment(JTextField.CENTER);
		betAmount.addFocusListener(new FocusListener() { 
			@Override
			public void focusGained(FocusEvent e) { 
				betAmount.setText("");
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				
			}
		});
		betAmount.setEnabled(false);
		
		buttonsPanel.add(deal);
		buttonsPanel.add(call);
		buttonsPanel.add(fold);
		buttonsPanel.add(bet);
		buttonsPanel.add(betAmount);
		
		buttonsPanel.setBounds(5, dealerPanel.getHeight() + 10 + 30 + 5, dealerPanel.getWidth() + 15 + communityPanel.getWidth(), 30);
	}
	
	private void CreateCommunityComposite() { 
		
		communityCards = new JLabel[5];
		
		communityPanel = new JPanel();
		communityPanel.setLayout(null);
		communityPanel.setBorder(sunkIn);
		
		communityPanel.setBounds(181, 30, 395, 107);
		System.out.println("Community Panel Bounds :" + communityPanel.getBounds());
	}
	
	private void CreateDealerComposite() { 
		
		dealerCards = new JLabel[2];
		
		dealerPanel = new JPanel();
		dealerPanel.setLayout(null); //No layout so cards can be centered in dealerPanel
		dealerPanel.setBorder(sunkIn);
		
		dealerPanel.setBounds(5, 30, 161, 107); //Set size of dealer panel to fit cards perfectly
		
		System.out.println("Dealer Panel Bounds: " + dealerPanel.getBounds());
		
	}
	
	private int currentPlayerCard = 0;
	private int playerTotalWidth = 5;
	
	private void CreatePlayerCard() { 
		int i = currentPlayerCard;
		playerCards[i] = new JLabel();
		player.addCard(deck.dealCard());
		ImageIcon playerIcon = new ImageIcon(player.getCard(i).getFileName());
		playerCards[i].setIcon(playerIcon);
		playerCards[i].setBounds(playerTotalWidth, 5, playerIcon.getIconWidth(), playerIcon.getIconHeight());
		playerTotalWidth += playerCards[i].getWidth() + 5;
		playerPanel.add(playerCards[i]);
		currentPlayerCard += 1;
		playerPanel.updateUI();
	}
	
	private int dealerTotalWidth = 5;
	private int dealerCurrentCard = 0;
	
	private void CreateDealerCards() { 
		
		int i = dealerCurrentCard;
		
		dealerCards[i] = new JLabel();
		dealerCards[i].setIcon(background);
		dealer.addCard(deck.dealCard());
		dealerCards[i].setBounds(dealerTotalWidth, 5, background.getIconWidth(), background.getIconHeight());
		dealerPanel.add(dealerCards[i]);
		dealerCurrentCard += 1;
		dealerTotalWidth += dealerCards[i].getWidth() + 5;
		dealerPanel.updateUI();
	}
	
	int communityTotalWidth = 5;
	int communityCurrentCard = 0;
	
	private void CreateCommunityCards() { 
		int i = communityCurrentCard;
		communityCards[i] = new JLabel();
		community.addCard(deck.dealCard());
		ImageIcon icon = new ImageIcon(community.getCard(i).getFileName());
		communityCards[i].setIcon(icon);
		communityCards[i].setBounds(communityTotalWidth, 5, icon.getIconWidth(), icon.getIconHeight());
		communityPanel.add(communityCards[i]);
		communityTotalWidth += communityCards[i].getWidth() + 5;
		communityCurrentCard += 1;
		communityPanel.updateUI();
	}
	
	private boolean dealerFolded = false;
	
	public void dealersTurn() { 
		dealerBet = dealer.bet(playerBet);
		if(dealerBet == -1){
			consoleSetText("The dealer folded. You win $" + pot + "!");
			pot = 0;
			bet.setEnabled(false);	
			betAmount.setEnabled(false);
			fold.setEnabled(false);
			call.setEnabled(false);
			deal.setEnabled(true);
			dealerFolded = true;
			showCards();
			endGame();
			return;
		}
		else if(dealerBet > playerBet){
			consoleSetText("The dealer has raised you $" + (dealerBet - playerBet) + "!");
			pot += dealerBet;
			System.out.println("Pot: $" + pot);
			call.setEnabled(true);
			fold.setEnabled(true);
			bet.setEnabled(true);
			bet.setLabel("Raise");
			betAmount.setEnabled(true);
			dealerBet = -1;
			return;
		}
		else{
			consoleSetText("The dealer called.");
			pot += dealerBet;
			dealerBet = -1;
			System.out.println("Pot: $" + pot);
		}
	}
	
	private void showCards() { 
		for(int i = 0; i < 2; i++) { 
			dealerCards[i].setIcon(new ImageIcon(dealer.getCard(i).getFileName()));
		}
	}
	
	private boolean hasBet = false;
	
	public void getPlayerBet() { 
		try { //Put in a catch clause to easily check if the player has input a valid number
			playerBet = Integer.parseInt(betAmount.getText().trim());
			pot += playerBet;
			hasBet = true;
			betAmount.setText("");
		} catch (NumberFormatException e) { 
			hasBet = false;
			consoleSetText("Please enter a valid number");
		}
	}
	
	public void endGame() { 
		deal.setEnabled(true);
		bet.setEnabled(false);
		call.setEnabled(false);
		fold.setEnabled(false);
		betAmount.setText("-Enter Bet Amount-");
		betAmount.setEnabled(false);
	}
	
	public void initialize() { 
		
		for(int i = 0; i < dealerCards.length; i++) { 
			dealerCards[i].setIcon(null);
		}
		for(int i = 0; i < playerCards.length; i++) { 
			playerCards[i].setIcon(null);
		}
		for(int i = 0; i < community.getSize(); i++) { 
			communityCards[i].setIcon(null);
		}
		
		player = new PokerHand();
		dealer = new PokerHand();
		community = new PokerHand();
		pot = 0;
		
		deck = new Deck();
		dealerFolded = false;
		deal.setEnabled(true);
		bet.setEnabled(false);
		call.setEnabled(false);
		fold.setEnabled(false);
		betAmount.setEnabled(false);
		
		currentPlayerCard = 0;
		playerTotalWidth = 5;
		dealerCurrentCard = 0;
		dealerTotalWidth = 5;
		communityCurrentCard = 0;
		communityTotalWidth = 5;
	}
	
	public void consoleSetText(final String s) { 
		Thread t = new Thread() {
			@Override
			public void run() { 
				console.setText(s);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				console.setText("");
			}
		};
		t.start();
	}
	
	private boolean isNewGame = false;
	
	private class ButtonListener implements ActionListener { 
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String event = e.getActionCommand();
			switch(event) { 
			case "deal": 
				DealCards();
				break;
			case "call":
				CallHand();
				break;
			case "bet":
				BetHand();
				break;
			case "fold":
				FoldHand();
				break;
			case "raise":
				Raise();
				break;
			}
		}
		
		public void Raise() { 
			
		}
		
		public void DealCards() { 
			
			if(isNewGame) {
				initialize();
			}
			
			isNewGame = true;
			
			fold.setEnabled(true);
			call.setEnabled(true);
			bet.setEnabled(true);
			
			deck.shuffle();
			
			Thread t = new Thread() { 
				@Override
				public void run() { 
					boolean isDealer = false;
					for(int i = 0; i < 4; i++) { 
						if(isDealer) { 
							CreateDealerCards();
							isDealer = false;
						} else { 
							CreatePlayerCard();
							isDealer = true;
						}
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {}
					}
					betAmount.setEnabled(true);
				}
			};
			
			t.start();
			deal.setEnabled(false);
		}
		
		public void CallHand() { 
			dealersTurn();
		}
		
		String betStage = "Flop";
		
		public void BetHand() { 
			Thread t = new Thread() { 
				@Override
				public void run() { 
					if(betStage.equals("Flop")) { 
						getPlayerBet();
						System.out.println("Pot: $" + pot);
						if(hasBet) {
							dealersTurn();
							if(!dealerFolded) {
								for(int i = 0; i < 3; i++) { 
									CreateCommunityCards();
									try { 
										Thread.sleep(500);
									} catch (InterruptedException e) {};
								}
								hasBet = false;
								betStage = "FourthStreet";
							}
						}
					} else if (betStage.equals("FourthStreet")) { 
						getPlayerBet();
						System.out.println("Pot: $" + pot);
						if(hasBet) { 
							CreateCommunityCards();
							hasBet = false;
							dealersTurn();
							betStage = "River";
						}
					} else if (betStage.equals("River")) { 
						getPlayerBet();
						System.out.println("Pot: $" + pot);
						if(hasBet) { 
							CreateCommunityCards();
							hasBet = false;
							dealersTurn();
							betStage = "Post";
							if(player.PokerValue()[0] == dealer.PokerValue()[0]){
								if(player.PokerValue()[1] > dealer.PokerValue()[1]){
									console.setText("You win $" + pot + " with a " + player.showPokerValue());
									endGame();
								}
								else if(player.PokerValue()[1] < dealer.PokerValue()[1]){
									console.setText("You lose $" + pot + " to a " + dealer.showPokerValue());
									endGame();
								}
							}
							else if(player.PokerValue()[0] > dealer.PokerValue()[0]){
								console.setText("You win $" + pot + " with a " + player.showPokerValue());
								endGame();
							}
							else if(player.PokerValue()[0] < dealer.PokerValue()[0]){
								console.setText("You lose $" + pot + " to a " + dealer.showPokerValue());
								endGame();
							}
							
						}
					} else { 
						System.out.println("Returned");
						return;
					}
				}
			};
			t.start();
		}
		
		public void FoldHand() { 
			consoleSetText("You've Folded");
			endGame();
		}
	}
}

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
		bettf.setHorizontalAlignment(JTextField.CENTER); //Centers the text in the middle of the Component
		bettf.addFocusListener(new FocusListener() { //Clears the text in bettf upon gaining focus

			@Override
			public void focusGained(FocusEvent e) {
				bettf.setText("");
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				bettf.setText("-enter amount-");
			} 
			
		});
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
		dealer.setLayout(new GridLayout());
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
		final TH window = new TH("Texas Holdem");
		window.setVisible(true);
		/*
		window.addComponentListener(new ComponentAdapter() { 
			@Override
			public void componentResized(ComponentEvent e) { 
				System.out.println(window.getSize());
			}
		});
		*/
		window.setSize(590, 387);
	}
	
}

