//non general Purpose.
package com.mk.intrigue.system;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.entity.Gobject;

//follows root Physical Object 

/*
*	This controls the object that is being chased but it does not describe where the camera looks.
*	This is intentional so that other games can simply use the cam.lookAt() to look at a crosshair or puck, or other object while follwing another.
*	
*	System requirements for entity:
*		-Gobject
*		-IntriguePhysicalComponent
*		
*	This system is a little different.
*/
public class IntrigueThirdPersonCameraViewSystem extends GameSys {
	protected final Array<Integer> internal = new Array<Integer>();
	private final Vector3 tmp3 = new Vector3();
	private final Vector3 tmp2 = new Vector3();
	private final Vector3 tmp = new Vector3();
	private final Matrix4 xxx = new Matrix4();
	protected final PerspectiveCamera cam;
	
	public IntrigueThirdPersonCameraViewSystem() {
		cam = IntrigueGraphicSystem.cam;
	}
	public void register(int guid) {
		Gobject q = Intrigue.mamaDukes.get(guid);
		this.requireComponent(q.getPhysicalComponent(), this, q);
		internal.add(guid);
	}
	public void deregister(int guid) {
		internal.removeValue(guid, false);
	}
	public void update(float delta) {
		for(Integer i : internal) {
			Gobject q = Intrigue.mamaDukes.get(i);
			q.getPhysicalComponent().getPhysicsBody().getMotionState().getWorldTransform(xxx);
			xxx.getTranslation(this.tmp);
			//this.tmp3.set(this.tmp);
			//Vector3 tmp4 = new Vector3();
			this.tmp2.set(0,350,-500);
			q.getPhysicalComponent().getPhysicsBody().getRigidBody().getOrientation().transform(this.tmp2);
			this.tmp2.add(this.tmp);
			cam.position.set(this.tmp2);
			cam.up.set(0,1,0);
			//this.tmp3.add(0,100,0);
			cam.update();
		}
	}
}