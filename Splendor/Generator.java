package Splendor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
        for(int i = 1; i<10; i++)
            nobles.add(new Noble(Generator.loadImage("Splendor/assets/nobles/2000" + i + ".jpg")));
            nobles.add(new Noble(Generator.loadImage("Splendor/assets/nobles/20010.jpg")));
        return nobles;
    }
    public static Stack[] getCards() {
        Stack[] cards = new Stack[3];
        //TODO read from csv
        return cards;
    }
}
