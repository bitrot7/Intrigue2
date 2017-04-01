//ParticleObject.java
//wrapper for particle effect
package com.mk.intrigue;

import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.math.Vector3;
import com.gamedev.drifter.DrifterObject;
import com.gamedev.drifter.DrifterParticleSys;

public class ParticleObject {
	private String name;
	private ParticleEffect pfx;
	//private final Vector3 position = new Vector3();
	//private final Vector3 scale = new Vector3();
	private boolean active = true;
	
	public ParticleObject(String name, String path) {
		this.name = name;
		if(!DrifterObject.assetManager.isLoaded(path)) {
			ParticleEffectLoader.ParticleEffectLoadParameter loadParam = new ParticleEffectLoader.ParticleEffectLoadParameter(DrifterParticleSys.particleSystem.getBatches());
			DrifterObject.assetManager.load(path, ParticleEffect.class, loadParam);
			DrifterObject.assetManager.finishLoading();
		}
		ParticleEffect originalEffect = DrifterObject.assetManager.get(path, ParticleEffect.class);
		this.pfx = originalEffect.copy();
		this.pfx.init();
	}
	public ParticleObject(String name, String path, Vector3 position, Vector3 scale) {
		this(name, path);
		this.pfx.translate(position);
		this.pfx.scale(scale);
	}
	public void setActive(boolean b) {
		this.active = b;
	}
	public boolean isActive() {
		return this.active;
	}
	public String getName() {
		return this.name;
	}
	public ParticleEffect getParticleEffect() {
		return this.pfx;
	}
}