package com.gamedev.drifter.system;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector3;
import com.gamedev.drifter.entity.DrifterEntity;
import com.gamedev.drifter.entity.component.DrifterCharacterActionsComponent;
import com.mk.intrigue.AtomicPhysicalObject;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.system.GameSys;
import com.mk.intrigue.system.ISystem;
/*
*	System requirements for entity:'
*		-DrifterObject
*		-IntriguePhysicalComponent
*		-DrifterCharacterActionsComponent
*
*/
public class DrifterMotionSystem extends GameSys {
	
	private Array<Integer> internal = new Array<Integer>();
	
	protected static float universal_push = 5100f; //naruto copout
	protected static float universal_rotate = 10500f;
	protected Vector3 impulse = new Vector3();
	protected Vector3 angularImpulse = new Vector3();
	protected Vector3 tmp = new Vector3();
	
	public DrifterMotionSystem(ISystem upstream) {
		super(upstream);
	}
	public void register(int guid) {
		super.register(guid);
		DrifterEntity d = Intrigue.mamaDukes.get(guid);
		this.requireComponent(d.getCharacterActionsComponent(), this, d);
		this.requireComponent(d.getPhysicalComponent(), this, d);
		internal.add(guid);
	}
	public void deregister(int guid) {
		super.deregister(guid);
		internal.removeValue(guid,true);
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		for(Integer i : internal) {
			DrifterEntity g = Intrigue.mamaDukes.get(i);
			DrifterCharacterActionsComponent s = g.getCharacterActionsComponent();
			impulse.set(0,0,0);
			angularImpulse.set(0,0,0);
			AtomicPhysicalObject a = g.getPhysicalComponent().getPhysicsBody();
			Vector3 v1 = a.getRigidBody().getLinearVelocity();
			//Vector3 v2 = a.getRigidBody().getAngularVelocity();
			
			
			a.getRigidBody().setDamping(.7f, .8f);
			if(s.isLeft()) {
				impulse.x = 1;
			}
			else if(s.isRight()) {
				impulse.x = -1;
			}
			if(s.isForward()) {
				impulse.z = 1;
			}
			else if(s.isBackward()) {
				impulse.z=-1;
			}
			if(s.isJump()) {
				impulse.y = 1;
			}
			if(Math.abs(v1.x) > 500) {
				a.getRigidBody().setDamping(.9f, .8f);
			}
			if(Math.abs(v1.z) > 500) {
				a.getRigidBody().setDamping(.9f, .8f);
			}
			if(Math.abs(v1.y) > 400) {
				a.getRigidBody().setDamping(.9f, .8f);
			}
			if(s.isIdle() && v1.y > -9f) {
				a.getRigidBody().setAngularVelocity(tmp);
			}
			//changed my method for getting orientation.  thank you bt and ockham
			impulse.scl(universal_push);
			impulse = a.getRigidBody().getOrientation().transform(impulse);
			
			if(s.isSprint() && s.isForward()) {
				impulse.scl(1.5f);
			}
			a.getRigidBody().applyCentralImpulse(impulse);
			
			if(s.isTurningLeft()) {
				angularImpulse.y = 1;
			}
			else if(s.isTurningRight()) {
				angularImpulse.y = -1;
			}
			tmp.set(0,1,0);
			a.getRigidBody().setAngularFactor(tmp);
			if(s.isAiming()) {
				angularImpulse.scl(universal_rotate*.2f);
			}
			else {
				angularImpulse.scl(universal_rotate);
			
			}
			a.getRigidBody().applyTorqueImpulse(angularImpulse);
			tmp.set(0,0,0);
			a.getRigidBody().setAngularFactor(tmp);
			
			a.getMotionState().getWorldTransform(g.getModelComponent().getModel().transform);
			g.getModelComponent().getModel().transform.translate(0,-90,0);
		}
		
	}
}