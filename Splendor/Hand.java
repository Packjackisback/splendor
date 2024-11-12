package Splendor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Hand {
    //private final HashMap<Gem, Integer> credits;
    private final HashMap<Gem, ArrayList<Token>> tokens;
    private final HashMap<Gem, ArrayList<Card>> cards;
    private int playerNum;
    private final ArrayList<Noble> nobles;
    private int score;
    private final ArrayList<Card> reservedCards;
    
    private int x, y, width, height;
    private String playerName;

    public Hand(int num) {
        playerNum = num;
        score = 0;
        tokens = new HashMap<Gem, ArrayList<Token>>();
        cards = new HashMap<Gem, ArrayList<Card>>();
        nobles = new ArrayList<Noble>();
        reservedCards = new ArrayList<Card>();
        //credits = new HashMap<Gem, Integer>();
    }

    // Getters
    public HashMap<Gem, ArrayList<Token>> getTokens() { return tokens; }
    public HashMap<Gem, ArrayList<Card>> getCards() { return cards; }
    public int getPlayerNum() { return playerNum; }
    public ArrayList<Noble> getNobles() { return nobles; }
    public int getScore() { return score; }
    public ArrayList<Card> getReservedCards() { return reservedCards; }

    public void changePlayerNum(int newNum) {
        playerNum = newNum;
    }

    public boolean canAfford(Card x) {
        HashMap<Gem, Integer> cost = x.getCost();
        boolean check = false;
        Set<Gem> keys = cost.keySet();
        Iterator<Gem> iter = keys.iterator();
        while (iter.hasNext()) {
            Gem gem = iter.next();
            int amt = cards.get(gem).size() + tokens.get(gem).size();
            if (amt >= cost.get(gem)) {
            	check = true;
            } else {
            	check = false;
            }
        }
        return check;
    }

    public void addCard(Card c) {
    	cards.get(c.getGem()).add(c);
    }
}
