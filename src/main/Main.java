package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.Timer;

import display.Display;

public class Main {
	public static void main(String[] args) {
		Display display = new Display("APCSA | PHYSICS PROJECT", 600, 600);
		
		new PhysicsObject(5 * Math.pow(10, 2), Math.pow(10, 15), Math.pow(10, 3), 0, 8000, 0);
		new PhysicsObject(5 * Math.pow(10, 2), Math.pow(10, 15), 0, -7500, 5000, 0);
		new PhysicsObject(5 * Math.pow(10, 2), Math.pow(10, 15), 0, -5000, 5000, 0);
		new PhysicsObject(5.5 * Math.pow(10, 2), Math.pow(10, 25), 0, 0);

		
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
