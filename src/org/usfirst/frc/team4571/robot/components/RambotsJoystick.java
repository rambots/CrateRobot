package org.usfirst.frc.team4571.robot.components;

import org.usfirst.frc.team4571.robot.commands.teleop.SimpleTeleopElevatorDownCommand;
import org.usfirst.frc.team4571.robot.commands.teleop.SimpleTeleopElevatorUpCommand;
import org.usfirst.frc.team4571.robot.commands.teleop.TeleopElevatorStopCommand;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * 
 * Defining buttons, sensitivities, control parameters for the joystick used to control this robot.
 * 
 * @author arjunrao87
 *
 */
public class RambotsJoystick extends Joystick{

	private final Button buttonA;
	private final Button buttonB;
	private final Button buttonX;
	private final Button buttonY;
	private double tuningParameter;

	public RambotsJoystick( int port ) {
		super(port);
		this.buttonA = new JoystickButton( this, 1 );
		this.buttonB = new JoystickButton( this, 2 );
		this.buttonX = new JoystickButton( this, 3 );
		this.buttonY = new JoystickButton( this, 4 );	
		this.assignButtonFunctions();
	}

	public RambotsJoystick tuningParameter( double tuningParameter ){
		this.tuningParameter = tuningParameter;
		return this;
	}

	public RambotsJoystick assignButtonFunctions(){
		//this.buttonA.whenPressed(new Grabber(1, false));
		//this.buttonX.whenPressed(new Sweep(1));
		this.buttonY.whenPressed( new SimpleTeleopElevatorUpCommand() );
		this.buttonY.whenReleased( new TeleopElevatorStopCommand() );
		this.buttonB.whenPressed( new SimpleTeleopElevatorDownCommand()  );
		this.buttonB.whenReleased( new TeleopElevatorStopCommand() );
		return this;
	}	

	public Button getButtonA(){
		return this.buttonA;
	}

	public Button getButtonB(){
		return this.buttonB;
	}

	public Button getButtonX(){
		return this.buttonX;
	}

	public Button getButtonY(){
		return this.buttonY;
	}

	// TODO : Is the Axis # correct?
	public double getXAxisSpeed() {
		return adjustForSensitivity( this.getRawAxis(0), getTuningParameter() );
	}

	// TODO : Is the Axis # correct?
	public double getYAxisSpeed() {
		return adjustForSensitivity( this.getRawAxis(1), getTuningParameter() );
	}

	// TODO : This might have to go on the other joystick
	public double getRotationSpeed() {
		return 0;
	}

	public double getTuningParameter() {
		return this.tuningParameter;
	}

	// Taken from 
	//	- https://www.chiefdelphi.com/forums/showthread.php?p=921992
	//  - https://www.chiefdelphi.com/media/papers/2421
	/**
	 * Run some calculations to figure out how sensitive we want the joystick to be
	 * 
	 * @param originalValue : Value passed in to calculate sensitivity
	 * @param tuningParameter : Ranges from 0 - 1. 
	 * 			  When tuningParametera = 0, result = original Value   i.e. not adjusted for sensitivity
	 * 			  When tuningParameter  = 1, result = originalValue ^3 i.e. very sensitive
	 * @return value corrected for sensitivity
	 */
	public double adjustForSensitivity( double originalValue, double tuningParameter ){
		return ( ( Math.pow(originalValue, 3) * tuningParameter ) + ( ( 1 - tuningParameter ) * originalValue ) );
	}
}