//DrifterTargetingAISystem
package com.mk.intrigue;


import com.badlogic.gdx.utils.Array;

public class DrifterTargetingAISystem implements GameSys {
	private Array<Integer> internal = new Array<Integer>();
	private Array<Integer> team1 = new Array<Integer>();
	private Array<Integer> team2 = new Array<Integer>();
	public void register(int guid) {
		internal.add(guid);
		DrifterObject d = Intrigue.mamaDukes.get(guid);
		if(d.getTargetingComponent().getTeamNumber() == 1) {
			team1.add(guid);
		}
		else {
			team2.add(guid);
		}
	}	
	public void deregister(int guid) {
		internal.removeValue(guid,true);
		team1.removeValue(guid,true);
		team2.removeValue(guid, true);
	}
	public void update(float delta) {
		for(Integer i : internal) {
			DrifterObject d = Intrigue.mamaDukes.get(i);
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