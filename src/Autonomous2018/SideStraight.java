package Autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.SPI.Port;


public class SideStraight extends AutonControlScheme {

	public SideStraight(SingDrive drive, Port gyroPort) {
		super(drive, gyroPort);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void MoveAuton() {
		super.vertical(0.5, 60);
		// TODO Auto-generated method stub
		
	}

}
