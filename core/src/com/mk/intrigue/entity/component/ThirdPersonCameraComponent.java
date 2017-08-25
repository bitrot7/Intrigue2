package com.mk.intrigue.entity.component;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.entity.Entity;
import com.mk.intrigue.exception.ComponentMissingException;
import com.mk.intrigue.system.IntrigueGraphicSystem;

/**
 * Controls the following of a character as a third person camera
 * 
 * TODO need camera/world collision logic.
 * 
 * Requires {@link IntriguePhysicalComponent} to be defined in the parent
 * entity.
 * 
 * @author wind2
 *
 */
public class ThirdPersonCameraComponent extends BaseComponent {
	
	protected final Array<Integer> internal = new Array<Integer>();
	private final Vector3 tmp2 = new Vector3();
	private final Vector3 tmp = new Vector3();
	private final Matrix4 xxx = new Matrix4();
	protected final PerspectiveCamera cam;
	
	public ThirdPersonCameraComponent()
	{
		cam = IntrigueGraphicSystem.cam;
	}
	
	private void resetUtilVectors() 
	{
		this.tmp.scl(0f);
		this.tmp2.scl(0);
	}
	
	@Override
	public void HandleUpdate(float delT) 
	{
		// TODO Auto-generated method stub
		this.resetUtilVectors();
		Entity q = GetParentEntity();
		if(q.getPhysicalComponent() == null)
		{
			throw new ComponentMissingException(ThirdPersonCameraComponent.class.getName(), 
					IntriguePhysicalComponent.class.getName(), 
					GetParentEntity().getGUID());
		}
		
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
