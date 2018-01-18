package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.controller2018.*;
import org.usfirst.frc.team5066.controller2018.controlSchemes.*;
import org.usfirst.frc.team5066.library.*;
import org.usfirst.frc.team5066.singularityDrive.*;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import Autonomous2018.LLS;
import Autonomous2018.SideStraight;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


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
	
	Port aHRSGyro;
	
	SingDrive drive;
	DrivePneumatics dPneumatics;
	Compressor compressor;
	
	SingularityProperties properties;
	
	ControlScheme currentScheme;
	
	Command autonomousCommand;
	SendableChooser autoChooser;
	
	final int XBOX_PORT = 0;
	final int BIG_JOYSTICK_PORT = 1;
	final int SMALL_JOYSTICK_PORT = 2;
	
	
	//testing variables
	
	public enum TestMode {
		CANTALON, PNEUMATIC
	}
	TestMode testMode;
	
	XboxController xbox;
	boolean currentRb, currentLb, prevRb, prevLb;
	int port;
	
	//cantalons
	CANTalon cantalon;
	double speed;
	
	//pneumatics
	Solenoid solenoid;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		properties = new SingularityProperties();
		
		try {
			properties = new SingularityProperties("/home/lvuser/robot.properties");
			
			//aHRSGyro = new Port(SerialPort::Port::kUSB);
			
		} catch (Exception e){
			setDefaultProperties();
			
			
			DriverStation.reportError("error in properties", true);
		} finally {
			
			loadProperties();
		
			drive = new SixWheelDrive(frontLeftMotor, backLeftMotor,
					frontRightMotor, backRightMotor, middleRightMotor, middleLeftMotor);
			dPneumatics = new DrivePneumatics(drivePneuForward, drivePneuReverse);
			compressor = new Compressor();
			compressor.start();
			
			currentScheme = new BasicDrive(XBOX_PORT);
			
			
			
			autoChooser = new SendableChooser();
			autoChooser.addDefault("Default program", new SideStraight(drive, aHRSGyro));
			autoChooser.addObject("LLS", new LLS(drive, aHRSGyro));
			
			
			SmartDashboard.putData("Autonomous mode chooser", autoChooser);
		}
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = (Command) autoChooser.getSelected();
		autonomousCommand.start();
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
	 * This function is called once each time the robot enters test-mode
	 */
	public void testInit() {
		
		testMode = TestMode.CANTALON;
		
		port = 0;
		prevRb = false;
		prevLb = false;
		xbox = new XboxController(XBOX_PORT);
		
		cantalon = new CANTalon(port);
		
		solenoid = new Solenoid(port);
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		
		//press right on the d-pad to switch to cantalon
		if (xbox.getPOVRight()) {
			testMode = TestMode.CANTALON;
		}
		
		//press left on the d-pad to switch to pneumatic
		else if (xbox.getPOVLeft()) {
			testMode = TestMode.PNEUMATIC;
		}

		/*
		 * Code to test the port numbers of cantalons
		 */
		if (testMode == TestMode.CANTALON) {

			currentRb = xbox.getRB();
			currentLb = xbox.getLB();

			// use Right Bumper to toggle up a cantalon port
			// use Left Bumper to toggle down a cantalon port
			if (currentRb && !prevRb) {
				cantalon.set(0.0);
				port++;
				cantalon = new CANTalon(port);
			} else if (currentLb && !prevLb) {
				cantalon.set(0.0);
				port--;
				cantalon = new CANTalon(port);
			}

			prevRb = currentRb;
			prevLb = currentLb;

			if (xbox.getAButton())
				speed = 0.5;
			else if (xbox.getBButton())
				speed = 1.0;
			else if (xbox.getYButton())
				speed = -0.5;
			else if (xbox.getXButton())
				speed = -1.0;
			// if no buttons pressed, get speed from the left stick y axis
			else if (xbox.getLS_Y() > .09)
				speed = xbox.getLS_Y();
			else
				speed = 0.0;

			cantalon.set(speed);

			// log information to keep track of port number and speed
			SmartDashboard.putString("DB/String 0", "Current CANTalon: " + port);
			SmartDashboard.putString("DB/String 1", "Current speed: " + speed);
		}
		
		/*
		 * Code to Test the Pneumatics
		 * This currently tests with SingleSolenoids,
		 * which will probably still work for testing DoubleSolenoids
		 */
		
		else if (testMode == TestMode.PNEUMATIC) {
			
			currentRb = xbox.getRB();
			currentLb = xbox.getLB();

			// use Right Bumper to toggle up a cantalon port
			// use Left Bumper to toggle down a cantalon port
			if (currentRb && !prevRb) {
				solenoid.set(false);
				port++;
				solenoid = new Solenoid(port);
			} else if (currentLb && !prevLb) {
				solenoid.set(false);
				port--;
				solenoid = new Solenoid(port);
			}

			prevRb = currentRb;
			prevLb = currentLb;
			
			if (xbox.getAButton()) solenoid.set(true);
			else solenoid.set(false);
			
			// log information to keep track of port number
			SmartDashboard.putString("DB/String 0", "Current CANTalon: " + port);
			SmartDashboard.putString("DB/String 1", "solenoid value: " + solenoid.get());
			
		}
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
