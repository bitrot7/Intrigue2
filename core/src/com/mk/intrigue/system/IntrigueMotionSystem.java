package com.mk.intrigue.system;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector3;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.entity.Entity2;
import com.mk.intrigue.entity.component.IntrigueActionsComponent;
import com.mk.intrigue.entity.component.IntrigueModelComponent;
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
	private void setMovementDirection(IntrigueActionsComponent actions) {
		if(actions.isLeft()) {
			impulse.x = 1;
		}
		else if(actions.isRight()) {
			impulse.x = -1;
		}
		if(actions.isForward()) {
			impulse.z = 1;
		}
		else if(actions.isBackward()) {
			impulse.z=-1;
		}
		if(actions.isJump()) {
			impulse.y = 1;
		}
		if(actions.isSprint() && actions.isForward()) {
			impulse.scl(1.5f);
		}
		if(actions.isTurningLeft()) {
			angularImpulse.y = 1;
		}
		else if(actions.isTurningRight()) {
			angularImpulse.y = -1;
		}
		if(actions.isAiming()) {
			angularImpulse.scl(universal_rotate*.2f);
		}
		else {
			angularImpulse.scl(universal_rotate);
		}
	}
	private void setVelocities(AtomicPhysicalObject a) {
		Vector3 v1 = a.getRigidBody().getLinearVelocity();
		impulse.scl(universal_push);
		impulse = a.getRigidBody().getOrientation().transform(impulse);
		a.getRigidBody().setLinearVelocity(new Vector3(impulse.x, v1.y, impulse.z));
		tmp.set(0,1,0);
		a.getRigidBody().setAngularFactor(tmp);
		a.getRigidBody().setAngularVelocity(angularImpulse);
		a.getRigidBody().setAngularFactor(tmp);
		
		
	}
	/**
	 * very important method.
	 * @param a
	 * @param g
	 */
	private void linkPhysicalandGraphicalModels(AtomicPhysicalObject a, IntrigueModelComponent g) {
		a.getMotionState().getWorldTransform(g.getModel().transform);
		//a hack for making the players touch the ground.
		g.getModel().transform.translate(0,-80f,0);
	}
	@Override
	public void update(float delta) {
		super.update(delta);
		for(Integer i : internal) {
			this.resetUtilVectors();
			Entity2 g = Intrigue.mamaDukes.get(i);
			this.setMovementDirection(g.getActionsComponent());
			this.setVelocities(g.getPhysicalComponent().getPhysicsBody());
			this.linkPhysicalandGraphicalModels(g.getPhysicalComponent().getPhysicsBody(), g.getModelComponent());
			
		}
		
	}
}