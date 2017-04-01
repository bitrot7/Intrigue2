package com.mk.intrigue;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;

public class IntrigueLevelComponent {
	private Music level_sound_effect;
	public IntrigueLevelComponent(String path) {
		this.level_sound_effect = Gdx.audio.newMusic(Gdx.files.internal(path));
	}
	public Music getLevelSoundEffect() {
		return this.level_sound_effect;
	}
}
