package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.controller2018.ControlScheme;
import org.usfirst.frc.team5066.controller2018.controlSchemes.BasicDrive;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;
import org.usfirst.frc.team5066.singularityDrive.SixWheelDrive;

import edu.wpi.first.wpilibj.IterativeRobot;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot {
	//comment
	int frontRightMotor, frontLeftMotor, middleRightMotor, middleLeftMotor, backRightMotor, backLeftMotor;
	
	SingDrive drive;
	
	ControlScheme currentScheme;
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		try {
			
		} catch (Exception e){
			
		} finally {
		
			drive = new SixWheelDrive(frontLeftMotor, backLeftMotor,
					frontRightMotor, backRightMotor, middleRightMotor, middleLeftMotor);
			currentScheme = new BasicDrive();
		}
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		
	}
	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	public void teleopInit() {
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		currentScheme.drive(drive);
	
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	
	}
}
