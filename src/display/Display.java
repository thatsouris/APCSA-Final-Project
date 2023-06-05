package display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.PhysicsObject;

public class Display extends JPanel {
	private static final long serialVersionUID = -2527740006111385328L;
	private static Color BG_COLOR = new Color(25, 25, 25);
	private static Color BORDER_COLOR = new Color(145, 0, 255);
	
	private JFrame frame;
	
	private static double ZOOM_MIN = 0.5;
	private static double ZOOM_MAX = 5.0;
	
	private static int GRID_LINES_PER_DISTANCE = 25;
	
	public int FPS = 60;
	
	private int cameraX;
	private int cameraY;
	private Point cameraPlot;
	private int cameraDX = 0;
	private int cameraDY = 0;
	
	private double zoom = 1;
	
	private Render[] renders;
	
	public void setFPS(int fps) {
		FPS = fps;
	}
	public int getFPS() {
		return FPS;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public void drawGrid(Graphics2D g) {
		int centerX = super.getWidth() / 2;
		int centerY = super.getHeight() / 2;
		
		int approxZoom = (int) Math.max(Math.min(zoom, ZOOM_MAX), 1);
		
		int amountX = (int) (super.getWidth() * approxZoom) / GRID_LINES_PER_DISTANCE;
		int amountY = (int) (super.getHeight() * approxZoom) / GRID_LINES_PER_DISTANCE;
		
		int offsetX = cameraX / GRID_LINES_PER_DISTANCE * approxZoom;
		int offsetY = cameraY / GRID_LINES_PER_DISTANCE * approxZoom;
		System.out.println(cameraY);
		for (int x = -amountX - 3 - offsetX; x < amountX + 3 - offsetX; x++) {
			GridLine line = new GridLine(
					cameraX + (x * GRID_LINES_PER_DISTANCE),
					-super.getHeight() * approxZoom,
					cameraX + (x * GRID_LINES_PER_DISTANCE),
					super.getHeight() * approxZoom
				);
	
			line.draw(g, centerX, centerY, cameraX, cameraY, zoom);
		}
		
		for (int y = -amountY - 3 - offsetY; y < amountY + 3 - offsetY; y++) {
			GridLine line = new GridLine(
					-super.getWidth() * approxZoom,
					cameraY + (y * GRID_LINES_PER_DISTANCE),
					super.getWidth() * approxZoom,
					cameraY + (y * GRID_LINES_PER_DISTANCE)
				);
			line.draw(g, centerX, centerY, cameraX, cameraY, zoom);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		//Background
		graphics.setColor(BG_COLOR);
		graphics.fillRect(0, 0, super.getWidth(), super.getHeight());
		
		graphics.setColor(new Color(255, 255, 255));

		int centerX = super.getWidth() / 2;
		int centerY = super.getHeight() / 2;
		graphics.drawRect(centerX, centerY, 1, 1);
		
		drawGrid(graphics);
		
		// Drawing trails first (so that objects overlap)
		// Drawing components
		if (renders != null) {
			for (Render trail : PhysicsObject.getRenderedTrails(BG_COLOR)) {
				if (trail == null) { continue; };
				
				trail.draw(graphics, centerX, centerY, cameraX, cameraY, zoom);
			}
			
			for (Render render : renders) {
				render.draw(graphics,
					centerX,
					centerY,
					cameraX, 
					cameraY, 
					zoom
				);
			}
		}
		
		graphics.setColor(BORDER_COLOR);
		graphics.drawRect(1, 1, super.getWidth() - 2, super.getHeight() - 2);
		graphics.setFont(new Font("Courier New", Font.BOLD, 15));
		
		// Info about frame
		graphics.setColor(new Color(240, 240, 240));
		graphics.drawString("FPS : " + this.getFPS() + 
				" | CAMERAX : " + this.cameraX +
				" | CAMERAY : " + this.cameraY +
				" | ZOOM : x" + this.zoom, 10, this.getHeight() - 10);
	}
	
	public void updateRenders(Render[] renders) {
		this.renders = renders;
		repaint();
	}
	
	public Display(String title, int sizeX, int sizeY) {
		super();
		
		cameraX = 0;
		cameraY = 0;
		
		frame = new JFrame(title);
		frame.setSize(sizeX, sizeY);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        
        // Favicon
        Image icon = new ImageIcon(this.getClass().getResource("logotrans.png")).getImage();
        frame.setIconImage(icon);
        
        // Mouse Listener
        Mouse mouse = new Mouse();
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.addMouseWheelListener(mouse);
        
        this.repaint();
        
        Render[] r = new Render[0];
        this.updateRenders(r);
        
	}
	
	protected class Mouse extends MouseAdapter {
		@Override
        public void mousePressed(MouseEvent e) {
            cameraPlot = e.getPoint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            cameraDX = 0;
            cameraDY = 0;
            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
        	cameraDX = cameraPlot.x - e.getX();
            cameraDY = cameraPlot.y - e.getY();
            
            cameraPlot = e.getPoint();
            cameraX -= cameraDX;
            cameraY -= cameraDY;
           
            repaint();
        }
        
        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
        	zoom += (double) e.getWheelRotation() * -0.5d;
        	zoom = Math.round(zoom * 10) / 10d;
        	
        	zoom = Math.max(Math.min(zoom, ZOOM_MAX), ZOOM_MIN);
        	
        	repaint();
        }
	}
}
