package Splendor;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import javax.imageio.ImageIO;

public class Generator {
    public static BufferedImage loadImage(String filePath) {
      System.out.println("Trying to load " + filePath);
    BufferedImage image = null;
      try {
          InputStream is = Generator.class.getResourceAsStream(filePath); // Load the resource as a stream
          if (is == null) {
              System.out.println("Resource not found: " + filePath);
              return null; // Resource doesn't exist
          }
          image = ImageIO.read(is); // Read the image from the stream
      } 
      catch (IOException e) {
        System.out.println("Error reading file: " + filePath);
        e.printStackTrace();
      }
      return image;
    }
    public static ArrayList<Noble> getNobles() {
        ArrayList<Noble> nobles = new ArrayList<Noble>();
        nobles.add(new Noble("/Splendor/assets/nobles/20001.jpg", new HashMap<Gem, Integer>() {{
            put(new Gem("Blue"),4);
            put(new Gem("White"),4);
        }}
        ));
        nobles.add(new Noble("/Splendor/assets/nobles/20002.jpg", new HashMap<Gem, Integer>() {{
            put(new Gem("Blue"),4);
            put(new Gem("Green"),4);
        }}
        ));
        nobles.add(new Noble("/Splendor/assets/nobles/20003.jpg", new HashMap<Gem, Integer>() {{
            put(new Gem("Red"),4);
            put(new Gem("Green"),4);
        }}
        ));
        nobles.add(new Noble("/Splendor/assets/nobles/20004.jpg", new HashMap<Gem, Integer>() {{
            put(new Gem("Red"),4);
            put(new Gem("Black"),4);
        }}
        ));
        nobles.add(new Noble("/Splendor/assets/nobles/20005.jpg", new HashMap<Gem, Integer>() {{
            put(new Gem("Black"),4);
            put(new Gem("White"),4);
        }}
        ));
        nobles.add(new Noble("/Splendor/assets/nobles/20006.jpg", new HashMap<Gem, Integer>() {{
            put(new Gem("Black"),3);
            put(new Gem("Blue"),3);
            put(new Gem("White"),3);
        }}
        ));
        nobles.add(new Noble("/Splendor/assets/nobles/20007.jpg", new HashMap<Gem, Integer>() {{
            put(new Gem("Green"),3);
            put(new Gem("Blue"),3);
            put(new Gem("White"),3);
        }}
        ));
        nobles.add(new Noble("/Splendor/assets/nobles/20008.jpg", new HashMap<Gem, Integer>() {{
            put(new Gem("Green"),3);
            put(new Gem("Blue"),3);
            put(new Gem("Red"),3);
        }}
        ));
        nobles.add(new Noble("/Splendor/assets/nobles/20009.jpg", new HashMap<Gem, Integer>() {{
            put(new Gem("Green"),3);
            put(new Gem("Black"),3);
            put(new Gem("Red"),3);
        }}
        ));
        nobles.add(new Noble("/Splendor/assets/nobles/20010.jpg", new HashMap<Gem, Integer>() {{
            put(new Gem("White"),3);
            put(new Gem("Black"),3);
            put(new Gem("Red"),3);
        }}
        ));
        return nobles;
    }
    public static Stack[] getCards() throws IOException {
        System.out.println("Cards being called from the csv");
        Stack[] cards = new Stack[3];
        InputStream inputStream = Generator.class.getResourceAsStream("/Splendor/assets/cards.csv");
        if (inputStream == null) {
          throw new IOException("Resource not found: Splendor/assets/cards.csv");
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
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
            int worth = tokens[2].equals("") ? 0 : Integer.parseInt(tokens[2]); //If the point value is not null, set that to the worth, else set 0
            switch(tokens[0]) {

                case "1":

                    cards[0].push(new Card("/Splendor/assets/Cards/" + tokens[4], new Gem(tokens[1]), worth, cost, 1));
                    break;
                case "2":
                    cards[1].push(new Card("/Splendor/assets/Cards/" + tokens[4], new Gem(tokens[1]), worth, cost, 2));
                    break;
                case "3":
                    cards[2].push(new Card("/Splendor/assets/Cards/" + tokens[4], new Gem(tokens[1]), worth, cost, 3));
                    break;
                default:
                    throw(new RuntimeException("Invalid back level:" + tokens[0]));
            }
        }

        return cards;
    }
    public static BufferedImage getTokenImg(Gem gem) {
        BufferedImage img = null;
        switch(gem.getGemType()) {
            case "Wild":
                img = Generator.loadImage("/Splendor/assets/tokens/GoldToken.png");
                break;
            case "Red":
                img = Generator.loadImage("/Splendor/assets/tokens/RedToken.png");
                break;
            case "Blue":
                img = Generator.loadImage("/Splendor/assets/tokens/BlueToken.png");
                break;
            case "Green":
                img = Generator.loadImage("/Splendor/assets/tokens/GreenToken.png");
                break;
            case "White":
                img = Generator.loadImage("/Splendor/assets/tokens/WhiteToken.png");
                break;
            case "Black":
                img = Generator.loadImage("/Splendor/assets/tokens/BrownToken.png");
                break;
            default:
                throw(new RuntimeException("Invalid token gem:" + gem.getGemType()));
        }
    return img;
    }
}
