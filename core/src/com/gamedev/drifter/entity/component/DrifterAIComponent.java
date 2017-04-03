package com.gamedev.drifter.entity.component;

import com.mk.intrigue.entity.component.IComponent;
import com.mk.intrigue.entity.component.IntriguePhysicalComponent;

public class DrifterAIComponent implements IComponent {
	private IntriguePhysicalComponent m_adversary_positional_info;
	private DrifterFiringComponent m_adversary_aiming_info;
	private DrifterCharacterActionsComponent adversary_actions;
	
	public DrifterAIComponent() {
		
	}
	public void setAdversaryFiringComponent(DrifterFiringComponent dfc) {
		this.m_adversary_aiming_info = dfc;
	}
	public void setAdversaryCharacterActionsComponent(DrifterCharacterActionsComponent dcac) {
		this.adversary_actions = dcac;
	}
	public void setAdversaryPhysicalComponent(IntriguePhysicalComponent ipc) {
		this.m_adversary_positional_info = ipc;
	}
	public DrifterFiringComponent getAdversaryFiringComponent() {
		return this.m_adversary_aiming_info;
	}
	public DrifterCharacterActionsComponent getAdversaryCharacterActionsComponent() {
		return this.adversary_actions;
	}
	public IntriguePhysicalComponent getAdversaryPhysicalComponent() {
		return this.m_adversary_positional_info;
	}
}