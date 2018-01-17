package Autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.SPI.Port;

public class LLSLS extends AutonControlScheme{

	public LLSLS(SingDrive drive, Port gyroPort) {
		super(drive, gyroPort);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void MoveAuton() {
		super.vertical(168);
		//raise arm
		super.rotate(90, false);
		//place block
		super.rotate(90, false);
		super.vertical(30);//don't know exactly
		super.rotate(90,true);
		super.vertical(20);//don't know exactly
		//pick up block
		super.vertical(20, -0.5);
		super.rotate(90, true);
		super.vertical(30);
		//lift arm
		super.rotate(90, false);
		//place block
	}

}
