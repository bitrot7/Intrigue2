/*
*	The System responsible for rendering appropriate Game Objects
*/
//Default Graphic System.
package com.mk.intrigue.system;
import com.mk.intrigue.Intrigue;


import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.decals.CameraGroupStrategy;
import com.badlogic.gdx.graphics.g3d.decals.DecalBatch;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

/*
*	System requirements for entity:
*		-Gobject
*		-IntrigueModelComponent
*/
public class IntrigueGraphicSystem {
	public static ModelBatch modelBatch;
	public static PerspectiveCamera cam;
	public static PointSpriteParticleBatch pointSpriteBatch;
	public static ParticleSystem particleSystem;
	public static DecalBatch decalBatch;
	public IntrigueGraphicSystem() {
		// TODO: Messy, refactored from old system notion
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.near = .1f;
		cam.far = 7500;
        cam.update();
        
        decalBatch = new DecalBatch(new CameraGroupStrategy(IntrigueGraphicSystem.cam));
    	modelBatch = new ModelBatch();
    	IntrigueGraphicSystem.particleSystem = new ParticleSystem();
    	pointSpriteBatch = new PointSpriteParticleBatch();
    	pointSpriteBatch.setCamera(IntrigueGraphicSystem.cam);
		particleSystem.add(pointSpriteBatch);
		
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl20.glDepthFunc(GL20.GL_LESS);
		Gdx.gl.glDepthRangef(1.0f, 2.0f);
		Gdx.gl.glClearColor(.160f, .160f, .160f, 1);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public void flush()
	{
		modelBatch.begin(cam);
		modelBatch.flush();
		modelBatch.end();
		decalBatch.flush();
	}
	
}