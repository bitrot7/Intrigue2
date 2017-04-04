//DrifterParticleComponent
package com.gamedev.drifter.entity.component;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector3;
import com.mk.intrigue.ParticleObject;
import com.mk.intrigue.entity.component.IComponent;
/*
*	Utilizes map.
*/
public class DrifterParticleComponent implements IComponent {
	private ObjectMap<String, ParticleObject> map = new ObjectMap<String, ParticleObject>();
	private Array<ParticleObject> pobjs = new Array<ParticleObject>();
	public DrifterParticleComponent(String name, String path, Vector3 pos, Vector3 scale) {
		//map.put(name, new ParticleObject(name, path));
		pobjs.add(new ParticleObject(name, path,pos,scale));
	}
	public DrifterParticleComponent(ParticleObject p) {
		this.pobjs.add(p);
	}
	public DrifterParticleComponent(Array<String> names, Array<String> paths) {
		if(names.size != paths.size) {
			System.out.println("ASSERTION FAILED: Particle path array and particle name array do not match");
			return;
		}
		for(int i = 0; i < names.size; i++) {
			ParticleObject obj = new ParticleObject(names.get(i), paths.get(i));
			map.put(names.get(i), obj);
			pobjs.add(obj);
		}
	}
	public ParticleObject getParticleObject(String name) {
		return map.get(name);
	}
	public Array<ParticleObject> getParticleObjects() {
		return pobjs;
	}
	
}