package com.mk.intrigue.system;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.entity.Entity2;
import com.mk.intrigue.entity.component.IntrigueActionsComponent;
import com.mk.intrigue.object.MyAnimationListener;
/*
	*	Animation names:
	*		Run_Forwards
	*		Run_backwards
	*		Walk
	*		Jump
	*		Idle
	*		Aim Running
	*		Run Firing
	*		Strafe Left
	*		Left Fire
	*		Strafe Right
	*		Right FIre
	*		Walk Aiming
	*		Walk Firing
	*		Walk Backwards
	*		Idle Aim
	*		Idle Firing
	*		Idle Reload
	*		Stand Aim Down
	*		Stand Aim Up
	*		Standing 2
	*/
/*
*	System requirments for entity:
*		-DrifterObject
*		
*/
public class IntrigueAnimationSystem extends SystemDecorator {
	
	private MyAnimationListener garbage = new MyAnimationListener();
	private Array<Integer> internal = new Array<Integer>();
	public IntrigueAnimationSystem(ISystem upstream) {
		super(upstream);
	}
	public void register(int guid) {
		super.register(guid);
		Entity2 d = Intrigue.mamaDukes.get(guid);
		this.requireComponent(d.getActionsComponent(), this, d);
		this.requireComponent(d.getModelComponent(), this, d);
		internal.add(guid);
	}
	public void deregister(int guid) {
		super.deregister(guid);
		internal.removeValue(guid,true);
	}
	
	public void update(float delta) {
		super.update(delta);
		for(Integer i : internal) {
			Entity2 g = Intrigue.mamaDukes.get(i);
			IntrigueActionsComponent s = g.getActionsComponent();
			AnimationController c = g.getAnimationController();
			if(s.isFiring()) {
				
				if(s.isForward()) {
					c.animate("Run Firing",-1,2,garbage, .03f);
				}
				if(s.isBackward()) {
					c.animate("Run_backwards",-1,2, garbage ,.03f);
				}
				if(s.isLeft()) {
					c.animate("Left Fire",-1,2, garbage, .03f);
				}
				if(s.isRight()) {
					c.animate("Right FIre",-1,2, garbage, .03f);
				}
				if(s.isIdle()){
					//System.out.println("here");
					c.animate("Idle Firing",-1,2, garbage, .03f);
				}
			/*	if(s.isTurningUp()) {
					c.animate("Stand Aim Up",-1,2, garbage, .03f);
				}
				if(s.isTurningDown()) {
					c.animate("Stand Aim Down",-1,2, garbage, .03f);
				}*/
			}
			else if(s.isAiming()) {
				if(s.isForward()) {
					c.animate("Aim Running",-1,2,garbage, .03f);
				}
				if(s.isBackward()) {
					c.animate("Run_backwards",-1,2, garbage ,.03f);
				}
				if(s.isLeft()) {
					c.animate("Strafe Left",-1,2, garbage, .03f);
				}
				if(s.isRight()) {
					c.animate("Strafe Right",-1,2, garbage, .03f);
				}
				if(s.isIdle()) {
					c.animate("Idle Aim",-1,2, garbage, .03f);
				}
				/*if(s.isTurningUp()) {
					c.animate("Stand Aim Up",-1,2, garbage, .03f);
				}
				if(s.isTurningDown()) {
					c.animate("Stand Aim Down",-1,2, garbage, .03f);
				}*/
			}
			else {
				if(s.isForward()) {
					c.animate("Run_Forwards",-1,2,garbage, .03f);
				}
				if(s.isBackward()) {
					c.animate("Run_backwards",-1,2, garbage ,.03f);
				}
				if(s.isLeft()) {
					c.animate("Strafe Left",-1,2 ,garbage, .03f);
				}
				if(s.isRight()) {
					c.animate("Strafe Right",-1,2 ,garbage, .03f);
				}
				if(s.isIdle()){
					c.animate("Idle",-1, garbage, .03f);
				}
				if(s.isReload()) {
					c.animate("Idle Reload", 1,5 ,garbage, .03f);
				}
				/*if(s.isTurningUp()) {
					c.animate("Stand Aim Up",-1,2, garbage, .03f);
				}
				if(s.isTurningDown()) {
					c.animate("Stand Aim Down",-1,2, garbage, .03f);
				}*/
			}
			
			c.update(delta);
		}
		
	}
}