package Splendor;

import java.io.IOException;

public class SplendorRunner {
    public static void main(String[] args) throws IOException {
        //We are going to chain the next one and the frame so we can call setPanel
        GameFrame gameFrame = new GameFrame();
        //The following code is for testing/demonstrative purposes only and should be removed
        Game game = new Game();
        EndPanel endPanel = new EndPanel();
        GameState gameState = new GameState(game, endPanel, gameFrame);
        MouseListener mouseListener = new MouseListener(game, gameState);
        GamePanel gamePanel = new GamePanel(game, endPanel, gameFrame, gameState);
        StartPanel startPanel = new StartPanel(gameFrame, gamePanel);
        gamePanel.addMouseListener(mouseListener);
        startPanel.addMouseListener(startPanel);


        gameFrame.setPanel(startPanel);


    }
    public static void setUpGame() {

    }
}