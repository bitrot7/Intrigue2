package com.gamedev.drifter.system;

import com.badlogic.gdx.utils.Array;
import com.gamedev.drifter.entity.DrifterEntity;
import com.gamedev.drifter.entity.component.DrifterCharacterActionsComponent;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.system.GameSys;
import com.mk.intrigue.system.ISystem;

public class DrifterCharacterSoundSystem extends GameSys {
	private final Array<Integer> internal = new Array<Integer>();
	private final float shortest_time_between_steps = .53f;
	private final float shortest_time_between_steps_sideways = .38f;
	private final float shortest_time_between_shots = .11f;
	public DrifterCharacterSoundSystem(ISystem upstream) {
		super(upstream);
	}
	@Override
	public void register(int guid) {
		super.register(guid);
		DrifterEntity d = Intrigue.mamaDukes.get(guid);
		this.requireComponent(d.getCharacterActionsComponent(), this, d);
		this.requireComponent(d.getCharacterSoundComponent(), this, d);
		internal.add(guid);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		for(Integer i : internal) {
			DrifterEntity d = Intrigue.mamaDukes.get(i);
			if(d.getCharacterActionsComponent().isFiring()) {
				if(this.stagger(this.shortest_time_between_shots)) {
					d.getCharacterSoundComponent().getShootingSound().play();
				}
				
			}
			DrifterCharacterActionsComponent dca = d.getCharacterActionsComponent();
			if(dca.isForward() || dca.isBackward()) {
				if(this.stagger(this.shortest_time_between_steps)) {
					d.getCharacterSoundComponent().getWalkingSound().play(.5f);
				}
			}
			else if(dca.isLeft() || dca.isRight()) {
				if(this.stagger(this.shortest_time_between_steps_sideways)) {
					d.getCharacterSoundComponent().getWalkingSound().play(.5f);
				}
			}
		}

	}

	@Override
	public void deregister(int guid) {
		super.deregister(guid);
		internal.removeValue(guid, false);
	}

}
