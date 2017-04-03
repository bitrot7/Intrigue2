package com.gamedev.drifter.system;


import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.gamedev.drifter.entity.DrifterObject;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.ParticleObject;
import com.mk.intrigue.system.GameSys;
import com.mk.intrigue.system.IntrigueGraphicSystem;

public class DrifterParticleSys extends GameSys {
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
		
	}
	public void register(int guid) {
		internal.add(guid);
		DrifterObject g  = Intrigue.mamaDukes.get(guid);
		this.requireComponent(g.getParticleComponent(), this, g);
	}
	public void deregister(int guid) {
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