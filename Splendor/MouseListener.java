package Splendor;
import java.awt.event.MouseEvent;
import java.util.*;
public class MouseListener implements java.awt.event.MouseListener {
    public Game game;
    private GameState gameState;
    private Set<Gem> tokenKeys;
    private ArrayList<ArrayList<Card>> cards;
    public MouseListener(Game game, GameState gameState) {
        this.game = game;
        tokenKeys = game.getTokens().keySet();
        cards = game.getCardArray();
        this.gameState = gameState;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        for (Gem gem : tokenKeys) {
        	for (Token token : game.getTokens().get(gem)) {
        		if (x >= token.getX() && x <= token.getX() + token.getWidth() && y >= token.getY() && y <= token.getY() + token.getHeight()) {
              if (!token.isWild()) {
                 
                gameState.addTokenToCurrentPlayer(token, game.getTokens().get(gem).size()>2);
                cards = game.getCardArray();
                break;
              }
        		  else {

                Game.showToast("To get a wild, click a card you can't afford", "Help", "Ok!", new Runnable() {
                  public void run() {
                    System.out.println("Reminded user to click a card.");
                  }
                });
                break;
              }
            }
        	}
        }
        
        if (gameState.getDrawnTokens().size() == 0) {
	        for (ArrayList<Card> arr: cards) {
	            for (Card card : arr) {
	                if (x >= card.getX() && x <= card.getX() + card.getWidth() && y >= card.getY() && y <= card.getY() + card.getHeight()) {
	                	HashMap<Gem, Integer> canAffordReturn = gameState.getCurrentPlayerHand().canAfford(card);
	                    if(canAffordReturn != null) {
	                        game.takeCard(card);
	                        card.flip();
	                        gameState.addCardToCurrentPlayer(card, canAffordReturn);
	                        gameState.nextTurn();
	                    } else if (gameState.getCurrentPlayerHand().reserveCheck()) {
	                    	game.takeCard(card);
	                    	gameState.getCurrentPlayerHand().addReservedCard(card);
	                    	gameState.nextTurn();
	                    }
	                    break;
	                }
	            }
	        }
	        
	        ArrayList<Stack<Card>> drawCards = game.getDrawCards();
	    	for (Stack<Card> stack : drawCards) {
	    		Card card = stack.peek();
	    		if (x >= card.getX() && x <= card.getX() + card.getWidth() && y >= card.getY() && y <= card.getY() + card.getHeight()) {
					  final boolean[] reserveCard = { true };
            if (reserveCard[0] ) { 
              if(gameState.getCurrentPlayerHand().reserveCheck()) {
						    card = stack.pop(); // To remove the card
						    card.flip();
						    gameState.getCurrentPlayerHand().addReservedCard(card);
					  	  game.getPanel().repaint();
						    gameState.nextTurn();
              }
					  }
            break;

					}
	    	}
	    	ArrayList<Card> playersReservedCards = gameState.getCurrentPlayerHand().getReservedCards();
	    	for (Card card : playersReservedCards) {
	    		if (x >= card.getX() && x <= card.getX() + card.getWidth() && y >= card.getY() && y <= card.getY() + card.getHeight()) {
					HashMap<Gem, Integer> canAffordReturn = gameState.getCurrentPlayerHand().canAfford(card);
	    			if (canAffordReturn != null) {
	    				card.flip(); // Because the gamestate function flips it
	    				gameState.addCardToCurrentPlayer(card, canAffordReturn);
						gameState.getCurrentPlayerHand().removeReservedCard(card);
	                    gameState.nextTurn();
	                    game.getPanel().repaint();
	                    break;
	    			}
	    		}
	    	}
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
