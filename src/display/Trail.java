package display;

import java.awt.Color;
import java.awt.Graphics2D;

public class Trail extends Render {
	private static final Color TRAIL_COLOR = new Color(100, 100, 100);
	
	private Color transparentColor;
	
	public Trail(int x1, int y1, int x2, int y2, Color backgroundColor, double opacity) {
		super(x1, y1, x2, y2);
		
		Color colorDelta = new Color(
				TRAIL_COLOR.getRed() - backgroundColor.getRed(),
				TRAIL_COLOR.getGreen() - backgroundColor.getGreen(),
				TRAIL_COLOR.getBlue() - backgroundColor.getBlue()
			);
		
		this.transparentColor = new Color(
				(int) (TRAIL_COLOR.getRed() - (colorDelta.getRed() * opacity)),
				(int) (TRAIL_COLOR.getGreen() - (colorDelta.getGreen() * opacity)),
				(int) (TRAIL_COLOR.getBlue() - (colorDelta.getBlue() * opacity))
			);
	}
	
	@Override
	public void draw(Graphics2D g, int centerX, int centerY, int cameraX, int cameraY, double zoom) {
		g.setColor(transparentColor);
		
		g.drawLine(
				(int) ((super.getX() + cameraX + centerX) * zoom), 
				(int) ((super.getY() + cameraY + centerY) * zoom), 
				(int) ((super.getSizeX() + cameraX + centerX) * zoom), 
				(int) ((super.getSizeY() + cameraY + centerY) * zoom));
	}
}
