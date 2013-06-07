package redditgroup.cardgame;
/*
 * Texas Holdem Program
 * 
 * Created by Michael Melchione
 */



import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
	
	JButton deal;
	JButton call;
	JButton fold;
	JButton bet;
	JTextField betAmount;
	
	PokerHand player;
	PokerHand dealer;
	PokerHand community;
	
	Border sunkIn;
	
	int pot = 0;
	int playerBet = 0;
	int dealerBet = 0;
	int previousBet = 0;
	
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
		
		deal = new JButton("Deal");
		deal.setActionCommand("deal");
		deal.addActionListener(new ButtonListener());
		
		call = new JButton("Call / Check");
		call.setActionCommand("call");
		call.addActionListener(new ButtonListener());
		call.setEnabled(false);
		
		fold = new JButton("Fold");
		fold.setActionCommand("fold");
		fold.addActionListener(new ButtonListener());
		fold.setEnabled(false);
		
		bet = new JButton("Bet");
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
		dealer.addCard(community.getCard(i));
		player.addCard(community.getCard(i));
		communityPanel.updateUI();
	}
	
	private boolean dealerFolded = false;
	private boolean dealerHasBet = false;
	private boolean dealerRaise = false;
	private boolean dealerHasRaised;
	
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
			betAmount.setEnabled(true);
			dealerHasRaised = true;
			return;
		}
		else{
			consoleSetText("The dealer called.");
			pot += dealerBet;
			dealerBet = 0;
			System.out.println("Pot: $" + pot);
		}
	}
	
	private void showCards() { 
		for(int i = 0; i < 2; i++) { 
			dealerCards[i].setIcon(new ImageIcon(dealer.getCard(i).getFileName()));
		}
	}
	
	private boolean hasBet = false;
	
	public void getPlayerBet(String action) { 
		if(!action.equals("call")) {
			try { //Put in a catch clause to easily check if the player has input a valid number
				playerBet = Integer.parseInt(betAmount.getText().trim());
				pot += playerBet;
				hasBet = true;
				betAmount.setText("");
			} catch (NumberFormatException e) { 
				hasBet = false;
				consoleSetText("Please enter a valid number");
			}
		} else { 
			hasBet = true;
			betAmount.setText("");
			playerBet = 0;
			dealerBet = 0;
		}
	}
	
	public void endGame() { 
		showCards();
		deal.setEnabled(true);
		bet.setEnabled(false);
		call.setEnabled(false);
		fold.setEnabled(false);
		betAmount.setText("-Enter Bet Amount-");
		betAmount.setEnabled(false);
	}
	
	String betStage = "Flop";
	
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
		betStage = "Flop";
		
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
		
		public void PlayHand(final String action) { 
			Thread t = new Thread() { 
				@Override
				public void run() { 
					if(betStage.equals("Flop")) { 
						getPlayerBet(action);
						System.out.println("Pot: $" + pot);
						if(hasBet) {
							dealersTurn();
							if(!dealerHasRaised) {
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
							} else { 
								dealerHasRaised = false;
							}
						}
						dealerBet = 0;
						playerBet = 0;
					} else if (betStage.equals("FourthStreet")) { 
						getPlayerBet(action);
						System.out.println("Pot: $" + pot);
						if(hasBet) { 
							CreateCommunityCards();
							hasBet = false;
							dealersTurn();
							betStage = "River";
						}
					} else if (betStage.equals("River")) { 
						getPlayerBet(action);
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
		
		public void CallHand() { 
			hasBet = true;
			PlayHand("call");
		}
		
		public void BetHand() { 
			PlayHand("bet");
		}
		
		public void FoldHand() { 
			consoleSetText("You've Folded");
			endGame();
		}
	}
	
	public static void main(String[] args){
		final TH window = new TH("Texas Holdem");
		window.setSize(590, 387);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		/*
		window.addComponentListener(new ComponentAdapter() { 
			@Override
			public void componentResized(ComponentEvent e) { 
				System.out.println(window.getSize());
			}
		});
		*/
	}
}

