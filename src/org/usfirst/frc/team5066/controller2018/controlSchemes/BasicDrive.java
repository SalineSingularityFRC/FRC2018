package org.usfirst.frc.team5066.controller2018.controlSchemes;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.DrivePneumatics;
import org.usfirst.frc.team5066.robot.Lift;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;

import org.usfirst.frc.team5066.controller2018.*;

/**
 * 
 * A simple driving scheme for the 2018 robot. Methods (implemented with
 * ControlScheme) will be used in teleopPeriodic of Robot.java
 * 
 * @author camtr
 *
 */

public class BasicDrive implements ControlScheme {

	XboxController xbox;
	LogitechController logitech;

	// true is fast, false is slow
	boolean speed;
	boolean safetyDisabled;
	
	boolean leftLowLimit;
	boolean rightLowLimit;

	// SpeedMode speedMode;

	/*
	 * boolean rBCurrent; boolean rBPrevious; boolean lBCurrent; boolean lBPrevious;
	 */

	/**
	 * A method for basic method for driving the drive train
	 * 
	 * @param drive
	 *            is a SingularityDrive object
	 * 
	 */

	public BasicDrive(int xboxPort, int logitechPort) {
		xbox = new XboxController(xboxPort);
		logitech = new LogitechController(logitechPort);
		speed = true;
		safetyDisabled = false;
		
		leftLowLimit = false;
		rightLowLimit = false;

		/*
		 * rBCurrent = false; rBPrevious = false; lBCurrent = false; lBPrevious = false;
		 */
	}

	public void drive(SingDrive drive, DrivePneumatics dPneu) {

		// code for Pneumatics. Press Right Bumper for fast,
		// Left Bumper for slow
		if (xbox.getRB())
			speed = true;
		else if (xbox.getLB())
			speed = false;

		if (speed)
			dPneu.setForward();
		else
			dPneu.setReverse();

		/*
		 * rBCurrent = xbox.getRB(); lBCurrent = xbox.getLB();
		 * 
		 * if (rBCurrent && !rBPrevious) { if (speedMode == SpeedMode.FAST) speedMode =
		 * SpeedMode.NORMAL; else speedMode = SpeedMode.FAST; } if (lBCurrent &&
		 * !lBPrevious) { if (speedMode == SpeedMode.SLOW) speedMode = SpeedMode.NORMAL;
		 * else speedMode = SpeedMode.SLOW; }
		 */

		drive.drive(xbox.getLS_Y(), 0, xbox.getLS_X(), true, SpeedMode.FAST);

		/*
		 * rBPrevious = rBCurrent; lBPrevious = lBCurrent;
		 */

	}

	public void lift(Lift lift, Timer timer) {
		
		if (logitech.getStickBackLeft()) {
			safetyDisabled = true;
			DriverStation.reportError("SAFETY DISABLED", true);
		}
		
		if (timer.get() >= 105.0 || safetyDisabled) {
		
			if (!leftLowLimit) {
				if (lift.releaseLiftLeft(logitech.getBaseFrontLeft())) {
					leftLowLimit = true;
					DriverStation.reportError("left lower limit reached", true);
				}
			}
			
			if (!rightLowLimit) {
				if (lift.releaseLiftRight(logitech.getBaseFrontRight())) {
					rightLowLimit = true;
					DriverStation.reportError("right lower limit reached", true);
			
				}
			}
			
		}
		
		if (rightLowLimit) {
			if (lift.controlRightLift(logitech.getBaseMiddleRight(), logitech.getBaseBackRight())) {
				DriverStation.reportError("right upper limit reached", true);
			}
		}
		
		if (leftLowLimit) {
			if (lift.controlLeftLift(logitech.getBaseMiddleLeft(), logitech.getBaseBackLeft())) {
				DriverStation.reportError("left lower limit reached", true);
			}
		}
		
		
		
		
	}
}
