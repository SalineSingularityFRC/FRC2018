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
	
	boolean safetyDisabled;
	boolean leftLowLimit;
	boolean rightLowLimit;
	
	Arm.Position lastPressed;
	
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
		
		safetyDisabled = false;
		leftLowLimit = false;
		rightLowLimit = false;
		
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
			((SixWheelDrive) sd).tankDrive(stickDrive.getLS_Y(), stickDrive.getRS_Y(), 2.0, speedMode.FAST);
			//sd.drive(stickDrive.getLS_Y(), 0.0, stickDrive.getRS_X(), 2.0, SpeedMode.FAST);
		}
		else {
			((SixWheelDrive) sd).tankDrive(-stickDrive.getRS_Y(), -stickDrive.getLS_Y(), 2.0, speedMode.FAST);
			//sd.drive(-stickDrive.getLS_Y(), 0.0, stickDrive.getRS_Y(), 2.0, SpeedMode.FAST);
		}
		
		
		
		
		
		//sd.drive(reverse * stickDrive.getStickY(), 0, sideMultiplier * stickDrive.getStickX() + rotateMultiplier * stickDrive.getStickZ(), 2.0, speedMode);
	}

	@Override
	public void lift(Lift lift, Timer timer) {
		
		if (!safetyDisabled && stickDrive.getYButton() && stickDrive.getPOVUp()) {
			safetyDisabled = true;
			DriverStation.reportWarning("SAFETY DISABLED", true);
		}
		
		//test to see if safety is on
		if (timer.get() >= 105.0 || safetyDisabled) {
			
			/*
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
			*/
			
			lift.controlLeftLift(-logitechSystems.getRS_Y());
			lift.controlRightLift(-logitechSystems.getLS_Y());
			
			
		}
		
		/*
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
		*/
		
		
		
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
		arm.testEncoderValue(logitechSystems.getTriggerRight()-logitechSystems.getTriggerLeft());
		
		//actual control arm
		if(logitechSystems.getPOVUp()){
			goSwitch = true;
		}
		
		else if(logitechSystems.getPOVDown()) {
			goSwitch = false;
		}
		//to be uncommented after testing encoder:
		//arm.setArmNew(goSwitch, 0.5);
	
		
		
		
		/*
		if (logitechSystems.getAButton())
			arm.setArmForward();
		else if (logitechSystems.getBButton())
			arm.setArmReverse();*/
		
	}

	@Override
	public void intake(Intake intake) {
		
		//intake.controlIntake(logitechSystems.getTriggerLeft() > 0.5, logitechSystems.getLB(), 
				//logitechSystems.getTriggerRight() > 0.5, logitechSystems.getRB());
		
		//intake.printDigitalInput();
				
		intake.booleanIntake(logitechSystems.getLB(), logitechSystems.getRB());
		
	}
	

}
