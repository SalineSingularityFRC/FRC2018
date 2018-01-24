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
	
	private double alpha, gamma, x, y;
	public enum Position {
		Exchange, Pickup, Scale, Start, Switch 
	}
	
	public Position currentPos;
	
	
	//TODO Figure out the encoder positions of the following
	private final double exchangeAlpha = 1.0;
	private final double exchangeGamma = 1.0;
	private final double pickupAlpha = 1.0;
	private final double pickupGamma = 1.0;
	private final double scaleAlpha = 1.0;
	private final double scaleGamma = 1.0;
	private final double startAlpha = 1.0;
	private final double startGamma = 1.0;
	private final double switchAlpha = 1.0;
	private final double switchGamma = 1.0;
	
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
	public void manualControl(double lowArmControl, double highArmControl, double power) {
		
		lowArm.set(ControlMode.PercentOutput, Math.pow(lowArmControl, power));
		highArm.set(ControlMode.PercentOutput, Math.pow(highArmControl, power));
	}
	
	/**
	 * Move both angles towards their values for a location
	 * Should not be used for pickup to scale, as that would 
	 * move outside the 16" boundary
	 * @param position The position to move to
	 */
	private void moveDirectlyToPosition(Position position) {
		
		if (position == Position.Exchange) {
			lowArm.set(ControlMode.Position, exchangeAlpha);
			lowArm.set(ControlMode.Position, exchangeGamma);
		}
		
		if (position == Position.Pickup) {
			lowArm.set(ControlMode.Position, pickupAlpha);
			lowArm.set(ControlMode.Position, pickupGamma);
		}
		
		if (position == Position.Scale) {
			lowArm.set(ControlMode.Position, scaleAlpha);
			lowArm.set(ControlMode.Position, scaleGamma);
		}
		
		if (position == Position.Start) {
			lowArm.set(ControlMode.Position, startAlpha);
			lowArm.set(ControlMode.Position, startGamma);
		}
		
		if (position == Position.Switch) {
			lowArm.set(ControlMode.Position, switchAlpha);
			lowArm.set(ControlMode.Position, switchGamma);
		}
	}
	
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
