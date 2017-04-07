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
import com.mk.intrigue.entity.component.IntrigueFiringComponent;
import com.mk.intrigue.object.AtomicPhysicalObject;

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
	/**
	 * 
	 * util vectors for aiming.  used for efficiency
	 */
	private Vector3 m_crosshair_pos = new Vector3();
	private Vector3 m_furthest_target_pos = new Vector3();
	private Vector3 m_camera_pos = new Vector3();
	/**
	 * util math objects
	 */
	private Vector3 temp = new Vector3();
	private Vector3 temp2 = new Vector3();
	private Matrix4 trans = new Matrix4();
	
	public IntrigueAimingSystem(ISystem upstream) {
		super(upstream);
		Gdx.gl.glDepthRangef(0f, 1.0f); //0 beeing the near plane and 1 being the far plane
		decalBatch = new DecalBatch(new CameraGroupStrategy(IntrigueGraphicSystem.cam));
	}
	private void resetUtilVectors() {
		this.m_camera_pos.set(0, 0, 0);
		this.m_crosshair_pos.set(0,0,0);
		this.m_furthest_target_pos.set(0,0,0);
		this.temp.set(0,0,0);
		this.temp2.set(0,0,0);
		this.trans.idt();
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
	/**
	 * this is am method for aiming up and down based on Action input s.
	 * @param s : actions taken by user.
	 */
	private void moveCrosshairPos(IntrigueActionsComponent s) {
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
	}
	private void calculateBulletRay(AtomicPhysicalObject a ) {
	
		a.getMotionState().getWorldTransform(this.trans); //coordinate system of player
		
		
		this.m_crosshair_pos.add(this.dx,this.dy, this.dz);
		
		this.m_crosshair_pos.mul(this.trans);
		
		this.m_camera_pos.set(IntrigueGraphicSystem.cam.position);
		this.temp.set(this.m_camera_pos);
		this.temp2.set(this.m_crosshair_pos);
		this.temp2.sub(temp);
		float t = 1000f;
		float x = this.temp.x + t*this.temp2.x;
		float y = this.temp.y + t*this.temp2.y;
		float z = this.temp.z + t*this.temp2.z;
		this.m_furthest_target_pos.add(x, y, z);
		
	}
	private void setBulletTrajectory(IntrigueFiringComponent f) {
		f.setBulletEndPoint(this.m_furthest_target_pos);
		f.setBulletStartPoint(this.m_camera_pos);
		f.setCrosshairPos(this.m_crosshair_pos);
		
	}
	private void drawCrosshair(IntrigueFiringComponent f) {
		IntrigueGraphicSystem.cam.lookAt(f.getCrosshairPos());
		Decal cross = f.getCrosshairGraphic();
		if(cross != null) {
			cross.setPosition(f.getCrosshairPos());
			cross.lookAt(IntrigueGraphicSystem.cam.position, IntrigueGraphicSystem.cam.up);
			decalBatch.add(cross);
		}
	}
	public void update(float delta) {
		super.update(delta);
		for(Integer i : internal) {
			
			Entity2 d = Intrigue.mamaDukes.get(i);
			
			this.resetUtilVectors();
			this.moveCrosshairPos(d.getActionsComponent());
			this.calculateBulletRay(d.getPhysicalComponent().getPhysicsBody());
			this.setBulletTrajectory(d.getFiringComponent());
			this.drawCrosshair(d.getFiringComponent());
			
			
		}
		if(this.stagger(0.005f)) {
			decalBatch.flush();
		}
	}
}