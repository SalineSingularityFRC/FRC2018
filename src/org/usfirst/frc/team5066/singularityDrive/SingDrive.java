package org.usfirst.frc.team5066.singularityDrive;

import org.usfirst.frc.team5066.library.SpeedMode;

import com.ctre.CANTalon;
//import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class SingDrive {
	public double slowSpeedConstant, normalSpeedConstant, fastSpeedConstant;
	public int mode = 0;
	
	protected CANTalon m_frontLeftMotor, m_rearLeftMotor, m_frontRightMotor, m_rearRightMotor;
	protected CANTalon m_leftMiddleMotor, m_rightMiddleMotor;
	

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
	private final static double DEFAULT_SLOW_SPEED_CONSTANT = 0.5;
	private final static double DEFAULT_NORMAL_SPEED_CONSTANT = 0.8;
	private final static double DEFAULT_FAST_SPEED_CONSTANT = 1.0;
	
	private final static double RAMP_RATE = 120.0;

	private int talonType;
	
	protected static double driveStraight;
	
	private static double prevStrafe;
	private static final double strafeTime = 0.05;
	Timer timer;
	
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
	public SingDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor,int midRightMotor,
			int midLeftMotor) {
		this(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor, midRightMotor,
				midLeftMotor, DEFAULT_TALON_TYPE,
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
	 * rearRightMotor, double velocityMultiplier) { m_frontLeftMotor =
	 * frontLeftMotor; m_rearLeftMotor = rearLeftMotor; m_frontRightMotor =
	 * frontRightMotor; m_rearRightMotor = rearRightMotor;
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
	
	public SingDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor, int leftMiddleMotor, int rightMiddleMotor,
			int talonType, double slowSpeedConstant, double normalSpeedConstant, double fastSpeedConstant) {//Six wheel constructor

		if (talonType == CANTALON_DRIVE) {
			m_frontLeftMotor = new CANTalon(frontLeftMotor);
			m_rearLeftMotor = new CANTalon(rearLeftMotor);
			m_frontRightMotor = new CANTalon(frontRightMotor);
			m_rearRightMotor = new CANTalon(rearRightMotor);
			m_leftMiddleMotor = new CANTalon(leftMiddleMotor);
			m_rightMiddleMotor = new CANTalon(rightMiddleMotor);

		} else {
			SmartDashboard.putNumber("INVALID VALUE FOR TALON TYPE.b\tvalue=", talonType);
		}

		this.velocityMultiplier = normalSpeedConstant;
		this.talonType = talonType;
		this.slowSpeedConstant = slowSpeedConstant;
		this.normalSpeedConstant = normalSpeedConstant;
		this.fastSpeedConstant = fastSpeedConstant;
		this.driveStraight = driveStraight;
		timer = new Timer();
		//this.gyro = gyro;
		
		((CANTalon) m_leftMiddleMotor).setEncPosition(0);
	}
	
	public SingDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor,
			int talonType, double slowSpeedConstant, double normalSpeedConstant, double fastSpeedConstant) { //Four wheel constructor

		if (talonType == CANTALON_DRIVE) {
			m_frontLeftMotor = new CANTalon(frontLeftMotor);
			m_rearLeftMotor = new CANTalon(rearLeftMotor);
			m_frontRightMotor = new CANTalon(frontRightMotor);
			m_rearRightMotor = new CANTalon(rearRightMotor);

		} else {
			SmartDashboard.putNumber("INVALID VALUE FOR TALON TYPE.b\tvalue=", talonType);
		}

		this.velocityMultiplier = normalSpeedConstant;
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
		((CANTalon) m_frontLeftMotor).reset();
		((CANTalon) m_leftMiddleMotor).reset();
		((CANTalon) m_frontRightMotor).reset();
	}
	
	public double getLeftPosition(){
		return (((CANTalon) m_frontLeftMotor).getPosition());
	}
	
	public double getRightPosition(){
		return (((CANTalon) m_frontRightMotor).getPosition());
	}
	
	public double getMiddlePosition(){
		return (((CANTalon) m_leftMiddleMotor).getPosition());
	}
	
	public double leftOverRight() {
		return getLeftPosition() / getRightPosition();
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
	
	public void resetEncoder() {
		((CANTalon) m_leftMiddleMotor).reset();
	}
	
	public void rampVoltage() {
		
		((CANTalon) m_frontRightMotor).setVoltageRampRate(RAMP_RATE);
		((CANTalon) m_rearRightMotor).setVoltageRampRate(RAMP_RATE);
		((CANTalon) m_frontLeftMotor).setVoltageRampRate(RAMP_RATE);
		((CANTalon) m_rearLeftMotor).setVoltageRampRate(RAMP_RATE);
		((CANTalon) m_rightMiddleMotor).setVoltageRampRate(RAMP_RATE);
		((CANTalon) m_leftMiddleMotor).setVoltageRampRate(RAMP_RATE);
		
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
		SmartDashboard.putNumber("encoder inches", encTicToInches(((CANTalon) m_leftMiddleMotor).getEncPosition()));
		SmartDashboard.putNumber("encoder ticks", ((CANTalon) m_leftMiddleMotor).getEncPosition());
		
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
		
		
		 double origPosition = ((CANTalon) m_leftMiddleMotor).getEncPosition();
		// copy and paste into while loop:
		 //((CANTalon) m_leftMiddleMotor).getEncPosition() - origPosition
		
		
		//((CANTalon) m_leftMiddleMotor).setEncPosition(0);
		
		Timer.delay(0.2);
		
		SmartDashboard.putNumber("Distance - (speed * 30)", distance - speed * 30);
		
		
		while (t.get() < maxTime && Math.abs(encTicToInches(((CANTalon) m_leftMiddleMotor).getEncPosition() - origPosition)) < distance - (Math.abs(speed) * 30)) {
			this.drive(speed, 0.0, gyroRotationConstant * (origAngle - gyro.getAngle()), false, SpeedMode.FAST);
			SmartDashboard.putNumber("abs value inch distance", Math.abs(encTicToInches(((CANTalon) m_leftMiddleMotor).getEncPosition())));
			SmartDashboard.putBoolean("FirstLoop excluding time", Math.abs(encTicToInches(((CANTalon) m_leftMiddleMotor).getEncPosition())) < distance - (speed * 30));
			this.displayEncoder();
		}
		
		while (t.get() < maxTime && Math.abs(encTicToInches(((CANTalon) m_leftMiddleMotor).getEncPosition() - origPosition)) < distance) {

			SmartDashboard.putNumber("abs value inch distance", Math.abs(encTicToInches(((CANTalon) m_leftMiddleMotor).getEncPosition())));
			SmartDashboard.putBoolean("SecondLoop excluding time", Math.abs(encTicToInches(((CANTalon) m_leftMiddleMotor).getEncPosition())) < distance);
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
	
	public abstract void drive(double vertical, double horizontal, double rotation, boolean squaredInputs, SpeedMode speedMode);
}
