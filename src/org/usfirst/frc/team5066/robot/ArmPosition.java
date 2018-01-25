package org.usfirst.frc.team5066.robot;

public class ArmPosition {
	
	private double alpha, gamma, x, y;
	
	public ArmPosition(double alpha, double gamma, double x, double y) {
		this.alpha = alpha;
		this.gamma = gamma;
		this.x = x;
		this.y = y;
	}
	
	public double getAlpha() {
		return this.alpha;
	}
	
	public double getGamma() {
		return this.gamma;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}

}
