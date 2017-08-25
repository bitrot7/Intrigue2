package com.mk.intrigue.entity.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.mk.intrigue.entity.Entity;
import com.mk.intrigue.exception.ComponentMissingException;
import com.mk.intrigue.system.IntrigueGraphicSystem;


/**
 * Component that handles 2D graphics creation in 3D space.
 * 
 * Requires {@link IntrigueFiringComponent} but this is an extraneous condition.
 * 
 * TODO make a crosshairDecalComponent that extends DecalComponent this way DecalComponent
 * can become atomic (no extraneous components required).
 * 
 * @author wind2
 *
 */
public class DecalComponent extends BaseComponent {

	
	public DecalComponent()
	{
		Gdx.gl.glDepthRangef(0f, 1.0f); //0 beeing the near plane and 1 being the far plane
	}
	
	private void drawCrosshair(IntrigueFiringComponent f) 
	{
		IntrigueGraphicSystem.cam.lookAt(f.getCrosshairPos());
		Decal cross = f.getCrosshairGraphic();
		if(cross != null) {
			cross.setPosition(f.getCrosshairPos());
			cross.lookAt(IntrigueGraphicSystem.cam.position, IntrigueGraphicSystem.cam.up);
			IntrigueGraphicSystem.decalBatch.add(cross);
		}
	
	}
	
	@Override
	public void HandleUpdate(float delT) 
	{
		Entity entity = GetParentEntity();
		if(entity.getFiringComponent() != null)
		{
			this.drawCrosshair(entity.getFiringComponent());
		}
		else
		{
			throw new ComponentMissingException(DecalComponent.class.getName(), 
					IntrigueFiringComponent.class.getName(), 
					GetParentEntity().getGUID());
		}
	}

}
