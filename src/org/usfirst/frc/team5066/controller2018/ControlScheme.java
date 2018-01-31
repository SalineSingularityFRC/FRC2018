package org.usfirst.frc.team5066.controller2018;

import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.DrivePneumatics;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.robot.Lift;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.Timer;

/**
 * This interface forces it's subclasses to have all the
 * necessary methods for controlling the robot. These methods 
 * should be called from teleop periodic in robot.java
 */

public interface ControlScheme {
	
	public void drive(SingDrive drive, DrivePneumatics dPneu);
	public void lift(Lift lift, Timer timer);
	public void arm(Arm arm);
	public void intake(Intake intake);
}
