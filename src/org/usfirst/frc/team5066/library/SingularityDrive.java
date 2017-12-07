package org.usfirst.frc.team5066.library;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Class for driving around a simple robot. It is based on the
 * {@link edu.wpi.first.wpilibj.RobotDrive RobotDrive} class, which is provided
 * by <b>WPILib</b>
 * 
 * @author Saline Singularity 5066
 *
 */
public class SingularityDrive {

	private double slowSpeedConstant, normalSpeedConstant, fastSpeedConstant;

	
	private SpeedController m_frontLeftMotor, m_rearLeftMotor, m_frontRightMotor, m_rearRightMotor;
	private SpeedController m_leftMiddleMotor, m_rightMiddleMotor;


	

	private final static double DEFAULT_VELOCITY_MULTIPLIER = 1.0;
	private double velocityMultiplier = 1.0;

	private boolean velocityReduceActivated = false;
	private double reducedVelocity;

	private final static double DEFAULT_MINIMUM_THRESHOLD = 0.09;
	
	public static boolean isreverse = false;
	private static boolean reverseB = false;

	// Talon type enum
	public static final int CANTALON_DRIVE = 0;
	public static final int TALON_SR_DRIVE = 1;

	private static final int DEFAULT_TALON_TYPE = CANTALON_DRIVE;
	private final static double DEFAULT_SLOW_SPEED_CONSTANT = 0.5;
	private final static double DEFAULT_NORMAL_SPEED_CONSTANT = 0.8;
	private final static double DEFAULT_FAST_SPEED_CONSTANT = 1.0;
	
	private final static double RAMP_RATE = 120.0;

	private int talonType;
	
	private static double driveStraight;
	
	private static double prevStrafe;
	private static final double strafeTime = 0.05;
	Timer timer;
	
	AHRS gyro;


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
	public SingularityDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor,int midRightMotor,
			int midLeftMotor, double driveStraight, AHRS gyro) {
		this(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor, midRightMotor,
				midLeftMotor, DEFAULT_TALON_TYPE,
				DEFAULT_SLOW_SPEED_CONSTANT, DEFAULT_NORMAL_SPEED_CONSTANT, DEFAULT_FAST_SPEED_CONSTANT,
				driveStraight, gyro);
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
	
	public SingularityDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor, int leftMiddleMotor, int rightMiddleMotor,
			int talonType, double slowSpeedConstant, double normalSpeedConstant, double fastSpeedConstant, double driveStraight, AHRS gyro) {

		if (talonType == CANTALON_DRIVE) {
			m_frontLeftMotor = new CANTalon(frontLeftMotor);
			m_rearLeftMotor = new CANTalon(rearLeftMotor);
			m_frontRightMotor = new CANTalon(frontRightMotor);
			m_rearRightMotor = new CANTalon(rearRightMotor);
			m_leftMiddleMotor = new CANTalon(leftMiddleMotor);
			m_rightMiddleMotor = new CANTalon(rightMiddleMotor);

		} else if (talonType == TALON_SR_DRIVE) {
			m_frontLeftMotor = new Talon(frontLeftMotor);
			m_rearLeftMotor = new Talon(rearLeftMotor);
			m_frontRightMotor = new Talon(frontRightMotor);
			m_rearRightMotor = new Talon(rearRightMotor);
			m_leftMiddleMotor = new Talon(leftMiddleMotor);
			m_rightMiddleMotor = new Talon(rightMiddleMotor);
		} else {
			SmartDashboard.putNumber("INVALID VALUE FOR TALON TYPE.      value=", talonType);
		}

		this.velocityMultiplier = normalSpeedConstant;
		this.talonType = talonType;
		this.slowSpeedConstant = slowSpeedConstant;
		this.normalSpeedConstant = normalSpeedConstant;
		this.fastSpeedConstant = fastSpeedConstant;
		this.driveStraight = driveStraight;
		timer = new Timer();
		this.gyro = gyro;
		
		((CANTalon) m_leftMiddleMotor).setEncPosition(0);
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

	private double threshold(double velocity) {
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
			this.arcadeSixWheel(speed, gyroRotationConstant * (origAngle - gyro.getAngle()), false, SpeedMode.FAST);
			SmartDashboard.putNumber("abs value inch distance", Math.abs(encTicToInches(((CANTalon) m_leftMiddleMotor).getEncPosition())));
			SmartDashboard.putBoolean("FirstLoop excluding time", Math.abs(encTicToInches(((CANTalon) m_leftMiddleMotor).getEncPosition())) < distance - (speed * 30));
			this.displayEncoder();
		}
		
		while (t.get() < maxTime && Math.abs(encTicToInches(((CANTalon) m_leftMiddleMotor).getEncPosition() - origPosition)) < distance) {

			SmartDashboard.putNumber("abs value inch distance", Math.abs(encTicToInches(((CANTalon) m_leftMiddleMotor).getEncPosition())));
			SmartDashboard.putBoolean("SecondLoop excluding time", Math.abs(encTicToInches(((CANTalon) m_leftMiddleMotor).getEncPosition())) < distance);
			this.arcadeSixWheel(0.25, gyroRotationConstant * (origAngle - gyro.getAngle()), false, SpeedMode.FAST);
		}
		
		this.arcadeSixWheel(0.0, 0.0, false, SpeedMode.FAST);
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
	
	public void rotateTo(double degrees, double maxTime) {
		double origAngle = gyro.getAngle();
		Timer t = new Timer();
		t.reset();
		t.start();
		double currentAngle = gyro.getAngle() - origAngle;
		
		while (t.get() < maxTime && Math.abs(degrees) - Math.abs(currentAngle) > 20) {
			currentAngle = gyro.getAngle() - origAngle;
			this.arcadeSixWheel(0.0, 0.25 * Math.abs(degrees) / degrees, false, SpeedMode.FAST);
		}
		
		while (t.get() < maxTime && Math.abs(degrees) - Math.abs(currentAngle) > 0) {
			currentAngle = gyro.getAngle() - origAngle;
			this.arcadeSixWheel(0.0, 0.20 * Math.abs(degrees) / degrees, false, SpeedMode.FAST);
		}
		
		this.arcadeSixWheel(0.0, 0.0, false, SpeedMode.FAST);
		t.reset();
		
		Timer.delay(0.2);
	}
	
	
	public void hDrive(double vertical, double horizontal, double rotation, boolean squaredInputs, SpeedMode speedMode) {
		
		setVelocityMultiplierBasedOnSpeedMode(speedMode);
		
		// Do squared inputs if necessary
		if (squaredInputs) {
			vertical *= Math.abs(vertical);
			rotation *= Math.abs(rotation);
			horizontal *= Math.abs(horizontal);
		}
		
		// Guard against illegal values
		double mainWheelMaximum = Math.max(1, Math.abs(vertical) + Math.abs(rotation));
		double hWheelMaximum = Math.max(1, Math.abs(horizontal));
		
		//if (buttonPressed) {
			//mainWheelMaximum *= 1 / reducedVelocity;
			//hWheelMaximum *= 1  / reducedVelocity;
		//}
		
		vertical = threshold(vertical);
		horizontal = threshold(horizontal);
		rotation = threshold(rotation);
		/*
		if (timer.get() - )
		
		
		
		
		prevStrafe = horizontal;
		*/
		
		
		m_frontLeftMotor.set(this.velocityMultiplier * ((-vertical + rotation) / mainWheelMaximum));
		m_rearLeftMotor.set(this.velocityMultiplier * ((-vertical + rotation) / mainWheelMaximum));
		m_frontRightMotor.set(this.velocityMultiplier * ((vertical + rotation) / mainWheelMaximum));
		m_rearRightMotor.set(this.velocityMultiplier * ((vertical + rotation) / mainWheelMaximum));
		m_rightMiddleMotor.set(this.velocityMultiplier * (-horizontal / hWheelMaximum));
		m_leftMiddleMotor.set(this.velocityMultiplier * (-horizontal / hWheelMaximum));
		
		//for testing purposes only
		/*SmartDashboard.putString("DB/String 0", "Front Left Motor " + m_frontLeftMotor.get());
		SmartDashboard.putString("DB/String 1", "Rear Left Motor " + m_rearLeftMotor.get());
		SmartDashboard.putString("DB/String 2", "Front Right Motor " +  m_frontRightMotor.get());
		SmartDashboard.putString("DB/String 3", "Rear Right Motor " +  m_rearRightMotor.get());
		SmartDashboard.putString("DB/String 4", "Left Middle Motor " +  m_leftMiddleMotor.get());
		SmartDashboard.putString("DB/String 5", "Right Middle Motor " +  m_rightMiddleMotor.get());
		*/
		
	}
	
	public void hDriveTank(double left, double right, double horizontal, boolean squaredInputs, SpeedMode speedMode) {
		setVelocityMultiplierBasedOnSpeedMode(speedMode);
		
		// Do squared inputs if necessary
		if (squaredInputs) {
			left *= Math.abs(left);
			right *= Math.abs(right);
			horizontal *= Math.abs(horizontal);
		}
		
		// Guard against illegal values
		double rightWheelMaximum = Math.max(1, Math.abs(right));
		double leftWheelMaximum = Math.max(1,  Math.abs(left));
		double hWheelMaximum = Math.max(1, Math.abs(horizontal));
	
		//if (buttonPressed) {
			//rightWheelMaximum *= 1 / reducedVelocity;
			//leftWheelMaximum *= 1 / reducedVelocity;
			//hWheelMaximum *= 1  / reducedVelocity;
		//}
				
		left = threshold(left);
		right = threshold(right);
		horizontal = threshold(horizontal);
		
		m_frontLeftMotor.set(this.velocityMultiplier * (-left /leftWheelMaximum));
		m_rearLeftMotor.set(this.velocityMultiplier * (-left / leftWheelMaximum));
		m_frontRightMotor.set(this.velocityMultiplier * (right / rightWheelMaximum));
		m_rearRightMotor.set(this.velocityMultiplier * (right / rightWheelMaximum));
		m_rightMiddleMotor.set(this.velocityMultiplier * (-horizontal / hWheelMaximum));
		m_leftMiddleMotor.set(this.velocityMultiplier * (-horizontal / hWheelMaximum));
				
	}
	
	/*
	 * A method for autonomously moving straight forward,
	 * using encoders to keep on a straight path.
	 * @param speed This is the speed to move at.
	 * Note: speed is not changed by squaredInputs or velocityMultiplier
	 */
	public void hDriveStraightEncoder(double vertical, double horizontal, double rotation, double distance) {
		
		this.resetAll();
		
		while(true) {
			
			double vertMax = Math.max(1,  Math.abs(vertical) + Math.abs(rotation));
			double strafeMax = Math.max(1,  Math.abs(horizontal));
			
			m_frontLeftMotor.set((-vertical + rotation) / (vertMax * leftOverRight()));
			m_rearLeftMotor.set((-vertical + rotation) / (vertMax * leftOverRight()));
			m_frontRightMotor.set((vertical * leftOverRight() + rotation) / vertMax);
			m_rearRightMotor.set((vertical * leftOverRight() + rotation) / vertMax);
			m_rightMiddleMotor.set(-horizontal / strafeMax);
			m_leftMiddleMotor.set(-horizontal / strafeMax);
			
			if (Math.abs((this.getRightPosition() + this.getLeftPosition())) / 2 > distance) return;
		}
	}
	
public void hDriveStrafeEncoder(double horizontal, double distance) {
		
		this.resetAll();
		
		while(true) {
			
			
			double strafeMax = Math.max(1,  Math.abs(horizontal));
			
			m_frontLeftMotor.set(0.0);
			m_rearLeftMotor.set(0.0);
			m_frontRightMotor.set(0.0);
			m_rearRightMotor.set(0.0);
			m_rightMiddleMotor.set(-horizontal / strafeMax);
			m_leftMiddleMotor.set(-horizontal / strafeMax);
			
			if (Math.abs(this.getMiddlePosition()) > distance) return;
		}
	}
	
	public void hDriveStraightConstant(double vertical, double horizontal, double rotation) {
		
		double vertMax = Math.max(1,  Math.abs(vertical));
		double strafeMax = Math.max(1, Math.abs(horizontal));
		
		m_frontLeftMotor.set((-vertical * driveStraight + rotation) / vertMax);
		m_rearLeftMotor.set((-vertical * driveStraight + rotation) / vertMax);
		m_frontRightMotor.set((vertical + rotation) / (vertMax * driveStraight));
		m_rearRightMotor.set((vertical + rotation) / (vertMax * driveStraight));
		m_rightMiddleMotor.set(-horizontal / strafeMax);
		m_leftMiddleMotor.set(-horizontal / strafeMax);
	}
	
	/**
	 * So called "arcade drive" method for driving a robot around. Drives much
	 * like one would expect a vehicle to move with a joy stick.
	 * 
	 * @param translation
	 *            Speed and direction at which to translate forwards
	 * @param rotation
	 *            Speed and direction at which to rotate clockwise
	 * @param squaredInputs
	 *            Whether or not to square the magnitude of the input values in
	 *            order to provide for finer motor control at lower velocities
	 * @param speedMode
	 *            The enum value corrresponding to the current speed mode: slow,
	 *            normal, or fast
	 * @param reverse
	 *            The value (180 or 0) to control reverse drive. 180 = reverse 
	 *            and 0 = forward
	 */
	
	public void arcade(double translation, double rotation, boolean squaredInputs, SpeedMode speedMode) {
		double translationVelocity = translation, rotationVelocity = rotation;
		
		setVelocityMultiplierBasedOnSpeedMode(speedMode);
		
		// Do squared inputs if necessary
		if (squaredInputs) {
			translationVelocity *= Math.abs(translation);
			rotationVelocity *= Math.abs(rotation);
		}
		
		// Do reverse drive when necessary. There are methods above for different inputs.
		/*if (reverse) {
			translationVelocity = -translationVelocity;
			rotationVelocity = -rotationVelocity;
		}
		*/
		// Guard against illegal values
		double maximum = Math.max(1, Math.abs(translationVelocity) + Math.abs(rotationVelocity));

		if (velocityReduceActivated) {
			maximum *= 1 / reducedVelocity;
		}

		translationVelocity = threshold(translationVelocity);
		rotationVelocity = threshold(rotationVelocity);

		// Set the motors
		m_frontLeftMotor.set(this.velocityMultiplier * ((-translationVelocity + rotationVelocity) / maximum));
		m_rearLeftMotor.set(this.velocityMultiplier * ((-translationVelocity + rotationVelocity) / maximum));
		m_frontRightMotor.set(this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
		m_rearRightMotor.set(this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
	}
	
	public void arcadeSixWheel(double translation, double rotation, boolean squaredInputs, SpeedMode speedMode) {
		double translationVelocity = translation, rotationVelocity = rotation;
		
		translationVelocity = threshold(translationVelocity);
		rotationVelocity = threshold(rotationVelocity);
		
		setVelocityMultiplierBasedOnSpeedMode(speedMode);
		
		// Do squared inputs if necessary
		if (squaredInputs) {
			translationVelocity *= Math.abs(translation);
			rotationVelocity *= Math.abs(rotation);
		}
		
		// Do reverse drive when necessary. There are methods above for different inputs.
		/*if (reverse) {
			translationVelocity = -translationVelocity;
			rotationVelocity = -rotationVelocity;
		}
		*/
		// Guard against illegal values
		double maximum = Math.max(1, Math.abs(translationVelocity) + Math.abs(rotationVelocity));

		if (velocityReduceActivated) {
			maximum *= 1 / reducedVelocity;
		}

		translationVelocity = threshold(translationVelocity);
		rotationVelocity = threshold(rotationVelocity);

		// Set the motors
		m_frontLeftMotor.set(this.velocityMultiplier * ((-translationVelocity + rotationVelocity) / maximum));
		m_rearLeftMotor.set(this.velocityMultiplier * ((-translationVelocity + rotationVelocity) / maximum));
		m_leftMiddleMotor.set(this.velocityMultiplier * ((-translationVelocity + rotationVelocity) / maximum));
		m_rightMiddleMotor.set(this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
		m_frontRightMotor.set(this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
		m_rearRightMotor.set(this.velocityMultiplier * ((translationVelocity + rotationVelocity) / maximum));
		SmartDashboard.putNumber("rightEncoder", ((CANTalon) m_rightMiddleMotor).getEncPosition());
		SmartDashboard.putNumber("leftEncoder", ((CANTalon) m_leftMiddleMotor).getEncPosition());
	}

	public void arcade(double translation, double rotation, boolean squaredInputs) {
		this.arcade(translation, rotation, squaredInputs, SpeedMode.NORMAL);
	}
	
	private void setVelocityMultiplierBasedOnSpeedMode(SpeedMode speedMode) {
		
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

	/**
	 * So called "arcade drive" method for driving a robot around. Drives much
	 * like one would expect a vehicle to move with a joy stick. This method
	 * does not assume squared inputs.
	 * 
	 * @param translation
	 *            Speed and direction at which to translate forwards
	 * @param rotation
	 *            Speed and direction at which to rotate clockwise
	 */
	public void arcade(double translation, double rotation) {
		// Just do the arcade without squared inputs at normal speed mode,
		//and without reverse
		this.arcade(translation, rotation, false, SpeedMode.NORMAL);
	}

	/**
	 * A method for driving a robot with Mecanum wheels (allowing full
	 * translation and rotation). This function uses the algorithm found on
	 * <a href=
	 * "http://thinktank.wpi.edu/resources/346/ControllingMecanumDrive.pdf">this
	 * pdf</a>, which was created by FRC team 2022.
	 * 
	 * @param horizontal
	 *            Velocity at which to translate horizontally
	 * @param vertical
	 *            Velocity at which to translate vertically
	 * @param rotation
	 *            Velocity at which to rotate
	 * @param translationMultiplier
	 *            Constant to reduce the translation speed
	 * @param rotationMultiplier
	 *            Constant to reduce the rotation speed
	 * @param squaredInputs
	 *            Whether or not to square the magnitude of the input values in
	 *            order to provide for finer motor control at lower velocities
	 */
	public void mecanum(double horizontal, double vertical, double rotation, double rotationMultiplier,
			boolean squaredInputs) {

		double translationVelocity, direction, maximum, rotationVelocity;

		// Do squared inputs if necessary
		if (squaredInputs) {
			horizontal *= Math.abs(horizontal);
			vertical *= Math.abs(vertical);
			rotation *= Math.abs(rotation);
		}

		// Use the Pythagorean theorem to find the speed of translation
		translationVelocity = this.velocityMultiplier * Math.sqrt(Math.pow(horizontal, 2) + Math.pow(vertical, 2));
		rotationVelocity = this.velocityMultiplier * rotation * rotationMultiplier;

		// Use trigonometry to find the direction of travel
		direction = Math.PI / 4 + Math.atan2(vertical, horizontal);

		// Guard against illegal inputs
		maximum = Math.max(Math.max(Math.abs(Math.sin(direction)), Math.abs(Math.cos(direction))) * translationVelocity
				+ Math.abs(rotationVelocity), 1);

		if (velocityReduceActivated) {
			maximum *= 1 / reducedVelocity;
		}

		translationVelocity = threshold(translationVelocity);
		rotationVelocity = threshold(rotationVelocity);

		// Set the motors' speeds
		m_frontLeftMotor.set((translationVelocity * Math.sin(direction) + rotationVelocity) / maximum);
		m_rearLeftMotor.set((translationVelocity * -Math.cos(direction) + rotationVelocity) / maximum);
		m_frontRightMotor.set((translationVelocity * Math.cos(direction) + rotationVelocity) / maximum);
		m_rearRightMotor.set((translationVelocity * -Math.sin(direction) + rotationVelocity) / maximum);
	}

	/**
	 * A method for driving a robot with Mecanum wheels (allowing full
	 * translation and rotation). This function uses the algorithm found on
	 * <a href=
	 * "http://thinktank.wpi.edu/resources/346/ControllingMecanumDrive.pdf">this
	 * pdf</a>, which was created by FRC team 2022. This method does not assume
	 * squared inputs.
	 * 
	 * @param horizontal
	 *            Velocity at which to translate horizontally
	 * @param vertical
	 *            Velocity at which to translate vertically
	 * @param rotation
	 *            Velocity at which to rotate
	 * @param translationMultiplier
	 *            Constant to reduce the translation speed
	 * @param rotationMultiplier
	 *            Constant to reduce the rotation speed
	 */
	public void mecanum(double horizontal, double vertical, double rotation, double translationMultiplier,
			double rotationMultiplier) {
		// Just ignore squared inputs
		this.mecanum(horizontal, vertical, rotation, rotationMultiplier, false);
	}

	/**
	 * A method for driving a robot with Mecanum wheels (allowing full
	 * translation and rotation). This function uses the algorithm found on
	 * <a href=
	 * "http://thinktank.wpi.edu/resources/346/ControllingMecanumDrive.pdf">this
	 * pdf</a>, which was created by FRC team 2022. This method uses maximum
	 * magnitudes of 0.8 for the translation and rotation variables.
	 * 
	 * @param horizontal
	 *            Velocity at which to translate horizontally
	 * @param vertical
	 *            Velocity at which to translate vertically
	 * @param rotation
	 *            Velocity at which to rotate
	 * @param squaredInputs
	 *            Whether or not to square the magnitude of the input values in
	 *            order to provide for finer motor control at lower velocities
	 */
	public void mecanum(double horizontal, double vertical, double rotation, boolean squaredInputs) {
		// Set default values for multipliers
		this.mecanum(horizontal, vertical, rotation, 0.8, squaredInputs);
	}

	/**
	 * A method for driving a robot with Mecanum wheels (allowing full
	 * translation and rotation). This function uses the algorithm found on
	 * <a href=
	 * "http://thinktank.wpi.edu/resources/346/ControllingMecanumDrive.pdf">this
	 * pdf</a>, which was created by FRC team 2022. This method uses maximum
	 * magnitudes of 0.8 for the translation and rotation variables and does not
	 * assume squared inputs.
	 * 
	 * @param horizontal
	 *            Velocity at which to translate horizontally
	 * @param vertical
	 *            Velocity at which to translate vertically
	 * @param rotation
	 *            Velocity at which to rotate
	 */
	public void mecanum(double horizontal, double vertical, double rotation) {
		// Ignore squared inputs and use default values for multipliers
		this.mecanum(horizontal, vertical, rotation, 0.8, false);
	}

	/**
	 * Method for driving the robot like a tank. It uses two sticks: one for the
	 * left pair of wheels and one for the right pair of wheels.
	 * 
	 * @param left
	 *            Velocity at which to rotate the left set of wheels
	 *            (counterclockwise i.e. forwards)
	 * @param right
	 *            Velocity at which to rotate the right set of wheels (clockwise
	 *            i.e. forwards)
	 * @param squaredInputs
	 *            Whether or not to square the magnitude of the input values in
	 *            order to provide for finer motor control at lower velocities
	 */
	public void tank(double left, double right, boolean squaredInputs, SpeedMode speedMode) {
		double leftVelocity = left, rightVelocity = right;
		
		this.setVelocityMultiplierBasedOnSpeedMode(speedMode);
		
		// Do squared inputs if necessary
		if (squaredInputs) {
			leftVelocity *= Math.abs(left);
			rightVelocity *= Math.abs(right);
		}
		SmartDashboard.putNumber("Post-sqaring inputs - Left Velocity", leftVelocity);
		SmartDashboard.putNumber("Post-sqaring inputs - Right Velocity", rightVelocity);

		
		// Guard against illegal inputs
		leftVelocity /= Math.max(1, Math.abs(leftVelocity));
		rightVelocity /= Math.max(1, Math.abs(rightVelocity));

		SmartDashboard.putNumber("Clamped Value - Left Velocity", leftVelocity);
		SmartDashboard.putNumber("Clamped Value - Right Velocity", rightVelocity);
		if (velocityReduceActivated) {
			leftVelocity *= reducedVelocity;
			rightVelocity *= reducedVelocity;
		}
		
		SmartDashboard.putNumber("Reduced Velocity - Left", leftVelocity);
		SmartDashboard.putNumber("Reduced Velocity - Right", rightVelocity);
		

		SmartDashboard.putNumber("Reduced Velocity - Left", leftVelocity);
		SmartDashboard.putNumber("Reduced Velocity - Right", rightVelocity);
		
		//reverse drive for tank. :D
		/*if (reverse == 180) isreverse = true;
		else if (reverse == 0) isreverse = false;
		if (isreverse) {
			leftVelocity = -leftVelocity;
			rightVelocity = -rightVelocity;
		}
		*/
		leftVelocity = threshold(leftVelocity);
		rightVelocity = threshold(rightVelocity);

		// Set the motors' speeds
		m_frontLeftMotor.set(this.velocityMultiplier * leftVelocity);
		m_rearLeftMotor.set(this.velocityMultiplier * leftVelocity);
		m_frontRightMotor.set(this.velocityMultiplier * -rightVelocity);
		m_rearRightMotor.set(this.velocityMultiplier * -rightVelocity);
	}

	/**
	 * Method for driving the robot like a tank. It uses two sticks: one for the
	 * left pair of wheels and one for the right pair of wheels. This method
	 * does not assume squared inputs.
	 * 
	 * @param left
	 *            Velocity at which to rotate the left set of wheels
	 *            (counterclockwise i.e. forwards)
	 * @param right
	 *            Velocity at which to rotate the right set of wheels (clockwise
	 *            i.e. forwards)
	 */
	public void tank(double left, double right, SpeedMode speedMode) {
		// Just ignore squared inputs
		this.tank(left, right, false, speedMode);
	}
	
	public void tank(double left, double right, boolean squaredInputs) {
		// Just ignore squared speedMode and reverse
		this.tank(left, right, true, SpeedMode.NORMAL);
	}
	
	
}