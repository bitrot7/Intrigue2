//Intrigue Physical Component

package com.mk.intrigue.entity.component;
import com.mk.intrigue.object.AtomicPhysicalObject;
import com.mk.intrigue.object.DefaultKinematicRagdoll;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape;

public class IntriguePhysicalComponent implements IComponent {
	private AtomicPhysicalObject atomic;
	private DefaultKinematicRagdoll ragdoll;
	
	public IntriguePhysicalComponent(btCollisionShape shape, float mass, Matrix4 transform) {
		Vector3 inertia = new Vector3();
		if(mass != 0) {
			shape.calculateLocalInertia(mass, inertia);
		}
		this.atomic = new AtomicPhysicalObject(shape, mass, inertia, transform);
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
	}
	public AtomicPhysicalObject getPhysicsBody() {
		return atomic;
	}
	public DefaultKinematicRagdoll getRagdoll() {
		return ragdoll;
	}
	
}