package org.usfirst.frc.team5066.controller2018.controlSchemes;




import org.usfirst.frc.team5066.controller2018.ControlScheme;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.DrivePneumatics;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TankHDrive implements ControlScheme {
	
	XboxController xbox;
	SpeedMode speedMode;
	boolean on, prevY;
	
	boolean rBCurrent;
	boolean rBPrevious;
	boolean lBCurrent;
	boolean lBPrevious;
	
	public TankHDrive(int xboxPort, int logitechPort) {
		xbox = new XboxController(xboxPort);
		
		rBCurrent = false;
		rBPrevious = false;
		lBCurrent = false;
		lBPrevious = false;
		
	}
	
	@Override
	public void drive(SingDrive sd, DrivePneumatics dPneu) {
		
		rBCurrent = xbox.getRB()
		//set speedMode
		if(xbox.getLB()) {
			speedMode = SpeedMode.SLOW;
		} else if(xbox.getRB()) {
			speedMode = SpeedMode.FAST;
		} else {
			speedMode = SpeedMode.NORMAL;
		}
		
		sd.hDriveTank(xbox.getLS_Y(), xbox.getRS_Y(), horizontalMax(xbox.getLS_X(), xbox.getRS_X()), squaredInputs, speedMode);
		
	}
	private double horizontalMax(double lS, double rS) {
		
		if (lS < 0 && rS < 0) 
			return Math.min(lS, rS);
		return Math.max(lS,  rS);
		
	}
	

}
