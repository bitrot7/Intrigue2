//DrifterTargetingAISystem
package com.gamedev.drifter.system;


import com.badlogic.gdx.utils.Array;
import com.gamedev.drifter.entity.DrifterEntity;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.system.GameSys;
import com.mk.intrigue.system.ISystem;

public class DrifterTargetingAISystem extends GameSys {
	private Array<Integer> internal = new Array<Integer>();
	private Array<Integer> team1 = new Array<Integer>();
	private Array<Integer> team2 = new Array<Integer>();
	public DrifterTargetingAISystem(ISystem upstream) {
		super(upstream);
	}
	public void register(int guid) {
		super.register(guid);
		internal.add(guid);
		DrifterEntity d = Intrigue.mamaDukes.get(guid);
		//this.requireComponent(d.getAIComponent(), this, d);
		this.requireComponent(d.getTargetingComponent(), this, d);
		if(d.getTargetingComponent().getTeamNumber() == 1) {
			team1.add(guid);
		}
		else {
			team2.add(guid);
		}
	}	
	public void deregister(int guid) {
		super.deregister(guid);
		internal.removeValue(guid,true);
		team1.removeValue(guid,true);
		team2.removeValue(guid, true);
	}
	public void update(float delta) {
		super.update(delta);
		for(Integer i : internal) {
			DrifterEntity d = Intrigue.mamaDukes.get(i);
			if(d.getAIComponent() != null) {
				if(d.getTargetingComponent().getTeamNumber() == 1) {
					int i2 = team2.get(0);
					d.getTargetingComponent().setTarget(i2);
				}
				else {
					int i2 = team1.get(0);
					d.getTargetingComponent().setTarget(i2);
				}
			}
		}
	}
	
}