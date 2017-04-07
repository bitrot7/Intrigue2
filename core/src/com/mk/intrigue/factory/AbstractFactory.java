package com.mk.intrigue.factory;


import com.mk.intrigue.entity.Entity;

public abstract class AbstractFactory<T extends Entity> {

	public abstract	T createLevel();

}
