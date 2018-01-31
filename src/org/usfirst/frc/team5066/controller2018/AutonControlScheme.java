package org.usfirst.frc.team5066.controller2018;

import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI.Port;

public abstract class AutonControlScheme {

	//TODO change later
	public static final double DistancePerRevolution = 6.2831853072;
	public static final double CenterRobotWidth = 27.5;//TODO change if have bumpers
	public final double CenterRobotLength = 32.5;//TODO change if have bumpers
	public final double CenterRobotCorner = Math.sqrt( Math.pow(CenterRobotWidth,2) + Math.pow(this.CenterRobotLength,2) );
	private static final double speed = 0.5;
	
	//gyro
	protected static AHRS gyro;
	
	//PIDController turnController;
	/*TODO Add PID Controller
	static final double kP = 0.03;
	static final double kI = 0.00;
	static final double kD = 0.00;
	static final double kF = 0.00;
	*/
	
	protected static SingDrive drive;

	
	public AutonControlScheme (SingDrive drive) {
		
		this.drive = drive;
		
		//creates new AHRS gyro object that takes the port located on the roborio
		//gyro = new AHRS(gyroPort);
		//gyro.reset();
	}
	
	public abstract void moveAuton();
	
	//if ur code isn't working for reverse, set vertspeed to negative
	public static void vertical(double verticalSpeed, double distance) {
	
			
		//normal speed
		while (getAverage() > -distance / DistancePerRevolution
				&& getAverage() < distance / DistancePerRevolution) {
			
			//Accelerate motors slowly
			drive.rampVoltage();
			
			//drive.encoderDriveStraight(verticalSpeed);
			drive.drive(verticalSpeed, verticalSpeed, 0.0, false, SpeedMode.NORMAL);
		}
		
		drive.resetAll();
		
		//slow down
		/*while (getAverage() > -2 / DistancePerRevolution
				&& getAverage() < 2 / DistancePerRevolution) {
			drive.drive(vertSpeed / 2, vertSpeed / 2, 0.0, false, SpeedMode.NORMAL);
		}*/
		
		drive.resetAll();
		drive.drive(0.0, 0.0, 0.0, false, SpeedMode.NORMAL);
	}
	
	public static void vertical(double distance) {
		vertical(speed, distance);
	}

	private static double getAverage() { return (drive.getLeftPosition() + drive.getRightPosition()) / 2; }

	//TODO Figure out AHRS gyro to get this method to work
	public static void rotate(double rotationSpeed, double angle, boolean counterClockwise) {
		
		gyro.reset();
		if(counterClockwise) rotationSpeed*= -1;
		
		while(gyro.getAngle() < angle) {
			
			//accelerate motors slowly
			drive.rampVoltage();
			
			drive.drive(0.0, 0.0, rotationSpeed, false, SpeedMode.NORMAL);
		}
		
		drive.drive(0.0, 0.0, 0.0, false, SpeedMode.NORMAL);
	}
	
	public static void rotate( double angle, boolean counterClockwise) {
		rotate(speed, angle, counterClockwise);
	}
	
}