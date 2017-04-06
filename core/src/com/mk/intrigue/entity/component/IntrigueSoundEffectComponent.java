package com.mk.intrigue.entity.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * 
 * @author Matt Keating
 * 
 * 
 *
 */

public class IntrigueSoundEffectComponent implements IComponent {
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
}
