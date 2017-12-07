package src.org.usfirst.frc.team5066.controller2018;

import edu.wpi.first.wpilibj.Joystick;
/** A simple Joystick subclass that makes button inputs more 
 * comprehensive and efficient when using the Logitech Controller. 
 * 
 * 
 * @author Max Brenner
 *
 */
public class LogitechController extends Joystick{

	public LogitechController(int port) {
		super(port);
	}
	
	public boolean getThumb() {
		return this.getRawButton(2);
	}
	public boolean getStickFrontLeft() {
		return this.getRawButton(5);
	}
	public boolean getStickFrontRight(){
		return this.getRawButton(6);
	}
	public boolean getStickBackLeft(){
		return this.getRawButton(3);
	}
	public boolean getStickBackRight(){
		return this.getRawButton(4);
	}
	public boolean getBaseFrontLeft(){
		return this.getRawButton(7);
	}
	public boolean getBaseFrontRight(){
		return this.getRawButton(8);
	}
	public boolean getBaseMiddleLeft(){
		return this.getRawButton(9);
	}
	public boolean getBaseMiddleRight(){
		return this.getRawButton(10);
	}
	public boolean getBaseBackLeft(){
		return this.getRawButton(11);
	}
	public boolean getBaseBackRight(){
		return this.getRawButton(12);
	}
	public double getStickX(){
		return this.getRawAxis(0);
	}
	public double getStickY(){
		return this.getRawAxis(1);
	}
	public double getStickZ() {
		return this.getRawAxis(2);
	}
	public double getBaseSlider() {
		return this.getRawAxis(3);
	}
	
	//maps raw input methods to usefully named methods
	
}
