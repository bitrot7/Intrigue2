package com.mk.intrigue.entity.component;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.entity.Entity2;
import com.mk.intrigue.object.AtomicPhysicalObject;


public class IntrigueAIComponent extends BaseComponent {
	private IntriguePhysicalComponent m_adversary_positional_info;
	private IntrigueFiringComponent m_adversary_aiming_info;
	private IntrigueActionsComponent adversary_actions;
	
	public IntrigueAIComponent() {
		
	}
	public void setAdversaryFiringComponent(IntrigueFiringComponent dfc) {
		this.m_adversary_aiming_info = dfc;
	}
	public void setAdversaryCharacterActionsComponent(IntrigueActionsComponent dcac) {
		this.adversary_actions = dcac;
	}
	public void setAdversaryPhysicalComponent(IntriguePhysicalComponent ipc) {
		this.m_adversary_positional_info = ipc;
	}
	public IntrigueFiringComponent getAdversaryFiringComponent() {
		return this.m_adversary_aiming_info;
	}
	public IntrigueActionsComponent getAdversaryCharacterActionsComponent() {
		return this.adversary_actions;
	}
	public IntriguePhysicalComponent getAdversaryPhysicalComponent() {
		return this.m_adversary_positional_info;
	}
	@Override
	public void HandleUpdate(float delT) {
		// TODO Auto-generated method stub
//		tmp.set(1,0,0);
//		Entity2 g = Intrigue.mamaDukes.get(i);
//		IntrigueActionsComponent states = g.getActionsComponent();
//		states.reset();
//		
//		IntrigueTargetingComponent tc = g.getTargetingComponent();
//		Entity2 adversary = Intrigue.mamaDukes.get(tc.getTarget());
//		//this.requireComponent(adversary.getCharacterActionsComponent(), s, g);
//		IntrigueActionsComponent states2 =  adversary.getActionsComponent();
//		AtomicPhysicalObject a = adversary.getPhysicalComponent().getPhysicsBody();
//		AtomicPhysicalObject b = g.getPhysicalComponent().getPhysicsBody();
//		
//		Matrix4 t = new Matrix4();
//		Matrix4 u = new Matrix4();
//		a.getMotionState().getWorldTransform(t);
//		b.getMotionState().getWorldTransform(u);
//		Vector3 target = new Vector3();
//		Vector3 position = new Vector3(); 
//		t.getTranslation(target);
//		u.getTranslation(position);
//		Vector3 distance = target.sub(position);
//		Vector3 direction = new Vector3();
//		direction.set(distance);
//		direction.nor();
//		//System.out.println("direction of Ai chase: " + direction);
//		b.getRigidBody().getOrientation().transform(tmp);
//		//tmp.x = 
//		//float distance = position.dst2(target);
//		tmp.set(tmp.nor());
//		float sign = direction.dot(tmp);
//		//float angleBetween =  (float) Math.toDegrees(Math.acos(direction.dot(tmp)));
//		//System.out.println("dot product of base and target: " + sign + " " + angleBetween + "deg") ;
//		//System.out.println(distance.len2());
//		if(states2.isAiming()) {
//			states.setAiming(true);
//		}
//		else if(states2.isFiring()) {
//			states.setFiring(true);
//		}
//		if(Math.abs(sign) < .01f) {
//			if(distance.len2() < 1000000) {
//				states.setIdle(true);
//			}
//			else {
//				states.setIdle(false);
//				states.setForward(true);
//				//System.out.println("distance : " + distance);
//			}
//		}
//		else {
//			if(sign > 0) {
//				states.setIdle(false);
//				states.setTurnLeft(true);
//			}
//			else {
//				states.setIdle(false);
//				states.setTurnRight(true);
//			}
//		}
	}
}