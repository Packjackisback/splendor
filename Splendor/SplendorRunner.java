package Splendor;

import java.io.IOException;

public class SplendorRunner {
    public static void main(String[] args) throws IOException {
        //We are going to chain the next one and the frame so we can call setPanel
        GameFrame gameFrame = new GameFrame();
        //The following code is for testing/demonstrative purposes only and should be removed
        Game game = new Game();
        GamePanel testing = new GamePanel(game);
        StartPanel testing3 = new StartPanel(gameFrame, testing);

        testing3.addMouseListener(testing3);

        EndPanel testing2 = new EndPanel();
        gameFrame.setPanel(testing3);

    }
    public static void setUpGame() {

    }
}
