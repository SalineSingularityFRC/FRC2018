package org.usfirst.frc.team5066.library;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
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
	private final static double DEFAULT_SLOW_SPEED_CONSTANT = 0.4;
	private final static double DEFAULT_NORMAL_SPEED_CONSTANT = 0.8;
	private final static double DEFAULT_FAST_SPEED_CONSTANT = 1.0;

	private int talonType;


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
			int midLeftMotor) {
		this(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor, midRightMotor,
				midLeftMotor, DEFAULT_TALON_TYPE,
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
	
	public SingularityDrive(int frontLeftMotor, int rearLeftMotor, int frontRightMotor, int rearRightMotor, int leftMiddleMotor, int rightMiddleMotor,
			int talonType, double slowSpeedConstant, double normalSpeedConstant, double fastSpeedConstant) {

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

	private double clamp(double velocityMultiplier) {
		if (velocityMultiplier > 1.0) {
			return 1.0;
		}else if (velocityMultiplier < -1.0) {
			return -1.0;
			}else {
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
	
	public void hDrive(double vertical, double rotation, boolean squaredInputs, SpeedMode speedMode) {
		
		setVelocityMultiplierBasedOnSpeedMode(speedMode);
		
		// Do squared inputs if necessary
		if (squaredInputs) {
			vertical *= Math.abs(vertical);
			rotation *= Math.abs(rotation);
		}
		
		// Guard against illegal values
		double mainWheelMaximum = Math.max(1, Math.abs(vertical) + Math.abs(rotation));
		double hWheelMaximum = Math.max(1, Math.abs(horizontal));
		
		//if (buttonPressed) {
			//mainWheelMaximum *= 1 / reducedVelocity;
			//hWheelMaximum *= 1  / reducedVelocity;
		//}
		
		horizontal = threshold(horizontal);
		rotation = threshold(rotation);
		
		m_frontLeftMotor.set(this.velocityMultiplier * ((-vertical + rotation) / mainWheelMaximum));
		m_rearLeftMotor.set(this.velocityMultiplier * ((-vertical + rotation) / mainWheelMaximum));
		m_leftMiddleMotor.set(this.velocityMultiplier * ((-vertical + rotation) / mainWheelMaximum));
		m_frontRightMotor.set(this.velocityMultiplier * ((vertical + rotation) / mainWheelMaximum));
		m_rearRightMotor.set(this.velocityMultiplier * ((vertical + rotation) / mainWheelMaximum));
		m_rightMiddleMotor.set(this.velocityMultiplier * ((vertical + rotation) / mainWheelMaximum));		
		
		//for testing purposes only
		/*SmartDashboard.putString("DB/String 0", "Front Left Motor " + m_frontLeftMotor.get());
		SmartDashboard.putString("DB/String 1", "Rear Left Motor " + m_rearLeftMotor.get());
		SmartDashboard.putString("DB/String 2", "Front Right Motor " +  m_frontRightMotor.get());
		SmartDashboard.putString("DB/String 3", "Rear Right Motor " +  m_rearRightMotor.get());
		SmartDashboard.putString("DB/String 4", "Left Middle Motor " +  m_leftMiddleMotor.get());
		SmartDashboard.putString("DB/String 5", "Right Middle Motor " +  m_rightMiddleMotor.get());
		*/
		
	}
	
	public void hDriveTank(double left, double right, boolean squaredInputs, SpeedMode speedMode) {
		setVelocityMultiplierBasedOnSpeedMode(speedMode);
		
		// Do squared inputs if necessary
		if (squaredInputs) {
			left *= Math.abs(left);
			right *= Math.abs(right);
		
		}
		
		// Guard against illegal values
		double rightWheelMaximum = Math.max(1, Math.abs(right));
		double leftWheelMaximum = Math.max(1,  Math.abs(left));
	
		//if (buttonPressed) {
			//rightWheelMaximum *= 1 / reducedVelocity;
			//leftWheelMaximum *= 1 / reducedVelocity;
			//hWheelMaximum *= 1  / reducedVelocity;
		//}
				
		left = threshold(left);
		right = threshold(right);		
		
		m_frontLeftMotor.set(this.velocityMultiplier * (-left /leftWheelMaximum));
		m_rearLeftMotor.set(this.velocityMultiplier * (-left / leftWheelMaximum));
		m_leftMiddleMotor.set(this.velocityMultiplier * (-left / leftWheelMaximum));
		m_frontRightMotor.set(this.velocityMultiplier * (right / rightWheelMaximum));
		m_rearRightMotor.set(this.velocityMultiplier * (right / rightWheelMaximum));
		m_rightMiddleMotor.set(this.velocityMultiplier * (right / rightWheelMaximum));
		
				
	}
	
	private void setVelocityMultiplierBasedOnSpeedMode(SpeedMode speedMode) {
		
		switch(speedMode) {
		case SLOW:
			velocitMultiplier = this.slowSpeedConstant;
			SmartDashboard.putString("Db/String 8",  "Using slow speed constant");
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