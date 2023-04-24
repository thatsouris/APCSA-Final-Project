package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.Timer;

import display.Display;

public class Main {
	public static void main(String[] args) {
		Display display = new Display("APCSA | PHYSICS PROJECT", 600, 600);
		
		new PhysicsObject(695000, 1.989 * Math.pow(10, 30), 0, 0);
		new PhysicsObject(6371, 5.972 * Math.pow(10, 24), 0, 150000000, 46d, 0);

		
		ActionListener render = new ActionListener() {
			long lastTime = new Date().getTime();
			double dt = 0d;
			long thisTime = lastTime;
			
			public void actionPerformed(ActionEvent e) {
				thisTime = new Date().getTime();
				dt = (thisTime - lastTime) / 1000d;
				lastTime = thisTime;
				
				PhysicsObject.simulateObjects(dt * 24 * 60 * 60);
				display.updateRenders(PhysicsObject.getRenders());
			}
		};
		new Timer((int) (1000d / (double) display.getFPS()), render).start();
	}
}
