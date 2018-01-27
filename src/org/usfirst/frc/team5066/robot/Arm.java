package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.controller2018.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Arm {

	private TalonSRX talonMotor;
	private VictorSPX victorMotor;
	
	private final double LOWERLIMIT = 10;
	private final double UPPERLIMIT = 100;
	
	double speedConstant;
	double speed;
	
	public Arm(int vic, int tal, double s) {
		talonMotor = new TalonSRX (tal);
		victorMotor = new VictorSPX (vic);
		speedConstant = s;
	}
	
	public void controlArm (double armStick, double exponent) {
			
		if (talonMotor.getSensorCollection().getQuadraturePosition() < LOWERLIMIT && armStick < 0) {
			speed = 0.0;
		}
		else if (talonMotor.getSensorCollection().getQuadraturePosition() > UPPERLIMIT && armStick > 0) {
			
			speed = 0.0;
		}
		else {
			
			speed = Math.pow(armStick, exponent);
			speed *= speedConstant;
		}
		talonMotor.set(ControlMode.PercentOutput, speed);
		victorMotor.set(ControlMode.PercentOutput, speed);
		
			
			
	}
}
