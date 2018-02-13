package org.usfirst.frc.team5066.robot;

import org.usfirst.frc.team5066.controller2018.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
//This class controls the power cube manipulator
public class Arm {

	//TalonSRX motor for arm
	private TalonSRX talonMotor;
	
	//limit arm from oer extending
	private final double LOWERLIMIT = 10;
	private final double UPPERLIMIT = 100;
	
	//names the different positions based off of the orientation of the motor
	private final double PICKUP = 10;
	private final double SWITCH = 25;
	private final double PORTAL = 25;
	private final double HIGHSCALE = 90;
	private final double LEVELSCALE = 88;
	private final double LOWSCALE = 85;
	private final double EXCHANGE = 15;
	private final double START = 30;
	private final double TRAVEL = 30;
	
	//creating a double speed
	double speedConstant;
	double speed;
	
	//naming DoubleSolenoid doubleSolenoid
	DoubleSolenoid doubleSolenoid;
	
	/**renaming:
	 * talonMotor to int tal
	 * speedConstant to s
	 * giving doubleSolenoid 2 values: forwardChannel, and reverseChannel
	 */
	public Arm(int tal, double s, int forwardChannel, int reverseChannel) {
		talonMotor = new TalonSRX (tal);
		
		speedConstant = s;
		doubleSolenoid = new DoubleSolenoid(forwardChannel, reverseChannel);
	}
	
	/**
	 * if statement stating that if there is a powercube in the arm and it has reached the lowerlimit
	 *  set the claw speed to 0
	 *  second if statement setting claw speed to 0 if the arm has reached the upperlimit switch
	 *
	 */
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

