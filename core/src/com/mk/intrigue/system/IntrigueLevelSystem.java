package com.mk.intrigue.system;

import com.badlogic.gdx.utils.Array;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.entity.Gobject;

public class IntrigueLevelSystem extends GameSys {
	private final Array<Integer> internal = new Array<Integer>();
	//private final DrifterParticleSys particleSys = new DrifterParticleSys();
	public IntrigueLevelSystem(ISystem upstream) {
		super(upstream);
	}
	@Override
	public void register(int guid) {
		super.register(guid);
		Gobject g = Intrigue.mamaDukes.get(guid);
		this.requireComponent(g.getLevelComponent(), this, g);
		internal.add(guid);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		for(Integer i : internal) {
			Gobject g = Intrigue.mamaDukes.get(i);
			if(!g.getLevelComponent().getLevelSoundEffect().isPlaying()) {
				g.getLevelComponent().getLevelSoundEffect().play();
			}
		}
	}
	@Override
	public void deregister(int guid) {
		super.deregister(guid);
		internal.removeValue(guid, false);
	}

}
