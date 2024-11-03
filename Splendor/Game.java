package Splendor;

import java.awt.*;
import java.util.HashMap;

public class Game {
    Card testingCard;
    public Game() {

        testingCard = new Card(ImageLoader.loadImage("Splendor/assets/BlueCard.jpg"), new Gem("Blue"), 1, new HashMap(), 1);
    }
    public void drawCards(Graphics g, int startX, int startY, int cardWidth, int cardHeight, int padding) {
        testingCard.draw(g, startX, startY, cardWidth, cardHeight);
        //Above is just testing, eventually this will hold the Lists of Cards, and this will display them
        //TODO Implement drawing 2d array of cards
    }
}
