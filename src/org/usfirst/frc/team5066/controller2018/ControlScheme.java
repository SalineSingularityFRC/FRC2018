package org.usfirst.frc.team5066.controller2018;

import org.usfirst.frc.team5066.library.SingularityDrive;

/**
 * This interface forces it's subclasses to have all the
 * necessary methods for controlling the robot. These methods 
 * should be called from teleop periodic in robot.java
 */

public interface ControlScheme {
	
	public void drive(SingularityDrive drive);
}
