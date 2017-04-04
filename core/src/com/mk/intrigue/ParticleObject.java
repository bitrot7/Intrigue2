//ParticleObject.java
//wrapper for particle effect
package com.mk.intrigue;

import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.gamedev.drifter.entity.DrifterObject;
import com.gamedev.drifter.system.DrifterParticleSys;

public class ParticleObject {
	private String name;
	private ParticleEffect pfx;
	private final Matrix4 m_transform = new Matrix4();
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
	public ParticleObject(String name, String path_to_asset, Matrix4 m_trans) {
		this(name, path_to_asset);
		this.setTransform(m_trans);
	}
	public void setTransform(Matrix4 m_trans) {
		this.m_transform.set(m_trans);
		this.pfx.setTransform(m_trans);
	}
	public Matrix4 getTransform() {
		return this.m_transform;
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