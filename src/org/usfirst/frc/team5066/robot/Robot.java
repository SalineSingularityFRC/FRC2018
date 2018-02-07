package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.autonomous2018.*;
import org.usfirst.frc.team5066.controller2018.*;
import org.usfirst.frc.team5066.controller2018.controlSchemes.*;
import org.usfirst.frc.team5066.library.*;
import org.usfirst.frc.team5066.singularityDrive.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Preferences;
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
	
	int frontRightMotor, frontLeftMotor, middleRightMotor, middleLeftMotor, backRightMotor, backLeftMotor;
	int drivePneuForward, drivePneuReverse;
	
	int liftLeft1, liftRight1;
	int leftLimitLow, leftLimitHigh, rightLimitLow, rightLimitHigh;
	
	int talonArmMotor;
	
	int intakeRight, intakeLeft;
	
	final double ARMSPEEDCONSTANT = 1.0;
	int armPneumaticsForward;
	int armPneumaticsReverse;
	
	final double LIFT_SPEED = 1.0;
	
	SingDrive drive;
	DrivePneumatics dPneumatics;
	Lift lift;
	Arm arm;
	UsbCamera front, rear;
	
	Preferences prefs;
	
	Compressor compressor;
	
	SingularityProperties properties;
	
	ControlScheme currentScheme;
	
	SendableChooser side;
	SendableChooser priority;
	
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
	VictorSPX cantalon;
	double speed;
	
	//pneumatics
	DoubleSolenoid solenoidDrive;
	DoubleSolenoid solenoidArm;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		
		compressor = new Compressor();
		
	
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
					
			//drive = new TankDrive(0, 1);
			drive.rampVoltage();
			
			/*lift = new Lift(liftRight1, liftRight2, liftLeft1, liftLeft2, rightLimitLow, 
					rightLimitHigh, leftLimitLow, leftLimitHigh, LIFT_SPEED);
			*/
			dPneumatics = new DrivePneumatics(drivePneuForward, drivePneuReverse);
			
			
			
			//arm = new Arm(talonArmMotor, ARMSPEEDCONSTANT, armPneumaticsForward, armPneumaticsReverse);
			
			currentScheme = new TankDrive(XBOX_PORT, BIG_JOYSTICK_PORT);
			/*
			front = CameraServer.getInstance().startAutomaticCapture();
			rear = CameraServer.getInstance().startAutomaticCapture();
			front.setResolution(320, 480);
			rear.setResolution(320, 480);
			*/
			timer = new Timer();
			
			side = new SendableChooser();
			priority = new SendableChooser();
			priority.addDefault("Default program", new String("Default program"));
			priority.addObject("Our Switch", new String("Our Switch"));
			priority.addObject("Opponent Switch", new String("Opponent Switch"));
			priority.addObject("Our Vault", new String("Our Vault"));
			
			side.addDefault("Left", new String("Left"));
			side.addObject("Middle", new String("Middle"));
			side.addObject("Right", new String("Right"));
			
			SmartDashboard.putData("Side:", side);
			SmartDashboard.putData("Priority:", priority);
		}
	}

	/**
	 * This function is run once each time the robot enters autonomous mode
	 */
	@Override
	public void autonomousInit() {
		//TODO figure out if setReverse = slow gear
		dPneumatics.setReverse();
		
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
			
			if(side.getSelected().equals("Right")){
				a=1;
				SmartDashboard.putString("Starting Position", "Starting Right");
			}
			else if(side.getSelected().equals("Middle")){
				a=2;
				SmartDashboard.putString("Starting Position", "Starting Middle");
			}
			else SmartDashboard.putString("Starting Position", "Starting Left");
			
			if(gameData.charAt(0) == 'R'){
				b=1;
				SmartDashboard.putString("Switch", "Our Switch is on the right");
			}
			else SmartDashboard.putString("Switch", "Our Switch is on the left");
			
			if(priority.getSelected().equals("Our Vault")){
				c=1;
				SmartDashboard.putString("Priorities", "The robot is going toward the vault");
			}
			else if(priority.getSelected().equals("Opponent Switch")){
				if(gameData.charAt(2) == 'R') {
					c=3;
					SmartDashboard.putString("Priorities", "The robot is going toward the opponents Right Switch");
				}
				else {
					c=2;
					SmartDashboard.putString("Priorities", "The robot is going toward the opponents Left Switch");
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
		
		port = 1;
		prevRb = false;
		prevLb = false;
		xbox = new XboxController(XBOX_PORT);

		cantalon = new VictorSPX(port);
		
		//solenoidDrive = new DoubleSolenoid(0, 1);
		//solenoidArm = new DoubleSolenoid(2, 3);
		
		compressor.getPressureSwitchValue();
		

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
		
		/*
		//press right on the d-pad to switch to cantalon
		if (xbox.getPOVRight()) {
			testMode = TestMode.TALON;
		}
		
		//press left on the d-pad to switch to pneumatic
		else if (xbox.getPOVLeft()) {
			testMode = TestMode.PNEUMATIC;
		}*/

		
		 /* Code to test the port numbers of cantalons
		 */
		 
		 
		if (testMode == TestMode.TALON) {

			currentRb = xbox.getRB();
			currentLb = xbox.getLB();

			// use Right Bumper to toggle up a cantalon port
			// use Left Bumper to toggle down a cantalon port
			if (currentRb && !prevRb) {
				cantalon.set(ControlMode.PercentOutput, 0.0);
				port++;
				cantalon = new VictorSPX(port);
			} else if (currentLb && !prevLb) {
				cantalon.set(ControlMode.PercentOutput, 0.0);
				port--;
				cantalon = new VictorSPX(port);
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
			/*
			if (xbox.getPOVUp()) solenoidDrive.set(DoubleSolenoid.Value.kForward);
			else if (xbox.getPOVDown()) solenoidDrive.set(DoubleSolenoid.Value.kReverse);
			else solenoidDrive.set(DoubleSolenoid.Value.kOff);
			
			if (xbox.getPOVRight()) solenoidArm.set(DoubleSolenoid.Value.kForward);
			else if (xbox.getPOVLeft()) solenoidArm.set(DoubleSolenoid.Value.kReverse);
			else solenoidArm.set(DoubleSolenoid.Value.kOff);
*/
			// log information to keep track of port number and speed
			SmartDashboard.putNumber("port: ", (double) port);
			SmartDashboard.putNumber("Speed: ", speed);
		}
		
		
		 /* Code to Test the Pneumatics
		 * This currently tests with SingleSolenoids,
		 * which will probably still work for testing DoubleSolenoids
		 */
		
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
*/
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
		
		}
	}
	
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
			middleRightMotor = properties.getInt("middleRightMotor");
			middleLeftMotor = properties.getInt("middleLeftMotor");
			
			//get 'er done
			
			liftLeft1 = properties.getInt("liftLeft1");
			liftRight1 = properties.getInt("liftRight1");
			
			rightLimitLow = properties.getInt("rightLimitLow");
			rightLimitHigh = properties.getInt("rightLimitHigh");
			leftLimitLow = properties.getInt("leftLimitLow");
			leftLimitHigh= properties.getInt("leftLimitHigh");
			
			drivePneuForward = properties.getInt("drivePneuForward");
			drivePneuReverse = properties.getInt("drivePneuReverse");
			
			talonArmMotor = properties.getInt("talonArmMotor");
			
			intakeRight = properties.getInt("intakeRight");
			intakeLeft = properties.getInt("intakeLeft");
			
			armPneumaticsForward = properties.getInt("armPneumaticsForward");
			armPneumaticsReverse = properties.getInt("armPneumaticsReverse");
			
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
		properties.addDefaultProp("frontLeftMotor", 9);
		properties.addDefaultProp("backRightMotor", 3);
		properties.addDefaultProp("backLeftMotor", 6);
		properties.addDefaultProp("middleRightMotor", 5);
		properties.addDefaultProp("middleLeftMotor", 7);
		
		properties.addDefaultProp("liftLeft1", 8);
		properties.addDefaultProp("liftRight1", 5);
		
		properties.addDefaultProp("rightLimitLow", 10);
		properties.addDefaultProp("rightLimitHigh", 11);
		properties.addDefaultProp("leftLimitLow", 15);
		properties.addDefaultProp("leftLimitHigh", 13);
		
		properties.addDefaultProp("drivePneuForward", 0);
		properties.addDefaultProp("drivePneuReverse", 1);
		
		properties.addDefaultProp("talonArmMotor", 7);
		
		properties.addDefaultProp("armPneumaticsForward", 2);
		properties.addDefaultProp("armPneumaticsReverse", 3);
		
		properties.addDefaultProp("intakeRight", 3);
		properties.addDefaultProp("intakeLeft", 6);
		
	}
}
