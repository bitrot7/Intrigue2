package com.mk.intrigue.factory;

import com.gamedev.drifter.entity.DrifterObject;
import com.mk.intrigue.entity.Gobject;

public abstract class AbstractFactory<T extends Gobject> {

	public abstract	T createLevel();

}
