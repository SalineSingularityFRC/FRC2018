package org.usfirst.frc.team5066.autonomous2018;

import org.usfirst.frc.team5066.controller2018.AutonControlScheme;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;
import edu.wpi.first.wpilibj.SPI.Port;



public class RLS extends AutonControlScheme{
	
	public RLS(SingDrive drive) {
		super(drive);
	}
	
	@Override
	public void moveAuton() {
		super.vertical(35);
		super.rotate(90, true);
		super.vertical(221.06-super.CenterRobotWidth+super.CenterRobotLength);
		super.rotate(90, false);
		super.vertical(133-super.CenterRobotLength);
		super.rotate(90, false);
		//raise PC
		super.vertical(12);
		//Drop PC
	}
}
