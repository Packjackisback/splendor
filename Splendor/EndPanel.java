package Splendor;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class EndPanel extends JPanel {
	BufferedImage background;
	private int[] scores; // Playernum, score
	private ArrayList<Hand> hands;
	
	public EndPanel() {
		this.background = Generator.loadImage("/Splendor/assets/resultsScreen.PNG"); // Initialize with the Game instance
		scores = new int[4];

		setPreferredSize(new Dimension(1920, 1080)); // Set a preferred size
		setVisible(true);
	}

	public void updateScores(int[] scores, ArrayList<Hand> hands) {
		this.scores = scores;
		this.hands = hands;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
		// TODO implement showing what score is the highest
		int highestScore = Integer.MIN_VALUE;
		for (int score : scores) {
			if (score > highestScore) {
				highestScore = score;
			}
		}
		
		ArrayList<Integer> highestScorers = new ArrayList<Integer>();
		ArrayList<Hand> losers = new ArrayList<Hand>();
		for (int i = 0; i < scores.length; i++) {
			if (scores[i] == highestScore) {
				highestScorers.add(i);
			} else {
				losers.add(hands.get(i));
			}
		}
		
		if (highestScorers.size() > 1) { // There are hands with the same scores
			int minimumHandSize = Integer.MAX_VALUE;
			for (int i : highestScorers) {
				if (hands.get(i).getNumberOfCards() < minimumHandSize) {
					minimumHandSize = hands.get(i).getNumberOfCards();
				}
			}
			
			ArrayList<Hand> handsWithMinimumCards = new ArrayList<Hand>();
			for (int i : highestScorers) {
				System.out.println(hands.get(i).getNumberOfCards());
				if (hands.get(i).getNumberOfCards() == minimumHandSize) {
					handsWithMinimumCards.add(hands.get(i));
				} else {
					losers.add(hands.get(i));
				}
			}
			
			if (handsWithMinimumCards.size() > 1) { // There is a tie
				String winners = "";
				for (int i = 0; i < handsWithMinimumCards.size(); i++) {
					if (i != handsWithMinimumCards.size()-1) {
						winners += handsWithMinimumCards.get(i).getPlayerName() + ", ";
					} else {
						winners += " and " + handsWithMinimumCards.get(i).getPlayerName();
					}
				}
				int fontSize = 0;
				if (handsWithMinimumCards.size() == 2) {
					fontSize = winners.length() * 5;
				} else if (handsWithMinimumCards.size() == 3) {
					fontSize = (int)(winners.length() * 2.5);
				} else {
					fontSize = (int)(winners.length() * 1.5);
				}
				g.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, fontSize));
				g.setColor(new Color(139, 69, 19));
				g.drawString(winners + " TIE! ", getWidth() / 100, getHeight() / 8 + 100);
				g.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 75));
				g.drawString("Score: " + highestScore, getWidth() / 100, getHeight() / 8 + 210);
				g.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 70));
				int yOffset = 0;
				for (Hand h : losers) {
					g.drawString(h.getPlayerName() + ": " + h.getScore(), getWidth() / 100 + 450,
							getHeight() / 8 + 210 + (80 * yOffset));
					yOffset++;
				}
			} else {
				g.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 200));
				g.setColor(new Color(139, 69, 19));
				g.drawString(handsWithMinimumCards.get(0).getPlayerName() + " WINS! ", getWidth() / 100, getHeight() / 8 + 100);
				g.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 75));
				g.drawString("Score: " + highestScore, getWidth() / 100, getHeight() / 8 + 210);
				g.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 70));
				int yOffset = 0;
				for (Hand h : losers) {
					g.drawString(h.getPlayerName() + ": " + h.getScore(), getWidth() / 100 + 450,
							getHeight() / 8 + 210 + (80 * yOffset));
					yOffset++;
				}
			}
		} else {
			g.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 200));
			g.setColor(new Color(139, 69, 19));
			g.drawString(hands.get(highestScorers.get(0)).getPlayerName() + " WINS! ", getWidth() / 100, getHeight() / 8 + 100);
			g.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 75));
			g.drawString("Score: " + highestScore, getWidth() / 100, getHeight() / 8 + 210);
			g.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 70));
			int yOffset = 0;
			for (Hand h : losers) {
				g.drawString(h.getPlayerName() + ": " + h.getScore(), getWidth() / 100 + 450,
						getHeight() / 8 + 210 + (80 * yOffset));
				yOffset++;
			}
		}
	}
}
