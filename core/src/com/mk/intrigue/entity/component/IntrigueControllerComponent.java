//IntrigueControllerComponent.java
/*
*	matt keating
*/
package com.mk.intrigue.entity.component;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.Controller;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.entity.Entity;
import com.mk.intrigue.exception.ComponentMissingException;

/**
 * Controller component takes input from 360 controller.
 * TODO Refactor this to be a child of a more broad controller class
 * that way multiple different input sources can be easily handled.
 * 
 * Requires {@link IntrigueActionsComponent}
 * 
 * @author wind2
 *
 */
public class IntrigueControllerComponent extends BaseComponent {
	private Controller controls;
	
	public IntrigueControllerComponent(int player_number) {
		
		if (player_number > 6) {
			System.out.println("Failure: too many Xbox Controllers, Max of 6 players on battlefield.");
			return;
		}
		player_number--;
		Array<Controller> t = Controllers.getControllers();
		if(t.size == 0) {
			System.out.println("Failure:  No Controllers Attached");
			return;
		}
		else if(player_number >= t.size) {
			System.out.println("Failure:  No Controllers Attached for player" + player_number + 1);
			return;
		}
		controls = Controllers.getControllers().get(player_number);
	}
	public Controller getController() {
		return controls;
	}
	
	private void ComputeActions(IntrigueActionsComponent c_actionComponent)
	{
		c_actionComponent.reset();
		float val1 = controls.getAxis(0);
		float val2 = controls.getAxis(1);
		float val4 = controls.getAxis(3); //x
		float val5 = controls.getAxis(4); //y
		float rt = controls.getAxis(5); // rt
		float lt = controls.getAxis(2); //lt
		//soldier.setIdle(true);
		if(controls.getButton(0)) {
			c_actionComponent.setJump(true);
		}
		if(val1 > 0.5f) {
			//soldier.setRunningRight(true);
			//soldier.setIdle(false);
			c_actionComponent.setIdle(false);
			c_actionComponent.setRight(true);
			//s.setTurnRight(true);
			//System.out.println("Axis 0: " + val1);
		}
		else if(val1 < -0.5f) {
			//System.out.println("Axis 0: " + val1);
			//soldier.setRunningLeft(true);
			//soldier.setIdle(false);
			c_actionComponent.setIdle(false);
			c_actionComponent.setLeft(true);
			//s.setTurnLeft(true);
		}
		else if(val2 > 0.5f) {
			//System.out.println("Axis 1: " + val2);
			//soldier.setRunningBackward(true);
			//soldier.setIdle(false);
			
			c_actionComponent.setIdle(false);
			c_actionComponent.setBackward(true);
		}
		else if(val2 < -0.5f) {
			//System.out.println("Axis 1: " + val2);
			//soldier.setRunningForward(true);
			//soldier.setIdle(false);
			
			c_actionComponent.setIdle(false);
			c_actionComponent.setForward(true);
		}
		if(val4 > 0.5f) {
			//soldier.setRotateRight(true);//Forward(true);
			//soldier.setIdle(false);
			c_actionComponent.setIdle(false);
			c_actionComponent.setTurnLeft(false);
			c_actionComponent.setTurnRight(true);
			//System.out.println("Axis 3: " + val4);
		}
		else if(val4 < -0.5f) {
			//soldier.setRotateLeft(true);
			//soldier.setIdle(false);
			c_actionComponent.setIdle(false);
			c_actionComponent.setTurnRight(false);
			c_actionComponent.setTurnLeft(true);
			//System.out.println("Axis 3: " + val4);
		}
		
		if(val5 > 0.5f) {
			c_actionComponent.setTurnDown(true);
			//soldier.setIdle(false);
			c_actionComponent.setIdle(false);
			//System.out.println("Axis 4: " + val5);
		}
		else if(val5 < -0.5f) {
			
			c_actionComponent.setTurnUp(true);
			//soldier.setIdle(false);
			c_actionComponent.setIdle(false);
			//System.out.println("Axis 4: " + val5);
		}
		if(controls.getButton(9)) {
			//soldier.setSprinting(true);
			c_actionComponent.setIdle(false);
			//System.out.println("Button 9 pressed.");
		}
		if(controls.getButton(2)) {
			c_actionComponent.setIdle(false);
			c_actionComponent.setReload(true);
		}
		if(rt > .5f) {
			//soldier.setShooting(true);
		//	s.setIdle(false);
			//s.setAiming(false);
			c_actionComponent.setFiring(true);
			//System.out.println("RT: " + rt);
		}
		if(lt > .5f) {
		//	s.setIdle(false);
			//s.setFiring(false)
			c_actionComponent.setAiming(true);
			//System.out.println("LT: " + lt );
		}
		else {
			c_actionComponent.setAiming(false);
		}
	}
	
	
	/**
	 * @brief This is the interface implementation that handles the action taken by the controller component.
	 * @param delT the delta time in frames per sec.
	 */
	@Override
	public void HandleUpdate(float delT) {
	
		Entity g = GetParentEntity();
		if(controls == null) {
			throw new RuntimeException("Failure: Gobject has no controller, "
					+ "yet it is registered with the controller subsystem.");
		}
		
		// retrieve action component (eventually causes movement)
		IntrigueActionsComponent c_actionComponent =  g.getActionsComponent();
		
		if(c_actionComponent != null)
		{
			ComputeActions(c_actionComponent);
		}
		else
		{
			throw new ComponentMissingException(IntrigueControllerComponent.class.getName(), 
					IntrigueActionsComponent.class.getName(), 
					GetParentEntity().getGUID());
		}
		
	}
}