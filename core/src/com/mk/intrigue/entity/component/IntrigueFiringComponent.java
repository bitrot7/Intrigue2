//A Firing Component for drifter objects that can shoot.
package com.mk.intrigue.entity.component;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.ClosestRayResultCallback;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.IntrigueGraphicalDebugger;
import com.mk.intrigue.entity.Entity;
import com.mk.intrigue.exception.ComponentMissingException;
import com.mk.intrigue.system.IntrigueTotalPhysicsSystem;

/**
 * Creates Bullet ray and ray traces to find collisions in the physics world.
 * 
 * Requires {@link IntrigueActionsComponent} 
 * @author wind2
 *
 */
public class IntrigueFiringComponent extends BaseComponent {
	private Decal crosshairGraphic;
	private final Vector3 bullet_start = new Vector3();
	private final Vector3 bullet_end = new Vector3();
	private final Vector3 m_crosshairPosition = new Vector3();
	
	private static Vector3 rayFrom = new Vector3();
	private static Vector3 rayTo = new Vector3();
	
	private static final ClosestRayResultCallback callback = new ClosestRayResultCallback(rayFrom, rayTo);
	private final float shortest_time_between_shots = .11f;
	
	/*
		A bullet is a 3 dim line segment.
		Any entity that can shoot must have this component.
	*/
	public IntrigueFiringComponent(Vector3 start, Vector3 end) { //probably dont use
		bullet_start.set(start);
		bullet_end.set(end);
		
	}
	public IntrigueFiringComponent() {
		//Audio.newSound();
	}
	public IntrigueFiringComponent(String path) {
		TextureRegion t = new TextureRegion(new Texture(Gdx.files.internal(path)));
		crosshairGraphic = Decal.newDecal(t, true);
		crosshairGraphic.setScale(.8f);
	}
	public void setBulletStartPoint(Vector3 in) {
		bullet_start.set(in);
	}
	public Vector3 getBulletStartPoint() {
		return bullet_start;
	}
	public void setBulletEndPoint(Vector3 in) {
		bullet_end.set(in);
	}
	public Vector3 getBulletEndPoint() {
		return bullet_end;
	}
	public void setCrosshairPos(Vector3 m_pos) {
		this.m_crosshairPosition.set(m_pos);
	}
	public Vector3 getCrosshairPos() {
		return this.m_crosshairPosition;
	}
	public Decal getCrosshairGraphic() {
		return crosshairGraphic;
	}
	public Sound getGunSound() {
		return null;
	}
	
	@Override
	public void HandleUpdate(float delT) {
		
		Entity d = GetParentEntity();
		if(d.getActionsComponent() == null)
		{
			throw new ComponentMissingException(IntrigueFiringComponent.class.getName(), 
					IntrigueActionsComponent.class.getName(), 
					GetParentEntity().getGUID());
		}
		
		rayFrom.set(bullet_start);
		rayTo.set(bullet_end);
		// define how far bullet can travel

		callback.setCollisionObject(null);
		callback.setClosestHitFraction(1f);
		callback.setRayFromWorld(rayFrom);
		callback.setRayToWorld(rayTo);
		IntrigueGraphicalDebugger.drawDebugRay(rayFrom, rayTo);
		
		// ray trace the bullet from "from" to "to" 
		IntrigueTotalPhysicsSystem.dynamicsWorld.rayTest(rayFrom, rayTo, callback);
		
		if(callback.hasHit() && d.getActionsComponent().isFiring()) 
		{
			//int index = callback.getCollisionObject().getUserIndex();
			
			//if(internal.contains(index, true)) 
			//{
			//	System.out.println("Object " + index + "was shot!");
			//}
			
		}
	}
	
}