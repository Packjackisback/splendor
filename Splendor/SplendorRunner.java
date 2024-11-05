package Splendor;

import java.io.IOException;

public class SplendorRunner {
    public static void main(String[] args) throws IOException {

        GameFrame gameFrame = new GameFrame();
        //The following code is for testing/demonstrative purposes only and should be removed
        Game game = new Game();
        GamePanel testing = new GamePanel(game);
        gameFrame.setPanel(testing);
       
    }
    public static void setUpGame() {

    }
}
