package main;

import java.awt.Color;
import java.util.ArrayList;

import display.Render;
import units.Force;

public class PhysicsObject {
	public static ArrayList<PhysicsObject> objects = new ArrayList<PhysicsObject>();
	public static final long GRAVITY_CONSTANT = (long) ((long) 6.67 * (long) Math.pow(10, -11));
	
	private double x;
	private double y;
	private double acelX;
	private double acelY;
	private double velX;
	private double velY;
	
	private double radius;
	private double mass;
	
	public double getRadius() { return radius; }
	public double getMass() { return mass; }
	public double getX() { return x; }
	public double getY() { return y; }
	public double getVelX() { return velX; }
	public double getVelY() { return velY; }
	public double getAcelX() { return acelX; }
	public double getAcelY() { return acelY; }
	
	// Constructors
	
	public PhysicsObject(double radius, double mass, double x, double y) {
		this.radius = radius;
		this.mass = mass;
		this.x = x;
		this.y = y;
		this.acelX = 0;
		this.acelY = 0;
		this.velX = 0;
		this.velY = 0;
		
		objects.add(this);
	}
	
	public PhysicsObject(double radius, double mass, double x, double y, double velX, double velY) {
		this.radius = radius;
		this.mass = mass;
		this.x = x;
		this.y = y;
		this.acelX = 0;
		this.acelY = 0;
		this.velX = velX;
		this.velY = velY;
		
		objects.add(this);
	}
	
	public PhysicsObject(double radius, double mass, double x, double y, double velX, double velY, double acelX, double acelY) {
		this.radius = radius;
		this.mass = mass;
		this.x = x;
		this.y = y;
		this.acelX = acelX;
		this.acelY = acelY;
		this.velX = velX;
		this.velY = velY;
		
		objects.add(this);
	}
	
	public double getDistance(PhysicsObject other) {
		return Math.sqrt(
				Math.pow(Math.abs(this.x - other.getX()), 2) +
				Math.pow(Math.abs(this.y - other.getY()), 2)
		);
	}
	
	public static Render[] getRenders() {
		Render[] renders = new Render[objects.size()];
		
		for (int i = 0; i < objects.size(); i++) {
			renders[i] = objects.get(i).castToRender();
		}
		
		return renders;
	}
	
	public Render castToRender() {
		return new Render(
				(int) (x / 100d), 
				(int) (y / 100d), 
				(int) (radius * 2 / 100d), 
				(int) (radius * 2 / 100d), 
				new Color(255, 255, 255)
			);
	}
	
	// Simulation Methods
	
	public Force calculateForce() {
		// EQ: (G*m1*m2)/(r^2)
		Force resultant = new Force();
		
		for (PhysicsObject other : objects) {
			if (other.equals(this)) continue;
			double distance = getDistance(other);
			
			double ratioX = (double) -x / distance;
			double ratioY = (double) -y / distance;

			double force = (long) ((6.67d * this.mass * other.getMass()) / (Math.pow(distance, 2) * Math.pow(10, 11)));
			System.out.println("Force : " + this + " " + force);
			resultant.add(new Force(ratioX * force, ratioY * force));
		}
		
		return resultant;
	}
	
	public static void simulateObjects(double dt) {
		for (PhysicsObject object : objects) {
			object.simulateStep(dt);
		}
	}
	
	public void simulateStep(double dt) {
		System.out.println("Deltatime : " + dt);
		Force nextForce = calculateForce();
		
		this.acelX = nextForce.getAcelX(mass);
		this.acelY = nextForce.getAcelY(mass);
		System.out.println(acelX);
		this.velX += acelX * dt;
		this.velY += acelY * dt;
		
		this.x += velX ;
		this.y += velY; // Possible error
		System.out.println("velx : " + velX);
		System.out.println("acelx : " + acelX);
		System.out.println("x : " + x);
		System.out.println("y : " + y);
	}
}
