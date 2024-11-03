package Splendor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {
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
}
