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
		this.x = x;
		this.x = y;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		this.color = color;
	}
	
	public void draw(Graphics2D g, int cameraX, int cameraY, double zoom) {
		g.setColor(color);
		g.fillOval(
				x + cameraX, 
				y + cameraY, 
				(int) (sizeX * zoom), 
				(int) (sizeY * zoom)
		);
	}
}
