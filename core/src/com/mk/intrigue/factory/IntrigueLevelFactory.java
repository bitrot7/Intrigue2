package com.mk.intrigue.factory;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.gamedev.drifter.entity.DrifterObject;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.entity.Gobject;

public class IntrigueLevelFactory extends AbstractFactory {
	public Gobject createLevel(String path_to_model, Matrix4 trans) {
		return new Gobject.Builder(Intrigue.mamaDukes.size)
		.IntrigueModelComponent(path_to_model)
		.IntriguePhysicalComponent(0, trans)
		.Build();
		//.ParticleComponent("Blizzard",
				//"3DParticles/blizzard.pfx",new Vector3(1000,1000, -2500), /*perhaps create a Weather Object in level*/
				//new Vector3(3000, 1000,2000 )).Build();
	}
}
