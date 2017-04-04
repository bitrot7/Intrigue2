package com.mk.intrigue;

import com.badlogic.gdx.math.Matrix4;

public class Weather {
	private ParticleObject weather;
	public Weather(String name, String path, Matrix4 trans) {
		this.weather = new ParticleObject(name, path, trans);
	}
	public ParticleObject getWeatherGraphics() {
		return this.weather;
	}
}
