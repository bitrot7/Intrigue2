package com.mk.intrigue;

public class DrifterTargetingComponent {
	int team_number = 0;
	int target_id = 0;
	public DrifterTargetingComponent(int team_number) {
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
}