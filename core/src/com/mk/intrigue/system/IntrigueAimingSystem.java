package com.mk.intrigue.system;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.Gdx;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.entity.Entity2;
import com.mk.intrigue.entity.component.IntrigueActionsComponent;

/*
*	System requirments for entity:
*		-DrifterObject
*		-IntriguePhysicalComponent (atomic)
*		-DrifterCharacterActionsComponent
*		-DrifterFiringComponent
*		-Decal or soon to be Crosshair
*		
*	Details:
*		This system draws the Ray or bullet path.  It also controls the aiming of the ray via DrifterCharacterActionsComponent.
*		The rays that are drawn here can be used as data for ray tracing e.g. as they are in the DrifterBulletCollisionSystem.
*		For easy code reuse three systems should be necessary.  One for aiming the ray, one for firing based on rate and ammo 
*		(active ray) and one for determining collisions.
*		Essentially one system (this) for ray creation, another (not yet implemented) for ray activation, and another for active ray collisions (DrifterBulletCollisionSystem)
*/
public class IntrigueAimingSystem extends SystemDecorator {
	private Array<Integer> internal = new Array<Integer>();
	private DecalBatch decalBatch;
	private int dy = -100;
	private int dz = 100;
	private int dx = 0;
	public IntrigueAimingSystem(ISystem upstream) {
		super(upstream);
		Gdx.gl.glDepthRangef(0f, 1.0f); //0 beeing the near plane and 1 being the far plane
		decalBatch = new DecalBatch(new CameraGroupStrategy(IntrigueGraphicSystem.cam));
	}
	public void register(int guid) {
		super.register(guid);
		Entity2 d = Intrigue.mamaDukes.get(guid);
		this.requireComponent(d.getPhysicalComponent(), this, d);
		this.requireComponent(d.getActionsComponent(), this, d);
		this.requireComponent(d.getFiringComponent(), this, d);
		internal.add(guid);
	}
	public void deregister(int guid) {
		super.deregister(guid);
		internal.removeValue(guid,true);
	}
	public void update(float delta) {
		super.update(delta);
		for(Integer i : internal) {
			
			Entity2 d = Intrigue.mamaDukes.get(i);
			IntrigueActionsComponent s = d.getActionsComponent();
			
			Vector3 m_crosshair_pos = new Vector3();
			Vector3 m_furthest_target_pos = new Vector3();
			Vector3 m_camera_pos = new Vector3();
			
			
			if(this.dy> 400) {
				this.dy = 399;
			}
			else if(this.dy < -400) {
				this.dy = -399;
			}
			
			
			if(s.isAiming()) {
				if(s.isTurningUp()) {
					this.dy += 2;
				}
				else if(s.isTurningDown()) {
					this.dy -= 2;
				}
			}
			else {
				if(s.isTurningUp()) {
					this.dy += 7;
				}
				else if(s.isTurningDown()) {
					this.dy -= 7;
				}
			}
			Matrix4 trans = new Matrix4();
			d.getPhysicalComponent().getPhysicsBody().getMotionState().getWorldTransform(trans); //coordinate system of player
			
			m_crosshair_pos.add(this.dx,this.dy, this.dz);
			
			m_crosshair_pos.mul(trans);
			
			m_camera_pos.set(IntrigueGraphicSystem.cam.position);
			Vector3 temp = new Vector3();
			Vector3 temp2 = new Vector3();
			temp.set(m_camera_pos);
			temp2.set(m_crosshair_pos);
			temp2.sub(temp);
			float t = 1000f;
			float x = temp.x + t*temp2.x;
			float y = temp.y + t*temp2.y;
			float z = temp.z + t*temp2.z;
			m_furthest_target_pos.add(x, y, z);
			
			
			
			
			d.getFiringComponent().setBulletEndPoint(m_furthest_target_pos);
			d.getFiringComponent().setBulletStartPoint(m_camera_pos);
			d.getFiringComponent().setCrosshairPos(m_crosshair_pos);
			
			IntrigueGraphicSystem.cam.lookAt(d.getFiringComponent().getCrosshairPos());
			Decal cross = d.getFiringComponent().getCrosshairGraphic();
			if(cross != null) {
				cross.setPosition(d.getFiringComponent().getCrosshairPos());
				cross.lookAt(IntrigueGraphicSystem.cam.position, IntrigueGraphicSystem.cam.up);
				decalBatch.add(cross);
			}
		}
		decalBatch.flush();
	}
}