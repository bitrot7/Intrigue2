package com.mk.intrigue.entity.component;

import com.mk.intrigue.Intrigue;
import com.mk.intrigue.entity.Entity;

/**
 * TODO needs to be reimplemented against new model
 * @author wind2
 *
 */
public class IntrigueTargetingComponent extends BaseComponent {
	int team_number = 0;
	int target_id = 0;
	public IntrigueTargetingComponent(int team_number) {
		this.team_number = team_number;
	}
	public int getTeamNumber() {
		return this.team_number;
	}
	public void setTarget(int guid) {
		this.target_id = guid;
	}
	public int getTarget() {
		return target_id;
	}
	@Override
	public void HandleUpdate(float delT) {
		// TODO Auto-generated method stub
		//Entity2 d = GetParentEntity();
		//if(d.getAIComponent() != null) 
		//{
		//	if(d.getTargetingComponent().getTeamNumber() == 1) {
		//		int i2 = team2.get(0);
		//		d.getTargetingComponent().setTarget(i2);
		//	}
		//	else {
		//		int i2 = team1.get(0);
		//		d.getTargetingComponent().setTarget(i2);
		//	}
		//}
	}
}