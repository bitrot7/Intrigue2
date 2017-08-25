package com.mk.intrigue.entity.component;

import com.badlogic.gdx.math.Vector3;
import com.mk.intrigue.entity.Entity;
import com.mk.intrigue.exception.ComponentMissingException;
import com.mk.intrigue.object.AtomicPhysicalObject;

/**
 * Controls the motion of the entity.  Based on the state of the actions component
 * it updates the physical component.
 * 
 * Requires {@link IntrigueActionsComponent} {@link IntriguePhysicalComponent} to be defined 
 * in the parent entity.
 * 
 * @author wind2
 *
 */
public class MotionComponent extends BaseComponent {

	protected static float sm_universal_push = 100f; //naruto copout
	protected static float sm_universal_rotate = .2f;
	protected Vector3 mt_impulse = new Vector3();
	protected Vector3 mt_angularImpulse = new Vector3();
	protected Vector3 mt_tmp = new Vector3();
	
	
	private void resetUtilVectors() {
		mt_impulse.set(0,0,0);
		mt_angularImpulse.set(0,0,0);
		mt_tmp.set(0,0,0);
	}
	
	/**
	 * Uses an impulse unit vector to set direction then normalizes (scales it)
	 * to a physical constant that needs to be better understood.
	 * 
	 * @param acActionsComp reference to the Parent Entities actions component
	 */
	private void setMovementDirection(IntrigueActionsComponent acActionsComp) {
		if(acActionsComp.isLeft()) {
			mt_impulse.x = 1;
		}
		else if(acActionsComp.isRight()) {
			mt_impulse.x = -1;
		}
		if(acActionsComp.isForward()) {
			mt_impulse.z = 1;
		}
		else if(acActionsComp.isBackward()) {
			mt_impulse.z=-1;
		}
		if(acActionsComp.isJump()) {
			mt_impulse.y = 1;
		}
		if(acActionsComp.isSprint() && acActionsComp.isForward()) {
			mt_impulse.scl(1.5f);
		}
		if(acActionsComp.isTurningLeft()) {
			mt_angularImpulse.y = 1;
		}
		else if(acActionsComp.isTurningRight()) {
			mt_angularImpulse.y = -1;
		}
		if(acActionsComp.isAiming()) {
			mt_angularImpulse.scl(sm_universal_rotate*.2f);
		}
		else {
			mt_angularImpulse.scl(sm_universal_rotate);
		}
	}
	
	// TODO re-evaluate the direct setting of linear velocity...  There are better ways.
	private void setVelocities(AtomicPhysicalObject atPhysicalObject) {
		Vector3 v1 = atPhysicalObject.getRigidBody().getLinearVelocity();
		mt_impulse.scl(sm_universal_push);
		mt_impulse = atPhysicalObject.getRigidBody().getOrientation().transform(mt_impulse);
		atPhysicalObject.getRigidBody().setLinearVelocity(new Vector3(mt_impulse.x, v1.y, mt_impulse.z));
		mt_tmp.set(0,1,0);
		atPhysicalObject.getRigidBody().setAngularFactor(mt_tmp);
		atPhysicalObject.getRigidBody().setAngularVelocity(mt_angularImpulse);
		atPhysicalObject.getRigidBody().setAngularFactor(mt_tmp);
		
		
	}
	
	/**
	 * Links the physical representation of the entity with the graphical representation.
	 * TODO I believe this functionality should be moved somewhere else..
	 * 
	 * @param atPhysicalObject physical object represents entities physical location.
	 * @param acModelComponent graphical component contains ref to object that contains
	 * 		  graphical location.
	 */
	private void linkPhysicalandGraphicalModels(AtomicPhysicalObject atPhysicalObject, 
			IntrigueModelComponent acModelComponent) 
	{
		atPhysicalObject.getMotionState().getWorldTransform(acModelComponent.getModel().transform);
		//a hack for making the players touch the ground.
		acModelComponent.getModel().transform.translate(0,-80f,0);
	}
	
	
	@Override
	public void HandleUpdate(float delT) {
		// TODO Auto-generated method stub
		this.resetUtilVectors();
		Entity leEntity = GetParentEntity();
		
		if(leEntity.getActionsComponent() == null)
		{
			throw new ComponentMissingException(MotionComponent.class.getName(), 
					IntrigueActionsComponent.class.getName(), 
					GetParentEntity().getGUID());
		}
		
		if(leEntity.getPhysicalComponent() == null)
		{
			throw new ComponentMissingException(MotionComponent.class.getName(), 
					IntriguePhysicalComponent.class.getName(), 
					GetParentEntity().getGUID());
		}
		this.setMovementDirection(leEntity.getActionsComponent());
		this.setVelocities(leEntity.getPhysicalComponent().getPhysicsBody());
		this.linkPhysicalandGraphicalModels(leEntity.getPhysicalComponent().getPhysicsBody(), leEntity.getModelComponent());
	}

}
