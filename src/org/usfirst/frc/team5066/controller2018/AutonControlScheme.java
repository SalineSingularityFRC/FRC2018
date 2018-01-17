package org.usfirst.frc.team5066.controller2018;

import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI.Port;

public abstract class AutonControlScheme {

	//Todo change later
	public static final double DistancePerRevolution = 12.56;
	public static final double CenterRobotWidth = 15.0;
	public static final double CenterRobotLength = 13.0;
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

	
	public AutonControlScheme (SingDrive drive, Port gyroPort) {
		
		this.drive = drive;
		
		//creates new AHRS gyro object that takes the port located on the roborio
		gyro = new AHRS(gyroPort);
		gyro.reset();
	}
	
	public abstract void MoveAuton();
	
	//if ur code isn't working for reverse, set vertspeed to negative
	public static void vertical(double verticalSpeed, double distance) {
	
		//int distanceAccelerated = 0;
		
		//Slowly start motors for i(acceleration) inches
		/*for(int i = acceleration; i > 0; i--) {
			if (2 * (acceleration - i + 1) > distance - 2) break;
			do {
				drive.drive(vertSpeed/i, vertSpeed/i, 0.0, false, SpeedMode.NORMAL);
			} 
			while (getAverage() > -2.0 / DistancePerRevolution
				&& getAverage() < 2.0 / DistancePerRevolution);
			
			distanceAccelerated += 2;
			
			drive.resetAll();
		}*/
			
		//normal speed
		while (getAverage() > -distance / DistancePerRevolution
				&& getAverage() < distance / DistancePerRevolution) {
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
		
		while (getAverage() > -distance / DistancePerRevolution
				&& getAverage() < distance / DistancePerRevolution) {
			drive.drive(speed, speed, 0.0, false, SpeedMode.NORMAL);
		}
		
		drive.resetAll();
		
		drive.resetAll();
		drive.drive(0.0, 0.0, 0.0, false, SpeedMode.NORMAL);
		
	}

	private static double getAverage() { return (drive.getLeftPosition() + drive.getRightPosition()) / 2; }

	//TODO Figure out AHRS gyro to get this method to work
	public static void rotate(double rotationSpeed, double angle, boolean counterClockwise) {
		
		gyro.reset();
		if(counterClockwise) rotationSpeed*= -1;
		
		while(gyro.getAngle() < angle) {
			drive.drive(0.0, 0.0, rotationSpeed, false, SpeedMode.NORMAL);
		}
		
		drive.drive(0.0, 0.0, 0.0, false, SpeedMode.NORMAL);
	}
	
}
