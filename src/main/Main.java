package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import display.Display;

public class Main {
	public static int timeStep = 60*60*3;
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				init();
			}
		});
	}
	
	public static void init() {
		Display display = new Display("APCSA | PHYSICS PROJECT", 600, 600);
		
		//new PhysicsObject(695000, 1.989 * Math.pow(10, 30), 0, 0);
		//new PhysicsObject(6371, 5.972 * Math.pow(10, 24), 0, 150000000, 46d, 0);

		new PhysicsObject(new Color(255, 255, 0), 3000000, 1.989 * Math.pow(10, 30), 0, 0);
		new PhysicsObject(new Color(0, 200, 20),1500000, 1.989 * Math.pow(10, 19), 0, 50000000, 500, 0);
		//new PhysicsObject(new Color(0, 255, 255),1500000, 16 * Math.pow(10, 21), 0, 120000000, 200, 0);
		//new PhysicsObject(new Color(255, 200, 20),1000000, 1.989 * Math.pow(10, 10), 0, 55000000, 500, 0);
		ActionListener render = new ActionListener() {
			long lastTime = new Date().getTime();
			double dt = 0d;
			long thisTime = lastTime;
			
			public void actionPerformed(ActionEvent e) {
				
				thisTime = new Date().getTime();
				dt = (thisTime - lastTime) / 1000d;
				lastTime = thisTime;
				
				
				if (display.getFrame().getFocusOwner() == null) {
					return;
				}
				PhysicsObject.simulateObjects(dt * timeStep);
				display.updateRenders(PhysicsObject.getRenders());
			}
		};
		new Timer((int) (1000d / (double) display.getFPS()), render).start();
		
		// Time skip slider
        JSlider timeSkip = new JSlider(JSlider.HORIZONTAL,
        		60*60, 24*60*60, 3*60*60);
        timeSkip.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
		        if (!source.getValueIsAdjusting()) {
		        	int time = (int) source.getValue();
		        	
		        	timeStep = time;
		        }
			}
        });
        
        timeSkip.setMajorTickSpacing(60*60);
        timeSkip.setPaintTicks(true);
        
        Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
        JLabel l1 = new JLabel("hr/s");
        JLabel l2 = new JLabel("day/s");
        l1.setForeground(new Color(255, 255, 255));
        l2.setForeground(new Color(255, 255, 255));
        
        labels.put(60*60, l1);
        labels.put(24*60*60, l2);
        timeSkip.setLabelTable(labels);
        
        timeSkip.setSize(50, 50);
        timeSkip.setPaintLabels(true);
        timeSkip.setVisible(true);
        timeSkip.setBackground(new Color(220, 220, 220));
        timeSkip.setOpaque(false);
    
        display.add(timeSkip);
 
        
        
	}
}
