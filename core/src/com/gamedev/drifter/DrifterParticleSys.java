package com.gamedev.drifter;


import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.mk.intrigue.GameSys;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.IntrigueGraphicSystem;
import com.mk.intrigue.ParticleObject;

public class DrifterParticleSys implements GameSys {
	/*
	*	System requirements for entity:
	*		-DrifterObject
	*		-
	*/
	private Array<Integer> internal = new Array<Integer>();
	public static ParticleSystem particleSystem;
	private PointSpriteParticleBatch pointSpriteBatch;
	private ModelBatch modelBatch;
	public DrifterParticleSys() {
		modelBatch = IntrigueGraphicSystem.modelBatch;
		particleSystem = new ParticleSystem();
		pointSpriteBatch = new PointSpriteParticleBatch();
		pointSpriteBatch.setCamera(IntrigueGraphicSystem.cam);
		particleSystem.add(pointSpriteBatch);
		

		//particleSystem.add(effect);
	}
	public void register(int guid) {
		internal.add(guid);
	}
	public void deregister(int guid) {
		internal.removeValue(guid, true);
	}
	public void update(float delta) {
		pointSpriteBatch.begin();
		for(Integer i : internal) {
			//System.out.println(Intrigue.mamaDukes.get(i).getParticleComponent());
			Array<ParticleObject> pobjs = Intrigue.mamaDukes.get(i).getParticleComponent().getParticleObjects();
			for(ParticleObject p : pobjs) {
				//pointSpriteBatch.add(p.getParticleEffect());
				if(p.isActive()) {
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