package com.gamedev.drifter.system;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.utils.Array;
import com.gamedev.drifter.entity.DrifterObject;
import com.gamedev.drifter.entity.component.DrifterCharacterActionsComponent;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.system.GameSys;
/*
*	System requirments for entity:
*		-DrifterObject
*		-IntrigueControllerComponent
*		-DrifterCharacterActionsComponent
*/
public class DrifterControllerSystem extends GameSys { 
	private Array<Integer> internal = new Array<Integer>();
	private float last_time = 0f;
	private final float shortest_time_between_shots = .11f;
	private final float shortest_time_between_steps = .53f;
	private final float shortest_time_between_steps_sideways = .38f;
	public void register(int guid) {
		DrifterObject d = Intrigue.mamaDukes.get(guid);
		this.requireComponent(d.getControllerComponent(), this, d);
		this.requireComponent(d.getCharacterActionsComponent(), this, d);
		internal.add(guid);
	}
	public void deregister(int guid) {
		internal.removeValue(guid,true);
	}
	private boolean stagger(float length) {
		if(this.last_time > length) {
			this.last_time = 0;
			return true;
		}
		return false;
	}
	@Override
	public void update(float delta) {
		this.last_time += delta;
		for(Integer i : internal) {
			DrifterObject g = Intrigue.mamaDukes.get(i);
			Controller c = g.getControllerComponent().getController();
			if(c == null) {
				System.out.println("Failure: Gobject has no controller, yet it is registered with the controller subsystem.");
				return;
			}
			DrifterCharacterActionsComponent s =  g.getCharacterActionsComponent();
			s.reset();
			float val1 = c.getAxis(0);
			float val2 = c.getAxis(1);
			float val4 = c.getAxis(3); //x
			float val5 = c.getAxis(4); //y
			float rt = c.getAxis(5); // rt
			float lt = c.getAxis(2); //lt
			//soldier.setIdle(true);
			if(c.getButton(0)) {
				s.setJump(true);
			}
			if(val1 > 0.5f) {
				//soldier.setRunningRight(true);
				//soldier.setIdle(false);
				s.setIdle(false);
				s.setRight(true);
				//s.setTurnRight(true);
				//System.out.println("Axis 0: " + val1);
			}
			else if(val1 < -0.5f) {
				//System.out.println("Axis 0: " + val1);
				//soldier.setRunningLeft(true);
				//soldier.setIdle(false);
				s.setIdle(false);
				s.setLeft(true);
				//s.setTurnLeft(true);
			}
			else if(val2 > 0.5f) {
				//System.out.println("Axis 1: " + val2);
				//soldier.setRunningBackward(true);
				//soldier.setIdle(false);
				
				if(this.stagger(this.shortest_time_between_steps)) {
					s.setIdle(false);
					s.setBackward(true);
				}
			}
			else if(val2 < -0.5f) {
				//System.out.println("Axis 1: " + val2);
				//soldier.setRunningForward(true);
				//soldier.setIdle(false);
				
				if(this.stagger(this.shortest_time_between_steps)) {
					s.setIdle(false);
					s.setForward(true);
				}
			}
			if(val4 > 0.5f) {
				//soldier.setRotateRight(true);//Forward(true);
				//soldier.setIdle(false);
				s.setIdle(false);
				s.setTurnLeft(false);
				s.setTurnRight(true);
				//System.out.println("Axis 3: " + val4);
			}
			else if(val4 < -0.5f) {
				//soldier.setRotateLeft(true);
				//soldier.setIdle(false);
				s.setIdle(false);
				s.setTurnRight(false);
				s.setTurnLeft(true);
				//System.out.println("Axis 3: " + val4);
			}
			
			if(val5 > 0.5f) {
				s.setTurnDown(true);
				//soldier.setIdle(false);
				s.setIdle(false);
				//System.out.println("Axis 4: " + val5);
			}
			else if(val5 < -0.5f) {
				
				s.setTurnUp(true);
				//soldier.setIdle(false);
				s.setIdle(false);
				//System.out.println("Axis 4: " + val5);
			}
			if(c.getButton(9)) {
				//soldier.setSprinting(true);
				s.setIdle(false);
				//System.out.println("Button 9 pressed.");
			}
			if(c.getButton(2)) {
				s.setIdle(false);
				s.setReload(true);
			}
			if(rt > .5f) {
				//soldier.setShooting(true);
			//	s.setIdle(false);
				//s.setAiming(false);
				s.setFiring(true);
				//System.out.println("RT: " + rt);
			}
			if(lt > .5f) {
			//	s.setIdle(false);
				//s.setFiring(false)
				s.setAiming(true);
				//System.out.println("LT: " + lt );
			}
			else {
				s.setAiming(false);
			}

		}
	}
	
}