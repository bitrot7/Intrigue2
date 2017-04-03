package com.gamedev.drifter.entity.component;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.mk.intrigue.entity.component.IComponent;

/**
 * 
 * @author Matt Keating
 * 
 * 
 *
 */

public class DrifterCharacterSoundComponent implements IComponent {
	private Sound shooting_sound;
	private Sound walking_sound;
	public DrifterCharacterSoundComponent(String walking_sound, String shooting_sound) {
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
