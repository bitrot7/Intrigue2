package com.mk.intrigue.entity.component;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.mk.intrigue.entity.Entity2;
import com.mk.intrigue.exception.ComponentMissingException;
import com.mk.intrigue.object.AtomicPhysicalObject;
import com.mk.intrigue.system.IntrigueGraphicSystem;

public class AimingComponent extends BaseComponent {

	
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
	
	
	
	/**
	 * this is a method for aiming up and down based on Action input s.
	 * @param acActionComponent : actions taken by user.
	 */
	private void moveCrosshairPos(IntrigueActionsComponent acActionComponent) {
		if(this.dy> 400) {
			this.dy = 399;
		}
		else if(this.dy < -400) {
			this.dy = -399;
		}
		if(acActionComponent.isAiming()) {
			if(acActionComponent.isTurningUp()) {
				this.dy += 5;
			}
			else if(acActionComponent.isTurningDown()) {
				this.dy -= 5;
			}
		}
		else {
			if(acActionComponent.isTurningUp()) {
				this.dy += 10;
			}
			else if(acActionComponent.isTurningDown()) {
				this.dy -= 10;
			}
		}
	}
	
	private void calculateBulletRay(AtomicPhysicalObject atPhysicalObject ) {
		
		atPhysicalObject.getMotionState()
			.getWorldTransform(this.trans); //coordinate system of player
		
		
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
	
	private void resetUtilVectors() {
		this.m_camera_pos.set(0, 0, 0);
		this.m_crosshair_pos.set(0,0,0);
		this.m_furthest_target_pos.set(0,0,0);
		this.temp.set(0,0,0);
		this.temp2.set(0,0,0);
		this.trans.idt();
	}
	
	@Override
	public void HandleUpdate(float delT) {
		
		Entity2 d = GetParentEntity();
		this.resetUtilVectors();
		
		if(d.getActionsComponent() != null)
		{
			this.moveCrosshairPos(d.getActionsComponent());
		}
		
		else
		{
			throw new ComponentMissingException(AimingComponent.class.getName(), 
					IntrigueActionsComponent.class.getName(), GetParentEntity().getGUID());
		}
		
		if(d.getPhysicalComponent() != null)
		{
			this.calculateBulletRay(d.getPhysicalComponent().getPhysicsBody());
		}
		
		else
		{
			throw new ComponentMissingException(AimingComponent.class.getName(), 
					IntriguePhysicalComponent.class.getName(), GetParentEntity().getGUID());
		}
		
		if(d.getFiringComponent() != null)
		{
			this.setBulletTrajectory(d.getFiringComponent());
		}
		
		else
		{
			throw new ComponentMissingException(AimingComponent.class.getName(), 
					IntrigueFiringComponent.class.getName(), GetParentEntity().getGUID());
		}
	}

}
