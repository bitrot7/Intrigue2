/*
*	The System responsible for rendering appropriate Game Objects
*/
//Default Graphic System.
package com.mk.intrigue.system;
import com.mk.intrigue.Intrigue;


import com.mk.intrigue.entity.Entity;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

/*
*	System requirements for entity:
*		-Gobject
*		-IntrigueModelComponent
*/
public class IntrigueGraphicSystem extends SystemDecorator{
	public static ModelBatch modelBatch;
	public static PerspectiveCamera cam;
    private CameraInputController camController;
	private Environment environment;
	protected final Array<Integer> internal = new Array<Integer>();
	public IntrigueGraphicSystem(ISystem upstream) {
		super(upstream);
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.near = .1f;
		cam.far = 7500;
        cam.update();
		
		modelBatch = new ModelBatch();
		environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(.8f, .8f, .8f, -1f, -0.8f, -0.2f));
        
		camController = new CameraInputController(cam);
        Gdx.input.setInputProcessor(camController);
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);
		Gdx.gl20.glDepthFunc(GL20.GL_LESS);
		Gdx.gl.glDepthRangef(1.0f, 2.0f);
		Gdx.gl.glClearColor(.160f, .160f, .160f, 1);
		Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	public void register(int guid) {
		super.register(guid);
		Entity g  = Intrigue.mamaDukes.get(guid);
		this.requireComponent(g.getModelComponent(), this, g);
		
		internal.add(guid);
	}
	public void deregister(int guid) {
		super.deregister(guid);
		internal.removeValue(guid, false);
	}
	public void destroy() {
		modelBatch.dispose();
	}
	private boolean isVisible(Camera cam, ModelInstance m) {
		Vector3 position = new Vector3();
		m.transform.getTranslation(position);
		BoundingBox bounds = new BoundingBox();
		m.calculateBoundingBox(bounds);
		Vector3 out = new Vector3();
		Vector3 out2 = new Vector3();
		bounds.getCenter(out);
		position.add(out);
		bounds.getDimensions(out2);
		out2.scl(1.5f);
        return cam.frustum.boundsInFrustum(position, out2);
	}
	public void update(float delta) {
		//handleInput();
		super.update(delta);
		camController.update();
		
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		modelBatch.begin(cam);
		for(Integer i : internal) {
			
				Entity q = Intrigue.mamaDukes.get(i);
				ModelInstance m = q.getModelComponent().getModel();
				
					if(m == null) {
						System.out.println("Failure:  Object registered with 3DGraphicSystem but it has no modleInstance object!");
						modelBatch.end();
						return;
					}
					if(isVisible(cam, m)) {
						modelBatch.render(m, environment);
					}
		}
		if(this.stagger(0.1f)) {
			modelBatch.flush();
		}
		modelBatch.end();
	}
	
}