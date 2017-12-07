package src.org.usfirst.frc.team5066.controller2018;

import edu.wpi.first.wpilibj.Joystick;

public class XboxController extends Joystick{

	public XboxController(int port) {
		super(port);
	}
	
	public boolean getAButton() {
		return this.getRawButton(1);
	}
	
	public boolean getBButton() {
		return this.getRawButton(2);
	}
	
	public boolean getXButton() {
		return this.getRawButton(3);
	}
	
	public boolean getYButton() {
		return this.getRawButton(4);
	}
	
	public boolean getLB() {
		return this.getRawButton(5);
	}
	
	public boolean getRB() {
		return this.getRawButton(6);
	}
	
	public boolean getBackButton() {
		return this.getRawButton(7);
	}
	
	public boolean getStartButton() {
		return this.getRawButton(8);
	}
	
	public boolean getL3() {
		return this.getRawButton(9);
	}
	
	public boolean getR3() {
		return this.getRawButton(10);
	}
	
	public double getLS_X() {
		return this.getRawAxis(0);
	}
	
	public double getLS_Y() {
		return this.getRawAxis(1);
	}
	
	public double getRS_X() {
		return this.getRawAxis(4);
	}
	
	public double getRS_Y() {
		return this.getRawAxis(5);
	}
	
	public double getTriggerRight() {
		return this.getRawAxis(3);
	}
	public double getTriggerLeft() {
		return this.getRawAxis(2);
	}
	
	
	
	
}