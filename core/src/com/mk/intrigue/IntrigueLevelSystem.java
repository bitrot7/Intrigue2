package com.mk.intrigue;

import com.badlogic.gdx.utils.Array;

public class IntrigueLevelSystem implements GameSys {
	private final Array<Integer> internal = new Array<Integer>();
	@Override
	public void register(int guid) {

		internal.add(guid);
	}

	@Override
	public void update(float delta) {
		
		for(Integer i : internal) {
			Gobject g = Intrigue.mamaDukes.get(i);
			if(!g.levelComponent.getLevelSoundEffect().isPlaying()) {
				g.levelComponent.getLevelSoundEffect().play();
			}
		}
	}

	@Override
	public void deregister(int guid) {
		
		internal.removeValue(guid, false);
	}

}
