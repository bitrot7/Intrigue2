//@author Matt
//PhysicalObject.java: a wrapper for btRigidBodies
//for use with Collision Sys and General Purpose G object.
package com.mk.intrigue;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody.btRigidBodyConstructionInfo;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.collision.btBvhTriangleMeshShape;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class AtomicPhysicalObject {
	private MyMotionState ms;
	private btRigidBody rbody;
	private btCollisionShape rshape;
	//private Matrix4 tmp = new Matrix4();
	public static class MyMotionState extends btMotionState {
        Matrix4 transform;
        @Override
        public void getWorldTransform (Matrix4 worldTrans) {
            worldTrans.set(transform);
        }
        @Override
        public void setWorldTransform (Matrix4 worldTrans) {
            transform.set(worldTrans);
        }
    }
	public AtomicPhysicalObject( btCollisionShape s, float mass, Vector3 inertia, Matrix4 initialTransform) {
		rshape = s;
		ms = new MyMotionState();
		ms.transform = initialTransform;
		if(mass == 0) {
			inertia.set(0,0,0);
		}
		btRigidBodyConstructionInfo rbodyCI = new btRigidBodyConstructionInfo(mass, ms, rshape, inertia);
        rbody = new btRigidBody(rbodyCI);
		//System.out.println("Shape" + rbody.getCollisionShape().getShapeType() );
		btCollisionShape shape = rbody.getCollisionShape();
		if(shape.getName().equalsIgnoreCase("BVHTRIANGLEMESH")) {
			//System.out.println("Here");
			btBvhTriangleMeshShape tri = (btBvhTriangleMeshShape) shape;
			System.out.println("Local Min: " + tri.getLocalAabbMin());
			System.out.println("Local Max: " + tri.getLocalAabbMax());
		}
		rbodyCI.dispose();
	}
	public btRigidBody getRigidBody() {
		return rbody;
	}
	public MyMotionState getMotionState() {
		return ms;
	}
}