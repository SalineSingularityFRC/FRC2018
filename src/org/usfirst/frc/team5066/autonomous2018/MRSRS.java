package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;

public class MRSRS extends AutonControlScheme{

	public MRSRS(SingDrive drive, AHRS gyro) {
		super(drive, gyro);
	}

	@Override
	public void moveAuton() {
		super.vertical(35.0);
		super.rotate( 90, false);
		super.vertical(42-super.CenterRobotWidth);
		super.rotate( 90, true);
		super.vertical(105/*delete this-->*/ -28/*<--delet this*/-super.CenterRobotLength);
		//Drop the PC
		super.vertical(32.5-super.CenterRobotLength);
		super.rotate(90, true);
		//Lower PC manipulator
		super.vertical(54-super.CenterRobotLength);
		//Pick up PC
		super.vertical(-(54-super.CenterRobotLength));
		super.rotate(90, false);
		//Drop PC
		
	}
	
	
	
}
