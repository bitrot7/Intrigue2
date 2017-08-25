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

//Default CollisionSYs
/*
*	System requirments for entity:
*		-IntriguePhysicalComponent
*/
public class IntrigueTotalPhysicsSystem {
	
	private btBroadphaseInterface broadphase;
	private btDefaultCollisionConfiguration collisionConfiguration;
	private btCollisionDispatcher dispatcher;
	private btSequentialImpulseConstraintSolver solver;
	public static btDiscreteDynamicsWorld dynamicsWorld;
	
	public IntrigueTotalPhysicsSystem() {
		
		Bullet.init();
		broadphase = new btDbvtBroadphase();
		collisionConfiguration = new btDefaultCollisionConfiguration();
		dispatcher = new btCollisionDispatcher(collisionConfiguration);
		solver = new btSequentialImpulseConstraintSolver();
		dynamicsWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
		dynamicsWorld.setGravity(new Vector3(0, -9.8f, 0));
		//dynamicsWorld.applyGravity();
	}
	public void destroy() {
		dispatcher.dispose();
        collisionConfiguration.dispose();
	}
	
	
	
}