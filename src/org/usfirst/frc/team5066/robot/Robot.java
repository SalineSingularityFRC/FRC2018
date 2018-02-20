package org.usfirst.frc.team5066.robot;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team5066.autonomous2018.*;
import org.usfirst.frc.team5066.controller2018.*;
import org.usfirst.frc.team5066.controller2018.controlSchemes.*;
import org.usfirst.frc.team5066.library.*;
import org.usfirst.frc.team5066.singularityDrive.*;
import org.usfirst.frc.team5066.autonomousFirstTier.*;
import org.usfirst.frc.team5066.autonomousSecondTier.*;

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
	
	int frontRightMotor, frontLeftMotor, middleRightMotor, middleLeftMotor, backRightMotor, backLeftMotor;
	int drivePneuForward, drivePneuReverse;
	
	int liftLeft1, liftRight1;
	int ultraRightInput, ultraRightOutput, ultraLeftInput, ultraLeftOutput;
	
	int talonArmMotor;
	
	int intakeRight, intakeLeft;
	int intakeSensorPort;
	
	final double ARMSPEEDCONSTANT = 1.0;
	int armPneumaticsForward;
	int armPneumaticsReverse;
	
	final double LIFT_SPEED = 0.5;
	
	SingDrive drive;
	DrivePneumatics dPneumatics;
	Lift lift;
	Arm arm;
	Intake intake;
	UsbCamera front, rear;
	
	Preferences prefs;
	
	Compressor compressor;
	
	SingularityProperties properties;
	
	ControlScheme currentScheme;
	
	//used to find the side we start on
	SendableChooser<Double> side;
	
	//Sendable choosers to find the first and second steps
	//depending on the game data
	SendableChooser<String> LLFirst, LLSecond;
	SendableChooser<String> LRFirst, LRSecond;
	SendableChooser<String> RLFirst, RLSecond;
	SendableChooser<String> RRFirst, RRSecond;
	
	
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
		
			drive = new SixWheelDrive(frontLeftMotor, backLeftMotor,
					frontRightMotor, backRightMotor, middleRightMotor, middleLeftMotor);
					
			//drive = new TankDrive(0, 1);
			drive.rampVoltage();
			
			dPneumatics = new DrivePneumatics(drivePneuForward, drivePneuReverse);
			
			lift = new Lift(liftRight1, liftLeft1, ultraRightInput, ultraRightOutput,
					ultraLeftInput, ultraLeftOutput, LIFT_SPEED);
			
			arm = new Arm(talonArmMotor, ARMSPEEDCONSTANT, armPneumaticsForward, armPneumaticsReverse);
			intake = new Intake(intakeLeft, intakeRight, intakeSensorPort);
			
			currentScheme = new TankDrive(XBOX_PORT, BIG_JOYSTICK_PORT);
			/*
			new Thread(() -> {
				*/
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
			SmartDashboard.putData("Side:", side);
			
			
			LLFirst = new SendableChooser<String>();
			LLFirst.addObject("LLFirst", "LLFirst");
			LLFirst.addDefault("Switch", "Switch");
			LLFirst.addObject("Scale", "Scale");
			LLFirst.addObject("Forward", "Forward");
			SmartDashboard.putData("LLFirst:", LLFirst);
			
			LLSecond = new SendableChooser<String>();
			LLSecond.addObject("LLSecond", "LLSecond");
			LLSecond.addDefault("Nothing", "Nothing");
			LLSecond.addObject("Switch", "Switch");
			LLSecond.addObject("Scale", "Scale");
			LLSecond.addObject("Vault", "Vault");
			LLSecond.addObject("Opponent", "Opponent");
			SmartDashboard.putData("LLSecond:", LLSecond);
			
			
			LRFirst = new SendableChooser<String>();
			LRFirst.addObject("LRFirst", "LRFirst");
			LRFirst.addDefault("Switch", "Switch");
			LRFirst.addObject("Scale", "Scale");
			LRFirst.addObject("Forward", "Forward");
			SmartDashboard.putData("LRFirst:", LRFirst);
			
			LRSecond = new SendableChooser<String>();
			LRSecond.addObject("LRSecond", "LRSecond");
			LRSecond.addDefault("Nothing", "Nothing");
			LRSecond.addObject("Switch", "Switch");
			LRSecond.addObject("Scale", "Scale");
			LRSecond.addObject("Vault", "Vault");
			LRSecond.addObject("Opponent", "Opponent");
			SmartDashboard.putData("LRSecond:", LRSecond);
			
			
			RLFirst = new SendableChooser<String>();
			RLFirst.addObject("RLFirst", "RLFirst");
			RLFirst.addDefault("Switch", "Switch");
			RLFirst.addObject("Scale", "Scale");
			RLFirst.addObject("Forward", "Forward");
			SmartDashboard.putData("RLFirst:", RLFirst);
			
			RLSecond = new SendableChooser<String>();
			RLSecond.addObject("RLSecond", "RLSecond");
			RLSecond.addDefault("Nothing", "Nothing");
			RLSecond.addObject("Switch", "Switch");
			RLSecond.addObject("Scale", "Scale");
			RLSecond.addObject("Vault", "Vault");
			RLSecond.addObject("Opponent", "Opponent");
			SmartDashboard.putData("RLSecond:", RLSecond);
			
			
			RRFirst = new SendableChooser<String>();
			RRFirst.addObject("RRFirst", "RRFirst");
			RRFirst.addDefault("Switch", "Switch");
			RRFirst.addObject("Scale", "Scale");
			RRFirst.addObject("Forward", "Forward");
			SmartDashboard.putData("RRFirst:", RRFirst);
			
			RRSecond = new SendableChooser<String>();
			RRSecond.addObject("RRSecond", "RRSecond");
			RRSecond.addDefault("Nothing", "Nothing");
			RRSecond.addObject("Switch", "Switch");
			RRSecond.addObject("Scale", "Scale");
			RRSecond.addObject("Vault", "Vault");
			RRSecond.addObject("Opponent", "Opponent");
			SmartDashboard.putData("RRSecond:", RRSecond);
			
			
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
		
		///////////////////////////////////////////////////////////////////////////////
		//NEW AUTON CODE WITH FIRST AND SECOND TIERS///////////////////////////////////
		this.chooseAutonNew();/////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////
		
		//OLD AUTON LOGIC::::::
		/*
		AutonControlScheme[][][] autonPrograms = 
			
			{{{new LLSLS(drive, gyro, arm, intake), new LLSV(drive, gyro, arm, intake),
				new LLSOL(drive, gyro, arm, intake), new LLSOR(drive, gyro, arm, intake)},
			{new LRSRS(drive, gyro, arm, intake), new LRSV(drive, gyro, arm, intake), 
				new LRSOL(drive, gyro, arm, intake), new LRSOR(drive, gyro, arm, intake)},
			{new LLT(drive, gyro, arm, intake), new LRT(drive, gyro, arm, intake)}},
					
			{{new RLSLS(drive, gyro, arm, intake), new RLSV(drive, gyro, arm, intake), 
				new RLSOL(drive, gyro, arm, intake), new RLSOR(drive, gyro, arm, intake)},
			{new RRSRS(drive, gyro, arm, intake), new RRSV(drive, gyro, arm, intake), 
				new RRSOL(drive, gyro, arm, intake), new RRSOR(drive, gyro, arm, intake)},
			{new RRT(drive, gyro, arm, intake), new RLT(drive, gyro, arm, intake)}},
			
			{{new MLSLS(drive, gyro, arm, intake), new MLSV(drive, gyro, arm, intake),
				new MLSOL(drive, gyro, arm, intake), new MLSOR(drive, gyro, arm, intake)},
			{new MRSRS(drive, gyro, arm, intake), new MRSV(drive, gyro, arm, intake),
				new MRSOL(drive, gyro, arm, intake), new MRSOL(drive, gyro, arm, intake)}}};
		
		
		//(new SideStraight(drive, gyro)).moveAuton();
		this.chooseAuton();	
		autonPrograms[(int) a][(int) b][(int) c].moveAuton();
		*/
		
		
	}
	
	
	public void chooseAutonNew() {
		
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		SmartDashboard.putString("Game Data: ", gameData);
		
		AutonControlScheme autonFirstTier;
		AutonControlScheme autonSecondTier;
		
		double startingPosition = side.getSelected();
		String firstOption, secondOption;
		
		//if the switch and scale are on the left
		if (gameData.startsWith("LL")) {
			
			firstOption = LLFirst.getSelected();
			secondOption = LLSecond.getSelected();
		}
		else if (gameData.startsWith("LR")) {
			
			firstOption = LRFirst.getSelected();
			secondOption = LRSecond.getSelected();
		}
		else if (gameData.startsWith("RL")) {
			
			firstOption = RLFirst.getSelected();
			secondOption = RLSecond.getSelected();
		}
		else {
			
			firstOption = RRFirst.getSelected();
			secondOption = RRSecond.getSelected();
		}
		
		

		// if we're on the left
		if (startingPosition == 0.0) {
			
			//if we want to go to the scale
			if (firstOption.equals("Scale")) {
				
				//if the scale is on the left
				if (gameData.charAt(1) == 'L') {
					autonFirstTier = new LeftLeftScale(drive, gyro, arm, intake);

					if (secondOption.equals("Scale"))
						autonSecondTier = new LeftScaleLeftScale(drive, gyro, arm, intake);

					else if (secondOption.equals("Opponent")) {
						if (gameData.charAt(2) == 'L')
							autonSecondTier = new LeftScaleOpponentLeft(drive, gyro, arm, intake);
						else
							autonSecondTier = new LeftScaleOpponetRight(drive, gyro, arm, intake);
					}

					else
						autonSecondTier = null;
				}
				
				//if the scale is on the right
				else {
					autonFirstTier = new LeftRightScale(drive, gyro, arm, intake);

					if (secondOption.equals("Scale"))
						autonSecondTier = new RightScaleRightScale(drive, gyro, arm, intake);

					else if (secondOption.equals("Opponent")) {
						if (gameData.charAt(2) == 'L')
							autonSecondTier = new RightScaleOpponentLeft(drive, gyro, arm, intake);
						else
							autonSecondTier = new RightScaleOpponentRight(drive, gyro, arm, intake);
					}

					else
						autonSecondTier = null;
				}
			}

			else if (firstOption.equals("Forward")) {
				autonFirstTier = new DriveForward(drive, gyro, arm, intake);
				autonSecondTier = null;
			}

			// The first option must be Switch
			else {
				
				//if the switch is on the left
				if (gameData.startsWith("L")) {
					if (secondOption.equals("Switch")) {
						autonFirstTier = new LeftLeftLightningBolt(drive, gyro, arm, intake);
						autonSecondTier = new LeftSwitchFrontLeftSwitch(drive, gyro, arm, intake);
					}
					else if (secondOption.equals("Scale")) {
						autonFirstTier = new LeftLeftDogLeg(drive, gyro, arm, intake);
						if (gameData.charAt(1) == 'L')
							autonSecondTier = new LeftSwitchSideLeftScale(drive, gyro, arm, intake);
						else
							autonSecondTier = new LeftSwitchSideRightScale(drive, gyro, arm, intake);
					}
					else if (secondOption.equals("Vault")) {
						autonFirstTier = new LeftLeftLightningBolt(drive, gyro, arm, intake);
						autonSecondTier = new LeftSwitchFrontVault(drive, gyro, arm, intake);
					}
					else if (secondOption.equals("Opponent")) {
						autonFirstTier = new LeftLeftDogLeg(drive, gyro, arm, intake);
						if (gameData.charAt(2) == 'L')
							autonSecondTier = new LeftSwitchsideOpponentLeft(drive, gyro, arm, intake);
						else
							autonSecondTier = new LeftSwitchSideOpponentRight(drive, gyro, arm, intake);
					} 
					else {
						autonFirstTier = new LeftLeftDogLeg(drive, gyro, arm, intake);
						autonSecondTier = null;
					}
				}
				
				//the switch must be on the right
				else {
					autonFirstTier = new LeftRightHook(drive, gyro, arm, intake);
					autonSecondTier = null;
				}
			}//switch
			
		}//left starting position
		
		//if we're on the right
		else if (startingPosition == 1.0) {
			
			//if we want to go to the scale
			if (firstOption.equals("Scale")) {
				
				//if the scale is on the left
				if (gameData.charAt(1) == 'L') {
					autonFirstTier = new RightLeftScale(drive, gyro, arm, intake);

					if (secondOption.equals("Scale"))
						autonSecondTier = new LeftScaleLeftScale(drive, gyro, arm, intake);

					else if (secondOption.equals("Opponent")) {
						if (gameData.charAt(2) == 'L')
							autonSecondTier = new LeftScaleOpponentLeft(drive, gyro, arm, intake);
						else
							autonSecondTier = new LeftScaleOpponetRight(drive, gyro, arm, intake);
					}

					else
						autonSecondTier = null;
				}
				
				//if the scale is on the right
				else {
					autonFirstTier = new RightRightScale(drive, gyro, arm, intake);

					if (secondOption.equals("Scale"))
						autonSecondTier = new RightScaleRightScale(drive, gyro, arm, intake);

					else if (secondOption.equals("Opponent")) {
						if (gameData.charAt(2) == 'L')
							autonSecondTier = new RightScaleOpponentLeft(drive, gyro, arm, intake);
						else
							autonSecondTier = new RightScaleOpponentRight(drive, gyro, arm, intake);
					}

					else
						autonSecondTier = null;
				}
			}

			else if (firstOption.equals("Forward")) {
				autonFirstTier = new DriveForward(drive, gyro, arm, intake);
				autonSecondTier = null;
			}

			// The first option must be Switch
			else {
				
				//if the switch is on the left
				if (gameData.startsWith("R")) {
					if (secondOption.equals("Switch")) {
						autonFirstTier = new RightRightLightningBolt(drive, gyro, arm, intake);
						autonSecondTier = new RightSwitchFrontRightSwitch(drive, gyro, arm, intake);
					}
					else if (secondOption.equals("Scale")) {
						autonFirstTier = new RightRightDogLeg(drive, gyro, arm, intake);
						if (gameData.charAt(1) == 'L')
							autonSecondTier = new RightSwitchSideLeftScale(drive, gyro, arm, intake);
						else
							autonSecondTier = new RightSwitchSideRightScale(drive, gyro, arm, intake);
					}
					else if (secondOption.equals("Vault")) {
						autonFirstTier = new RightRightLightningBolt(drive, gyro, arm, intake);
						autonSecondTier = new RightSwitchFrontVault(drive, gyro, arm, intake);
					}
					else if (secondOption.equals("Opponent")) {
						autonFirstTier = new RightRightDogLeg(drive, gyro, arm, intake);
						if (gameData.charAt(2) == 'L')
							autonSecondTier = new RightSwitchSideOpponentLeft(drive, gyro, arm, intake);
						else
							autonSecondTier = new RightSwitchSideOpponentRight(drive, gyro, arm, intake);
					} 
					else {
						autonFirstTier = new RightRightDogLeg(drive, gyro, arm, intake);
						autonSecondTier = null;
					}
				}
				
				//the switch must be on the left
				else {
					autonFirstTier = new RightLeftHook(drive, gyro, arm, intake);
					autonSecondTier = null;
				}
			}//switch
			
		}//right starting position
		
		//if we're in the middle
		else {
			
			if (firstOption.equals("Switch")) {
				
				//if the switch is on the left
				if (gameData.charAt(0) == 'L') {
					autonFirstTier = new MiddleLeftLightningBolt(drive, gyro, arm, intake);
					
					if (secondOption.equals("Switch"))
						autonSecondTier = new LeftSwitchFrontLeftSwitch(drive, gyro, arm, intake);
					else if (secondOption.equals("Vault"))
						autonSecondTier = new LeftSwitchFrontVault(drive, gyro, arm, intake);
					else
						autonSecondTier = null;
				}
				//if the switch is on the right
				else {
					autonFirstTier = new MiddleRightSwitchLightningBolt(drive, gyro, arm, intake);
					
					if (secondOption.equals("Switch"))
						autonSecondTier = new RightSwitchFrontRightSwitch(drive, gyro, arm, intake);
					else if (secondOption.equals("Vault"))
						autonSecondTier = new RightSwitchFrontVault(drive, gyro, arm, intake);
					else
						autonSecondTier = null;
				}
			
			}//first option is switch
			
			//probably won't be used, but if we want to drive straight ahead
			else {
				
				autonFirstTier = new DriveForward(drive, gyro, arm, intake);
				autonSecondTier = null;
				
			}
		}//in the middle
		
		autonFirstTier.moveAuton();
		if (autonSecondTier != null)
			autonSecondTier.moveAuton();
		
	}
	
	
	private void chooseAuton() {
		
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();		
		
		this.a = side.getSelected();
		
		/*
		if(side.getSelected().equals("Right")){
			a=1;
			SmartDashboard.putString("Starting Position", "Starting Right");
		}
		else if(side.getSelected().equals("Middle")){
			a=2;
			SmartDashboard.putString("Starting Position", "Starting Middle");
		}
		else SmartDashboard.putString("Starting Position", "Starting Left");*/
		
		
		
		if (priority.getSelected().equals("Our Vault")){
			c = 1.0;
			SmartDashboard.putString("Priorities", "The robot is going toward the vault");
		}
		
		else if(priority.getSelected().equals("Opponent Switch")){
			if(gameData.charAt(2) == 'R') {
				c = 3.0;
				SmartDashboard.putString("Priorities", "The robot is going toward the opponents Right Switch");
			}
			else {
				c = 2.0;
				SmartDashboard.putString("Priorities", "The robot is going toward the opponents Left Switch");
			}
		}
		
		else if (priority.getSelected().equals("Scale") && a != 2.0) {
			b = 2.0;
			if (gameData.charAt(1) == 'L') {
				c = 0.0;
			}
			else {
				c = 1.0;
			}
			return;
		}
		
		else SmartDashboard.putString("Priorities", "The robot is going toward the switch again");
		
		
		if(gameData.charAt(0) == 'R'){
			b = 1.0;
			SmartDashboard.putString("Switch", "Our Switch is on the right");
		}
		else SmartDashboard.putString("Switch", "Our Switch is on the left");
		
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
		drive.setControlMode(true);
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		currentScheme.drive(drive, dPneumatics);
		currentScheme.lift(lift, timer);
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

		cantalon = new VictorSPX[15];
		for (int i = 0; i < 15; i++) {
			cantalon[i] = new VictorSPX(i);
		}
		
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
	public void disabledInit() {
		drive.resetAll();
	}
	
	@Override
	public void disabledPeriodic() {
		//dPneumatics.setOff();
		//System.out.println("right: " + drive.getRightPosition());
		System.out.println("left: " + drive.getLeftPosition());
		//System.out.println(gyro.getAngle());
		
		//System.out.println("left:" + lift.getLeftUltra());
		//System.out.println("    right:" + lift.getRightUltra());
	}
	
	private void loadProperties() {
		
		try {
			frontRightMotor = properties.getInt("frontRightMotor");
			frontLeftMotor = properties.getInt("frontLeftMotor");
			backRightMotor = properties.getInt("backRightMotor");
			backLeftMotor = properties.getInt("backLeftMotor");
			middleRightMotor = properties.getInt("middleRightMotor");
			middleLeftMotor = properties.getInt("middleLeftMotor");
			
			drivePneuForward = properties.getInt("drivePneuForward");
			drivePneuReverse = properties.getInt("drivePneuReverse");
			
			liftLeft1 = properties.getInt("liftLeft1");
			liftRight1 = properties.getInt("liftRight1");
			
			ultraRightInput = properties.getInt("ultraRightInput");
			ultraRightOutput = properties.getInt("ultraRightOutput");
			ultraLeftInput = properties.getInt("ultraLeftInput");
			ultraLeftOutput = properties.getInt("ultraLeftOutput");
			
			talonArmMotor = properties.getInt("talonArmMotor");
			armPneumaticsForward = properties.getInt("armPneumaticsForward");
			armPneumaticsReverse = properties.getInt("armPneumaticsReverse");
			
			intakeRight = properties.getInt("intakeRight");
			intakeLeft = properties.getInt("intakeLeft");
			intakeSensorPort = properties.getInt("intakeSensorPort");
			
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
		
		properties.addDefaultProp("drivePneuForward", 0);
		properties.addDefaultProp("drivePneuReverse", 1);
		
		properties.addDefaultProp("liftLeft1", 10);
		properties.addDefaultProp("liftRight1", 2);
		
		properties.addDefaultProp("ultraRightInput", 1);
		properties.addDefaultProp("ultraRightOutput", 2);
		properties.addDefaultProp("ultraLeftInput", 8);
		properties.addDefaultProp("ultraLeftOutput", 9);
		
		properties.addDefaultProp("talonArmMotor", 7);
		properties.addDefaultProp("armPneumaticsForward", 2);
		properties.addDefaultProp("armPneumaticsReverse", 3);
		
		properties.addDefaultProp("intakeRight", 4);
		properties.addDefaultProp("intakeLeft", 8);
		properties.addDefaultProp("intakeSensorPort", 5);
		
	}
}
