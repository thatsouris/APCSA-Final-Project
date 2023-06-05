package display;

import java.awt.Color;
import java.awt.Graphics2D;

public class GridLine extends Render{
	private static Color GRID_COLOR = new Color(45, 45, 45);
	
	public GridLine(int x1, int y1, int x2, int y2) {
		super(x1, y1, x2, y2, GRID_COLOR);
	}
	
	@Override
	public void draw(Graphics2D g, int centerX, int centerY, int cameraX, int cameraY, double zoom) {
		g.setColor(super.getColor());
		
		cameraX /= 100;
		cameraY /= 100;
		
		g.drawLine(
				(int) ((super.getX() + cameraX) * zoom) + centerX, 
				(int) ((super.getY() + cameraY) * zoom) + centerY, 
				(int) ((super.getSizeX() + cameraX) * zoom) + centerX, 
				(int) ((super.getSizeY() + cameraY) * zoom) + centerY);
	}
}
