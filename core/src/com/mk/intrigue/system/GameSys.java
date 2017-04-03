package com.mk.intrigue.system;

import com.mk.intrigue.entity.Gobject;
import com.mk.intrigue.entity.component.IComponent;
import com.mk.intrigue.exception.ComponentMissingException;


public abstract class GameSys {
	public abstract void register(int x);
	public abstract  void update(float delta);
	public abstract void deregister(int x);
	/**
	 * @throws ComponentMissingException if Component not Found
	 */
	public void requireComponent(IComponent c, GameSys s, Gobject g) {
		if(c == null) {
			throw new ComponentMissingException("Component",s.getClass().getName(), g.getGUID());
		}
	}
}