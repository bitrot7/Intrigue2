package com.mk.intrigue.system;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btDbvtBroadphase;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.entity.Entity;

//Default CollisionSYs
/*
*	System requirments for entity:
*		-IntriguePhysicalComponent
*/
public class IntrigueTotalPhysicsSystem extends GameSys {
	
	private Array<Integer> internal = new Array<Integer>();
	private btBroadphaseInterface broadphase;
	private btDefaultCollisionConfiguration collisionConfiguration;
	private btCollisionDispatcher dispatcher;
	private btSequentialImpulseConstraintSolver solver;
	public static btDiscreteDynamicsWorld dynamicsWorld;
	public IntrigueTotalPhysicsSystem(ISystem upstream) {
		super(upstream);
		Bullet.init();
		broadphase = new btDbvtBroadphase();
		collisionConfiguration = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfiguration);
		solver = new btSequentialImpulseConstraintSolver();
		dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
		dynamicsWorld.setGravity(new Vector3(0, -900.8f, 0));
		//dynamicsWorld.applyGravity();
	}
	public void destroy() {
		dispatcher.dispose();
        collisionConfiguration.dispose();
	}
	public void register(int guid) {
		super.register(guid);
		if(Intrigue.mamaDukes.size <= guid) {
			System.out.println("Bad guid passed to mamadukes, are you messing with me?");
			return;
		}
		Entity g = Intrigue.mamaDukes.get(guid);
		if(g.getPhysicalComponent().getPhysicsBody() != null) {
			dynamicsWorld.addRigidBody(g.getPhysicalComponent().getPhysicsBody().getRigidBody());
			internal.add(guid);
		}
		
		/*if(Intrigue.mamaDukes.get(guid).getKinematicRagdoll() != null) {
			for(AtomicPhysicalObject a : g.getAllAtomicPhysicalObjects()) {		
				dynamicsWorld.addRigidBody(a.getRigidBody());
			}
			internal.add(guid);
		}*/
	}
	
	public void deregister(int guid) {
		super.deregister(guid);
		internal.removeValue(guid, false);
	}
	public void update(float delta) {
		super.update(delta);
		final float sdelta = Math.min(1f / 30f, delta);

        dynamicsWorld.stepSimulation(sdelta, 10, 1f/60f);
	}
	
}