package org.usfirst.frc.team5066.controller2018.controlSchemes;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.DrivePneumatics;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;
import org.usfirst.frc.team5066.singularityDrive.SixWheelDrive;

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
	
	boolean goSwitch;

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
		
		goSwitch = true;

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

		((SixWheelDrive) drive).tankDrive(-xbox.getRS_Y(), -xbox.getLS_Y(), 2.0, SpeedMode.FAST);

		/*
		 * rBPrevious = rBCurrent; lBPrevious = lBCurrent;
		 */

	}
/*
	@Override
	public void intake(Intake intake) {
		// TODO Auto-generated method stub
		
	}*/

	@Override
	public void drive(SingDrive sd) {
		// TODO Auto-generated method stub
		
	}
}
