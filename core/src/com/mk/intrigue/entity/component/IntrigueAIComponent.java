package com.mk.intrigue.entity.component;


public class IntrigueAIComponent extends BaseComponent {
	private IntriguePhysicalComponent m_adversary_positional_info;
	private IntrigueFiringComponent m_adversary_aiming_info;
	private IntrigueActionsComponent adversary_actions;
	
	public IntrigueAIComponent() {
		
	}
	public void setAdversaryFiringComponent(IntrigueFiringComponent dfc) {
		this.m_adversary_aiming_info = dfc;
	}
	public void setAdversaryCharacterActionsComponent(IntrigueActionsComponent dcac) {
		this.adversary_actions = dcac;
	}
	public void setAdversaryPhysicalComponent(IntriguePhysicalComponent ipc) {
		this.m_adversary_positional_info = ipc;
	}
	public IntrigueFiringComponent getAdversaryFiringComponent() {
		return this.m_adversary_aiming_info;
	}
	public IntrigueActionsComponent getAdversaryCharacterActionsComponent() {
		return this.adversary_actions;
	}
	public IntriguePhysicalComponent getAdversaryPhysicalComponent() {
		return this.m_adversary_positional_info;
	}
}