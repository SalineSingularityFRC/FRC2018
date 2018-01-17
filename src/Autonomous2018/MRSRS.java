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
		super.vertical(35.0);
		super.rotate( 0.5, 90, false);
		//super.vertical(42-super.);
		
	}
	
	
	
}
