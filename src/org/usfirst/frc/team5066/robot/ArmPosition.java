package org.usfirst.frc.team5066.robot;

public class ArmPosition {
	
	
	private double alpha, gamma, x, y;
	private boolean isPosition;
	
	public ArmPosition(double alpha, double gamma, double x, double y) {
		
		this.alpha = alpha;
		this.gamma = gamma;
		this.x = x;
		this.y = y;
		
		isPosition = false;
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
	
	public boolean isAtPosition(double currentAlpha, double currentGamma) {
		
		return Math.abs(alpha - currentAlpha) < Math.PI / 12 && Math.abs(gamma - currentGamma) < Math.PI / 12;
	}
	
	public boolean 

}
