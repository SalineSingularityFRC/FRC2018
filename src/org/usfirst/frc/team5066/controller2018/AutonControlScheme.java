package org.usfirst.frc.team5066.controller2018;

import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;
import org.usfirst.frc.team5066.singularityDrive.SixWheelDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class AutonControlScheme {

	//TODO change later
	public static final double DistancePerRevolution = 20;
	public static final double encoderTicks = 30720;
	public static final double CenterRobotWidth = 27.5;//TODO change if have bumpers
	public final double CenterRobotLength = 32.5;//TODO change if have bumpers
	public final double CenterRobotCorner = Math.sqrt( Math.pow(CenterRobotWidth,2) + Math.pow(this.CenterRobotLength,2) );
	private static final double speed = 0.2;
	
	
	//PIDController turnController;
	/*TODO Add PID Controller
	static final double kP = 0.03;
	static final double kI = 0.00;
	static final double kD = 0.00;
	static final double kF = 0.00;
	*/
	protected AHRS gyro;
	protected SingDrive drive;
	
	static double initialEncoderPos;
	static double initialAngle;

	
	public AutonControlScheme (SingDrive drive, AHRS gyro) {
		
		this.drive = drive;
		this.gyro = gyro;
		//creates new AHRS gyro object that takes the port located on the roborio
		//gyro = new AHRS(gyroPort);
		//gyro.reset();
	}
	
	public abstract void moveAuton();
	
	//if ur code isn't working for reverse, set vertspeed to negative
	public void vertical(double verticalSpeed, double distance) {
		drive.rampVoltage();
		
		initialEncoderPos = drive.getRightPosition();
		initialAngle = gyro.getAngle();
			
		//normal speed 3072
		while (Math.abs(drive.getRightPosition() - initialEncoderPos) < 
				distance * encoderTicks / DistancePerRevolution) {
			
			SmartDashboard.putString("DB/String 4", ""+drive.getRightPosition());
			System.out.println(drive.getRightPosition());
			
			if (Math.abs(drive.getRightPosition() - initialEncoderPos) > 0.5 * distance * encoderTicks / DistancePerRevolution) {
				drive.rampVoltage(0.0);
			}
			
			//drive.encoderDriveStraight(verticalSpeed);
			((SixWheelDrive)drive).tankDrive(-verticalSpeed + 0.1 * (gyro.getAngle() - initialAngle),
					-verticalSpeed, false, SpeedMode.FAST);
		}
		
		//slow down
		/*while (getAverage() > -2 / DistancePerRevolution
				&& getAverage() < 2 / DistancePerRevolution) {
			drive.drive(vertSpeed / 2, vertSpeed / 2, 0.0, false, SpeedMode.NORMAL);
		}*/
		
		((SixWheelDrive)drive).tankDrive(0.0, 0.0, false, SpeedMode.FAST);
		
		drive.rampVoltage();
	}
	
	public void vertical(double distance) {
		vertical(speed, distance);
	}

	
	
	//private double getAverage() { return Math.abs(drive.getLeftPosition()) + Math.abs(drive.getRightPosition()) / 2; }

	//TODO Figure out AHRS gyro to get this method to work
	public void rotate(double rotationSpeed, double angle, boolean counterClockwise) {
		drive.rampVoltage();
		initialAngle = gyro.getAngle();
		
		System.out.println(gyro.getAngle());
		
		if(counterClockwise) rotationSpeed*= -1;
		while(Math.abs(gyro.getAngle() - initialAngle) < angle) {
			
			//accelerate motors slowly
			//drive.rampVoltage();

			System.out.println(gyro.getAngle());
			
			if (Math.abs(gyro.getAngle() - initialAngle) > 0.5 * angle) {
				drive.rampVoltage(0.0);
			}
			
			drive.drive(0.0, 0.0, -rotationSpeed, false, SpeedMode.FAST);
		}
		
		drive.drive(0.0, 0.0, 0.0, false, SpeedMode.FAST);
		
		drive.rampVoltage();
		
	}
	public void rotate(double angle, boolean counterClockwise) {
		rotate(speed, angle, counterClockwise);
	}

}