package org.usfirst.frc.team5066.robot;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import org.usfirst.frc.team5066.controller2018.*;
import org.usfirst.frc.team5066.controller2018.controlSchemes.*;
import org.usfirst.frc.team5066.library.*;
import org.usfirst.frc.team5066.singularityDrive.*;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;
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
	
	Timer autonTimer;
	double initialAngle;
	
	int leftVictor1, leftVictor2, leftVictor3, leftTalon, rightVictor1, rightVictor2, rightVictor3, rightTalon;
	int drivePneuForward, drivePneuReverse;
	
	SingDrive drive;
	DrivePneumatics dPneumatics;
	
	UsbCamera front, rear;
	
	Preferences prefs;
	
	Compressor compressor;
	
	SingularityProperties properties;
	
	ControlScheme currentScheme;
	
	//used to find the side we start on
	SendableChooser<Double> side;
	
	//Sendable choosers to find the first and second steps
	//depending on the game data
	SendableChooser<String> LFirst, LSecond;
	SendableChooser<String> RFirst, RSecond;
	
	SendableChooser<String> priority;
	private double a, b, c;
	
	Timer timer;
	
	final int XBOX_PORT = 0;
	final int BIG_JOYSTICK_PORT = 1;
	final int SMALL_JOYSTICK_PORT = 2;
	
	
	//testing variables
	public enum TestMode {
		TALON, PNEUMATIC, COMPRESS
	}
	TestMode testMode;
	
	XboxController xbox;
	boolean currentRb, currentLb, prevRb, prevLb;
	int port;
	
	//cantalons
	VictorSPX[] cantalon;
	double speed;
	
	//pneumatics
	DoubleSolenoid solenoidDrive;
	DoubleSolenoid solenoidArm;

	public static AHRS gyro;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		compressor = new Compressor();
		compressor.start();
	
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
			
			gyro = new AHRS(SPI.Port.kMXP);
		
			drive = new SixWheelDrive(leftVictor1, leftVictor2, leftVictor3, leftTalon,
					rightVictor1, rightVictor2, rightVictor3, rightTalon);
					
			//drive = new TankDrive(0, 1);
			drive.rampVoltage();
			
			dPneumatics = new DrivePneumatics(drivePneuForward, drivePneuReverse);
			
			currentScheme = new TankDrive(BIG_JOYSTICK_PORT, XBOX_PORT);
			/*
			new Thread(() -> {
				
				//Next two lines are the normal way to instantiate a camera
				front = CameraServer.getInstance().startAutomaticCapture();
				front.setResolution(320, 240);
				
				rear = CameraServer.getInstance().startAutomaticCapture();
				rear.setResolution(320, 240);
				/*
				CvSink cvSink = CameraServer.getInstance().getVideo();
				CvSource outputStream = CameraServer.getInstance().putVideo("Blur", 640, 480);

				Mat source = new Mat();
				Mat output = new Mat();

				while (!Thread.interrupted()) {
					cvSink.grabFrame(source);
					Imgproc.cvtColor(source, output, Imgproc.COLOR_BGR2GRAY);
					outputStream.putFrame(output);
				}
			}).start();
			*/
			timer = new Timer();
			
			/*
			priority = new SendableChooser<String>();
			priority.addDefault("Default program", new String("Default program"));
			priority.addObject("Our Switch", new String("Our Switch"));
			priority.addObject("Opponent Switch", new String("Opponent Switch"));
			priority.addObject("Vault", new String("Vault"));
			priority.addObject("Scale", new String("Scale"));
			
			side = new SendableChooser<Double>();
			side.addDefault("Left", 0.0);
			side.addObject("Middle", 2.0);
			side.addObject("Right", 1.0);
			
			SmartDashboard.putData("Side:", side);
			SmartDashboard.putData("Priority:", priority);
			
			a = 0.0;
			b = 0.0;
			c = 0.0;
			*/
			
			side = new SendableChooser<Double>();
			side.addObject("Side", -1.0);
			side.addDefault("Left", 0.0);
			side.addObject("Middle", 2.0);
			side.addObject("Right", 1.0);
			side.addObject("Exchange", 3.0);
			SmartDashboard.putData("Side:", side);
			
			
			LFirst = new SendableChooser<String>();
			LFirst.addObject("LFirst", "LFirst");
			LFirst.addDefault("Switch", "Switch");
			LFirst.addObject("Forward", "Forward");
			LFirst.addObject("Exchange", "Exchange");
			SmartDashboard.putData("LFirst:", LFirst);
			
			
			RFirst = new SendableChooser<String>();
			RFirst.addObject("RFirst", "RFirst");
			RFirst.addDefault("Switch", "Switch");
			RFirst.addObject("Forward", "Forward");
			RFirst.addObject("Exchange", "Exchange");
			SmartDashboard.putData("RFirst:", RFirst);
			
		}
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		
		
		//TODO figure out if setReverse = slow gear
		dPneumatics.setReverse();
		drive.setControlMode(false);
		
		//AutonControlScheme driveForward = new DriveForward(drive, gyro, arm, intake);
		//driveForward.moveAuton();
			
		autonTimer = new Timer();
		autonTimer.start();
		initialAngle = gyro.getAngle();
		
		///////////////////////////////////////////////////////////////////////////////
		//NEW AUTON CODE WITH FIRST AND SECOND TIERS///////////////////////////////////
		//this.chooseAutonNew();/////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////
		
		/*
		//(new SideStraight(drive, gyro)).moveAuton();
		this.chooseAuton();	
		autonPrograms[(int) a][(int) b][(int) c].moveAuton();
		*/
		
		
	}
	
	
	
	
	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		
		if (autonTimer.get() < 4.0) {
			((SixWheelDrive)drive).tankDrive(-0.4 + 0.07 * (gyro.getAngle() - initialAngle),
					-0.4, 1.0, SpeedMode.FAST);
		}
		else
			((SixWheelDrive)drive).tankDrive(0.0, 0.0, 1.0, SpeedMode.FAST);
	}
	/**
	 * This function is called once each time the robot enters tele-operated
	 * mode
	 */
	@Override
	public void teleopInit() {
		timer.start();
		drive.setControlMode(true);
		
		
		
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		currentScheme.drive(drive, dPneumatics);
		//currentScheme.lift(lift, timer);
		//currentScheme.arm(arm);
		//currentScheme.intake(intake);
		
	}
	
	/**
	 * This function is called once each time the robot enters test-mode
	 */
	public void testInit() {
		
		testMode = TestMode.TALON;
		
		
		prevRb = false;
		prevLb = false;
		xbox = new XboxController(XBOX_PORT);

		cantalon = new VictorSPX[30];
		for (int i = 0; i < 30; i++) {
			cantalon[i] = new VictorSPX(i);
		}
		
		//solenoidDrive = new DoubleSolenoid(0, 1);
		//solenoidArm = new DoubleSolenoid(2, 3);
		
		compressor.getPressureSwitchValue();
		port = 21;
		
		dPneumatics.setOff();
		

	}
	/*
	@Override
	public void testPeriodic() {		
		if (testMode == TestMode.COMPRESS)
			compressor.start();
} */
	
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		
		compressor.start();

		//Code to test the port numbers of cantalons 
		 
		if (testMode == TestMode.TALON) {

			currentRb = xbox.getRB();
			currentLb = xbox.getLB();

			// use Right Bumper to toggle up a cantalon port
			// use Left Bumper to toggle down a cantalon port
			if (currentRb && !prevRb) {
				cantalon[port].set(ControlMode.PercentOutput, 0.0);
				port++;
				
			} else if (currentLb && !prevLb) {
				cantalon[port].set(ControlMode.PercentOutput, 0.0);
				port--;
				
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

			cantalon[port].set(ControlMode.PercentOutput, speed);
			
			// log information to keep track of port number and speed
			SmartDashboard.putNumber("port: ", (double) port);
			SmartDashboard.putNumber("Speed: ", speed);
		}
		
		
		 /* Code to Test the Pneumatics
		 * This currently tests with SingleSolenoids,
		 * which will probably still work for testing DoubleSolenoids
		 */
		/*
		else if (testMode == TestMode.PNEUMATIC) {
			
			currentRb = xbox.getRB();
			currentLb = xbox.getLB();

			// use Right Bumper to toggle up a cantalon port
			// use Left Bumper to toggle down a cantalon port
			/*if (currentRb && !prevRb) {
				solenoid.set(DoubleSolenoid.Value.kForward);
				//port++;
				//solenoid = new DoubleSolenoid(0, port);
			} else if (currentLb && !prevLb) {
				solenoid.set(DoubleSolenoid.Value.kOff);
				port--;
				solenoid = new DoubleSolenoid(0, port);
			}

			prevRb = currentRb;
			prevLb = currentLb;
			
			if (xbox.getAButton()) solenoidDrive.set(DoubleSolenoid.Value.kForward);
			else if (xbox.getBButton()) solenoidDrive.set(DoubleSolenoid.Value.kReverse);
			else solenoidDrive.set(DoubleSolenoid.Value.kOff);
			
			if (xbox.getYButton()) solenoidArm.set(DoubleSolenoid.Value.kForward);
			else if (xbox.getXButton()) solenoidArm.set(DoubleSolenoid.Value.kReverse);
			else solenoidArm.set(DoubleSolenoid.Value.kOff);
			
			// log information to keep track of port number
			SmartDashboard.putString("DB/String 0", "Current CANTalon: " + port);
			//SmartDashboard.putString("DB/String 1", "solenoid value: " + solenoid.get());
		*/
		//}
	}
	@Override
	public void disabledInit() {
		drive.resetAll();
	}
	
	@Override
	public void disabledPeriodic() {
		//dPneumatics.setOff();
		//System.out.println("right: " + drive.getRightPosition());
		//System.out.println("left: " + drive.getLeftPosition());
		//System.out.println(gyro.getAngle());
		
		//System.out.println("left:" + lift.getLeftUltra());
		//System.out.println("    right:" + lift.getRightUltra());
	}
	
	private void loadProperties() {
		
		try {
			rightTalon = properties.getInt("rightTalon");
			leftTalon = properties.getInt("leftTalon");
			rightVictor1 = properties.getInt("rightVictor1");
			leftVictor1 = properties.getInt("leftVictor1");
			rightVictor2 = properties.getInt("rightVictor2");
			leftVictor2 = properties.getInt("leftVictor2");
			rightVictor3 = properties.getInt("rightVictor3");
			leftVictor3 = properties.getInt("leftVictor3");
			
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
		
		properties.addDefaultProp("rightTalon", 14);
		properties.addDefaultProp("leftTalon",15);
		properties.addDefaultProp("rightVictor1", 5);
		properties.addDefaultProp("leftVictor1", 2);
		properties.addDefaultProp("rightVictor2", 7);
		properties.addDefaultProp("leftVictor2", 4);
		properties.addDefaultProp("rightVictor3", 10);
		properties.addDefaultProp("leftVictor3", 8);
		
		properties.addDefaultProp("drivePneuForward", 0);
		properties.addDefaultProp("drivePneuReverse", 1);
		
	}
}
