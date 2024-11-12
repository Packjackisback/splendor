package Splendor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Hand {
    private final HashMap<Gem, Integer> credits;
    private final ArrayList<ArrayList<Token>> chips;
    private final ArrayList<ArrayList<Card>> cards;
    private int playerNum;
    private final ArrayList<Noble> nobles;
    private int score;
    private final ArrayList<Card> reservedCards;

    public Hand(int num) {
        playerNum = num;
        score = 0;
        chips = new ArrayList<ArrayList<Token>>();
        cards = new ArrayList<ArrayList<Card>>();
        nobles = new ArrayList<Noble>();
        reservedCards = new ArrayList<Card>();
        credits = new HashMap<Gem, Integer>();
    }

    public ArrayList<ArrayList<Token>> getChips() {
        return chips;
    }

    public ArrayList<ArrayList<Card>> getCard() {
        return cards;
    }

    public ArrayList<Noble> getNobles() {
        return nobles;
    }

    public int getScore() {
        return score;
    }

    public void changePlayerNum(int newNum) {
        playerNum = newNum;
    }

    public boolean CanAfford(Card x) {
        HashMap<Gem, Integer> cost = x.getCost();
        boolean check = false;
        Set<Gem> keys = cost.keySet();
        Iterator<Gem> iter = keys.iterator();
        while (iter.hasNext()) {
            Gem gem = iter.next();
            if (cost.get(keys) > credits.get(keys))
                check = false;
        }
        return check;
    }

    public void addCard(Card x, String Color) {

        HashMap<Gem, Integer> cost = x.getCost();
        Set<Gem> keys = cost.keySet();
        Gem gem = x.getGem();

        int index;

        if (Color == "Green") {
            index = 2;
        } else if (Color == "yellow") {
            index = 1;
        } else {
            index = 0;
        }

        ArrayList<Card> modify = cards.get(index);
        modify.add(x);

        Iterator<Gem> iter = keys.iterator();
        while (iter.hasNext()) {
            Gem gemm = iter.next();
            int temp = cost.get(gemm);
            credits.replace(gemm, credits.get(gemm) + temp);
        }

        score += x.getWorth();


    }
    public void addChip(Chip chip)
    {
    	if (chips.containsKey(chip))
    	    chips.replace(chip, chips.get(chip)+1);
    	else
    	    chips.put(chip,1);
    }
}
