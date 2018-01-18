package Autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.SPI.Port;

public class LLSV extends AutonControlScheme{

	public LLSV(SingDrive drive) {
		super(drive);
	}

	@Override
	public void moveAuton() {
		super.vertical(168-super.CenterRobotLength);
		//raise arm
		super.rotate(90, false);
		//Drop PC
		super.rotate(90, false);
		super.rotate(90, false);
		//lower arm
		super.vertical(30-super.CenterRobotLength);//don't know exactly
		super.rotate(90,true);
		super.vertical(20);//don't know exactly
		//pick up block
		super.rotate(90,false);
		super.vertical(98-super.CenterRobotLength);
	}

}
