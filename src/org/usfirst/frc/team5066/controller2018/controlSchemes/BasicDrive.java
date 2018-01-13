package org.usfirst.frc.team5066.controller2018.controlSchemes;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
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
	SpeedMode speedMode;
	
	boolean rBCurrent;
	boolean rBPrevious;
	
	boolean lBCurrent;
	boolean lBPrevious;
	
	/**
	 * A method for basic method for driving the drive train
	 * 
	 * @param drive is a SingularityDrive object
	 * 
	 */
	
	public BasicDrive(int xboxPort) {
		xbox = new XboxController(xboxPort);
		
		rBCurrent = false;
		rBPrevious = false;
		
		lBCurrent = false;
		lBPrevious = false;
		
	}
	
	public void drive(SingDrive drive) {
		
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
		
		drive.drive(xbox.getLS_Y(), 0, xbox.getLS_X(), true, speedMode);
		
		rBPrevious = rBCurrent;
		
	}
}
