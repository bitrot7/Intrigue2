//A Firing Component for drifter objects that can shoot.
package com.mk.intrigue.entity.component;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g3d.decals.Decal;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.Gdx;

public class IntrigueFiringComponent extends BaseComponent {
	private Decal crosshairGraphic;
	private final Vector3 bullet_start = new Vector3();
	private final Vector3 bullet_end = new Vector3();
	private final Vector3 m_crosshairPosition = new Vector3();
	/*
		A bullet is a 3 dim line segment.
		Any entity that can shoot must have this component.
	*/
	public IntrigueFiringComponent(Vector3 start, Vector3 end) { //probably dont use
		bullet_start.set(start);
		bullet_end.set(end);
		
	}
	public IntrigueFiringComponent() {
		//Audio.newSound();
	}
	public IntrigueFiringComponent(String path) {
		TextureRegion t = new TextureRegion(new Texture(Gdx.files.internal(path)));
		crosshairGraphic = Decal.newDecal(t, true);
		crosshairGraphic.setScale(.8f);
	}
	public void setBulletStartPoint(Vector3 in) {
		bullet_start.set(in);
	}
	public Vector3 getBulletStartPoint() {
		return bullet_start;
	}
	public void setBulletEndPoint(Vector3 in) {
		bullet_end.set(in);
	}
	public Vector3 getBulletEndPoint() {
		return bullet_end;
	}
	public void setCrosshairPos(Vector3 m_pos) {
		this.m_crosshairPosition.set(m_pos);
	}
	public Vector3 getCrosshairPos() {
		return this.m_crosshairPosition;
	}
	public Decal getCrosshairGraphic() {
		return crosshairGraphic;
	}
	public Sound getGunSound() {
		return null;
	}
	
}