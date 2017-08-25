package com.mk.intrigue.entity.component;

import com.mk.intrigue.entity.Entity2;
import com.mk.intrigue.*;
import com.mk.intrigue.exception.*;
public abstract class BaseComponent implements IComponent {
	/**
	 * Flag that determines if this component will handle an update.
	 */
	public boolean active = false;
	
	/**
	 * Integer id for the @class Entity that this component is attached to,
	 * this eid resolves to a guid of an entity which is used to get a 
	 * reference to the attached entity object because of this a component
	 * instance may only be attached to a single entity instance.
	 */
	private int eid = -1;
	
	protected BaseComponent(int eid)
	{
		this.eid = eid;
	}
	
	protected BaseComponent()
	{
		
	}
	
	/**
	 * @brief called by Entity when constructing component.
	 * 
	 * @param eid the entity id (index) of entity.
	 */
	public void setEid(int eid) 
	{
		this.eid = eid;
	}
	
	/**
	 * the time span between the current frame and the last frame in seconds
	 */
	@Override
	public abstract void HandleUpdate(float delT);
	
	public Entity2 GetParentEntity()
	{
		if(eid == -1)
		{
			throw new LeakedComponentException("Call to GetParentEntity() with no attached entity");
		}
		return Intrigue.mamaDukes.get(eid);
	}
}
