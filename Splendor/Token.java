package Splendor;
import java.awt.*;

public class Token extends Piece {
	private final Gem gem;
	private final boolean isWild;

	public Token(Gem g) {
		super("Token", Generator.getTokenImg(g));
		gem = g;
		isWild = g.getGemType().equals("Wild");
	}

	public boolean isWild() {
		return isWild;
	}
	
	public Gem getGem() {
		return gem;
	}
	
	public void draw(Graphics g, int sizeOfStack, boolean drawOnBottom) {
		if (drawOnBottom) {
			g.setColor(Color.WHITE);
			if (sizeOfStack > 0) {
				g.drawString("" + sizeOfStack, getX()+getWidth()/2, getY()+getHeight()+getHeight()/2);
			}
		} else {
			g.setColor(Color.WHITE);
			if (sizeOfStack > 0) {
				g.drawString("" + sizeOfStack, getX(), getY());
			}
		}
		
		g.drawImage(super.getImage(), getX(), getY(), getWidth(), getHeight(), null);
	}



}