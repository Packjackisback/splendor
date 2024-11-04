package Splendor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Token extends Piece implements Drawable{
	private Gem gem;
	
	public Token(BufferedImage img, Gem g) {
		super("Token", img);
		gem = g;
	}

	@Override
	public void draw(Graphics g, int x, int y, int width, int height) {
		g.drawImage(super.getImage(), x, y, width, height, null);
	}
}
