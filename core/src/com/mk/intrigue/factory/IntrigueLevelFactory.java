package com.mk.intrigue.factory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Json;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.entity.Entity2;
import com.mk.intrigue.entity.Entity;
import com.mk.intrigue.object.ParticleObject;

public class IntrigueLevelFactory<T extends Entity2> {
	private final Json serializer = new Json();
	public T createLevel(String path_to_model,String path_to_music,
			String path_to_weather_fx, Matrix4 m_trans, Class<T> type) {
		int guid = Intrigue.mamaDukes.size;
		
		ParticleObject p = new ParticleObject("dummy", path_to_weather_fx, m_trans);
		p.getParticleEffect().scale(1000f, 1000f, 1000f);
		return type.cast(new T.DrifterObjectBuilder(guid)
		.BaseObject(new Entity.Builder(guid)
			.IntrigueModelComponent(path_to_model)
			.IntriguePhysicalComponent(0, m_trans)
			.IntrigueMusicComponent(path_to_music)
			.ParticleComponent(p)
			.Build())
		.Build());
		 
		//.ParticleComponent("Blizzard",
				//"3DParticles/blizzard.pfx",new Vector3(1000,1000, -2500), /*perhaps create a Weather Object in level*/
				//new Vector3(3000, 1000,2000 )).Build();a
	}
	public T createLevel(String path_to_model, String path_to_weather_fx, Matrix4 m_trans, Class<T> type) {
		ParticleObject p = new ParticleObject("dummy", path_to_weather_fx, m_trans);
		p.getParticleEffect().scale(1000f, 1000f, 1000f);
		int guid = Intrigue.mamaDukes.size;
		
		return type.cast(new T.DrifterObjectBuilder(guid)
		.BaseObject(new Entity.Builder(guid)
			.IntrigueModelComponent(path_to_model)
			.IntriguePhysicalComponent(0, m_trans)
			.ParticleComponent(p)
			.Build())
		.Build());
	}
	public T createLevel(String path_to_json_config) {
		FileHandle file = Gdx.files.internal(path_to_json_config);
		//serializer.fromJson(LecelConfig.class, file.readString());
		return null;
	}
	
}
