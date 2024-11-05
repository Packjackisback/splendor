package Splendor;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class Game {
    HashMap<Gem, Integer> tokenBank;
    Stack<Card> greenCards;
    Stack<Card> yellowCards;
    Stack<Card> blueCards;
    ArrayList<Noble> nobleBank;
    ArrayList<Card> greenBoard;
    ArrayList<Card> yellowBoard;
    ArrayList<Card> blueBoard;
    Card testingCard;


    public Game() throws IOException {
//TODO implement reading from csv file
        Stack[] cards = Generator.getCards();
        this.greenCards = cards[0];
        this.yellowCards = cards[1];
        this.blueCards = cards[2];

        testingCard = new Card("Splendor/assets/BlueCard.jpg", new Gem("Blue"), 1, new HashMap(), 1);
    }

    //Getters
    public ArrayList<ArrayList<Card>> getCardArray() {
        //Not sure what this is for, but this is in the UML
        ArrayList<ArrayList<Card>> out = new ArrayList<ArrayList<Card>>();
        out.add(greenBoard);
        out.add(yellowBoard);
        out.add(blueBoard);
        return out;
    }

    public Card drawCard(int x, int y) {
        //This needs to be followed with a call to the class to redraw the cards
        Card out;
        switch(y) {
            case 0:
                out = greenBoard.remove(x);
                break;
            case 1:
                out = yellowBoard.remove(x);
                break;
            case 2:
                out = blueBoard.remove(x);
                break;
            default:
                throw new RuntimeException("Input card y value is incorrect. Should be 0-2, but is " + y +"\nError in Game.drawCard");
        }
        dealCards();
        return out;
    }

    public void dealCards() { //Puts cards into the board from the stacks, should be called whenever a change to the board is made
        while(greenBoard.size()<4) {
            greenCards.peek().flip();
            greenBoard.add(0, greenCards.pop());
        }
        while(yellowBoard.size()<4) {
            yellowCards.peek().flip();
            yellowBoard.add(0, yellowCards.pop());
        }
        while(blueBoard.size()<4) {
            blueCards.peek().flip();
            blueBoard.add(0, blueCards.pop());
        }
    }

    //Draw methods
    public void drawTokens(Graphics g, int startX, int startY, int tokenBankWidth, int tokenBankHeight) {

    }
    public void drawCards(Graphics g, int startX, int startY, int cardWidth, int cardHeight ) {
        //also includes the
        testingCard.draw(g, startX, startY, cardWidth, cardHeight);
        //Above is just testing, eventually this will hold the Lists of Cards, and this will display them
        //TODO Implement drawing 2d array of cards
    }
}
