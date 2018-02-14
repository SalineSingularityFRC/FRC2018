package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.controller2018.LogitechController;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;

public class Lift {
	
	
	private VictorSPX right1, left1;
	
	/*
	 * For the nice silver ultrasonics,
	 * plug the main port into analog, which is the port no.
	 * Also, plug the random red wire into the 12V/2A
	 * on the voltage Regulator Module
	 */
	AnalogInput ultraRight, ultraLeft;
	
	private final double RELEASEVOLTAGE = 2.5;
	private final double LIFTVOLTAGE = 2.5;
	
	double normalSpeed, highSpeed;
	
	
	public Lift(int right1, int left1, int ultraRightPort, int ultraLeftPort, double normalSpeed) {
		
		this.right1 = new VictorSPX(right1);
		//this.right2 = new TalonSRX(right2);
		this.left1 = new VictorSPX(left1);
		//this.left2 = new TalonSRX(left2);
		
		this.ultraRight = new AnalogInput(ultraRightPort);
		this.ultraLeft = new AnalogInput(ultraLeftPort);
		
		
		this.normalSpeed = normalSpeed;
		this.highSpeed = 1.0;
		
	}
	
	public boolean releaseLiftRight(boolean rightReleaseNormal, boolean rightReleaseHigh) {
		
		//release lift if button is pressed 
		//and not touching lower limit switch
		if (rightReleaseHigh && ultraRight.getVoltage() > RELEASEVOLTAGE) {
			right1.set(ControlMode.PercentOutput, highSpeed);
			//right2.set(ControlMode.PercentOutput, highSpeed);
		}
		
		else if (rightReleaseNormal && ultraRight.getVoltage() > RELEASEVOLTAGE) {
			right1.set(ControlMode.PercentOutput, normalSpeed);
			//right2.set(ControlMode.PercentOutput, normalSpeed);
		}	
			
		else {
			right1.set(ControlMode.PercentOutput, 0.0);
			//right2.set(ControlMode.PercentOutput, 0.0);
		}
		
		//return right limit switch value
		return ultraRight.getVoltage() < RELEASEVOLTAGE;
		
	}
	
	public boolean releaseLiftLeft(boolean leftReleaseNormal, boolean leftReleaseHigh) {
		
		//release lift if button is pressed 
		//and not touching lower limit switch
		if (leftReleaseHigh && ultraLeft.getVoltage() > RELEASEVOLTAGE) {
			left1.set(ControlMode.PercentOutput, highSpeed);
			//left2.set(ControlMode.PercentOutput, highSpeed);
		}
		
		else if (leftReleaseHigh && ultraLeft.getVoltage() > RELEASEVOLTAGE) {
			left1.set(ControlMode.PercentOutput, normalSpeed);
			//left2.set(ControlMode.PercentOutput, normalSpeed);
		
		}
		else {
			left1.set(ControlMode.PercentOutput, 0.0);
			//left2.set(ControlMode.PercentOutput, 0.0);
		}
		
		//return left limit switch value
		return ultraLeft.getVoltage() < RELEASEVOLTAGE;
		
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
		return ultraRight.getVoltage() < LIFTVOLTAGE;
		
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
		return ultraLeft.getVoltage() < LIFTVOLTAGE;
	}
	
	public void resetLeft(boolean reset){
		
		if (reset) {
			
			left1.set(ControlMode.PercentOutput, -normalSpeed);
			//left2.set(ControlMode.PercentOutput, -normalSpeed);
		}
	}
	public void resetRight(boolean reset){
		if (reset){
			
			right1.set(ControlMode.PercentOutput, -normalSpeed);
			//right2.set(ControlMode.PercentOutput, -normalSpeed);
		}
	}

}
