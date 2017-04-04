package com.mk.intrigue.entity.component;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.Gdx;
import com.mk.intrigue.Weather;

public class IntrigueLevelComponent implements IComponent {
	private Music level_sound_effect;
	private Weather weather;
	
	private IntrigueLevelComponent(Builder b) {
		this.level_sound_effect = b.level_sound_effect;//Gdx.audio.newMusic(Gdx.files.internal(path));
	}
	public Music getLevelSoundEffect() {
		return this.level_sound_effect;
	}
	public Weather getWeather() {
		return weather;
	}
	public static class Builder {
		protected Music level_sound_effect;
		protected Weather weather;
		/**
		 * create default values after code is tested.
		 */
		public Builder() {
			//this.level_sound_effect = Gdx.audio.newMusic(Gdx.files.internal("SoundEffects/stages/snow stage/wind1.mp3"));
			//this.weather = new Weather("myweather", "3DParticles/blizzard.pfx", new Matrix4());
		}
		public Builder music(String path_to_file) {
			this.level_sound_effect = Gdx.audio.newMusic(Gdx.files.internal(path_to_file));
			return this;
		}
		public Builder weather(String path_to_pfx, Matrix4 m_trans) {
			this.weather = new Weather("myweather",path_to_pfx, m_trans );
			return this;
		}
		public IntrigueLevelComponent build() {
			return new IntrigueLevelComponent(this);
		}
	}
}
