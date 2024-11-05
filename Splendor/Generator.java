package Splendor;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import javax.imageio.ImageIO;

public class Generator {
    public static BufferedImage loadImage(String filePath) {
        BufferedImage image = null;
        try {
            File file = new File(filePath); // Create a File object from the file path
            image = ImageIO.read(file); // Read the file into a BufferedImage
        } catch (IOException e) {
            System.out.println("File: " + filePath);
            e.printStackTrace(); // Handle the exception if the file is not found or cannot be read
        }
        return image;
    }
    public static ArrayList<Noble> getNobles() {
        ArrayList<Noble> nobles = new ArrayList<Noble>();

        return nobles;
    }
    public static Stack[] getCards() throws IOException {
        Stack[] cards = new Stack[3];

        FileReader in = new FileReader("Splendor/assets/cards.csv");
        BufferedReader reader = new BufferedReader(in);
        String line;
        cards[0] = new Stack<Card>();
        cards[1] = new Stack<Card>();
        cards[2] = new Stack<Card>();

        while((line=reader.readLine())!=null) {
            //Read cost from the 5-9 cols
            HashMap<Gem, Integer> cost = new HashMap<Gem, Integer>();
            String[] tokens = line.split(",", -1);
            System.out.println(tokens[4]);
            if(!tokens[5].equals("")) {cost.put(new Gem("White"), Integer.parseInt(tokens[5]));}
            if(!tokens[6].equals("")) {cost.put(new Gem("Blue"), Integer.parseInt(tokens[6]));}
            if(!tokens[7].equals("")) {cost.put(new Gem("Green"), Integer.parseInt(tokens[7]));}
            if(!tokens[8].equals("")) {cost.put(new Gem("Red"), Integer.parseInt(tokens[8]));}
            if(!tokens[9].equals("")) {cost.put(new Gem("Black"), Integer.parseInt(tokens[9]));}
            int worth = tokens[2].equals("") ? Integer.parseInt(tokens[2]) : 0; //If the point value is not null, set that to the worth, else set 0
            switch(tokens[0]) {

                case "1":

                    cards[0].push(new Card("Splendor/assets/Cards/" + tokens[4], new Gem(tokens[1]), worth, cost, 1));
                    break;
                case "2":
                    cards[1].push(new Card("Splendor/assets/Cards/" + tokens[4], new Gem(tokens[1]), worth, cost, 2));
                    break;
                case "3":
                    cards[2].push(new Card("Splendor/assets/Cards/" + tokens[4], new Gem(tokens[1]), worth, cost, 3));
                    break;
                default:
                    throw(new RuntimeException("Invalid back level:" + tokens[0]));
            }
        return cards;

        }

        return cards;
    }
}
