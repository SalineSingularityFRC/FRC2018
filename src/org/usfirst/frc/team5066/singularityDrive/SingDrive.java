package org.usfirst.frc.team5066.singularityDrive;

import org.usfirst.frc.team5066.library.SpeedMode;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
//import com.kauailabs.navx.frc.AHRS;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class SingDrive {
	public double slowSpeedConstant, normalSpeedConstant, fastSpeedConstant;
	public int mode = 0;
	
	protected VictorSPX m_leftVictor1, m_leftVictor2, m_leftVictor3, m_rightVictor1, m_rightVictor2, m_rightVictor3;
	 
	protected TalonSRX m_leftTalon, m_rightTalon;
	
	protected VictorSPX m_frontRightMotor, m_frontLeftMotor, m_rightMiddleMotor, m_leftMiddleMotor, m_rearRightMotor, m_rearLeftMotor;
	

	private final static double DEFAULT_VELOCITY_MULTIPLIER = 1.0;
	protected double velocityMultiplier = 1.0;

	protected boolean velocityReduceActivated = false;
	protected double reducedVelocity;

	private final static double DEFAULT_MINIMUM_THRESHOLD = 0.09;
	
	public static boolean isreverse = false;
	private static boolean reverseB = false;

	// Talon type enum
	public static final int CANTALON_DRIVE = 0;
	public static final int TALON_SR_DRIVE = 1;

	private static final int DEFAULT_TALON_TYPE = CANTALON_DRIVE; //Set CAN-iness of TALONs here
	private final static double DEFAULT_SLOW_SPEED_CONSTANT = 0.3;
	private final static double DEFAULT_NORMAL_SPEED_CONSTANT = 0.6;
	private final static double DEFAULT_FAST_SPEED_CONSTANT = 1.0;
	
	private final static double RAMP_RATE = 0.5;

	private int talonType;
	
	protected static double driveStraight;
	
	private static double prevStrafe;
	private static final double strafeTime = 0.05;
	Timer timer;
	
	//AutonDriveStraightVariables
	private static double error = 0.0;
	private static final double KP = 5;
	private static double leftEncVal, rightEncVal;
	
	SendableChooser<Double> speedChooser;
	
	
	//AHRS gyro;


	/**
	 * Constructor for {@link org.usfirst.frc.team5066.library.SingularityDrive
	 * SingularityDrive}. Takes in integers to use for motor ports.
	 * 
	 * @param frontLeftMotor
	 *            Channel for front left motor
	 * @param rearLeftMotor
	 *            Channel for rear left motor
	 * @param frontRightMotor
	 *            Channel for front right motor
	 * @param rearRightMotor
	 *            Channel for rear right motor
	 */
	public SingDrive(int leftVictor1, int leftVictor2, int leftVictor3, int leftTalon,
			int rightVictor1, int rightVictor2, int rightVictor3, int rightTalon) {
		this(leftVictor1, leftVictor2, leftVictor3, leftTalon, rightVictor1, rightVictor2,
				rightVictor3, rightTalon, DEFAULT_TALON_TYPE,
				DEFAULT_SLOW_SPEED_CONSTANT, DEFAULT_NORMAL_SPEED_CONSTANT, DEFAULT_FAST_SPEED_CONSTANT);
	}
	
	public SingDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor) {
		this(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor,  DEFAULT_TALON_TYPE,
				DEFAULT_SLOW_SPEED_CONSTANT, DEFAULT_NORMAL_SPEED_CONSTANT, DEFAULT_FAST_SPEED_CONSTANT);
	}
	
	

	/**
	 * Constructor for {@link org.usfirst.frc.team5066.library.SingularityDrive
	 * SingularityDrive}. Takes in {@link edu.wpi.first.wpilibj.SpeedController
	 * SpeedControllers} as arguments.
	 * 
	 * @param frontLeftMotor
	 *            SpeedController for front left motor
	 * @param rearLeftMotor
	 *            SpeedController for rear left motor
	 * @param frontRightMotor
	 *            SpeedController for front right motor
	 * @param rearRightMotor
	 *            SpeedController for rear right motor
	 */

	/*
	 * public SingularityDrive(SpeedController frontLeftMotor, SpeedController
	 * rearLeftMotor, SpeedController frontRightMotor, SpeedController
	 * rearRightMotor, double velocityMultiplier) { m_leftTalon =
	 * frontLeftMotor; m_leftVictor1 = rearLeftMotor; m_rightTalon =
	 * frontRightMotor; m_rightVictor1 = rearRightMotor;
	 * this.velocityMultiplier = velocityMultiplier; }
	 * 
	 * 
	 * public SingularityDrive(SpeedController frontLeftMotor, SpeedController
	 * rearLeftMotor, SpeedController frontRightMotor, SpeedController
	 * rearRightMotor) { this(frontLeftMotor, rearLeftMotor, frontRightMotor,
	 * rearRightMotor, DEFAULT_VELOCITY_MULTIPLIER); }
	 */
	
	/*Possible methods to use:
	 * configEncoderCodesPerRev()
	 * getP / setP
	 * getI / setI
	 * getD / setD
	 * pidGet
	 */
	
	public SingDrive(int leftVictor1, int leftVictor2, int leftVictor3, int leftTalon,
			int rightVictor1, int rightVictor2, int rightVictor3, int rightTalon,
			int talonType, double slowSpeedConstant, double normalSpeedConstant, double fastSpeedConstant) {//Six wheel constructor

		if (talonType == CANTALON_DRIVE) {
			
			m_leftTalon = new TalonSRX(leftTalon);
			m_leftVictor1 = new VictorSPX(leftVictor1);
			//m_leftTalon.set(ControlMode.Follower, frontLeftMotor);
			m_leftVictor2 = new VictorSPX(leftVictor2);
			//m_leftVictor2.set(ControlMode.Follower, frontLeftMotor);
			m_leftVictor3 = new VictorSPX(leftVictor3);
			
			m_rightTalon = new TalonSRX(rightTalon);
			m_rightVictor1 = new VictorSPX(rightVictor1);
			//m_rightTalon.set(ControlMode.Follower, frontRightMotor);
			m_rightVictor2 = new VictorSPX(rightVictor2);
			//m_rightVictor2.set(ControlMode.Follower, frontRightMotor);
			m_rightVictor3 = new VictorSPX(rightVictor3);

		} else {
			SmartDashboard.putNumber("INVALID VALUE FOR TALON TYPE.b\tvalue=", talonType);
		}

		this.velocityMultiplier = DEFAULT_FAST_SPEED_CONSTANT;
		this.talonType = talonType;
		this.slowSpeedConstant = slowSpeedConstant;
		this.normalSpeedConstant = normalSpeedConstant;
		this.fastSpeedConstant = fastSpeedConstant;
		this.driveStraight = driveStraight;
		timer = new Timer();
		//this.gyro = gyro;
		
		this.resetAll();
		
		speedChooser = new SendableChooser<Double>();
		speedChooser.addDefault("1.0", 1.0);
		speedChooser.addObject("0.8", 0.8);
		speedChooser.addObject("0.6", 0.6);
		speedChooser.addObject("0.4", 0.4);
		speedChooser.addObject("0.2", 0.2);
		
		SmartDashboard.putData("Speed Chooser:", speedChooser);
	}
	
	public SingDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor,
			int talonType, double slowSpeedConstant, double normalSpeedConstant, double fastSpeedConstant) { //Four wheel constructor

		if (talonType == CANTALON_DRIVE) {
			m_leftTalon = new TalonSRX(frontLeftMotor);
			m_leftVictor1 = new VictorSPX(rearLeftMotor);
			m_rightTalon = new TalonSRX(frontRightMotor);
			m_rightVictor1 = new VictorSPX(rearRightMotor);

		} else {
			SmartDashboard.putNumber("INVALID VALUE FOR TALON TYPE.b\tvalue=", talonType);
		}

		this.velocityMultiplier = fastSpeedConstant;
		this.talonType = talonType;
		this.slowSpeedConstant = slowSpeedConstant;
		this.normalSpeedConstant = normalSpeedConstant;
		this.fastSpeedConstant = fastSpeedConstant;
		this.driveStraight = driveStraight;
		timer = new Timer();
		
		//this.gyro = gyro;
	}
	
	
	
	
	//Encoder code:
	public void resetAll(){
		
		m_leftTalon.getSensorCollection().setPulseWidthPosition(0, 0);
		//m_leftVictor2.getSensorCollection().setQuadraturePosition(0, 10);
		m_rightTalon.getSensorCollection().setPulseWidthPosition(0, 0);
	}
	
	public double getLeftPosition(){
		return m_leftTalon.getSensorCollection().getPulseWidthPosition();
	}
	
	public double getRightPosition(){
		return m_rightTalon.getSensorCollection().getPulseWidthPosition();
	}
	
	public void resetEncoders() {
		m_leftTalon.getSensorCollection().setPulseWidthPosition(0, 10);
		m_leftTalon.getSensorCollection().setPulseWidthPosition(0, 10);
	}
	
	public double getMiddlePosition(){
		return m_leftVictor2.getSensorCollection().getQuadraturePosition();
	}
	
	public double leftOverRight() {
		return getLeftPosition() / getRightPosition();
	}
	
	public void setControlMode(boolean coast) {
		if(coast) {
			m_rightTalon.setNeutralMode(NeutralMode.Coast);
			m_rightVictor1.setNeutralMode(NeutralMode.Coast);
			m_rightVictor2.setNeutralMode(NeutralMode.Coast);
			m_rightVictor3.setNeutralMode(NeutralMode.Coast);
			m_leftTalon.setNeutralMode(NeutralMode.Coast);
			m_leftVictor1.setNeutralMode(NeutralMode.Coast);
			m_leftVictor2.setNeutralMode(NeutralMode.Coast);
			m_leftVictor3.setNeutralMode(NeutralMode.Coast);
		} else {
			m_rightTalon.setNeutralMode(NeutralMode.Brake);
			m_rightVictor1.setNeutralMode(NeutralMode.Brake);
			m_rightVictor2.setNeutralMode(NeutralMode.Brake);
			m_rightVictor3.setNeutralMode(NeutralMode.Brake);
			m_leftTalon.setNeutralMode(NeutralMode.Brake);
			m_leftVictor1.setNeutralMode(NeutralMode.Brake);
			m_leftVictor2.setNeutralMode(NeutralMode.Brake);
			m_leftVictor3.setNeutralMode(NeutralMode.Brake);
		}
	}
	
	
	private double clamp(double velocityMultiplier) {
		if (velocityMultiplier > 1.0) {
			return 1.0;
		} else if (velocityMultiplier < -1.0) {
			return -1.0;
		} else {
			return velocityMultiplier;
		}
	}

	public void setVelocityMultiplier(double velocityMultiplier) {
		this.velocityMultiplier = this.clamp(velocityMultiplier);
	}

	public double getVelocityMultiplier() {
		return this.velocityMultiplier;
	}

	//public void reduceVelocity(boolean reduceVelocityButton) {
	//	this.velocityReduceActivated = reduceVelocityButton;
	//}

	public void setReducedVelocity(double reducedVelocity) {
		this.reducedVelocity = reducedVelocity;
	}

	public double threshold(double velocity) {
		if (Math.abs(velocity) <= DEFAULT_MINIMUM_THRESHOLD) {
			return 0;
		}
		return velocity;
	}
	
	
	
	public void rampVoltage() {
		
		m_leftTalon.configOpenloopRamp(RAMP_RATE, 10);
		m_leftVictor1.configOpenloopRamp(RAMP_RATE, 10);
		m_leftVictor2.configOpenloopRamp(RAMP_RATE, 10);
		m_leftVictor3.configOpenloopRamp(RAMP_RATE, 10);
		
		m_rightTalon.configOpenloopRamp(RAMP_RATE, 10);
		m_rightVictor1.configOpenloopRamp(RAMP_RATE, 10);
		m_rightVictor2.configOpenloopRamp(RAMP_RATE, 10);
		m_rightVictor3.configOpenloopRamp(RAMP_RATE, 10);
	}
	
	public void rampVoltage(double rampRate) {
		
		m_leftTalon.configOpenloopRamp(rampRate, 10);
		m_leftVictor1.configOpenloopRamp(rampRate, 10);
		m_leftVictor2.configOpenloopRamp(rampRate, 10);
		m_leftVictor3.configOpenloopRamp(rampRate, 10);
		
		m_rightTalon.configOpenloopRamp(rampRate, 10);
		m_rightVictor1.configOpenloopRamp(rampRate, 10);
		m_rightVictor2.configOpenloopRamp(rampRate, 10);
		m_rightVictor3.configOpenloopRamp(rampRate, 10);
	}
	
	// reverse drive method for booleans. You have to hold the button to
	// reverse. This method is used in control schemes to plug-in to SingDrive.
	/*public static int booleanHoldReverse(boolean reverse) {
		if (reverse) {
			return 180;
		} else {
			return 0;
		}
	} // In the next method, you can toggle using 2 boolean buttons.
	public static int booleanToggleReverse(boolean forward, boolean reverse) {
		if (reverse) reverseB = true;
		else if (forward) reverseB = false;
		if (reverseB) return 180;
		else return 0;
	}
	
	public static boolean POVReverse(int intReverse) {
		if (intReverse == 180 || reverseB) reverseB = true;
		else if (intReverse == 0) reverseB = false;
		if (reverseB) return true;
		else return false;
	}
	*/
	
	private double inchesToEncTic(double inches) {
		return (inches * 4096.0) / (4.0 * Math.PI);
	}
	
	private double encTicToInches(double tics) {
		return (4.0 * Math.PI * tics) / 4096.0;
	}
	
	public void displayEncoder() {
		SmartDashboard.putNumber("encoder inches", encTicToInches(m_leftVictor2.getSensorCollection().getQuadraturePosition()));
		SmartDashboard.putNumber("encoder ticks", m_leftVictor2.getSensorCollection().getQuadraturePosition());
		
	}
	
	/**
	 * Drive Straight with arcadeSixeWheel(), mostly for auton.
	 * Use negative speed to drive backwards, distance should always be positive
	 * @param distance distance <b> in inches </b> to travel
	 * @param speed speed from -1.0 to 1.0
	 * @param gyroRotationConstant a constant for rotating with the gyro. 0.05 is a good value
	 * @param maxTime the maxTime <b> in seconds </b> to run before exiting from the loop and moving on
	 */
	/*
	public void driveStraight(double distance, double speed, double gyroRotationConstant, double maxTime) {
		double origAngle = gyro.getAngle();
		distance = Math.abs(distance);
		Timer t = new Timer();
		t.reset();
		t.start();
		
		
		 double origPosition = ((CANTalon) m_leftVictor2).getEncPosition();
		// copy and paste into while loop:
		 //((CANTalon) m_leftVictor2).getEncPosition() - origPosition
		
		
		//((CANTalon) m_leftVictor2).setEncPosition(0);
		
		Timer.delay(0.2);
		
		SmartDashboard.putNumber("Distance - (speed * 30)", distance - speed * 30);
		
		
		while (t.get() < maxTime && Math.abs(encTicToInches(((CANTalon) m_leftVictor2).getEncPosition() - origPosition)) < distance - (Math.abs(speed) * 30)) {
			this.drive(speed, 0.0, gyroRotationConstant * (origAngle - gyro.getAngle()), false, SpeedMode.FAST);
			SmartDashboard.putNumber("abs value inch distance", Math.abs(encTicToInches(((CANTalon) m_leftVictor2).getEncPosition())));
			SmartDashboard.putBoolean("FirstLoop excluding time", Math.abs(encTicToInches(((CANTalon) m_leftVictor2).getEncPosition())) < distance - (speed * 30));
			this.displayEncoder();
		}
		
		while (t.get() < maxTime && Math.abs(encTicToInches(((CANTalon) m_leftVictor2).getEncPosition() - origPosition)) < distance) {

			SmartDashboard.putNumber("abs value inch distance", Math.abs(encTicToInches(((CANTalon) m_leftVictor2).getEncPosition())));
			SmartDashboard.putBoolean("SecondLoop excluding time", Math.abs(encTicToInches(((CANTalon) m_leftVictor2).getEncPosition())) < distance);
			this.drive(0.25, 0.0, gyroRotationConstant * (origAngle - gyro.getAngle()), false, SpeedMode.FAST);
		}
		
		this.drive(0.0, 0.0, 0.0, false, SpeedMode.FAST);
		t.reset();
		
		Timer.delay(0.2);
		
	}
	
	/**
	 * Turn a certain number of degrees base on  the gyro.
	 * @param degrees The number of degrees to turn. Negative is for left
	 * @param rotationConstant The speed to multiply the difference in target degrees 
	 * and actual degrees by.
	 * @param maxTime Half maximum amount of time the method can take to run.
	 */
	/*
	public void rotateTo(double degrees, double maxTime) {
		double origAngle = gyro.getAngle();
		Timer t = new Timer();
		t.reset();
		t.start();
		double currentAngle = gyro.getAngle() - origAngle;
		
		while (t.get() < maxTime && Math.abs(degrees) - Math.abs(currentAngle) > 20) {
			currentAngle = gyro.getAngle() - origAngle;
			this.drive(0.0, 0.0, 0.25 * Math.abs(degrees) / degrees, false, SpeedMode.FAST);
		}
		
		while (t.get() < maxTime && Math.abs(degrees) - Math.abs(currentAngle) > 0) {
			currentAngle = gyro.getAngle() - origAngle;
			this.drive(0.0, 0.0, 0.20 * Math.abs(degrees) / degrees, false, SpeedMode.FAST);
		}
		
		this.drive(0.0, 0.0, 0.0, false, SpeedMode.FAST);
		t.reset();
		
		Timer.delay(0.2);
	}
	*/
	
	public void encoderDriveStraight(double speed){
		leftEncVal = this.getLeftPosition();
		rightEncVal = this.getRightPosition();
		
		error = rightEncVal - leftEncVal;
		
		this.drive(speed, 0.0, error/KP, 1.0, SpeedMode.NORMAL);
		
		leftEncVal = 0;
		rightEncVal = 0;
		
		//repeat 10 times per second
		//wait 100 milliseconds
		Timer.delay(100);
	}
	
	protected void setVelocityMultiplierBasedOnSpeedMode(SpeedMode speedMode) {
		
		switch(speedMode) {
		case SLOW:
			velocityMultiplier = this.slowSpeedConstant;
			SmartDashboard.putString("DB/String 8", "Using slow speed constant");
			break;
		case NORMAL:
			velocityMultiplier = this.normalSpeedConstant;
			SmartDashboard.putString("DB/String 8", "Using normal speed constant");
			break;
		case FAST:
			velocityMultiplier = this.fastSpeedConstant;
			SmartDashboard.putString("DB/String 8", "Using fast speed constant");
			break;
		}
	}
	
	protected double getSendableSpeed() {
		return speedChooser.getSelected();
	}
	
	public abstract void drive(double vertical, double horizontal, double rotation, double inputExponent, SpeedMode speedMode);
}
