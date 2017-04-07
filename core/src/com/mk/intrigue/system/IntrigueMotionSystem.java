package com.mk.intrigue.system;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector3;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.entity.Entity2;
import com.mk.intrigue.entity.component.IntrigueActionsComponent;
import com.mk.intrigue.object.AtomicPhysicalObject;
/*
*	System requirements for entity:'
*		-DrifterObject
*		-IntriguePhysicalComponent
*		-DrifterCharacterActionsComponent
*
*/
public class IntrigueMotionSystem extends SystemDecorator {
	
	private Array<Integer> internal = new Array<Integer>();
	
	protected static float universal_push = 500f; //naruto copout
	protected static float universal_rotate = .8f;
	protected Vector3 impulse = new Vector3();
	protected Vector3 angularImpulse = new Vector3();
	protected Vector3 tmp = new Vector3();
	
	public IntrigueMotionSystem(ISystem upstream) {
		super(upstream);
	}
	public void register(int guid) {
		super.register(guid);
		Entity2 d = Intrigue.mamaDukes.get(guid);
		this.requireComponent(d.getActionsComponent(), this, d);
		this.requireComponent(d.getPhysicalComponent(), this, d);
		internal.add(guid);
	}
	public void deregister(int guid) {
		super.deregister(guid);
		internal.removeValue(guid,true);
	}
	private void resetUtilVectors() {
		impulse.set(0,0,0);
		angularImpulse.set(0,0,0);
		tmp.set(0,0,0);
	}
	@Override
	public void update(float delta) {
		super.update(delta);
		for(Integer i : internal) {
			this.resetUtilVectors();
			Entity2 g = Intrigue.mamaDukes.get(i);
			IntrigueActionsComponent s = g.getActionsComponent();
			
			AtomicPhysicalObject a = g.getPhysicalComponent().getPhysicsBody();
			Vector3 v1 = a.getRigidBody().getLinearVelocity();
			//Vector3 v2 = a.getRigidBody().getAngularVelocity();
			
			
			a.getRigidBody().setDamping(0f, 0f);
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
			if(s.isIdle() && v1.y > -9f) {
				a.getRigidBody().setAngularVelocity(tmp);
			}
			//changed my method for getting orientation.  thank you bt and ockham
			impulse.scl(universal_push);
			impulse = a.getRigidBody().getOrientation().transform(impulse);
			
			if(s.isSprint() && s.isForward()) {
				impulse.scl(1.5f);
			}
			a.getRigidBody().setLinearVelocity(impulse);
			
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
			a.getRigidBody().setAngularVelocity(angularImpulse);
			a.getRigidBody().setAngularFactor(tmp);
			
			a.getMotionState().getWorldTransform(g.getModelComponent().getModel().transform);
			g.getModelComponent().getModel().transform.translate(0,0,0);
		}
		
	}
}