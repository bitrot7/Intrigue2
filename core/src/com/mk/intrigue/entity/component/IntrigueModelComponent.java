//Intrigue Visual Component
package com.mk.intrigue.entity.component;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.assets.AssetManager;

public class IntrigueModelComponent implements IComponent {
	private Model asset;
	private ModelInstance model;
	public static final AssetManager assetLoader = new AssetManager();

	public IntrigueModelComponent(String path_to_asset) {
		this.loadModel(path_to_asset);
		this.model = new ModelInstance(this.asset);
	}
	private void loadModel(String path) {
		System.out.print("loading asset");
		System.out.println("");
		if(assetLoader.isLoaded(path)) {
			asset = assetLoader.get(path);
		}
		else {
			assetLoader.load(path, Model.class);
			assetLoader.finishLoading();
			this.asset = assetLoader.get(path);
		}
			
		System.out.println("asset" + asset);
	}
	public ModelInstance getModel() {
		return this.model;
	}
}