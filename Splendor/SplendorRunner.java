package Splendor;

import java.io.IOException;

public class SplendorRunner {
    public static void main(String[] args) throws IOException {
        //We are going to chain the next one and the frame so we can call setPanel
        GameFrame gameFrame = new GameFrame();
        //The following code is for testing/demonstrative purposes only and should be removed
        Game game = new Game();
        EndPanel testing2 = new EndPanel();
        GamePanel testing = new GamePanel(game, testing2, gameFrame);
        StartPanel testing3 = new StartPanel(gameFrame, testing);

        testing3.addMouseListener(testing3);


        gameFrame.setPanel(testing);
        

    }
    public static void setUpGame() {

    }
}
