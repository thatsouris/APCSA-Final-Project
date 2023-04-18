package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.Timer;

import display.Display;

public class Main {
	public static void main(String[] args) {
		Display display = new Display("APCSA | PHYSICS PROJECT", 600, 600);
		
		new PhysicsObject(5 * Math.pow(10, 2), Math.pow(10, 15), 0, 5000, 500, 500d, 5d, 5d);
		new PhysicsObject(5 * Math.pow(10, 3), Math.pow(10, 20), 0, 5.5* Math.pow(10, 4));
		
		
		ActionListener render = new ActionListener() {
			long lastTime = new Date().getTime();
			double dt = 0d;
			long thisTime = lastTime;
			
			public void actionPerformed(ActionEvent e) {
				thisTime = new Date().getTime();
				dt = (thisTime - lastTime) / 1000d;
				lastTime = thisTime;
				
				PhysicsObject.simulateObjects(dt);
				display.updateRenders(PhysicsObject.getRenders());
			}
		};
		new Timer((int) (1000d / (double) display.getFPS()), render).start();
	}
}
