import java.awt.*;
import java.awt.image.BufferedImage;

public class Token extends Piece implements Drawable{
	private Gem gem;
	private boolean isWild;
	
	public Token(BufferedImage img, Gem g, boolean wild) {
		super("Token", img);
		gem = g;
		isWild = wild;
	}

	public boolean isWild() {
		return isWild;
	}
	
	public Gem getGem() {
		return gem;
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int width, int height) {
		g.drawImage(super.getImage(), x, y, width, height, null);
	}
}
