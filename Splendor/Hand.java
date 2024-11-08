package Splendor;
import java.util.*;
public class Hand {
private HashMap <String,Integer> credits ;
private ArrayList<ArrayList<Token>> chips;
private ArrayList<ArrayList<Card>> cards;
private int playerNum;
private ArrayList<Noble> nobles ;
private int score; 
private ArrayList<Card> reservedCards;

public Hand (int num)
{
	playerNum = num; 
	score= 0; 
	chips = new ArrayList<ArrayList<Token>>();
	cards = new ArrayList<ArrayList<Card>>();
	nobles= new ArrayList<Noble>();
	reservedCards = new ArrayList<Card>();
	credits = new HashMap<String, Integer>();
}
 public ArrayList<ArrayList<Token>> getChips(){ return chips;}
 public ArrayList<ArrayList<Card>> getCard() {return cards;}
 public ArrayList<Noble> getNobles(){return nobles;}
 public int getScore() {return score;}
 public void changePlayerNum(int newNum) {playerNum = newNum;}
 public boolean CanAfford (Card x)
 {
	 HashMap <Gem, Integer> cost = x.getCost();
	 boolean check = false;
	 
	 return check; 
 }
 public void addCard(Card x, String Color)
 {
	 HashMap <Gem, Integer> cost = x.getCost();
	 Gem gem = x.getGem();
	 String gemName = gem.getGemType();
	int index; 
	if (Color=="Green") {index = 2;}
	else if(Color =="yellow") {index = 1;}
	else {index =0; }
	ArrayList<Card> modify = cards.get(index);
	modify.add(x);
	score+=x.getWorth();
	ArrayList <String> gemNames = new ArrayList <String> (); 


 }
}
