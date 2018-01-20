package src.org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.SPI.Port;

public class LLS extends AutonControlScheme{

	public LLS(SingDrive drive, Port gyroPort) {
		super(drive, gyroPort);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void moveAuton() {
		// TODO Auto-generated method stub
		super.vertical(168);
		//lift arm
		super.rotate(90, false);
		//place block
	}

}
