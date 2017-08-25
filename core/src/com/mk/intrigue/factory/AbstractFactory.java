package com.mk.intrigue.factory;

import com.mk.intrigue.entity.Entity2;



public abstract class AbstractFactory<T extends Entity2> {

	public abstract	T createLevel();

}
