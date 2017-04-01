package com.mk.intrigue;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class IntrigueLevelComponent {
	private Sound level_sound_effect;
	public IntrigueLevelComponent(String fileHandle) {
		this.level_sound_effect = Gdx.audio.newSound(Gdx.files.internal("data/mysound.mp3"));
	}
	public Sound getLevelSoundEffect() {
		return this.level_sound_effect;
	}
}
