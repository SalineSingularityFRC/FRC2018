package org.usfirst.frc.team5066.controller2018.controlSchemes;

import org.usfirst.frc.team5066.controller2018.ControlScheme;
import org.usfirst.frc.team5066.controller2018.LogitechController;
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
	
	//XboxController logitechDrive;
	LogitechController stickDrive;
	XboxController logitechSystems;
	SpeedMode speedMode;
	boolean on, prevY;
	
	boolean rBCurrent;
	boolean rBPrevious;
	boolean lBCurrent;
	boolean lBPrevious;
	
	//arm
	boolean armNew;
	boolean previousX, currentX;
	
	
	boolean speed;
	
	boolean safetyDisabled;
	boolean leftLowLimit;
	boolean rightLowLimit;
	
	Arm.Position lastPressed;
	
	final double rotateMultiplier = 0.4;
	
	public TankDrive(int logitechJoyPort, int logitechSystemsPort) {
		//logitechDrive = new XboxController(logitechDrivePort);
		logitechSystems = new XboxController(logitechSystemsPort);
		stickDrive = new LogitechController(logitechJoyPort);
		
		rBCurrent = false;
		rBPrevious = false;
		lBCurrent = false;
		lBPrevious = false;
		
		safetyDisabled = false;
		leftLowLimit = false;
		rightLowLimit = false;
		
		armNew = true;
		previousX = false;
		currentX = false;
		
	}
	
	@Override
	public void drive(SingDrive sd, DrivePneumatics dPneu) {

		if (stickDrive.getStickBackRight())
			speed = true;
		else if (stickDrive.getStickBackLeft())
			speed = false;

		if (speed)
			dPneu.setForward();
		
		else
			dPneu.setReverse();
		
		//((SixWheelDrive) sd).tankDrive(logitechSystems.getLS_Y(), logitechSystems.getRS_Y(), 2.0, speedMode.FAST);
		sd.drive(stickDrive.getStickY(), 0, stickDrive.getStickX() + rotateMultiplier * stickDrive.getStickZ(), 2, speedMode);
	}

	@Override
	public void lift(Lift lift, Timer timer) {
		
		if (!safetyDisabled && stickDrive.getBaseBackLeft() && stickDrive.getBaseBackRight() && stickDrive.getBaseFrontLeft() 
				&& stickDrive.getBaseFrontRight() && stickDrive.getBaseMiddleLeft() && stickDrive.getBaseMiddleRight()) {
			safetyDisabled = true;
			DriverStation.reportError("SAFETY DISABLED", true);
		}
		
		//test to see if safety is on
		if (timer.get() >= 105.0 || safetyDisabled) {
			
			//release left lift until lower limit switch is pressed
			if (!leftLowLimit) {
				if (lift.releaseLiftLeft(logitechSystems.getL3())) {
			
					leftLowLimit = true;
					DriverStation.reportError("left lower limit reached", true);
			
				}
			}
			
			//release right lift until lower limit switch is pressed
			if (!rightLowLimit) {
				if (lift.releaseLiftRight(logitechSystems.getR3())) {
			
				rightLowLimit = true;
				DriverStation.reportError("right lower limit reached", true);
				}
			}
		}
		
		//lifts right lift. When reached upper limit switch, ping driver
		if (rightLowLimit) {
			if (lift.controlRightLift(-logitechSystems.getRS_Y())) {
		
				DriverStation.reportError("right upper limit reached", true);
			}
		}
				
		//lifts left lift. When reached upper limit switch, ping driver
		if (leftLowLimit) {
			if (lift.controlLeftLift(-logitechSystems.getLS_Y())) {
		
			DriverStation.reportError("left upper limit reached", true);
			}
		}
		
		
		//lift.resetLeft(logitechDrive.getBackButton());
		//lift.resetRight(logitechDrive.getStartButton());
		
		
	}

	@Override
	public void arm(Arm arm) {
		/*
		if(logitechSystems.getAButton()){
			lastPressed = Arm.Position.PICKUP;
		}
		else if (logitechSystems.getBButton()) {
			lastPressed = Arm.Position.TRAVEL;
		}
		else if (logitechSystems.getXButton()){
			lastPressed = Arm.Position.EXCHANGE;
		}
		else if (logitechSystems.getYButton()){
			lastPressed = Arm.Position.SWITCH;
		}
		else if (logitechSystems.getStartButton()){
			lastPressed = Arm.Position.START;
		}
		else if (logitechSystems.getBackButton()){
			lastPressed = Arm.Position.PORTAL;
		}
		else if (logitechSystems.getPOVLeft()){
			lastPressed = Arm.Position.LOWSCALE;
		}
		else if (logitechSystems.getPOVUp()){
			lastPressed = Arm.Position.LEVELSCALE;
		}
		else if (logitechSystems.getPOVRight()){
			lastPressed = Arm.Position.HIGHSCALE;
		}
		
		arm.setArm(lastPressed);
		*/
		//man. control arm
		
		/*
		currentX = logitechSystems.getXButton();
		
		if(currentX && !previousX) {
			if (armNew)
				armNew = false;
			else
				armNew = true;
		}
		
		arm.setArmNew(armNew, 0.3);
		
		previousX = currentX;
		*/
		arm.controlArm(logitechSystems.getLS_Y() * 0.8, 2.0);
		
		
		
		/*
		if (logitechSystems.getAButton())
			arm.setArmForward();
		else if (logitechSystems.getBButton())
			arm.setArmReverse();*/
		
	}

	@Override
	public void intake(Intake intake) {
		
		intake.controlIntake(logitechSystems.getTriggerLeft() > 0.5, logitechSystems.getLB(), 
				logitechSystems.getTriggerRight() > 0.5, logitechSystems.getRB());
		
		intake.printDigitalInput();
		
	}
	

}
