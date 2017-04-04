package com.mk.intrigue.factory;
import com.badlogic.gdx.math.Matrix4;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.entity.Gobject;
import com.mk.intrigue.entity.component.IntrigueLevelComponent;

public class IntrigueLevelFactory<T extends Gobject> {
	public T createLevel(String path_to_model,String path_to_music,
			String path_to_weather_fx, Matrix4 m_trans, Class<T> type) {
		int guid = Intrigue.mamaDukes.size;
		
		return type.cast(new T.Builder(guid)
		.IntrigueModelComponent(path_to_model)
		.IntriguePhysicalComponent(0, m_trans)
		.IntrigueLevelComponent(
				new IntrigueLevelComponent.Builder()
				.music(path_to_music)
				.weather(path_to_weather_fx, m_trans)
				.build())
		.Build());
		 
		//.ParticleComponent("Blizzard",
				//"3DParticles/blizzard.pfx",new Vector3(1000,1000, -2500), /*perhaps create a Weather Object in level*/
				//new Vector3(3000, 1000,2000 )).Build();
	}
}
