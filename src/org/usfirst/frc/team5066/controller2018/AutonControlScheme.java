package org.usfirst.frc.team5066.controller2018;

import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;
import org.usfirst.frc.team5066.singularityDrive.SixWheelDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class AutonControlScheme {

	//TODO change later
	public static final double DistancePerRevolution = 20;
	public static final double encoderTicks = 30720;
	public static final double CenterRobotWidth = 27.5;//TODO change if have bumpers
	public final double CenterRobotLength = 32.5;//TODO change if have bumpers
	public final double CenterRobotCorner = Math.sqrt( Math.pow(CenterRobotWidth,2) + Math.pow(this.CenterRobotLength,2) );
	private static final double speed = 0.5;
	
	
	//PIDController turnController;
	/*TODO Add PID Controller
	static final double kP = 0.03;
	static final double kI = 0.00;
	static final double kD = 0.00;
	static final double kF = 0.00;
	*/
	protected static ADXRS450_Gyro gyro;
	protected static SingDrive drive;

	
	public AutonControlScheme (SingDrive drive, ADXRS450_Gyro gyro) {
		
		this.drive = drive;
		this.gyro = gyro;
		//creates new AHRS gyro object that takes the port located on the roborio
		//gyro = new AHRS(gyroPort);
		//gyro.reset();
	}
	
	public abstract void moveAuton();
	
	//if ur code isn't working for reverse, set vertspeed to negative
	public static void vertical(double verticalSpeed, double distance) {
		drive.rampVoltage();
		drive.resetAll();
			
		//normal speed 3072
		while ( drive.getRightPosition() < distance*encoderTicks / DistancePerRevolution) {
			SmartDashboard.putString("DB/String 4", ""+drive.getRightPosition());
			System.out.println(drive.getRightPosition());
			
			//drive.encoderDriveStraight(verticalSpeed);
			((SixWheelDrive)drive).tankDrive(-verticalSpeed, -verticalSpeed, false, SpeedMode.NORMAL);
		}
		
		//slow down
		/*while (getAverage() > -2 / DistancePerRevolution
				&& getAverage() < 2 / DistancePerRevolution) {
			drive.drive(vertSpeed / 2, vertSpeed / 2, 0.0, false, SpeedMode.NORMAL);
		}*/
		
		((SixWheelDrive)drive).tankDrive(0.0, 0.0, false, SpeedMode.NORMAL);
	}
	
	public static void vertical(double distance) {
		vertical(speed, distance);
	}

	private static double getAverage() { return Math.abs(drive.getLeftPosition()) + Math.abs(drive.getRightPosition()) / 2; }

	//TODO Figure out AHRS gyro to get this method to work
	public static void rotate(double rotationSpeed, double angle, boolean counterClockwise) {
		drive.rampVoltage();
		gyro.reset();
		
		System.out.println(gyro.getAngle());
		
		if(counterClockwise) rotationSpeed*= -1;
		while(Math.abs(gyro.getAngle()) < angle) {
			
			//accelerate motors slowly
			//drive.rampVoltage();

			System.out.println(gyro.getAngle());
			
			drive.drive(0.0, 0.0, -rotationSpeed, false, SpeedMode.FAST);
		}
		
		drive.drive(0.0, 0.0, 0.0, false, SpeedMode.FAST);
	}
	public static void rotate(double angle, boolean counterClockwise) {
		rotate(speed, angle, counterClockwise);
	}

}