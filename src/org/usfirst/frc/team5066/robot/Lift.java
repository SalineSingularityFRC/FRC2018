package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.controller2018.LogitechController;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;

public class Lift {
	
	//TODO:
	//only activate-able in end game
	//notification for driver station
	//do javabat when done (WHO DONE THIS LMFAOOOOO)
	
	
	private VictorSPX right1, left1;
	DigitalInput rightLimLow, rightLimHigh, leftLimLow, leftLimHigh, rightLimStart, leftLimStart;
	double normalSpeed, highSpeed;
	
	
	public Lift(int right1, int left1, int rightLimLow, int rightLimHigh, 
			int leftLimLow, int leftLimHigh, int rightLimStart, int leftLimStart, double normalSpeed) {
		
		this.right1 = new VictorSPX(right1);
		//this.right2 = new TalonSRX(right2);
		this.left1 = new VictorSPX(left1);
		//this.left2 = new TalonSRX(left2);
		
		this.rightLimLow = new DigitalInput(rightLimLow);
		this.rightLimHigh = new DigitalInput(rightLimHigh);
		this.rightLimStart = new DigitalInput(rightLimStart);
		this.leftLimLow = new DigitalInput(leftLimLow);
		this.leftLimHigh = new DigitalInput(leftLimHigh);
		this.leftLimStart = new DigitalInput(leftLimStart);
		
		this.normalSpeed = normalSpeed;
		this.highSpeed = 1.0;
		
	}
	
	public boolean releaseLiftRight(boolean rightReleaseNormal, boolean rightReleaseHigh) {
		
		//release lift if button is pressed 
		//and not touching lower limit switch
		if (rightReleaseHigh && !rightLimLow.get()) {
			right1.set(ControlMode.PercentOutput, highSpeed);
			//right2.set(ControlMode.PercentOutput, highSpeed);
		}
		
		else if (rightReleaseNormal && !rightLimLow.get()) {
			right1.set(ControlMode.PercentOutput, normalSpeed);
			//right2.set(ControlMode.PercentOutput, normalSpeed);
		}	
			
		else {
			right1.set(ControlMode.PercentOutput, 0.0);
			//right2.set(ControlMode.PercentOutput, 0.0);
		}
		
		//return right limit switch value
		return rightLimLow.get();
		
	}
	
	public boolean releaseLiftLeft(boolean leftReleaseNormal, boolean leftReleaseHigh) {
		
		//release lift if button is pressed 
		//and not touching lower limit switch
		if (leftReleaseHigh && !leftLimLow.get()) {
			left1.set(ControlMode.PercentOutput, highSpeed);
			//left2.set(ControlMode.PercentOutput, highSpeed);
		}
		
		else if (leftReleaseHigh && !leftLimLow.get()) {
			left1.set(ControlMode.PercentOutput, normalSpeed);
			//left2.set(ControlMode.PercentOutput, normalSpeed);
		
		}
		else {
			left1.set(ControlMode.PercentOutput, 0.0);
			//left2.set(ControlMode.PercentOutput, 0.0);
		}
		
		//return left limit switch value
		return leftLimLow.get();
		
	}
	
	public boolean controlRightLift (boolean rightLiftUpNormal, boolean rightLiftDownNormal, 
			boolean rightLiftUpHigh, boolean rightLiftDownHigh){
		
		//raise or lower lift with controls
		if (rightLiftUpHigh){
			right1.set(ControlMode.PercentOutput, highSpeed);
			//right2.set(ControlMode.PercentOutput, highSpeed);
		}
		else if (rightLiftDownHigh) {
			right1.set(ControlMode.PercentOutput, -highSpeed);
			//right2.set(ControlMode.PercentOutput, -highSpeed);
		}
		
		if (rightLiftUpNormal){
			right1.set(ControlMode.PercentOutput, normalSpeed);
			//right2.set(ControlMode.PercentOutput, normalSpeed);
		}
		else if (rightLiftDownNormal) {
			right1.set(ControlMode.PercentOutput, -normalSpeed);
			//right2.set(ControlMode.PercentOutput, -normalSpeed);
		}
		else {
			right1.set(ControlMode.PercentOutput, 0.0);
			//right2.set(ControlMode.PercentOutput, 0.0);
		}
		
		//return right limit high switch
		return rightLimHigh.get();
		
	}
	
	public boolean controlLeftLift (boolean leftLiftUpNormal, boolean leftLiftDownNormal,
			boolean leftLiftUpHigh, boolean leftLiftDownHigh) {
		
		//raise or lower lift with controls
		if (leftLiftUpHigh){
			right1.set(ControlMode.PercentOutput, highSpeed);
			//right2.set(ControlMode.PercentOutput, highSpeed);
		}
		else if (leftLiftDownHigh) {
			right1.set(ControlMode.PercentOutput, -highSpeed);
			//right2.set(ControlMode.PercentOutput, -highSpeed);
		}
		
		if (leftLiftUpNormal){
			left1.set(ControlMode.PercentOutput, normalSpeed);
			//left2.set(ControlMode.PercentOutput, normalSpeed);
		}
		else if (leftLiftDownNormal) {
			left1.set(ControlMode.PercentOutput, -normalSpeed);
			//left2.set(ControlMode.PercentOutput, -normalSpeed);
		}
		else {
			left1.set(ControlMode.PercentOutput, 0.0);
			//left2.set(ControlMode.PercentOutput, 0.0);
		}
		
		//return left limit high switch
		return leftLimHigh.get();
	}
	
	public void resetLeft(){
		if (!leftLimStart.get()){
			left1.set(ControlMode.PercentOutput, -normalSpeed);
			//left2.set(ControlMode.PercentOutput, -normalSpeed);
		}
	}
	public void resetRight(){
		if (!rightLimStart.get()){
			right1.set(ControlMode.PercentOutput, -normalSpeed);
			//right2.set(ControlMode.PercentOutput, -normalSpeed);
		}
	}

}
