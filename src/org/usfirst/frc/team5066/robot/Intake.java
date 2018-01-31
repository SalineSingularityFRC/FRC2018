package org.usfirst.frc.team5066.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Intake {
	
	private VictorSPX left;
	private VictorSPX right;
	
	public Intake(int leftPort, int rightPort){
		
		left = new VictorSPX(leftPort);
		right = new VictorSPX(rightPort);
	}
	
	public void controlIntake(boolean leftIn, boolean leftOut, boolean rightIn, boolean rightOut){
		
		if (leftIn){
			left.set(ControlMode.PercentOutput, 1.0);
			
		}
		
		else if (leftOut){
			left.set(ControlMode.PercentOutput, -1.0);
			
		}
		
		else {
			left.set(ControlMode.PercentOutput, 0.0);
		}
		
		if (rightIn){
			right.set(ControlMode.PercentOutput, 1.0);
				
		}
		
		else if (rightOut){
			
			right.set(ControlMode.PercentOutput, -1.0);
		}
		
		else {
			right.set(ControlMode.PercentOutput, 0.0);
		}
	
	}

}
