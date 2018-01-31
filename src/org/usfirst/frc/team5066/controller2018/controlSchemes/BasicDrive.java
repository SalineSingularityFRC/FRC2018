package org.usfirst.frc.team5066.controller2018.controlSchemes;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.Arm;
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
		else /*if (xbox.getLB())*/
			speed = false;
		
		//TODO fix 
		/*if (speed)
			dPneu.setForward();
		else
			dPneu.setReverse();
		 */
		/*
		 * rBCurrent = xbox.getRB(); lBCurrent = xbox.getLB();
		 * 
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
		
		//override safety timer
		if (logitech.getStickBackLeft()) {
			safetyDisabled = true;
			DriverStation.reportError("SAFETY DISABLED", true);
		}
		
		//test to see if safety is on
		if (timer.get() >= 105.0 || safetyDisabled) {
			
			//release left lift until lower limit switch is pressed
			if (!leftLowLimit && lift.releaseLiftLeft(logitech.getBaseFrontLeft())) {
				leftLowLimit = true;
				DriverStation.reportError("left lower limit reached", true);
			}
			
			//release right lift until lower limit switch is pressed
			if (!rightLowLimit && lift.releaseLiftRight(logitech.getBaseFrontRight())) {
				rightLowLimit = true;
				DriverStation.reportError("right lower limit reached", true);
			
			}
			
		}
		
		//lifts right lift. When reached upper limit switch, ping driver
		if (rightLowLimit && lift.controlRightLift(logitech.getBaseMiddleRight(), logitech.getBaseBackRight())) {
			DriverStation.reportError("right upper limit reached", true);
		}
		
		//lifts left lift. When reached upper limit switch, ping driver
		if (leftLowLimit && lift.controlLeftLift(logitech.getBaseMiddleLeft(), logitech.getBaseBackLeft())) {
			DriverStation.reportError("left upper limit reached", true);
		}
		
	}

	@Override
	public void arm(Arm arm) {
		
		arm.controlArm(logitech.getStickY(), 2.0);
		if(logitech.getStickFrontRight()) {
			arm.setArmForward();
		}
		
		else if(logitech.getStickBackRight()) {
			arm.setArmReverse();
		}
	}
}
