package org.usfirst.frc.team5066.controller2018.controlSchemes;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.DrivePneumatics;
import org.usfirst.frc.team5066.robot.Lift;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;
import org.usfirst.frc.team5066.controller2018.*;

/**
 * 
 * A simple driving scheme for the 2018 robot.
 * Methods (implemented with ControlScheme) will
 * be used in teleopPeriodic of Robot.java
 * 
 * @author camtr
 *
 */

public class BasicDrive implements ControlScheme {
	
	XboxController xbox;
	LogitechController logitech;
	
	//true is fast, false is slow
	boolean speed;
	
	
	//SpeedMode speedMode;
	
	/*
	boolean rBCurrent;
	boolean rBPrevious;
	boolean lBCurrent;
	boolean lBPrevious;
	*/
	
	/**
	 * A method for basic method for driving the drive train
	 * 
	 * @param drive is a SingularityDrive object
	 * 
	 */
	
	public BasicDrive(int xboxPort, int logitechPort) {
		xbox = new XboxController(xboxPort);
		logitech = new LogitechController(logitechPort);
		speed = true;
		
		/*
		rBCurrent = false;
		rBPrevious = false;
		lBCurrent = false;
		lBPrevious = false;
		*/
	}
	
	public void drive(SingDrive drive, DrivePneumatics dPneu) {
		
		
		//code for Pneumatics. Press Right Bumper for fast,
		//Left Bumper for slow
		if (xbox.getRB()) speed = true;
		else if (xbox.getLB()) speed = false;
		
		if (speed) dPneu.setForward();
		else dPneu.setReverse();
		
		/*
		rBCurrent = xbox.getRB();
		lBCurrent = xbox.getLB();
		
		if (rBCurrent && !rBPrevious) {
			if (speedMode == SpeedMode.FAST) speedMode = SpeedMode.NORMAL;
			else speedMode = SpeedMode.FAST;
		}
		if (lBCurrent && !lBPrevious) {
			if (speedMode == SpeedMode.SLOW) speedMode = SpeedMode.NORMAL;
			else speedMode = SpeedMode.SLOW;
		}
		*/
		
		drive.drive(xbox.getLS_Y(), 0, xbox.getLS_X(), true, SpeedMode.FAST);
		
		/*
		rBPrevious = rBCurrent;
		lBPrevious = lBCurrent;
		*/
		
	}
	
	public void controlLifts(Lift lift) {
		
		lift.releaseLiftLeft(logitech.getBaseFrontLeft());
		
	}
}
