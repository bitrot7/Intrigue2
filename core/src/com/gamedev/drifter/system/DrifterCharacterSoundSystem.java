package com.gamedev.drifter.system;

import com.badlogic.gdx.utils.Array;
import com.gamedev.drifter.entity.DrifterObject;
import com.gamedev.drifter.entity.component.DrifterCharacterActionsComponent;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.system.GameSys;

public class DrifterCharacterSoundSystem extends GameSys {
	private final Array<Integer> internal = new Array<Integer>();
	private float last_time;
	private final float shortest_time_between_shots = .11f;
	private final float shortest_time_between_steps = .53f;
	private final float shortest_time_between_steps_sideways = .38f;
	@Override
	public void register(int x) {
		DrifterObject d = Intrigue.mamaDukes.get(x);
		this.requireComponent(d.getCharacterActionsComponent(), this, d);
		this.requireComponent(d.getCharacterSoundComponent(), this, d);
		internal.add(x);
	}

	@Override
	public void update(float delta) {
		this.last_time += delta;
		for(Integer i : internal) {
			DrifterObject d = Intrigue.mamaDukes.get(i);
			if(d.getCharacterActionsComponent().isFiring()) {
				//System.out.println(delta);
				if(this.last_time > this.shortest_time_between_shots) {
					d.getCharacterSoundComponent().getShootingSound().play();
					this.last_time = 0;
				}
			}
			DrifterCharacterActionsComponent dca = d.getCharacterActionsComponent();
			if(dca.isForward() || dca.isBackward()) {
				if(this.last_time > this.shortest_time_between_steps) {
					d.getCharacterSoundComponent().getWalkingSound().play(.5f);
					this.last_time = 0;
				}
			}
			else if(dca.isLeft() || dca.isRight()) {
				if(this.last_time > this.shortest_time_between_steps_sideways) {
					d.getCharacterSoundComponent().getWalkingSound().play(.5f);
					this.last_time = 0;
				}
			}
		}

	}

	@Override
	public void deregister(int x) {
		// TODO Auto-generated method stub
		internal.removeValue(x, false);
	}

}
