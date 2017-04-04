package com.gamedev.drifter.system;


import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Matrix4;
import com.gamedev.drifter.entity.DrifterObject;
import com.gamedev.drifter.entity.component.DrifterCharacterActionsComponent;
import com.gamedev.drifter.entity.component.DrifterTargetingComponent;
import com.mk.intrigue.AtomicPhysicalObject;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.system.GameSys;
import com.mk.intrigue.system.ISystem;
public class DrifterAiSys extends GameSys {
	/*
	*	System requirements for entity:
	*		-DrifterObject
	*		-Adversary (soon to be replaced) --> pre-requires IntriguePhysicalComponent, DrifterCharacterActionsComponent
	*		-IntriguePhysicalComponent
	*		-DrifterCharacterActionsComponent
	*		NOTE:  it is likely that whatever the System needs the adversary is likely to need.  
	*/
	private Array<Integer> internal = new Array<Integer>();
	private final Vector3 tmp = new Vector3();
	public DrifterAiSys(ISystem upstream) {
		super(upstream);
	}
	/**
	 * requires components to function
	 */
	public void register(int guid) {
		super.register(guid);
		DrifterObject d = Intrigue.mamaDukes.get(guid);
		this.requireComponent(d.getCharacterActionsComponent(), this, d);
		this.requireComponent(d.getTargetingComponent(), this, d);
		this.requireComponent(d.getPhysicalComponent(), this, d);
		internal.add(guid);
	}
	public void deregister(int guid) {
		super.deregister(guid);
		internal.removeValue(guid, false);
	}
	public void update(float delta) {
		super.update(delta);
		for(Integer i : internal) {
		
			tmp.set(1,0,0);
			DrifterObject g = Intrigue.mamaDukes.get(i);
			DrifterCharacterActionsComponent states = g.getCharacterActionsComponent();
			states.reset();
			
			DrifterTargetingComponent tc = g.getTargetingComponent();
			DrifterObject adversary = Intrigue.mamaDukes.get(tc.getTarget());
			//this.requireComponent(adversary.getCharacterActionsComponent(), s, g);
			DrifterCharacterActionsComponent states2 =  adversary.getCharacterActionsComponent();
			AtomicPhysicalObject a = adversary.getPhysicalComponent().getPhysicsBody();
			AtomicPhysicalObject b = g.getPhysicalComponent().getPhysicsBody();
			
			Matrix4 t = new Matrix4();
			Matrix4 u = new Matrix4();
			a.getMotionState().getWorldTransform(t);
			b.getMotionState().getWorldTransform(u);
			Vector3 target = new Vector3();
			Vector3 position = new Vector3(); 
			t.getTranslation(target);
			u.getTranslation(position);
			Vector3 distance = target.sub(position);
			Vector3 direction = new Vector3();
			direction.set(distance);
			direction.nor();
			//System.out.println("direction of Ai chase: " + direction);
			b.getRigidBody().getOrientation().transform(tmp);
			//tmp.x = 
			//float distance = position.dst2(target);
			tmp.set(tmp.nor());
			float sign = direction.dot(tmp);
			//float angleBetween =  (float) Math.toDegrees(Math.acos(direction.dot(tmp)));
			//System.out.println("dot product of base and target: " + sign + " " + angleBetween + "deg") ;
			//System.out.println(distance.len2());
			if(states2.isAiming()) {
				states.setAiming(true);
			}
			else if(states2.isFiring()) {
				states.setFiring(true);
			}
			if(Math.abs(sign) < .01f) {
				if(distance.len2() < 1000000) {
					states.setIdle(true);
				}
				else {
					states.setIdle(false);
					states.setForward(true);
					//System.out.println("distance : " + distance);
				}
			}
			else {
				if(sign > 0) {
					states.setIdle(false);
					states.setTurnLeft(true);
				}
				else {
					states.setIdle(false);
					states.setTurnRight(true);
				}
			}
		}
	}
}