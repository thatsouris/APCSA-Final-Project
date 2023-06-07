package main;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import display.Render;
import display.Trail;
import units.Force;

public class PhysicsObject {
	public static ArrayList<PhysicsObject> objects = new ArrayList<PhysicsObject>();
	public static final long GRAVITY_CONSTANT = (long) ((long) 6.67 * (long) Math.pow(10, -11));
	private static final int TRAIL_SIZE = 250;
	public static double SCALE = 10000000d;
	
	private double x;
	private double y;
	private double acelX;
	private double acelY;
	private double velX;
	private double velY;
	
	private double radius;
	private double mass;
	
	private Point2D.Double[] trail;
	
	private Color color;
	
	public double getRadius() { return radius; }
	public double getMass() { return mass; }
	public double getX() { return x; }
	public double getY() { return y; }
	public double getVelX() { return velX; }
	public double getVelY() { return velY; }
	public double getAcelX() { return acelX; }
	public double getAcelY() { return acelY; }
	
	// Constructors
	
	public PhysicsObject(Color color, double radius, double mass, double x, double y) {
		this.color = color;
		this.radius = radius;
		this.mass = mass;
		this.x = x;
		this.y = y;
		this.acelX = 0;
		this.acelY = 0;
		this.velX = 0;
		this.velY = 0;
		this.trail = new Point2D.Double[TRAIL_SIZE];
		
		objects.add(this);
	}
	
	public PhysicsObject(Color color, double radius, double mass, double x, double y, double velX, double velY) {
		this.color = color;
		this.radius = radius;
		this.mass = mass;
		this.x = x;
		this.y = y;
		this.acelX = 0;
		this.acelY = 0;
		this.velX = velX;
		this.velY = velY;
		this.trail = new Point2D.Double[TRAIL_SIZE];
		
		objects.add(this);
	}
	
	public PhysicsObject(Color color, double radius, double mass, double x, double y, double velX, double velY, double acelX, double acelY) {
		this.color = color;
		this.radius = radius;
		this.mass = mass;
		this.x = x;
		this.y = y;
		this.acelX = acelX;
		this.acelY = acelY;
		this.velX = velX;
		this.velY = velY;
		this.trail = new Point2D.Double[TRAIL_SIZE];
		
		objects.add(this);
	}
	
	public Point2D.Double[] getTrail() {
		return this.trail;
	}
	
	public double getDistance(PhysicsObject other) {
		double s1 = Math.abs(this.x - other.getX());
		double s2 = Math.abs(this.y - other.getY());
		
		return Math.sqrt(
				(s1*s1) +
				(s2*s2)
		);
	}
	
	public static Render[] getRenders() {
		Render[] renders = new Render[objects.size()];
		
		for (int i = 0; i < objects.size(); i++) {
			renders[i] = objects.get(i).castToRender();
		}
		
		return renders;
	}
	
	public static Point2D.Double[] shiftPoints(Point2D.Double[] arr) {
		for (int i = arr.length - 2; i >= 0; i--) {
			arr[i+1] = arr[i];
		}
		
		return arr;
	}
	
	public static Render[] getRenderedTrails(Color backgroundColor) {
		Render[] renders = new Render[TRAIL_SIZE * objects.size()];
		
		int count = 0;
		
		for (int i = 0; i < objects.size(); i++) {
			PhysicsObject object = objects.get(i);
			Point2D.Double[] trails = object.getTrail();
			
			for (int k = 0; k < trails.length - 1; k++) {
				Point2D.Double p1 = trails[k];
				Point2D.Double p2 = trails[k + 1];
				
				if (p1 == null || p2 == null) { count++; continue; };
				
				renders[count] = new Trail(
						(int) (p1.x / SCALE), 
						(int) (p1.y / SCALE),
						(int) (p2.x / SCALE),
						(int) (p2.y / SCALE),
						backgroundColor,
						(double) ((double)k / trails.length)
					);
				
				count++;
			}
		}
		
		return renders;
	}
	
	public Render castToRender() {
		return new Render(
				(int) (x / SCALE), 
				(int) (y / SCALE), 
				(int) Math.max(3, radius * 2 / SCALE), 
				(int) Math.max(3, radius * 2 / SCALE), 
				this.color
			);
	}
	
	
	
	// Simulation Methods
	
	public Force calculateForce() {
		// EQ: (G*m1*m2)/(r^2)
		Force resultant = new Force();
		
		for (PhysicsObject other : objects) {
			if (other.equals(this)) continue;
			double distance = getDistance(other);
			double dx = x - other.getX();
			double dy = y - other.getY();
			
			double angle = Math.atan2(other.getY()-y, other.getX()-x);
			
			double ratioX = (double) -dx / distance;
			double ratioY = (double) -dy / distance;
			double force = (long) ((this.mass * other.getMass()) * Math.pow(distance, -2)) *(Math.pow(10, -7) * 6.67d);
 
			resultant.add(new Force(force * ratioX, force * ratioY));
		}
		
		return resultant;
	}
	
	public static void simulateObjects(double dt) {
		for (PhysicsObject object : objects) {
			object.simulateStep(dt);
		}
	}
	
	public void simulateStep(double dt) {
		Force nextForce = calculateForce();
		
		this.acelX = nextForce.getAcelX(mass);
		this.acelY = nextForce.getAcelY(mass);
		
		this.velX += nextForce.getAcelX(mass) * dt; // acelX * dt;
		this.velY += nextForce.getAcelY(mass) * dt; //acelY * dt;
		System.out.println(nextForce.getAcelY(mass) * dt);
		
		this.x += velX * dt;
		this.y += velY * dt; // Possible error
		
		shiftPoints(this.trail);
		this.trail[0] = new Point2D.Double(this.x, this.y);
	}
}
