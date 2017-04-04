package com.mk.intrigue.system;

import com.mk.intrigue.entity.Gobject;
import com.mk.intrigue.entity.component.IComponent;
import com.mk.intrigue.exception.ComponentMissingException;


public abstract class GameSys implements ISystem {
	private float last_time;
	private ISystem upstream_system;
	
	public GameSys(ISystem ups) {
		this.upstream_system = ups;
	}
	@Override
	public void register(int guid) {
		this.upstream_system.register(guid);
	}
	@Override
	public void update(float delta) {
		this.last_time += delta;
		this.upstream_system.update(delta);
	}
	@Override
	public void deregister(int guid) {
		this.upstream_system.deregister(guid);
	}
	/**
	 * @throws ComponentMissingException if Component not Found
	 */
	public void requireComponent(IComponent c, GameSys s, Gobject g) {
		if(c == null) {
			throw new ComponentMissingException("Component",s.getClass().getName(), g.getGUID());
		}
	}
	protected boolean stagger(float length) {
		
		if(this.last_time > length) {
			this.last_time = 0;
			return true;
		}
		return false;
	}
}