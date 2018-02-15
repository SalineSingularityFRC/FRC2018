package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.controller2018.LogitechController;
import org.usfirst.frc.team5066.library.RangeFinder;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Ultrasonic;

public class Lift {
	
	
	private VictorSPX right1, left1;
	
	/*
	 * For the nice silver ultrasonics,
	 * plug the main port into analog, which is the port no.
	 * Also, plug the random red wire into the 12V/2A
	 * on the voltage Regulator Module
	 */
	Ultrasonic ultraRight, ultraLeft;
	
	private final double RELEASEDISTANCE = 20.0;
	private final double FINISHDISTANCE = 5.0;
	
	double normalSpeed, highSpeed;
	
	
	public Lift(int right1, int left1, int ultraRightInput, int ultraRightOutput,
			int ultraLeftInput, int ultraLeftOutput, double normalSpeed) {
		
		this.right1 = new VictorSPX(right1);
		//this.right2 = new TalonSRX(right2);
		this.left1 = new VictorSPX(left1);
		//this.left2 = new TalonSRX(left2);
		
		this.ultraRight = new Ultrasonic(ultraRightInput, ultraRightOutput);
		this.ultraLeft = new Ultrasonic(ultraLeftInput, ultraLeftOutput);
		
		
		this.normalSpeed = normalSpeed;
		this.highSpeed = 1.0;
		
	}
	
	public boolean releaseLiftRight(boolean rightReleaseNormal, boolean rightReleaseHigh) {
		
		//release lift if button is pressed 
		//and not touching lower limit switch
		if (rightReleaseHigh && ultraRight.getRangeInches() > RELEASEDISTANCE) {
			right1.set(ControlMode.PercentOutput, highSpeed);
			//right2.set(ControlMode.PercentOutput, highSpeed);
		}
		
		else if (rightReleaseNormal && ultraRight.getRangeInches() > RELEASEDISTANCE) {
			right1.set(ControlMode.PercentOutput, normalSpeed);
			//right2.set(ControlMode.PercentOutput, normalSpeed);
		}	
			
		else {
			right1.set(ControlMode.PercentOutput, 0.0);
			//right2.set(ControlMode.PercentOutput, 0.0);
		}
		
		//return right limit switch value
		return ultraRight.getRangeInches() < RELEASEDISTANCE;
		
	}
	
	public boolean releaseLiftLeft(boolean leftReleaseNormal, boolean leftReleaseHigh) {
		
		//release lift if button is pressed 
		//and not touching lower limit switch
		if (leftReleaseHigh && ultraLeft.getRangeInches() > RELEASEDISTANCE) {
			left1.set(ControlMode.PercentOutput, highSpeed);
			//left2.set(ControlMode.PercentOutput, highSpeed);
		}
		
		else if (leftReleaseHigh && ultraLeft.getRangeInches() > RELEASEDISTANCE) {
			left1.set(ControlMode.PercentOutput, normalSpeed);
			//left2.set(ControlMode.PercentOutput, normalSpeed);
		
		}
		else {
			left1.set(ControlMode.PercentOutput, 0.0);
			//left2.set(ControlMode.PercentOutput, 0.0);
		}
		
		//return left limit switch value
		return ultraLeft.getRangeInches() < RELEASEDISTANCE;
		
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
		return ultraRight.getRangeInches() < FINISHDISTANCE;
		
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
		return ultraLeft.getRangeInches() < FINISHDISTANCE;
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
