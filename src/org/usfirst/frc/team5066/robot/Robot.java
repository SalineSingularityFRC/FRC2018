package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.controller2018.ControlScheme;
import org.usfirst.frc.team5066.controller2018.controlSchemes.BasicDrive;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SingularityProperties;
import org.usfirst.frc.team5066.library.SingularityPropertyNotFoundException;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;
import org.usfirst.frc.team5066.singularityDrive.SixWheelDrive;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */

public class Robot extends IterativeRobot {
	
	int frontRightMotor, frontLeftMotor, middleRightMotor, middleLeftMotor, backRightMotor, backLeftMotor;
	int drivePneuForward, drivePneuReverse;
	
	SingDrive drive;
	DrivePneumatics dPneumatics;
	
	SingularityProperties properties;
	
	ControlScheme currentScheme;
	
	final int XBOX_PORT = 0;
	final int BIG_JOYSTICK_PORT = 1;
	final int SMALL_JOYSTICK_PORT = 2;
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		try {
			properties = new SingularityProperties("/home/lvuser/robot.properties");
		} catch (Exception e){
			setDefaultProperties();
			
			properties = new SingularityProperties();
			DriverStation.reportError("error in properties", true);
		} finally {
			
			loadProperties();
		
			drive = new SixWheelDrive(frontLeftMotor, backLeftMotor,
					frontRightMotor, backRightMotor, middleRightMotor, middleLeftMotor);
			dPneumatics = new DrivePneumatics(drivePneuForward, drivePneuReverse);
			
			currentScheme = new BasicDrive(XBOX_PORT);
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
		
		currentScheme.drive(drive, dPneumatics);
	
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	
	}
	
	@Override
	public void disabledPeriodic() {
		dPneumatics.setOff();
	}
	
	private void loadProperties() {
		
		try {
			frontRightMotor = properties.getInt("frontRightMotor");
			frontLeftMotor = properties.getInt("frontLeftMotor");
			backRightMotor = properties.getInt("backRightMotor");
			backLeftMotor = properties.getInt("backLeftMotor");
			frontRightMotor = properties.getInt("middleRightMotor");
			frontLeftMotor = properties.getInt("middleLeftMotor");
			
			drivePneuForward = properties.getInt("drivePneuForward");
			drivePneuReverse = properties.getInt("drivePneuReverse");
			
		} catch (SingularityPropertyNotFoundException e) {
			DriverStation.reportError("The property \"" + e.getPropertyName()
				+ "was not found --> CODE SPLINTERED(CRASHED)!!!!!! \n _POSSIBLE CAUSES:\n - Property missing in file and defaults"
				+ "\n - Typo in property name in code or file/n - using a different properties file than the one that actually contains the property you are looking for",
				false);
			e.printStackTrace();
		}
	}
	
	private void setDefaultProperties() {
		
		properties.addDefaultProp("frontRightMotor", 2);
		properties.addDefaultProp("frontLeftMotor", 3);
		properties.addDefaultProp("backRightMotor", 4);
		properties.addDefaultProp("backLeftMotor", 5);
		properties.addDefaultProp("middleRightMotor", 6);
		properties.addDefaultProp("middleLeftMotor", 7);
		
		properties.addDefaultProp("drivePneuForward", 1);
		properties.addDefaultProp("drivePneuReverse", 2);
	}
}
