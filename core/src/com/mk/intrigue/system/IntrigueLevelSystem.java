package com.mk.intrigue.system;

import com.badlogic.gdx.utils.Array;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.entity.Gobject;

public class IntrigueLevelSystem extends GameSys {
	private final Array<Integer> internal = new Array<Integer>();
	@Override
	public void register(int guid) {
		Gobject g = Intrigue.mamaDukes.get(guid);
		this.requireComponent(g.getLevelComponent(), this, g);
		internal.add(guid);
	}

	@Override
	public void update(float delta) {
		
		for(Integer i : internal) {
			Gobject g = Intrigue.mamaDukes.get(i);
			if(!g.getLevelComponent().getLevelSoundEffect().isPlaying()) {
				g.getLevelComponent().getLevelSoundEffect().play();
			}
		}
	}

	@Override
	public void deregister(int guid) {
		
		internal.removeValue(guid, false);
	}

}
