package org.usfirst.frc.team5066.controller2018;

import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;
import org.usfirst.frc.team5066.singularityDrive.SixWheelDrive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class AutonControlScheme {

	//TODO change later
	public static final double DistancePerRevolution = 20;
	public static final double encoderTicks = 30720;
	public static final double CenterRobotWidth = 34;//TODO change if have bumpers
	public final double CenterRobotLength = 39.5;//TODO change if have bumpers (16.3 is half-way w/o bumpers)
	public final double CenterRobotLengthWithArm = 51.5;
	public final double CenterFieldPortal = 30;
	//TODO make sure ^^this^^ distance is from the Center of the Robot to the end of the cube when it's loaded
	public final double CenterRobotCorner = Math.sqrt( Math.pow(CenterRobotWidth,2) + Math.pow(this.CenterRobotLength,2) );
	private static final double speed = 0.35;
	
	private final double accelInGs = 0.15;
	
	private final double armSpeed = 0.25;
	
	
	//PIDController turnController;
	/*TODO Add PID Controller
	static final double kP = 0.03;
	static final double kI = 0.00;
	static final double kD = 0.00;
	static final double kF = 0.00;
	*/
	protected AHRS gyro;
	protected SingDrive drive;
	protected Arm arm;
	protected Intake intake;
	
	static double initialEncoderPos;
	static double initialAngle;
	
	boolean ramped;

	
	public AutonControlScheme (SingDrive drive, AHRS gyro, Arm arm, Intake intake) {
		
		this.drive = drive;
		this.gyro = gyro;
		this.arm = arm;
		this.intake = intake;
		
		ramped = true;
		
		//creates new AHRS gyro object that takes the port located on the roborio
		//gyro = new AHRS(gyroPort);
		//gyro.reset();
	}
	
	public abstract void moveAuton();
	
	//if ur code isn't working for reverse, set vertspeed to negative
	public void vertical(double verticalSpeed, double distance) {
		drive.rampVoltage();
		ramped = true;
		
		initialEncoderPos = drive.getRightPosition();
		initialAngle = gyro.getAngle();
			
		//normal speed 3072
		while (Math.abs(drive.getRightPosition() - initialEncoderPos) < 
				distance * encoderTicks / DistancePerRevolution &&
				DriverStation.getInstance().isAutonomous()) {
			
			
			
			SmartDashboard.putString("DB/String 4", ""+drive.getRightPosition());
			System.out.println(drive.getRightPosition());
			
			if (ramped && Math.abs(drive.getRightPosition() - initialEncoderPos) > 0.5 * distance * encoderTicks / DistancePerRevolution) {
				drive.rampVoltage(0.0);
				ramped = false;
			}
			
			//drive.encoderDriveStraight(verticalSpeed);
			((SixWheelDrive)drive).tankDrive(-verticalSpeed + 0.07 * (gyro.getAngle() - initialAngle),
					-verticalSpeed, 1.0, SpeedMode.FAST);
		}
		
		//slow down
		/*while (getAverage() > -2 / DistancePerRevolution
				&& getAverage() < 2 / DistancePerRevolution) {
			drive.drive(vertSpeed / 2, vertSpeed / 2, 0.0, false, SpeedMode.NORMAL);
		}*/
		
		((SixWheelDrive)drive).tankDrive(0.0, 0.0, 1.0, SpeedMode.FAST);
		
		drive.rampVoltage();
		ramped = true;
	}
	
	
	/**
	 * The preferred method of moving in a straight line for 2018
	 * 
	 * @param verticalSpeed the speed we want to move in voltage percentage, make negative to go backwards
	 * @param distance the distance in inches
	 * @param armPosition the position of the arm we should move to while traveling
	 * @param intakeOn write true if we want to intake while traveling
	 */
	public void vertical(double verticalSpeed, double distance, boolean armPosition, boolean intakeOn) {
		
		//ramp voltage to accelerate smoothly
		drive.rampVoltage(0.8);
		ramped = true;
		
		drive.resetEncoders();
		
		//record initial data to compare to while driving
		initialEncoderPos = this.getAverage();
		initialAngle = gyro.getAngle();
		
		//Timer distanceTimer = new Timer();
		//distanceTimer.start();
		
		//Timer impactTimer = new Timer();
		//impactTimer.start();
			
		//normal speed 3072
		//while the position is less than what we want to travel
		while (this.getAverage() - initialEncoderPos < 
				(distance - 2)  * encoderTicks / DistancePerRevolution &&
				DriverStation.getInstance().isAutonomous()) {
				//&& distanceTimer.get() < distance * 2.2 / 48) {
				//&& !(Math.abs(gyro.getRawAccelX()) > accelInGs && impactTimer.get() > 0.5)) {
			
			
			//Move the arm towards the preferred position
			arm.setArmNew(armPosition, armSpeed);
			
			//intake if wanted
			if (intakeOn) {
				this.intake.booleanIntake(true, false);
			}
			else {
				this.intake.booleanIntake(false, false);
			}
			
			
			SmartDashboard.putString("DB/String 4", ""+drive.getRightPosition());
			System.out.println(drive.getRightPosition());
			
			//unramp the voltage once we get halfway to avoid rolling through the stop
			if (ramped && this.getAverage() - initialEncoderPos > 0.5 * distance * encoderTicks / DistancePerRevolution) {
				drive.rampVoltage(0.0);
				ramped = false;
			}
			
			//drive.encoderDriveStraight(verticalSpeed);
			
			//Drive at the intended speed, with correction from the gyro
			((SixWheelDrive)drive).tankDrive(-verticalSpeed + 0.07 * (gyro.getAngle() - initialAngle),
					-verticalSpeed, 1.0, SpeedMode.FAST);
		}
		
		//slow down
		/*while (getAverage() > -2 / DistancePerRevolution
				&& getAverage() < 2 / DistancePerRevolution) {
			drive.drive(vertSpeed / 2, vertSpeed / 2, 0.0, false, SpeedMode.NORMAL);
		}*/
		
		//turn off motors
		((SixWheelDrive)drive).tankDrive(0.0, 0.0, 1.0, SpeedMode.FAST);
		
		//ramp the voltage again and turn off the intake
		drive.rampVoltage();
		ramped = true;
		this.intake.booleanIntake(false, false);
		Timer.delay(0.25);
	}
	
	public void vertical(double distance) {
		vertical(speed, distance);
	}
	
	/**
	 * The preferred method of moving in a straight line for 2018 with default speed
	 * 
	 * 
	 * @param distance the distance in inches
	 * @param armPosition the position of the arm we should move to while traveling
	 * @param intakeOn write true if we want to intake while traveling
	 */
	public void vertical(double distance, boolean armPosition, boolean intakeOn) {
		vertical(speed, distance, armPosition, intakeOn);
	}
	
	/**
	 * The preferred method of moving backwards in a straight line for 2018 with default speed
	 * 
	 * 
	 * @param distance the distance in inches
	 * @param armPosition the position of the arm we should move to while traveling
	 * @param intakeOn write true if we want to intake while traveling
	 */
	public void verticalReverse(double distance, boolean armPosition, boolean intakeOn) {
		
		vertical(-speed, distance, armPosition, intakeOn);
	}

	
	
	private double getAverage() {
		return (Math.abs(drive.getLeftPosition()) + Math.abs(drive.getRightPosition())) / 2;
	}

	//TODO Figure out AHRS gyro to get this method to work
	public void rotate(double rotationSpeed, double angle, boolean counterClockwise) {
		drive.rampVoltage();
		ramped = true;
		
		initialAngle = gyro.getAngle();
		
		System.out.println(gyro.getAngle());
		
		if(counterClockwise) rotationSpeed*= -1;
		while(Math.abs(gyro.getAngle() - initialAngle) < angle &&
				DriverStation.getInstance().isAutonomous()) {
			
			//accelerate motors slowly
			//drive.rampVoltage();

			System.out.println(gyro.getAngle());
			
			if (ramped && Math.abs(gyro.getAngle() - initialAngle) > 0.5 * angle) {
				drive.rampVoltage(0.0);
				ramped = false;
			}
			
			((SixWheelDrive)drive).tankDrive(-rotationSpeed, rotationSpeed, 1.0, SpeedMode.FAST);
		}
		
		drive.drive(0.0, 0.0, 0.0, 1.0, SpeedMode.FAST);
		
		drive.rampVoltage();
		ramped = true;
		
	}
	
	/**
	 * The preferred method of rotating for 2018
	 * 
	 * @param rotationSpeed the speed in voltage percentage
	 * @param angle the angle to turn
	 * @param counterClockwise true if we want to turn counterclockwise
	 * @param armPosition the arm position to set to while moving
	 */
	public void rotate(double rotationSpeed, double angle, boolean counterClockwise, 
			boolean armPosition) {
		
		//ramp voltage to accelerate smoothly
		drive.rampVoltage();
		ramped = true;
		
		//record the initial angle
		initialAngle = gyro.getAngle();
		
		System.out.println(gyro.getAngle());
		
		//account for moving counterclockwise
		if(counterClockwise) rotationSpeed*= -1;
		
		//while the current angle is less than the desired angle
		while(Math.abs(gyro.getAngle() - initialAngle) < angle - 15 &&
				DriverStation.getInstance().isAutonomous()) {
			
			
			//set the arm to the desired position
			arm.setArmNew(armPosition, armSpeed);
			
			//System.out.println(gyro.getAngle());
			
			//stop ramping voltage once halfway there
			if (ramped && Math.abs(gyro.getAngle() - initialAngle) > 0.5 * angle) {
				drive.rampVoltage(0.0);
				ramped = false;
			}
			
			//drive the motors the proper way
			((SixWheelDrive)drive).tankDrive(-rotationSpeed, rotationSpeed, 1.0, SpeedMode.FAST);
		}
		
		//stop the motors
		((SixWheelDrive)drive).tankDrive(0.0, 0.0, 1.0, SpeedMode.FAST);
		
		//ramp the motors again for the future
		drive.rampVoltage();
		ramped = true;
		
		Timer.delay(0.25);
		
	}
	
	public void rotate(double angle, boolean counterClockwise) {
		rotate(speed, angle, counterClockwise);
	}
	
	/**
	 * The preferred method of rotating for 2018 with default speed
	 * 
	 * @param angle the angle to turn
	 * @param counterClockwise true if we want to turn counterclockwise
	 * @param armPosition the arm position to set to while moving
	 */
	public void rotate(double angle, boolean counterClockwise, boolean armPosition) {
		rotate(speed, angle, counterClockwise, armPosition);
	}
	
	public void vertical(double distance, Arm.Position armPosition, boolean intakeOn) {
		System.out.println("YOU ARE USING THE WRONG VERTICAL METHOD");
	}
	public void verticalReverse(double distance, Arm.Position armPosition, boolean intakeOn) {
		System.out.println("YOU ARE USING THE WRONG VERTICAL METHOD");
	}
	public void rotate(double ange, boolean counterClockwise, Arm.Position armPosition) {
		System.out.println("YOU ARE USING THE WRONG ROTATE METHOD");
	}

}