package org.usfirst.frc.team5066.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * A class for precisely controlling the 2018 arm
 * @author camtr
 *
 */

public class Arm {
	
	private TalonSRX lowArm, highArm;
	
	public enum Position {
		Start, Pickup, Exchange, Switch, LowScale, HighScale, Inbetween 
	}
	public Position currentPos;
	
	private double gammaVelocity;
	
	
	//TODO Figure out the ENCODER POSITIONS of the following
	//alpha, gamma, x, y
	private final ArmPosition start = new ArmPosition(1.0, 1.0, -30.0, 50.0);
	private final ArmPosition pickup = new ArmPosition(1.0, 1.0, 0.0, 0.0);
	private final ArmPosition exchange = new ArmPosition(1.0, 1.0, 0.0, 3.0);
	private final ArmPosition swich = new ArmPosition(1.0, 1.0, 0.0, 32.0);
	private final ArmPosition lowScale = new ArmPosition(1.0, 1.0, 0.0, 58.0);
	private final ArmPosition highScale = new ArmPosition(1.0, 1.0, 0.0, 82.0);
	
	/*
	private final ArmPosition[] positions = {new ArmPosition("start", 1.0, 1.0, -30.0, 50.0),
			 new ArmPosition("pickup", 1.0, 1.0, 0.0, 0.0),
			 new ArmPosition("exchange", 1.0, 1.0, 0.0, 3.0),
			 new ArmPosition("switch", 1.0, 1.0, 0.0, 32.0),
			 new ArmPosition("lowScale", 1.0, 1.0, 0.0, 58.0),
			 new ArmPosition("highScale", 1.0, 1.0, 0.0, 82.0)};
	*/
	private final double length1 = 15;
	private final double length2 = 10;
	
	/**
	 * 
	 * @param lowArm the port for the first joint motor
	 * @param highArm the port for the second joint motor
	 */
	public Arm(int lowArm, int highArm) {
		this.lowArm = new TalonSRX(lowArm);
		this.highArm = new TalonSRX(highArm);
		
		currentPos = Position.Start;
	}
	
	/**
	 * The method used for controlling the arm with two joystick axes
	 * @param lowArmControl the value controlling the first joint
	 * @param highArmControl the value controlling the second joint
	 * @param power the control values are raised to the power-eth power
	 */
	public void manualControl(double lowArmControl, double highArmControl, double lowArmExponent, double highArmExponent) {
		
		lowArm.set(ControlMode.PercentOutput, Math.pow(lowArmControl, lowArmExponent));
		highArm.set(ControlMode.PercentOutput, Math.pow(highArmControl, highArmExponent));
	}
	
	public void autoControl(Position position) {
		
		this.checkPosition();
		
		if (currentPos == position) {
			this.moveDirectlyToPosition(position);
		}
		
		else if ((currentPos == Position.Pickup || currentPos == Position.Exchange 
				|| currentPos == Position.Switch || currentPos == Position.Start) &&
			(position == Position.Pickup || position == Position.Exchange 
				|| position == Position.Switch || currentPos == Position.Start)) {
			
			this.moveDirectlyToPosition(position);
			
		}
		
		else if ((currentPos == Position.HighScale || currentPos == Position.LowScale) &&
				(position == Position.HighScale || position == Position.LowScale)) {
			
			this.moveDirectlyToPosition(position);
		}
		
		
		
	}
	
	
	private void checkPosition() {
		if (start.isAtPosition(this.getAlpha(), this.getGamma())) currentPos = Position.Start;
		else if (pickup.isAtPosition(this.getAlpha(), this.getGamma())) currentPos = Position.Pickup;
		else if (exchange.isAtPosition(this.getAlpha(), this.getGamma())) currentPos = Position.Exchange;
		else if (swich.isAtPosition(this.getAlpha(), this.getGamma())) currentPos = Position.Switch;
		else if (lowScale.isAtPosition(this.getAlpha(), this.getGamma())) currentPos = Position.LowScale;
		else if (highScale.isAtPosition(this.getAlpha(), this.getGamma())) currentPos = Position.HighScale;
		
		else currentPos = Position.Inbetween;
	}
	
	/**
	 * Move both angles towards their values for a location
	 * Should not be used for pickup to scale, as that would 
	 * move outside the 16" boundary
	 * @param position The position to move to
	 */
	private void moveDirectlyToPosition(Position position) {
		
		switch (position) {
		
		case Exchange:
			lowArm.set(ControlMode.Position, exchange.getAlpha());
			highArm.set(ControlMode.Position, exchange.getGamma());
			break;

		case Pickup:
			lowArm.set(ControlMode.Position, pickup.getAlpha());
			highArm.set(ControlMode.Position, pickup.getGamma());
			break;

		case LowScale:
			lowArm.set(ControlMode.Position, lowScale.getAlpha());
			highArm.set(ControlMode.PercentOutput, lowScale.getAlpha());
			break;

		case HighScale:
			lowArm.set(ControlMode.Position, highScale.getAlpha());
			highArm.set(ControlMode.Position, highScale.getGamma());
			break;

		case Start:
			lowArm.set(ControlMode.Position, start.getAlpha());
			highArm.set(ControlMode.Position, start.getGamma());
			break;

		case Switch:
			lowArm.set(ControlMode.Position, swich.getAlpha());
			highArm.set(ControlMode.Position, swich.getGamma());
			break;
		}
	}
	
	
	//private double moveGamma(double dAlphadT, double currentAlpha) {
		
		//gammaVelocity = 
		
		//highArm.set(ControlMode.Velocity, );
		
	//}
	
	
	/**
	 * 
	 * @return the current angle alpha
	 */
	private double getAlpha() {
		
		//TODO Figure out the actual equation for this that relates
		//the quadrature position to alpha through testing.
		
		return lowArm.getSensorCollection().getQuadraturePosition();
	}
	
	/**
	 * 
	 * @return the current angle gamma
	 */
	private double getGamma() {
		
		//TODO Figure out the actual equation for this that relates
		//the quadrature position to gamma through testing.
		
		return highArm.getSensorCollection().getQuadraturePosition();
	}

}
