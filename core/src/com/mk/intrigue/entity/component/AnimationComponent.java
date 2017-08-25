package com.mk.intrigue.entity.component;

import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.mk.intrigue.Intrigue;
import com.mk.intrigue.entity.Entity2;
import com.mk.intrigue.exception.ComponentMissingException;
import com.mk.intrigue.object.MyAnimationListener;

public class AnimationComponent extends BaseComponent {
	
	private MyAnimationListener garbage = new MyAnimationListener();
	private AnimationController animation3d;
	
	public AnimationComponent(IntrigueModelComponent modelComp)
	{
		if(modelComp.getModel() != null)
		{
			animation3d = new AnimationController(modelComp.getModel());
		}
		else
		{
			throw new ComponentMissingException(AnimationComponent.class.getName(),
					IntrigueModelComponent.class.getName(),
					-1);
		}
	}
	
	
	private void Animate()
	{
		Entity2 entity = GetParentEntity();
		IntrigueActionsComponent s = entity.getActionsComponent();
		if(s != null)
		{
			if(s.isFiring()) {
				
				if(s.isForward()) {
					animation3d.animate("Run Firing",-1,2,garbage, .03f);
				}
				if(s.isBackward()) {
					animation3d.animate("Run_backwards",-1,2, garbage ,.03f);
				}
				if(s.isLeft()) {
					animation3d.animate("Left Fire",-1,2, garbage, .03f);
				}
				if(s.isRight()) {
					animation3d.animate("Right FIre",-1,2, garbage, .03f);
				}
				if(s.isIdle()){
					//System.out.println("here");
					animation3d.animate("Idle Firing",-1,2, garbage, .03f);
				}
			/*	if(s.isTurningUp()) {
					c.animate("Stand Aim Up",-1,2, garbage, .03f);
				}
				if(s.isTurningDown()) {
					c.animate("Stand Aim Down",-1,2, garbage, .03f);
				}*/
			}
			else if(s.isAiming()) {
				if(s.isForward()) {
					animation3d.animate("Aim Running",-1,2,garbage, .03f);
				}
				if(s.isBackward()) {
					animation3d.animate("Run_backwards",-1,2, garbage ,.03f);
				}
				if(s.isLeft()) {
					animation3d.animate("Strafe Left",-1,2, garbage, .03f);
				}
				if(s.isRight()) {
					animation3d.animate("Strafe Right",-1,2, garbage, .03f);
				}
				if(s.isIdle()) {
					animation3d.animate("Idle Aim",-1,2, garbage, .03f);
				}
				/*if(s.isTurningUp()) {
					c.animate("Stand Aim Up",-1,2, garbage, .03f);
				}
				if(s.isTurningDown()) {
					c.animate("Stand Aim Down",-1,2, garbage, .03f);
				}*/
			}
			else {
				if(s.isForward()) {
					animation3d.animate("Run_Forwards",-1,2,garbage, .03f);
				}
				if(s.isBackward()) {
					animation3d.animate("Run_backwards",-1,2, garbage ,.03f);
				}
				if(s.isLeft()) {
					animation3d.animate("Strafe Left",-1,2 ,garbage, .03f);
				}
				if(s.isRight()) {
					animation3d.animate("Strafe Right",-1,2 ,garbage, .03f);
				}
				if(s.isIdle()){
					animation3d.animate("Idle",-1, garbage, .03f);
				}
				if(s.isReload()) {
					animation3d.animate("Idle Reload", 1,5 ,garbage, .03f);
				}
				/*if(s.isTurningUp()) {
					c.animate("Stand Aim Up",-1,2, garbage, .03f);
				}
				if(s.isTurningDown()) {
					c.animate("Stand Aim Down",-1,2, garbage, .03f);
				}*/
			}
		}
		else
		{
			throw new ComponentMissingException(AnimationComponent.class.getName(),
					IntrigueActionsComponent.class.getName(),
					entity.getGUID());
		}
		
		
	}
	
	@Override
	public void HandleUpdate(float delT) {
		Animate();
		animation3d.update(delT);
	}

}
