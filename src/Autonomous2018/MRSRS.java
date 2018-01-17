package Autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.SPI.Port;

public class MRSRS extends AutonControlScheme{

	public MRSRS(SingDrive drive, Port gyroPort) {
		super(drive, gyroPort);
	}

	@Override
	public void MoveAuton() {
		//drive.vertical();
		
	}
	
	
	
}
