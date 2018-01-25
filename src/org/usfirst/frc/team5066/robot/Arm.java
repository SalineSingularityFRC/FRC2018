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
		Start, Pickup, Exchange, Switch, LowScale, HighScale 
	}
	public Position currentPos;
	
	
	//TODO Figure out the ENCODER POSITIONS of the following
	//alpha, gamma, x, y
	private final ArmPosition start = new ArmPosition(1.0, 1.0, -30.0, 50.0);
	private final ArmPosition pickup = new ArmPosition(1.0, 1.0, 0.0, 0.0);
	private final ArmPosition exchange = new ArmPosition(1.0, 1.0, 0.0, 3.0);
	private final ArmPosition swich = new ArmPosition(1.0, 1.0, 0.0, 32.0);
	private final ArmPosition lowScale = new ArmPosition(1.0, 1.0, 0.0, 58.0);
	private final ArmPosition highScale = new ArmPosition(1.0, 1.0, 0.0, 82.0);
	
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
	public void manualControl(double lowArmControl, double highArmControl, double power) {
		
		lowArm.set(ControlMode.PercentOutput, Math.pow(lowArmControl, power));
		highArm.set(ControlMode.PercentOutput, Math.pow(highArmControl, power));
	}
	
	public void autoControl(Position position) {
		
		if (currentPos == position) {
			this.moveDirectlyToPosition(position);
		}
		
		if ((currentPos == Position.Pickup || currentPos == Position.Exchange 
				|| currentPos == Position.Switch || currentPos == Position.Start) &&
			(position == Position.Pickup || position == Position.Exchange 
				|| position == Position.Switch || currentPos == Position.Start)) {
			
			this.moveDirectlyToPosition(position);
			
		}
		
		
		
		
		
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
