package org.usfirst.frc.team5066.controller2018.controlSchemes;

import org.usfirst.frc.team5066.controller2018.ControlScheme;
import org.usfirst.frc.team5066.controller2018.LogitechController;
import org.usfirst.frc.team5066.controller2018.XboxController;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.DrivePneumatics;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;
import org.usfirst.frc.team5066.singularityDrive.SixWheelDrive;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TankDrive implements ControlScheme {
	
	//XboxController logitechDrive;
	XboxController stickDrive;
	XboxController logitechSystems;
	SpeedMode speedMode;
	boolean on, prevY;
	
	boolean rBCurrent;
	boolean rBPrevious;
	boolean lBCurrent;
	boolean lBPrevious;
	
	//arm
	boolean armNew;
	boolean goSwitch;
	
	
	boolean speed;
	boolean currentTrigger, previousTrigger;
	boolean reverse;
	
	
	
	
	final double rotateMultiplier = 0.5;
	final double sideMultiplier = 0.8;
	
	public TankDrive(int logitechJoyPort, int logitechSystemsPort) {
		//logitechDrive = new XboxController(logitechDrivePort);
		logitechSystems = new XboxController(logitechSystemsPort);
		stickDrive = new XboxController(logitechJoyPort);
		
		rBCurrent = false;
		rBPrevious = false;
		lBCurrent = false;
		lBPrevious = false;
		
		
		armNew = true;
		
		goSwitch = true;
		
		reverse = false;
		currentTrigger = false;
		previousTrigger = false;
		
	}
	
	@Override
	public void drive(SingDrive sd, DrivePneumatics dPneu) {

		if (stickDrive.getRB())
			speed = true;
		else if (stickDrive.getLB())
			speed = false;

		if (speed)
			dPneu.setForward();
		
		else
			dPneu.setReverse();
		
		
		
		currentTrigger = stickDrive.getAButton();
		if (currentTrigger && !previousTrigger) {
			
			reverse = !reverse;
			
		}
		previousTrigger = currentTrigger;
		
		if (!reverse) {
			//((SixWheelDrive) sd).tankDrive(stickDrive.getLS_Y(), stickDrive.getRS_Y(), 2.0, speedMode.FAST);
			sd.drive(stickDrive.getLS_Y(), 0.0, stickDrive.getRS_X(), 2.0, SpeedMode.FAST);
		}
		else {
			//((SixWheelDrive) sd).tankDrive(-stickDrive.getRS_Y(), -stickDrive.getLS_Y(), 2.0, speedMode.FAST);
			sd.drive(-stickDrive.getLS_Y(), 0.0, stickDrive.getRS_X(), 2.0, SpeedMode.FAST);
		}
		
		
		
		
		
		//sd.drive(reverse * stickDrive.getStickY(), 0, sideMultiplier * stickDrive.getStickX() + rotateMultiplier * stickDrive.getStickZ(), 2.0, speedMode);
	}
	
	//FOR DRIVING WITHOUT PNEUMATIC CONTROL ONLY!!!!
	@Override
	public void drive(SingDrive sd){
		if (stickDrive.getRB())
			speed = true;
		else if (stickDrive.getLB())
			speed = false;
		
		currentTrigger = stickDrive.getAButton();
		if (currentTrigger && !previousTrigger) {
			
			reverse = !reverse;
			
		}
		previousTrigger = currentTrigger;
		
		if (!reverse) {
			//((SixWheelDrive) sd).tankDrive(stickDrive.getLS_Y(), stickDrive.getRS_Y(), 2.0, speedMode.FAST);
			sd.drive(stickDrive.getLS_Y(), 0.0, stickDrive.getRS_X(), 2.0, SpeedMode.FAST);
		}
		else {
			//((SixWheelDrive) sd).tankDrive(-stickDrive.getRS_Y(), -stickDrive.getLS_Y(), 2.0, speedMode.FAST);
			sd.drive(-stickDrive.getLS_Y(), 0.0, stickDrive.getRS_X(), 2.0, SpeedMode.FAST);
		}
		
	}

	/*
	@Override
	public void intake(Intake intake) {
		
		//intake.controlIntake(logitechSystems.getTriggerLeft() > 0.5, logitechSystems.getLB(), 
				//logitechSystems.getTriggerRight() > 0.5, logitechSystems.getRB());
		
		//intake.printDigitalInput();
				
		intake.booleanIntake(logitechSystems.getLB(), logitechSystems.getRB());
		
	}
	*/

}
