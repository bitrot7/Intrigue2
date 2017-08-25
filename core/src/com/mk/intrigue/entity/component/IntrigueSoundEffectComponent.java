package com.mk.intrigue.entity.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.entity.Entity2;
import com.mk.intrigue.exception.ComponentMissingException;

/**
 * 
 * @author Matt Keating
 * 
 * 
 *
 */

public class IntrigueSoundEffectComponent extends BaseComponent {
	
	private Sound shooting_sound;
	private Sound walking_sound;
	
	public IntrigueSoundEffectComponent(String walking_sound, String shooting_sound) {
		this.walking_sound = Gdx.audio.newSound(Gdx.files.internal(walking_sound));
		this.shooting_sound = Gdx.audio.newSound(Gdx.files.internal(shooting_sound));
	}
	
	public Sound getShootingSound() {
		return this.shooting_sound;
	}
	
	public Sound getWalkingSound() {
		return this.walking_sound;
	}
	
	@Override
	public void HandleUpdate(float delT) {
		Entity2 d = GetParentEntity();
		if(d.getActionsComponent() == null)
		{
			throw new ComponentMissingException(IntrigueSoundEffectComponent.class.getName(), 
					IntrigueActionsComponent.class.getName(), 
					GetParentEntity().getGUID());
		}
		
		if(d.getActionsComponent().isFiring()) {
			shooting_sound.play();	
		}
		
		IntrigueActionsComponent dca = d.getActionsComponent();
		
		if(dca.isForward() || dca.isBackward()) {
			
			walking_sound.play(.5f);
		}
		
		else if(dca.isLeft() || dca.isRight()) {
			walking_sound.play(.5f);
		}
	}
}
