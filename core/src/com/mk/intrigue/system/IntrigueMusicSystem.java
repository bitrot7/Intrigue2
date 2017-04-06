package com.mk.intrigue.system;

import com.badlogic.gdx.utils.Array;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.entity.Entity;

public class IntrigueMusicSystem extends SystemDecorator {
	private final Array<Integer> internal = new Array<Integer>();
	//private final DrifterParticleSys particleSys = new DrifterParticleSys();
	public IntrigueMusicSystem(ISystem upstream) {
		super(upstream);
	}
	@Override
	public void register(int guid) {
		super.register(guid);
		Entity g = Intrigue.mamaDukes.get(guid);
		this.requireComponent(g.getMusicComponent(), this, g);
		internal.add(guid);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		for(Integer i : internal) {
			Entity g = Intrigue.mamaDukes.get(i);
			if(!g.getMusicComponent().getMusic().isPlaying()) {
				g.getMusicComponent().getMusic().play();
			}
		}
	}
	@Override
	public void deregister(int guid) {
		super.deregister(guid);
		internal.removeValue(guid, false);
	}

}
