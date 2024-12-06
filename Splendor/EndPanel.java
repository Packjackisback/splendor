package Splendor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
public class EndPanel extends JPanel {
    BufferedImage background;
    private List<Map.Entry<Integer, Integer>> score; //Playernum, score

    public EndPanel() {
        this.background = Generator.loadImage("Splendor/assets/resultsScreen.PNG");  // Initialize with the Game instance
        //TreeMap<Integer, Integer> firstScores = GameState.getScore(); 
        TreeMap<Integer, Integer> firstScores = new TreeMap<Integer, Integer>();
        firstScores.put(new Integer(1), new Integer(5));
        firstScores.put(new Integer(2), new Integer(9));
        firstScores.put(new Integer(3), new Integer(15));
        firstScores.put(new Integer(4), new Integer(2));

        score = new ArrayList<>(firstScores.entrySet());
        score.sort((entry1, entry2) -> {
            int valueCompare = entry1.getValue().compareTo(entry2.getValue());
            System.out.println(""  + entry1.getValue() + " is more than " + entry2.getValue() + " : " + valueCompare);
            if (valueCompare == 0) {
              if(GameState.hands.get(entry1.getValue()-1).getCards().size()>GameState.hands.get(entry2.getValue()-1).getCards().size())  {
                return 1;
              }
              if(GameState.hands.get(entry1.getValue()-1).getCards().size()<GameState.hands.get(entry2.getValue()-1).getCards().size()) {
                return -1;
              }
              return 0; //if they have the same amount
            }
            return valueCompare;
        }); //Now hopefully the winner is last
        setPreferredSize(new Dimension(1920, 1080)); // Set a preferred size
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, null);
        //TODO implement showing what score is the highest
       
        g.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 200));
        g.setColor(new Color(139, 69, 19));
        g.drawString("Player " + score.get(3).getKey() + " WINS! " , getWidth() / 100, getHeight() / 8 + 100);
        g.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 75));
        g.drawString("Score: " + score.get(3).getValue() , getWidth() / 100, getHeight() / 8 + 210);
        g.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 70));
        g.drawString("Player " + score.get(2).getKey() + ": " + score.get(2).getValue(), getWidth() / 100 + 450, getHeight() / 8 + 210);
        g.drawString("Player " + score.get(1).getKey() + ": " + score.get(1).getValue(), getWidth() / 100 + 450, getHeight() / 8 + 290);
        g.drawString("Player " + score.get(0).getKey() + ": " + score.get(0).getValue(), getWidth() / 100 + 450, getHeight() / 8 + 370);
    }
}
