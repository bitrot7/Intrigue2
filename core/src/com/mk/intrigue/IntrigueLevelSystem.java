package com.mk.intrigue;

import com.badlogic.gdx.utils.Array;

public class IntrigueLevelSystem implements GameSys {
	private final Array<Integer> internal = new Array<Integer>();
	@Override
	public void register(int guid) {
		// TODO Auto-generated method stub
		internal.add(guid);
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		for(Integer i : internal) {
			Gobject g = Intrigue.mamaDukes.get(i);
			
				g.levelComponent.getLevelSoundEffect().play();
			
		}
	}

	@Override
	public void deregister(int guid) {
		// TODO Auto-generated method stub
		internal.removeValue(guid, false);
	}

}
