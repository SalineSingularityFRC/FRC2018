package src.org.usfirst.frc.team5066.controller2018;

public class ControlSystem// implements ControlScheme
{
	//public enum controlScheme = ;
	//this enum 
	
	private static ControlScheme currentScheme;
	
	public static ControlScheme getCurrentScheme() {
		return currentScheme;
	}
	
	//for each ControlScheme method, call currentScheme.[that method]
	//TODO - use the method that Mr. Katha emailed me to not have to manually write each method in this class
	//to call the method in each different control scheme class.
}
