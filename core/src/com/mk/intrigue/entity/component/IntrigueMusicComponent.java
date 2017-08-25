package com.mk.intrigue.entity.component;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;
import com.mk.intrigue.Intrigue;

/**
 * Component for attaching music to play on an entity
 * Will loop music.
 * 
 * Is an Atomic Component.
 * @author wind2
 *
 */
public class IntrigueMusicComponent extends BaseComponent {
	private Music music;
	
	public IntrigueMusicComponent(String path_to_file) {
		this.music = Gdx.audio.newMusic(Gdx.files.internal(path_to_file));
	}
	public Music getMusic() {
		return this.music;
	}
	@Override
	public void HandleUpdate(float delT) {
		if(music != null)
		{
			if(!music.isPlaying()) {
				music.play();
			}
		}
	}
}
