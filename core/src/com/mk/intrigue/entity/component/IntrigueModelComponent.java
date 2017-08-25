//Intrigue Visual Component
package com.mk.intrigue.entity.component;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.assets.AssetManager;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.system.IntrigueGraphicSystem;

public class IntrigueModelComponent extends BaseComponent 
{
	// TODO: Refactored from deprecated system notion.
	private Environment environment;
    private CameraInputController camController;
    private ModelBatch modelBatch;
	private Model asset;
	private ModelInstance model;
	
	public static final AssetManager assetLoader = new AssetManager();

	public IntrigueModelComponent(String path_to_asset) 
	{
		environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(.8f, .8f, .8f, -1f, -0.8f, -0.2f));
        
        modelBatch = IntrigueGraphicSystem.modelBatch;
		camController = new CameraInputController(IntrigueGraphicSystem.cam);
        //Gdx.input.setInputProcessor(camController);
		
		
		this.loadModel(path_to_asset);
		this.model = new ModelInstance(this.asset);
	}
	private void loadModel(String path) 
	{
		System.out.print("loading asset");
		System.out.println("");
		if(assetLoader.isLoaded(path)) 
		{
			asset = assetLoader.get(path);
		}
		else 
		{
			assetLoader.load(path, Model.class);
			assetLoader.finishLoading();
			this.asset = assetLoader.get(path);
		}
			
		System.out.println("asset" + asset);
	}
	
	public ModelInstance getModel() 
	{
		return this.model;
	}
	
	@Override
	public void HandleUpdate(float delT) 
	{
		//camController.update();
		modelBatch.begin(IntrigueGraphicSystem.cam);
		modelBatch.render(model, environment);
		modelBatch.end();
	}
	
	public void destroy() 
	{
		modelBatch.dispose();
	}
	
	private boolean isVisible(Camera cam, ModelInstance m) 
	{
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
}