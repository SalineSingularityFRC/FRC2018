package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.controller2018.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Arm {

	private TalonSRX talonMotor;
	private VictorSPX victorMotor;
	
	private final double LOWERLIMIT = 10;
	private final double UPPERLIMIT = 100;
	
	private final double PICKUP = 10;
	private final double SWITCH = 25;
	private final double PORTAL = 25;
	private final double HIGHSCALE = 90;
	private final double LEVELSCALE = 88;
	private final double LOWSCALE = 85;
	private final double EXCHANGE = 15;
	private final double START = 30;
	private final double TRAVEL = 30;
	
	double speedConstant;
	double speed;
	
	DoubleSolenoid doubleSolenoid;
	
	
	public Arm(int vic, int tal, double s, int forwardChannel, int reverseChannel) {
		talonMotor = new TalonSRX (tal);
		victorMotor = new VictorSPX (vic);
		
		victorMotor.set(ControlMode.Follower, tal);
		
		speedConstant = s;
		doubleSolenoid = new DoubleSolenoid(forwardChannel, reverseChannel);
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
	}
	
	public void setArmForward() {
		doubleSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public void setArmReverse() {
		doubleSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void setArmOff() {
		doubleSolenoid.set(DoubleSolenoid.Value.kOff);
	}
	
	public void setPositionPickup() {
		talonMotor.set(ControlMode.Position, PICKUP);
		this.setArmReverse();
		
	}
	
	public void setPositionSwitch() {
		talonMotor.set(ControlMode.Position, SWITCH);
		this.setArmReverse();
		
	}
	
	public void setPositionPortal() {
		talonMotor.set(ControlMode.Position, PORTAL);
		this.setArmReverse();
		
	}
	public void setPositionHighScale() {
		talonMotor.set(ControlMode.Position, HIGHSCALE);
		this.setArmReverse();
		
	}
	
	public void setPositionLevelScale() {
		talonMotor.set(ControlMode.Position, LEVELSCALE);
		this.setArmReverse();
	}

	public void setPositionLowScale() {
		talonMotor.set(ControlMode.Position, LOWSCALE);
		this.setArmReverse();
		
	}

	public void setPositionExchange() {
		talonMotor.set(ControlMode.Position, EXCHANGE);
		this.setArmReverse();
		
	}

	public void setPositionStart() {
		talonMotor.set(ControlMode.Position, START);
		this.setArmReverse();
		
	}
	
	public void setPositionTravel() {
		talonMotor.set(ControlMode.Position, TRAVEL);
		this.setArmForward();
	}


}

