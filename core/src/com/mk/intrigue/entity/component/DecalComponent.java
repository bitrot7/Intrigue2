package com.mk.intrigue.entity.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.mk.intrigue.entity.Entity2;
import com.mk.intrigue.exception.ComponentMissingException;
import com.mk.intrigue.system.IntrigueGraphicSystem;

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
		Entity2 entity = GetParentEntity();
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
