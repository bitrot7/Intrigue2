package com.gamedev.drifter.system;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.gamedev.drifter.entity.DrifterObject;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.IntrigueGraphicalDebugger;
import com.mk.intrigue.system.GameSys;
import com.mk.intrigue.system.IntrigueTotalPhysicsSystem;
/*
*	System requirements for entity:
*		-DrifterObject
*		-DrifterFiringComponent
*		-Drifter States
*		
*/
public class DrifterBulletCollisionSystem extends GameSys {
	private Array<Integer> internal = new Array<Integer>();
	private static Vector3 rayFrom = new Vector3();
	private static Vector3 rayTo = new Vector3();
	
	private static final ClosestRayResultCallback callback = new ClosestRayResultCallback(rayFrom, rayTo);
	private final float shortest_time_between_shots = .11f;
	
	public DrifterBulletCollisionSystem() {
	
	}
	public void register(int guid) {
		DrifterObject d = Intrigue.mamaDukes.get(guid);
		this.requireComponent(d.getFiringComponent(),this, d);
		this.requireComponent(d.getCharacterActionsComponent(), this, d);
		internal.add(guid);
	}
	public void deregister(int guid) {
		internal.removeValue(guid, false);
	}
	public void update(float delta) {
		super.update(delta);
		//here we go
		for(Integer i : internal) {
			DrifterObject d = Intrigue.mamaDukes.get(i);
			rayFrom.set(d.getFiringComponent().getBulletStartPoint());
			rayTo.set(d.getFiringComponent().getBulletEndPoint());
			// define how far bullet can travel

			callback.setCollisionObject(null);
			callback.setClosestHitFraction(1f);
			callback.setRayFromWorld(rayFrom);
			callback.setRayToWorld(rayTo);
			IntrigueGraphicalDebugger.drawDebugRay(rayFrom, rayTo);
			IntrigueTotalPhysicsSystem.dynamicsWorld.rayTest(rayFrom, rayTo, callback);
			if(callback.hasHit() && d.getCharacterActionsComponent().isFiring() &&
					this.stagger(this.shortest_time_between_shots)) {
				int index = callback.getCollisionObject().getUserIndex();
				//System.out.println("index == " + index);
				
			
				if(internal.contains(index, true)) {

					System.out.println("Object " + index + "was shot!");
				}
				
			}
		}
	}
}