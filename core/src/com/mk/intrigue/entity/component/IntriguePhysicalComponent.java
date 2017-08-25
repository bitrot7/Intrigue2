//Intrigue Physical Component

package com.mk.intrigue.entity.component;
import com.mk.intrigue.object.AtomicPhysicalObject;
import com.mk.intrigue.object.DefaultKinematicRagdoll;
import com.mk.intrigue.system.IntrigueTotalPhysicsSystem;
import com.mk.intrigue.utility.Logger;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.badlogic.gdx.utils.Array;

public class IntriguePhysicalComponent extends BaseComponent {
	// TODO: Refactored from old system notion
	
	
	private AtomicPhysicalObject atomic;
	private DefaultKinematicRagdoll ragdoll;
	
	// TODO: Refactored from old system notion.
	public IntriguePhysicalComponent(btCollisionShape shape, float mass, Matrix4 transform) 
	{
		Vector3 inertia = new Vector3();
		if(mass != 0) {
			shape.calculateLocalInertia(mass, inertia);
		}
		this.atomic = new AtomicPhysicalObject(shape, mass, inertia, transform);
		IntrigueTotalPhysicsSystem.dynamicsWorld.addRigidBody(atomic.getRigidBody());
	}
	
	public IntriguePhysicalComponent(DefaultKinematicRagdoll ragdoll) {
		//currently unsupported
		/*if(asset == null) {
			System.out.println("Error:  Kinematic Ragdoll must have Model.");
			return this;
		}
		if(atomic != null) {
			System.out.println("WARNING: Overwriting previously declared atomic physical object and replacing it with Root node");
		}	
		//physical_objects = new Array<PhysicalObject>();
		//model = null;
		ragdoll = new DefaultKinematicRagdoll(asset);
		//model = null;
		model = ragdoll.getAllInstances();
		//this.atomic = ragdoll.getBodyPart("Root");
		*/
	}
	
	public IntriguePhysicalComponent(ModelInstance model, float mass, Matrix4 transform) {
		btCollisionShape shape = new btBvhTriangleMeshShape(model.model.meshParts);
		Vector3 inertia = new Vector3();
		if(mass != 0) {
			shape.calculateLocalInertia(mass, inertia);
		}
		this.atomic = new AtomicPhysicalObject(shape, mass, inertia, transform);
		IntrigueTotalPhysicsSystem.dynamicsWorld.addRigidBody(atomic.getRigidBody());
	}
	public AtomicPhysicalObject getPhysicsBody() {
		return atomic;
	}
	public DefaultKinematicRagdoll getRagdoll() {
		return ragdoll;
	}
	@Override
	public void HandleUpdate(float delT) {
		Matrix4 update = new Matrix4();
		atomic.getMotionState().getWorldTransform(update);
		Vector3 position = new Vector3();
		update.getTranslation(position);
		
		// Debug for dumping all positions in the world
		//Logger.WriteToPositionLogs("guid = " + GetParentEntity().getGUID() + " pos = " + position + "\n");
		
		final float sdelta = Math.min(1f / 30f, delT);
		IntrigueTotalPhysicsSystem.dynamicsWorld.stepSimulation(sdelta, 10, 1f/60f);
	}
	
}