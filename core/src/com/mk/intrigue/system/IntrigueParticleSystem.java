package com.mk.intrigue.system;


import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.entity.Entity2;
import com.mk.intrigue.object.ParticleObject;

public class IntrigueParticleSystem extends SystemDecorator {
	/*
	*	System requirements for entity:
	*		-DrifterObject
	*		-
	*/
	private Array<Integer> internal = new Array<Integer>();
	public static ParticleSystem particleSystem;
	private PointSpriteParticleBatch pointSpriteBatch;
	private ModelBatch modelBatch;
	public IntrigueParticleSystem(ISystem upstream) {
		super(upstream);
		modelBatch = IntrigueGraphicSystem.modelBatch;
		particleSystem = new ParticleSystem();
		pointSpriteBatch = new PointSpriteParticleBatch();
		pointSpriteBatch.setCamera(IntrigueGraphicSystem.cam);
		particleSystem.add(pointSpriteBatch);
		
	}
	public void register(int guid) {
		super.register(guid);
		internal.add(guid);
		Entity2 g  = Intrigue.mamaDukes.get(guid);
		this.requireComponent(g.getParticleComponent(), this, g);
	}
	public void deregister(int guid) {
		super.deregister(guid);
		internal.removeValue(guid, true);
	}
	public void update(float delta) {
		super.update(delta);
		pointSpriteBatch.begin();
		for(Integer i : internal) {
			//System.out.println(Intrigue.mamaDukes.get(i).getParticleComponent());
			Array<ParticleObject> pobjs = Intrigue.mamaDukes.get(i).getParticleComponent().getParticleObjects();
			for(ParticleObject p : pobjs) {
				//pointSpriteBatch.add(p.getParticleEffect());
				if(p.isActive()) {
					Intrigue.mamaDukes.get(i).getPhysicalComponent().getPhysicsBody()
					.getMotionState().getWorldTransform(p.getTransform());
					p.getParticleEffect().update();
					p.getParticleEffect().draw();
				}
			}
		}
		
		pointSpriteBatch.end();
		//particleSystem.removeAll();
		modelBatch.render(particleSystem);
	}
}