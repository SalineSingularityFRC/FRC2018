package org.usfirst.frc.team5066.controller2018.controlSchemes;

import org.usfirst.frc.team5066.controller2018.ControlScheme;
import org.usfirst.frc.team5066.controller2018.LogitechController;
import org.usfirst.frc.team5066.controller2018.XboxController;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.DrivePneumatics;
import org.usfirst.frc.team5066.robot.Lift;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;
import org.usfirst.frc.team5066.singularityDrive.SixWheelDrive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TankDrive implements ControlScheme {
	
	XboxController xboxDrive;
	XboxController xboxSystems;
	SpeedMode speedMode;
	boolean on, prevY;
	
	boolean rBCurrent;
	boolean rBPrevious;
	boolean lBCurrent;
	boolean lBPrevious;
	
	boolean speed;
	
	public TankDrive(int xboxDrivePort, int xboxSystemsPort) {
		xboxDrive = new XboxController(xboxDrivePort);
		xboxSystems = new XboxController(xboxSystemsPort);
		
		rBCurrent = false;
		rBPrevious = false;
		lBCurrent = false;
		lBPrevious = false;
		
	}
	
	@Override
	public void drive(SingDrive sd, DrivePneumatics dPneu) {
		
		/*rBCurrent = xboxDrive.getRB();
		//set speedMode
		if(xboxDrive.getLB()) {
			speedMode = SpeedMode.SLOW;
		} else if(xboxDrive.getRB()) {
			speedMode = SpeedMode.FAST;
		} else {
			speedMode = SpeedMode.NORMAL;
		}*/
		
		if (xboxDrive.getRB())
			speed = true;
		else if (xboxDrive.getLB())
			speed = false;

		if (speed)
			dPneu.setForward();
		else
			dPneu.setReverse();
		
		((SixWheelDrive) sd).tankDrive(xboxDrive.getLS_Y(), xboxDrive.getRS_Y(), true, speedMode);
		
	}

	@Override
	public void lift(Lift lift, Timer timer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void arm(Arm arm) {
		arm.controlArm(xboxSystems.getLS_Y(), 2.0);
		if(xboxSystems.getRB()) {
			arm.setArmForward();
		}
		
		else if(xboxSystems.getLB()) {
			arm.setArmReverse();
		}
	}
	

}
