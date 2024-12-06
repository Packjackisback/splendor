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
        		if (!token.isWild()) {
        			if (x >= token.getX() && x <= token.getX() + token.getWidth() && y >= token.getY() && y <= token.getY() + token.getHeight()) {
                        gameState.addTokenToCurrentPlayer(token, game.getTokens().get(gem).size()>2);
                        cards = game.getCardArray();
                        break;
                    }
        		}
        	}
        }

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
				final boolean[] reserveCard = { false };
				Runnable doYouWantToReserve = new Runnable() {
					public void run() {
						reserveCard[0] = true;
						System.out.println("Reserving Card");
					}
				};
				if (game.containsWildToken()) {
					Game.showToast("Reserve the draw card?", "Reserve?", "Yes", doYouWantToReserve);
				} else {
					Game.showToast("Reserve the draw card?", "Reserve? (no wilds)", "Yes", doYouWantToReserve);
				}

				if (reserveCard[0]) {
					card = stack.pop(); // To remove the card
					card.flip();
					gameState.getCurrentPlayerHand().addReservedCard(card);
					game.getPanel().repaint();
					gameState.nextTurn();
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