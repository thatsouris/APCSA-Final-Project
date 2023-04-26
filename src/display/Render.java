package display;

import java.awt.Color;
import java.awt.Graphics2D;

public class Render {
	
	private int x;
	private int y;
	private int sizeX;
	private int sizeY;
	private Color color;
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getSizeX() {
		return sizeX;
	}
	
	public int getSizeY() {
		return sizeY;
	}
	
	public Color getColor() {
		return color;
	}
	
	public Render(int x, int y, int sizeX, int sizeY, Color color) {
		this.x = x;
		this.y = y;
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
		
		g.setColor(new Color(255, 255, 255));
		g.drawOval(
				(int) ((x + centerX + cameraX) * zoom) - (int) (sizeX * zoom / 2),
				(int) ((y + centerY + cameraY) * zoom) - (int) (sizeY * zoom / 2),
				(int) (sizeX * zoom),
				(int) (sizeY * zoom)
			);
	}
}
