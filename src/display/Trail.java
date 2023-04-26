package display;

import java.awt.Color;

public class Trail extends Render {
	private static final Color TRAIL_COLOR = new Color(100, 100, 100);
	
	private double opacity;
	private Color backgroundColor;
	
	public Trail(int x1, int y1, int x2, int y2, Color backgroundColor, double opacity) {
		super(x1, y1, x2-x1, y2-y1);
		
		this.opacity = opacity;
		this.backgroundColor = backgroundColor;
	}
}
