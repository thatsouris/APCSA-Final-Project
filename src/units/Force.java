package units;

public class Force {
	private long forceX;
	private long forceY;
	
	public Force(double forceX, double forceY) {
		this.forceX = (long) forceX;
		this.forceY = (long) forceY;
	}
	
	public Force(double force, long angle) {
		this.forceX = (long) (force * Math.cos(angle));
		this.forceY = (long) (force * Math.sin(angle));
	}
	
	public Force() {
		this.forceX = 0l;
		this.forceY = 0l;
	}
	
	public long getForceX() {
		return this.forceX;
	}
	
	public long getForceY() {
		return this.forceY;
	}
	
	public double getAcelX(double mass) {
		return (double) (this.forceX / mass);
	}
	
	public double getAcelY(double mass) {
		return (double) (this.forceY / mass);
	}
	
	public Force add(Force other) {
		this.forceX += other.getForceX();
		this.forceY += other.getForceY();
		
		return this;
	}
	
	@Override
	public String toString() {
		return "[FORCE] | FORCEX: " + this.forceX + ", FORCEY: " + this.forceY;
	}
}
