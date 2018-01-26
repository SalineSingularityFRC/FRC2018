package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.autonomous2018.*;
import org.usfirst.frc.team5066.controller2018.*;
import org.usfirst.frc.team5066.controller2018.controlSchemes.*;
import org.usfirst.frc.team5066.library.*;
import org.usfirst.frc.team5066.singularityDrive.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import javafx.scene.Camera;


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
	
	int liftLeft1, liftLeft2, liftRight1, liftRight2;
	int leftLimitLow, leftLimitHigh, rightLimitLow, rightLimitHigh;
	
	final double LIFT_SPEED = 1.0;
	
	SingDrive drive;
	DrivePneumatics dPneumatics;
	Compressor compressor;
	Lift lift;
	UsbCamera front, rear;
	
	Preferences prefs;
	
	SingularityProperties properties;
	
	ControlScheme currentScheme;
	
	Command autonomousCommand;
	SendableChooser side;
	SendableChooser priority;
	
	Timer timer;
	
	final int XBOX_PORT = 0;
	final int BIG_JOYSTICK_PORT = 1;
	final int SMALL_JOYSTICK_PORT = 2;
	
	
	//testing variables
	
	public enum TestMode {
		TALON, PNEUMATIC
	}
	TestMode testMode;
	
	XboxController xbox;
	boolean currentRb, currentLb, prevRb, prevLb;
	int port;
	
	//cantalons
	TalonSRX cantalon;
	double speed;
	
	//pneumatics
	Solenoid solenoid;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
	
		//SmartDashboard Preferences code to change port value
		/*
		frontRightMotor = prefs.getInt("frontRightMotor", 13);
		frontLeftMotor = prefs.getInt("frontLeftMotor", 6);
		middleRightMotor = prefs.getInt("middleRightMotor",4);
		middleLeftMotor = prefs.getInt("middleLeftMotor", 2);
		backRightMotor = prefs.getInt("backRightMotor", 10);
		backLeftMotor = prefs.getInt("backLeftMotor", 7);
		
		liftLeft1 = prefs.getInt("liftLeft1", 8);
		liftLeft2 = prefs.getInt("liftLeft2", 6);
		liftRight1 = prefs.getInt("liftRight1", 5);
		liftRight2 = prefs.getInt("liftRight2", 12);
		leftLimitLow = prefs.getInt("leftLimitLow", 0);
		leftLimitHigh = prefs.getInt("leftLimitHigh", 1);
		rightLimitLow = prefs.getInt("rightLimitLow", 2);
		rightLimitHigh = prefs.getInt("righLimitHigh", 3);
		*/
		
		try {
			properties = new SingularityProperties("/home/lvuser/robot.properties");
		} catch (Exception e){
			properties = new SingularityProperties();
			
			setDefaultProperties();
			DriverStation.reportError("error in properties", true);
			
			DriverStation.reportError("error in properties", true);
		} finally {
			
			loadProperties();
		
			drive = new SixWheelDrive(frontLeftMotor, backLeftMotor,
					frontRightMotor, backRightMotor, middleRightMotor, middleLeftMotor);
			drive.rampVoltage();
			
			/*lift = new Lift(liftRight1, liftRight2, liftLeft1, liftLeft2, rightLimitLow, 
					rightLimitHigh, leftLimitLow, leftLimitHigh, LIFT_SPEED);
			
			dPneumatics = new DrivePneumatics(drivePneuForward, drivePneuReverse);
			*/
			
			currentScheme = new BasicDrive(XBOX_PORT, BIG_JOYSTICK_PORT);
			
			front = CameraServer.getInstance().startAutomaticCapture();
			rear = CameraServer.getInstance().startAutomaticCapture();
			front.setResolution(320, 480);
			rear.setResolution(320, 480);
			
			timer = new Timer();
			
			side = new SendableChooser();
			priority = new SendableChooser();
			priority.addDefault("Default program", new SideStraight(drive));
			priority.addObject("Our Switch", new LLSLS(drive));
			priority.addObject("Opponet Switch", new LLSOL(drive));
			priority.addObject("Our Vault", new LLSV(drive));
			
			side.addDefault("Left", new LLS(drive));
			side.addObject("Middle", new MLSLS(drive));
			side.addObject("Right", new RLSLS(drive));
			
			SmartDashboard.putData("Proirities: ", priority);
			SmartDashboard.putData("Starting Side: ", side);
			
		}
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		AutonControlScheme[][][] autonPrograms = 
			{{{new LLSLS(drive), new LLSV(drive), new LLSOL(drive), new LLSOR(drive)},
			{new LRSRS(drive), new LRSV(drive), new LRSOL(drive), new LRSOR(drive)}},
			{{new RRSRS(drive), new RRSV(drive), new RRSOL(drive), new RRSOR(drive)},
			{new RLSLS(drive), new RLSV(drive), new RLSOL(drive), new RLSOR(drive)}},
			{{new MLSLS(drive), new MLSV(drive), new MLSOL(drive), new MLSOR(drive)},
			{new MRSRS(drive), new MRSV(drive), new MRSOL(drive), new MRSOL(drive)}}};

			String gameData;
			int a=0,b=0,c=0;
			gameData = DriverStation.getInstance().getGameSpecificMessage();		
			
			if(side.equals(new RLSLS(drive))){
				a=1;
				SmartDashboard.putString("Starting Position", "Starting Right");
			}
			else if(side.equals(new MLSLS(drive))){
				a=2;
				SmartDashboard.putString("Starting Position", "Starting Middle");
			}
			else SmartDashboard.putString("Starting Position", "Starting Left");
			
			if(gameData.charAt(0) == 'R'){
				b=1;
				SmartDashboard.putString("Switch", "Our Switch is on the right");
			}
			else SmartDashboard.putString("Switch", "Our Switch is on the right");
			
			if(priority.equals(new LLSV(drive))){
				c=1;
				SmartDashboard.putString("Proirities", "The robot is going toward the vault");
			}
			else if(priority.equals(new LLSOL(drive))){
				if(gameData.charAt(2) == 'R') {
					c=3;
					SmartDashboard.putString("Priorities", "The robot is going toward the opponets Right Switch");
				}
				else {
					c=2;
					SmartDashboard.putString("Priorities", "The robot is going toward the opponets Left Switch");
				}
			}
			else SmartDashboard.putString("Priorities", "The robot is going toward the switch again");
			
			autonPrograms[a][b][c].moveAuton();
			
			
				//autonomousCommand = (Command) side.getSelected();
				//autonomousCommand.start();
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
		timer.start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		currentScheme.drive(drive, dPneumatics);
		//currentScheme.lift(lift, timer);
	}
	
	/**
	 * This function is called once each time the robot enters test-mode
	 */
	public void testInit() {
		
		testMode = TestMode.TALON;
		
		port = 0;
		prevRb = false;
		prevLb = false;
		xbox = new XboxController(XBOX_PORT);
		
		cantalon = new TalonSRX(port);
		
		solenoid = new Solenoid(port);
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	/*@Override
	public void testPeriodic() {
		
		//press right on the d-pad to switch to cantalon
		if (xbox.getPOVRight()) {
			testMode = TestMode.TALON;
		}
		
		//press left on the d-pad to switch to pneumatic
		else if (xbox.getPOVLeft()) {
			testMode = TestMode.PNEUMATIC;
		}

		
		 * Code to test the port numbers of cantalons
		 
		if (testMode == TestMode.TALON) {

			currentRb = xbox.getRB();
			currentLb = xbox.getLB();

			// use Right Bumper to toggle up a cantalon port
			// use Left Bumper to toggle down a cantalon port
			if (currentRb && !prevRb) {
				cantalon.set(ControlMode.PercentOutput, 0.0);
				port++;
				cantalon = new TalonSRX(port);
			} else if (currentLb && !prevLb) {
				cantalon.set(ControlMode.PercentOutput, 0.0);
				port--;
				cantalon = new TalonSRX(port);
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

			cantalon.set(ControlMode.PercentOutput, speed);

			// log information to keep track of port number and speed
			SmartDashboard.putString("DB/String 0", "Current CANTalon: " + port);
			SmartDashboard.putString("DB/String 1", "Current speed: " + speed);
		}
		
		
		 * Code to Test the Pneumatics
		 * This currently tests with SingleSolenoids,
		 * which will probably still work for testing DoubleSolenoids
		 
		
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
	}*/
	
	@Override
	public void disabledPeriodic() {
		//dPneumatics.setOff();
	}
	
	private void loadProperties() {
		
		try {
			frontRightMotor = properties.getInt("frontRightMotor");
			frontLeftMotor = properties.getInt("frontLeftMotor");
			backRightMotor = properties.getInt("backRightMotor");
			backLeftMotor = properties.getInt("backLeftMotor");
			frontRightMotor = properties.getInt("middleRightMotor");
			frontLeftMotor = properties.getInt("middleLeftMotor");
			
			liftLeft1 = properties.getInt("liftLeft1");
			liftLeft2 = properties.getInt("liftLeft2");
			liftRight1 = properties.getInt("liftRight1");
			liftRight2 = properties.getInt("liftRight2");
			
			rightLimitLow = properties.getInt("rightLimitLow");
			rightLimitHigh = properties.getInt("rightLimitHigh");
			leftLimitLow = properties.getInt("leftLimitLow");
			leftLimitHigh= properties.getInt("leftLimitHigh");
			
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
		
		properties.addDefaultProp("frontRightMotor", 13);
		properties.addDefaultProp("frontLeftMotor", 6);
		properties.addDefaultProp("backRightMotor", 4);
		properties.addDefaultProp("backLeftMotor", 2);
		properties.addDefaultProp("middleRightMotor", 10);
		properties.addDefaultProp("middleLeftMotor", 7);
		
		properties.addDefaultProp("liftLeft1", 8);
		properties.addDefaultProp("liftLeft2", 9);
		properties.addDefaultProp("liftRight1", 5);
		properties.addDefaultProp("liftRight2", 12);
		
		properties.addDefaultProp("rightLimitLow", 0);
		properties.addDefaultProp("rightLimitHigh", 1);
		properties.addDefaultProp("leftLimitLow", 2);
		properties.addDefaultProp("leftLimitHigh", 3);
		
		properties.addDefaultProp("drivePneuForward", 1);
		properties.addDefaultProp("drivePneuReverse", 2);
	}
}
