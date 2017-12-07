package src.org.usfirst.frc.team5066.controller2018;

import edu.wpi.first.wpilibj.Joystick;
/** A simple Joystick subclass that makes button inputs more 
 * comprehensive and efficient when using the Small Logitech Controller. 
 * 
 * 
 * @author Alex Mah
 *
 */
public class SmallLogitechController extends Joystick {

	public SmallLogitechController(int port) {
		super(port);
	}
	
	public boolean getTopLeftButton() {
		return this.getRawButton(4);
	}
	
	public boolean getTopRightButton() {
		return this.getRawButton(5);
	}
	
	public boolean getTopMiddleButton() {
		return this.getRawButton(3);
	}
	
	public boolean getTopBackButton() {
		return this.getRawButton(2);
	}
	
	public boolean getBottomFrontLeftButton() {
		return this.getRawButton(6);
	}
	
	public boolean getBottomBackLeftButton() {
		return this.getRawButton(7);
	}
	
	public boolean getBottomFrontRightButton() {
		return this.getRawButton(11);
	}
	
	public boolean getBottomBackRightButton() {
		return this.getRawButton(10);
	}
	
	public boolean getBottomPlusButton() {
		return this.getRawButton(8);
	}
	
	public boolean getBottomMinusButton() {
		return this.getRawButton(9);
	}
	
	public double getBaseSlider() {
		return this.getRawAxis(3);
	}
}
