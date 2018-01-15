package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.controller2018.LogitechController;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;

public class Lift {
	
	//TODO:
	//work off singularity 
	//limit switches
	//create method
	//run motor until limit switch
	//create method
	//run until other limit switch
	//don't do while loop
	//be running continously
	//unlock button
	//button to raise
	//button to unlock
	//do javabat when done
	
	
	LogitechController lc;
	private CANTalon right1, right2, left1, left2;
	DigitalInput rightLimLow, rightLimHigh, leftLimLow, leftLimHigh;
	double speed;
	boolean previousButR, nowButR, previousButL, nowButL;
	
	public Lift(int r1, int r2, int l1, int l2, int rL1, int rL2, int lL1, int  lL2, int logitechPort, double s) {
		
		right1 = new CANTalon(r1);
		right2 = new CANTalon(r2);
		left1 = new CANTalon(l1);
		left2 = new CANTalon(l2);
		
		rightLimLow = new DigitalInput(rL1);
		rightLimHigh = new DigitalInput(rL2);
		leftLimLow = new DigitalInput(lL1);
		leftLimHigh = new DigitalInput(lL2);
		
		speed = s;
		
		previousButR = false;
		nowButR = false;
		previousButL = false;
		nowButL = false;
		
		lc = new LogitechController(logitechPort);
		
	}
	
	public void raise() {
		
		nowButL = lc.getStickFrontLeft();
		nowButR = lc.getStickFrontRight();
		
		//if the right button is toggled and the right lift is at the high limit it'll go down
		//or if the right button is toggled and the right lift is at the low limit it'll go up
		if(nowButR && !previousButR) { 
			if (!rightLimHigh.get()) {
				right1.set(speed);
				right2.set(speed);
			}
			else{
				right1.set(-speed);
				right2.set(-speed);
			}
		}
		
		//if the right lift is at the high limit and it's still going up or vice versa it'll stop
		else if((right1.getSpeed() > 0 && rightLimHigh.get()) || (right1.getSpeed() < 0 && rightLimLow.get())) {
			right1.set(0.0);
			right2.set(0.0);
		}
		
		//if the button is toggled and it's at the high limit it'll go down
		//or if the button is toggled and it's at the low limit it'll go up
		if(nowButL && !previousButL) { 
			if (!rightLimHigh.get()) {
				left1.set(speed);
				left2.set(speed);
			}
			else{
				left1.set(-speed);
				left2.set(-speed);
			}
		}
				
		//if the left lift is at the high limit and it's still going up or vice versa it'll stop
		else if((left1.getSpeed() > 0 && leftLimHigh.get()) || (left1.getSpeed() < 0 && leftLimLow.get())) {
			left1.set(0.0);
			left2.set(0.0);
		}
			
		previousButR = nowButR;
		previousButL = nowButL;
			
	}

}
