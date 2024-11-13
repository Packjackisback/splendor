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
    private Game game;
    private int x, y, width, height;
    private String playerName;

    public Hand(int num, Game game) {
        this.game = game;
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
                //Implement asking for cards
                int wildsNeeded=cost.get(gem)-amt;
                if(tokens.get(new Gem("Wild")).size()>wildsNeeded) {
                    final boolean[] useWild = {false};
                    Runnable doYouWantAWild = new Runnable() {
                        public void run() {
                            useWild[0] = true;
                            System.out.println("Using wild");
                        }
                    };
                    Game.showToast("Do you want to use your " + wildsNeeded + " wilds?", "Use your wild(s)?","Yes",doYouWantAWild);
                    for(int i = 0; i<wildsNeeded; i++) {
                        tokens.get(new Gem("Wild")).remove(0);
                    }
                    break;
                }
            	check = false;
                break;
            }
        }
        return check;
    }
    public boolean canAffordNoble(Noble x) {
        boolean check = false;
        for(Gem g : x.getCost().keySet()) {
            if(cards.get(g).size()<x.getCost().get(g)) {
                check=false;
            }
        }
        return check;
    }

    public void addCard(Card c) {
    	cards.get(c.getGem()).add(c);
    }
    
    public void addToken(Token t) {
    	tokens.get(t.getGem()).add(t);
    }
    public void addNoble(Noble n) {
        nobles.add(n);
    }


    public void drawTurn(Object o) {
        if(o.getClass().getSimpleName().equals("Noble")) {
            if(canAffordNoble((Noble)o)) {
                game.takeNoble((Noble) o);
                //TODO implement turn over
            }
        }
    }
}
