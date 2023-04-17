package display;

import java.awt.Color;
import java.awt.Graphics2D;

public class Render {
	
	private int x;
	private int y;
	private int sizeX;
	private int sizeY;
	private Color color;
	
	public Render(int x, int y, int sizeX, int sizeY, Color color) {
		System.out.println(x);
		this.x = x;
		this.x = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		this.color = color;
	}
	
	public void draw(Graphics2D g, int centerX, int centerY, int cameraX, int cameraY, double zoom) {
		g.setColor(color);
		
		g.fillOval(
				(int) ((x + centerX + cameraX) * zoom) - (int) (sizeX * zoom / 2),
				(int) ((y + centerY + cameraY) * zoom) - (int) (sizeY * zoom / 2),
				(int) (sizeX * zoom),
				(int) (sizeY * zoom)
			);
	}
}
