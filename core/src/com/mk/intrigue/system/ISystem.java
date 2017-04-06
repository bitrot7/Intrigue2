package com.mk.intrigue.system;

public interface ISystem {
	public void register(int guid);
	public void deregister(int guid);
	public void update(float delta);
}
