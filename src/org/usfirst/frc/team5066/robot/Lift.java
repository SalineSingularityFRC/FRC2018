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
	
	private final double CLOSEENOUGHSPEED = 0.6;
	
	double normalSpeed, highSpeed;
	double ctrl;
	
	DigitalInput rightRelease, leftRelease, rightFinished, leftFinished;
	
	
	public Lift(int right1, int left1, double normalSpeed,
			int ultraRightInput, int ultraRightOutput, int ultraLeftInput, int ultraLeftOutput,
			int rightReleasePort, int leftReleasePort, int rightFinishedPort, int leftFinishedPort) {
		
		this.right1 = new VictorSPX(right1);
		//this.right2 = new TalonSRX(right2);
		this.left1 = new VictorSPX(left1);
		//this.left2 = new TalonSRX(left2);
		
		//this.ultraRight = new Ultrasonic(ultraRightInput, ultraRightOutput);
		//ultraRight.setAutomaticMode(true);
		//this.ultraLeft = new Ultrasonic(ultraLeftInput, ultraLeftOutput);
		//ultraLeft.setAutomaticMode(true);
		/*
		this.rightRelease = new DigitalInput(rightReleasePort);
		this.leftRelease = new DigitalInput(leftReleasePort);
		this.rightFinished = new DigitalInput(rightFinishedPort);
		this.leftFinished = new DigitalInput(leftFinishedPort);
		*/
		this.normalSpeed = normalSpeed;
		this.highSpeed = 1.0;
		
	}
	
	public boolean releaseLiftRight(boolean rightReleaseNormal) {
		
		//release lift if button is pressed 
		//and not touching lower limit switch
		if (rightReleaseNormal && ultraRight.getRangeInches() > RELEASEDISTANCE) {
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
	
	public boolean releaseLiftRightLimitSwitch(boolean rightReleaseNormal) {
		
		// release lift if button is pressed
		// and not touching lower limit switch
		if (!rightRelease.get()) {
			if (rightReleaseNormal)
				right1.set(ControlMode.PercentOutput, normalSpeed);
			else
				right1.set(ControlMode.PercentOutput, 0.0);

			return false;

		}
		right1.set(ControlMode.PercentOutput, 0.0);

		// return right limit switch value
		return true;

	}
	
	public boolean releaseLiftLeft(boolean leftReleaseNormal) {
		
		//release lift if button is pressed 
		//and not touching lower limit switch
		if (leftReleaseNormal && ultraLeft.getRangeInches() > RELEASEDISTANCE) {
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
	
	public boolean releaseLiftLeftLimitSwitch(boolean leftReleaseNormal) {
		
		// release lift if button is pressed
		// and not touching lower limit switch
		if (!leftRelease.get()) {
			if (leftReleaseNormal)
				left1.set(ControlMode.PercentOutput, normalSpeed);
			else
				left1.set(ControlMode.PercentOutput, 0.0);
			
			return false;

		}
		left1.set(ControlMode.PercentOutput, 0.0);
		
		// return left limit switch value
		return true;
	}
	
	public boolean controlRightLift (double control){
		
		ctrl = control;
		/*
		if (ultraRight.getRangeInches() < FINISHDISTANCE && ctrl > CLOSEENOUGHSPEED) {
			ctrl = CLOSEENOUGHSPEED;
		}
		
		if (ctrl < 0) {
			ctrl /= 4;
		}
		*/
		right1.set(ControlMode.PercentOutput,  Math.pow(ctrl, 3.0));
		
		/*
		//raise or lower lift with controls
		if (rightLiftUpHigh && ultraRight.getRangeInches() > FINISHDISTANCE){
			right1.set(ControlMode.PercentOutput, highSpeed);
			//right2.set(ControlMode.PercentOutput, highSpeed);
		}
		else if (rightLiftDownHigh) {
			right1.set(ControlMode.PercentOutput, -highSpeed);
			//right2.set(ControlMode.PercentOutput, -highSpeed);
		}
		
		else if (rightLiftUpNormal){
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
		*/
		//return right limit high switch
		return false;
		//return ultraRight.getRangeInches() < FINISHDISTANCE;
		
	}
	
	public boolean controlRightLiftLimitSwitch(double control) {
		
		if (!rightFinished.get()) {
			right1.set(ControlMode.PercentOutput, control);
			return false;
		}
		
		right1.set(ControlMode.PercentOutput, 0.0);
		return true;
		
	}
	
	public boolean controlLeftLift (double control) {
		
		ctrl = control;
		/*
		if (ultraRight.getRangeInches() < FINISHDISTANCE && ctrl > CLOSEENOUGHSPEED) {
			ctrl = CLOSEENOUGHSPEED;
		}
		
		if (ctrl < 0) {
			ctrl /= 4;
		}
		*/
		left1.set(ControlMode.PercentOutput,  Math.pow(ctrl, 3.0));
		
		/*
		//raise or lower lift with controls
		if (leftLiftUpHigh && ultraRight.getRangeInches() > FINISHDISTANCE){
			right1.set(ControlMode.PercentOutput, highSpeed);
			//right2.set(ControlMode.PercentOutput, highSpeed);
		}
		else if (leftLiftDownHigh) {
			right1.set(ControlMode.PercentOutput, -highSpeed);
			//right2.set(ControlMode.PercentOutput, -highSpeed);
		}
		
		else if (leftLiftUpNormal){
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
		*/
		//return left limit high switch
		return false;
		//return ultraLeft.getRangeInches() < FINISHDISTANCE;
	}
	
	public boolean controlLeftLiftLimitSwitch(double control) {
		
		if (!leftFinished.get()) {
			left1.set(ControlMode.PercentOutput, control);
			return false;
		}
		
		left1.set(ControlMode.PercentOutput, 0.0);
		return true;
		
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
	
	public double getRightUltra() {
		return ultraRight.getRangeInches();
	}
	
	public double getLeftUltra() {
		return ultraLeft.getRangeInches();
	}

}
