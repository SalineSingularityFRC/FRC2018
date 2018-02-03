package org.usfirst.frc.team5066.controller2018.controlSchemes;

import org.usfirst.frc.team5066.controller2018.ControlScheme;
import org.usfirst.frc.team5066.controller2018.XboxController;
import org.usfirst.frc.team5066.library.SingularityDrive;
import org.usfirst.frc.team5066.library.SpeedMode;
import org.usfirst.frc.team5066.robot.Arm;
import org.usfirst.frc.team5066.robot.DrivePneumatics;
import org.usfirst.frc.team5066.robot.Intake;
import org.usfirst.frc.team5066.robot.Lift;
import org.usfirst.frc.team5066.singularityDrive.SingDrive;
import org.usfirst.frc.team5066.singularityDrive.SixWheelDrive;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TankDrive implements ControlScheme {
	
	XboxController logitechDrive;
	XboxController logitechSystems;
	SpeedMode speedMode;
	boolean on, prevY;
	
	boolean rBCurrent;
	boolean rBPrevious;
	boolean lBCurrent;
	boolean lBPrevious;
	
	boolean speed;
	
	boolean safetyDisabled;
	boolean leftLowLimit;
	boolean rightLowLimit;
	
	public enum ArmPosition {
		SWITCH,
		PICKUP,
		TRAVEL,
		EXCHANGE,
		START,
		PORTAL,
		LOWSCALE,
		LEVELSCALE,
		HIGHSCALE,
	}
	
	ArmPosition lastPressed;
	
	public TankDrive(int logitechDrivePort, int logitechSystemsPort) {
		logitechDrive = new XboxController(logitechDrivePort);
		logitechSystems = new XboxController(logitechSystemsPort);
		
		rBCurrent = false;
		rBPrevious = false;
		lBCurrent = false;
		lBPrevious = false;
		
		safetyDisabled = false;
		leftLowLimit = false;
		rightLowLimit = false;
		
		lastPressed = ArmPosition.START;
		lastPressed = ArmPosition.SWITCH;
		lastPressed = ArmPosition.PICKUP;
		lastPressed = ArmPosition.TRAVEL;
		lastPressed = ArmPosition.EXCHANGE;
		lastPressed = ArmPosition.PORTAL;
		lastPressed = ArmPosition.LOWSCALE;
		lastPressed = ArmPosition.LEVELSCALE;
		lastPressed = ArmPosition.HIGHSCALE;
		
	}
	
	@Override
	public void drive(SingDrive sd, DrivePneumatics dPneu) {
		
		/*
		//set speedMode
		if(logitechDrive.getLB()) {
			speedMode = SpeedMode.SLOW;
		} else if(logitechDrive.getRB()) {
			speedMode = SpeedMode.FAST;
		} else {
			speedMode = SpeedMode.NORMAL;
		}*/
		

		if (logitechDrive.getRB())
			speed = true;
		else if (logitechDrive.getLB())
			speed = false;

		if (speed)
			dPneu.setForward();
		
		else
			dPneu.setReverse();
		
		((SixWheelDrive) sd).tankDrive(logitechDrive.getLS_Y(), logitechDrive.getRS_Y(), true, speedMode.NORMAL);

		
	}

	@Override
	public void lift(Lift lift, Timer timer) {
		
		if (!safetyDisabled && logitechDrive.getPOVDown() && logitechDrive.getAButton() && logitechDrive.getLB() 
				&& logitechDrive.getRB() && (logitechDrive.getTriggerLeft() > 0.1) && (logitechDrive.getTriggerRight() > 0.1)) {
			safetyDisabled = true;
			DriverStation.reportError("SAFETY DISABLED", true);
		}
		
		//test to see if safety is on
		if (timer.get() >= 105.0 || safetyDisabled) {
			
			//release left lift until lower limit switch is pressed
			if (!leftLowLimit && lift.releaseLiftLeft(logitechDrive.getPOVDown(), logitechDrive.getPOVLeft())) {
				leftLowLimit = true;
				DriverStation.reportError("left lower limit reached", true);
			}
			
			//release right lift until lower limit switch is pressed
			if (!rightLowLimit && lift.releaseLiftRight(logitechSystems.getAButton(), logitechSystems.getBButton())) {
				rightLowLimit = true;
				DriverStation.reportError("right lower limit reached", true);
			}
		}
		//lifts right lift. When reached upper limit switch, ping driver
		if (rightLowLimit && lift.controlRightLift(logitechSystems.getYButton(), logitechSystems.getAButton(),
				logitechSystems.getBButton(), logitechSystems.getXButton())) {
			DriverStation.reportError("right upper limit reached", true);
		}
				
		//lifts left lift. When reached upper limit switch, ping driver
		if (leftLowLimit && lift.controlLeftLift(logitechSystems.getPOVUp(), logitechSystems.getPOVDown(),
				logitechSystems.getPOVRight(), logitechSystems.getPOVLeft())) {
			DriverStation.reportError("left upper limit reached", true);
		}
		
		if (logitechSystems.getBackButton()) {
			lift.resetLeft();
			lift.resetRight();
			
		}
	
		
	}

	@Override
	public void arm(Arm arm) {
		
		if(logitechSystems.getAButton()){
			lastPressed = ArmPosition.PICKUP;
		}
		else if (logitechSystems.getBButton()) {
			lastPressed = ArmPosition.TRAVEL;
		}
		else if (logitechSystems.getXButton()){
			lastPressed = ArmPosition.EXCHANGE;
		}
		else if (logitechSystems.getYButton()){
			lastPressed = ArmPosition.SWITCH;
		}
		else if (logitechSystems.getStartButton()){
			lastPressed = ArmPosition.START;
		}
		else if (logitechSystems.getBackButton()){
			lastPressed = ArmPosition.PORTAL;
		}
		else if (logitechSystems.getPOVLeft()){
			lastPressed = ArmPosition.LOWSCALE;
		}
		else if (logitechSystems.getPOVUp()){
			lastPressed = ArmPosition.LEVELSCALE;
		}
		else if (logitechSystems.getPOVRight()){
			lastPressed = ArmPosition.HIGHSCALE;
		}
			
		
		if (lastPressed == ArmPosition.PICKUP) {
			arm.setPositionPickup();
		}
		else if (lastPressed == ArmPosition.TRAVEL) {
			arm.setPositionTravel();
		}
		else if (lastPressed == ArmPosition.EXCHANGE) {
			arm.setPositionExchange();
		}
		else if (lastPressed == ArmPosition.SWITCH) {
			arm.setPositionSwitch();
		}
		else if (lastPressed == ArmPosition.PORTAL) {
			arm.setPositionPortal();
		}
		else if (lastPressed == ArmPosition.LOWSCALE) {
			arm.setPositionLowScale();
		}
		else if (lastPressed == ArmPosition.LEVELSCALE) {
			arm.setPositionLevelScale();
		}
		else if (lastPressed == ArmPosition.HIGHSCALE) {
			arm.setPositionHighScale();
		}
		
	}

	@Override
	public void intake(Intake intake) {
		
		
	}
	

}
