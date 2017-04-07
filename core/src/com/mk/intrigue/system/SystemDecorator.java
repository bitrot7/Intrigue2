package com.mk.intrigue.system;

import com.mk.intrigue.entity.Entity;
import com.mk.intrigue.entity.component.IComponent;
import com.mk.intrigue.exception.ComponentMissingException;


public abstract class SystemDecorator implements ISystem {
	private float last_time;
	private ISystem upstream_system;
	
	public SystemDecorator(ISystem ups) {
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
		this.stagger(100000000f); //reset last_time variable when it gets very long
	}
	@Override
	public void deregister(int guid) {
		this.upstream_system.deregister(guid);
	}
	/**
	 * @throws ComponentMissingException if Component not Found
	 */
	public void requireComponent(IComponent c, ISystem s, Entity g) {
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