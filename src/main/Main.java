package main;

import display.Display;

public class Main {
	public static void main(String[] args) {
		Display display = new Display("APCSA | PHYSICS PROJECT", 600, 600);
		
		PhysicsObject object1 = new PhysicsObject(50, 5, 1, 1);
		PhysicsObject object2 = new PhysicsObject(50, 5* Math.pow(10, 24), 3, 5);
		System.out.println(object1.calculateForce());
	}
}
