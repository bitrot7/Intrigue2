package com.mk.intrigue.entity.component;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;

public class IntrigueMusicComponent implements IComponent {
	private Music music;
	
	public IntrigueMusicComponent(String path_to_file) {
		this.music = Gdx.audio.newMusic(Gdx.files.internal(path_to_file));
	}
	public Music getMusic() {
		return this.music;
	}
}
