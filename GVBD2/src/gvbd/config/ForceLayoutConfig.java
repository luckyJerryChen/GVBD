package gvbd.config;

public class ForceLayoutConfig extends LayoutConfig {
	private int speed;
	private int k;
	private double forceThreshold; 
	private boolean isDirected;

	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	public int getK() {
		return k;
	}
	public void setK(int k) {
		this.k = k;
	}
	public boolean isDirected() {
		return isDirected;
	}
	public void setDirected(boolean isDirected) {
		this.isDirected = isDirected;
	}
	public double getForceThreshold() {
		return forceThreshold;
	}
	public void setForceThreshold(double forceThreshold) {
		this.forceThreshold = forceThreshold;
	}

}
