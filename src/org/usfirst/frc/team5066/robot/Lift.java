package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.controller2018.LogitechController;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;

public class Lift {
	
	
	private TalonSRX right1, right2, left1, left2;
	DigitalInput rightLimLow, rightLimHigh, leftLimLow, leftLimHigh;
	double speed;
	
	
	public Lift(int r1, int r2, int l1, int l2, int rL1, int rL2, int lL1, int  lL2, double s) {
		
		right1 = new TalonSRX(r1);
		right2 = new TalonSRX(r2);
		left1 = new TalonSRX(l1);
		left2 = new TalonSRX(l2);
		
		rightLimLow = new DigitalInput(rL1);
		rightLimHigh = new DigitalInput(rL2);
		leftLimLow = new DigitalInput(lL1);
		leftLimHigh = new DigitalInput(lL2);
		
		speed = s;
		
		
	}
	
	public boolean releaseLiftRight(boolean rightRelease) {
		
		//release lift if button is pressed 
		//and not touching lower limit switch
		if (rightRelease && !rightLimLow.get()) {
			right1.set(ControlMode.PercentOutput, speed);
			right2.set(ControlMode.PercentOutput, speed);
		}
		
		else {
			right1.set(ControlMode.PercentOutput, 0.0);
			right2.set(ControlMode.PercentOutput, 0.0);
		}
		
		//return right limit switch value
		return rightLimLow.get();
		
	}
	
	public boolean releaseLiftLeft(boolean leftRelease) {
		
		//release lift if button is pressed 
		//and not touching lower limit switch
		if (leftRelease && !leftLimLow.get()) {
			left1.set(ControlMode.PercentOutput, speed);
			left2.set(ControlMode.PercentOutput, speed);
		}
		else {
			left1.set(ControlMode.PercentOutput, 0.0);
			left2.set(ControlMode.PercentOutput, 0.0);
		}
		
		//return left limit switch value
		return leftLimLow.get();
		
	}
	
	public boolean controlRightLift (boolean rightLiftUp, boolean rightLiftDown){
		
		//raise or lower lift with controls
		if (rightLiftUp){
			right1.set(ControlMode.PercentOutput, speed);
			right2.set(ControlMode.PercentOutput, speed);
		}
		else if (rightLiftDown) {
			right1.set(ControlMode.PercentOutput, -speed);
			right2.set(ControlMode.PercentOutput, -speed);
		}
		else {
			right1.set(ControlMode.PercentOutput, 0.0);
			right2.set(ControlMode.PercentOutput, 0.0);
		}
		
		//return right limit high switch
		return rightLimHigh.get();
		
	}
	
	public boolean controlLeftLift (boolean leftLiftUp, boolean leftLiftDown) {
		
		//raise or lower lift with controls
		if (leftLiftUp){
			left1.set(ControlMode.PercentOutput, speed);
			left2.set(ControlMode.PercentOutput, speed);
		}
		else if (leftLiftDown) {
			left1.set(ControlMode.PercentOutput, -speed);
			left2.set(ControlMode.PercentOutput, -speed);
		}
		else {
			left1.set(ControlMode.PercentOutput, 0.0);
			left2.set(ControlMode.PercentOutput, 0.0);
		}
		
		//return left limit high switch
		return leftLimHigh.get();
	}

}
