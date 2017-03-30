/*
*	The System responsible for rendering appropriate Game Objects
*/
//Default Graphic System.
package com.mk.intrigue;
import com.mk.intrigue.Intrigue;


import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.GL20;

/*
*	System requirements for entity:
*		-Gobject
*		-IntrigueModelComponent
*/
public class IntrigueGraphicSystem implements GameSys{
	public static ModelBatch modelBatch;
	public static PerspectiveCamera cam;
    private CameraInputController camController;
	private Environment environment;
	protected final Array<Integer> internal = new Array<Integer>();
	public IntrigueGraphicSystem() {
		
		cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.near = .1f;
		cam.far = 10000;
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
		if(Intrigue.mamaDukes.get(guid) == null) {
			System.out.println("WARNING: Ignoring attempt to register Gobject " + guid + " to Graphics Sub System.  Did you build it with a ModelInstance?  Because it needs one.");
			return;
		}
		
		internal.add(guid);
	}
	public void deregister(int guid) {
		internal.removeValue(guid, false);
	}
	public void destroy() {
		modelBatch.dispose();
	}
	/*private boolean isVisible(Camera cam, ModelInstance m) {
		Vector3 position = new Vector3();
		m.transform.getTranslation(position);
		BoundingBox bounds = new BoundingBox();
		m.calculateBoundingBox(bounds);
		Vector3 out = new Vector3();
		Vector3 out2 = new Vector3();
		bounds.getCenter(out);
		position.add(out);
		bounds.getDimensions(out2);
		out2.scl(4.5f);
        return cam.frustum.boundsInFrustum(position, out2);
	}*/
	public void update(float delta) {
		//handleInput();
		
		camController.update();
		
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		modelBatch.begin(cam);
		for(Integer i : internal) {
			
				Gobject q = Intrigue.mamaDukes.get(i);
				ModelInstance m = q.getModelComponent().getModel();
				
					if(m == null) {
						System.out.println("Failure:  Object registered with 3DGraphicSystem but it has no modleInstance object!");
						modelBatch.end();
						return;
					}
					//if(isVisible(cam, m)) {
						modelBatch.render(m, environment);
					//}
		}
		modelBatch.flush();
		modelBatch.end();
	}
	
}