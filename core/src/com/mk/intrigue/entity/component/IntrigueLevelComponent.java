package com.mk.intrigue.entity.component;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;
import com.mk.intrigue.Weather;

public class IntrigueLevelComponent implements IComponent {
	private Music level_sound_effect;
	private Weather weather;
	
	private IntrigueLevelComponent(String path) {
		this.level_sound_effect = Gdx.audio.newMusic(Gdx.files.internal(path));
	}
	public Music getLevelSoundEffect() {
		return this.level_sound_effect;
	}
	public Weather getWeather() {
		return weather;
	}
	public static class Builder {
		private Music level_sound_effect;
		private Weather weather
	}
}
