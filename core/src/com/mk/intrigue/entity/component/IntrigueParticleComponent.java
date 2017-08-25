//DrifterParticleComponent
package com.mk.intrigue.entity.component;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;
import com.badlogic.gdx.math.Vector3;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.entity.Entity;
import com.mk.intrigue.exception.ComponentMissingException;
import com.mk.intrigue.object.ParticleObject;
import com.mk.intrigue.system.IntrigueGraphicSystem;

/**
 *  Component class is responsible for the creation and rendering of
 *  point sprite batch particles.
 * 
 *	Requires {@link IntriguePhysicalComponent} to be defined in parent entity
 */
public class IntrigueParticleComponent extends BaseComponent {
	private ObjectMap<String, ParticleObject> map = new ObjectMap<String, ParticleObject>();
	private Array<ParticleObject> pobjs = new Array<ParticleObject>();


	private ModelBatch modelBatch;
	
	
	public IntrigueParticleComponent(String name, String path, Vector3 pos, Vector3 scale) {
		CommonInit();
		pobjs.add(new ParticleObject(name, path,pos,scale));
	}
	private void CommonInit()
	{
		modelBatch = IntrigueGraphicSystem.modelBatch;	
	}
	
	public IntrigueParticleComponent(ParticleObject p) {
		CommonInit();
		this.pobjs.add(p);
	}
	public IntrigueParticleComponent(Array<String> names, Array<String> paths) {
		CommonInit();
		if(names.size != paths.size) {
			System.out.println("ASSERTION FAILED: Particle path array and particle name array do not match");
			return;
		}
		for(int i = 0; i < names.size; i++) {
			ParticleObject obj = new ParticleObject(names.get(i), paths.get(i));
			map.put(names.get(i), obj);
			pobjs.add(obj);
		}
	}
	public ParticleObject getParticleObject(String name) {
		return map.get(name);
	}
	public Array<ParticleObject> getParticleObjects() {
		return pobjs;
	}
	@Override
	public void HandleUpdate(float delT) {
		
		//System.out.println(Intrigue.mamaDukes.get(i).getParticleComponent());
		Entity entity = GetParentEntity();
		
		if(entity.getPhysicalComponent() == null)
		{
			throw new ComponentMissingException(IntrigueParticleComponent.class.getName(), 
					IntriguePhysicalComponent.class.getName(), 
					GetParentEntity().getGUID());
		}
		IntrigueGraphicSystem.pointSpriteBatch.begin();
		Array<ParticleObject> pobjs = getParticleObjects();
			for(ParticleObject p : pobjs) {
				//pointSpriteBatch.add(p.getParticleEffect());
				if(p.isActive()) {
					entity.getPhysicalComponent().getPhysicsBody()
					.getMotionState().getWorldTransform(p.getTransform());
					p.getParticleEffect().update();
					p.getParticleEffect().draw();
				}
			}
		
		
		IntrigueGraphicSystem.pointSpriteBatch.end();
		//particleSystem.removeAll();

		modelBatch.render(IntrigueGraphicSystem.particleSystem);
	
	}
	
}