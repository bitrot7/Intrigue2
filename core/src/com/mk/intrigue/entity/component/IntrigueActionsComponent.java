package com.mk.intrigue.entity.component;


public class IntrigueActionsComponent extends BaseComponent {
	private boolean idle = true;
	private boolean forward = false;
	private boolean backward = false;
	private boolean left = false;
	private boolean right = false;
	private boolean sprint = false;
	private boolean turnRight = false;
	private boolean turnLeft = false;
	private boolean turnUp = false;
	private boolean turnDown = false; //:D!!
	private boolean aiming = false;
	private boolean firing = false;
	private boolean reload = false;
	private boolean jump = false;
	private boolean bleeding = false;
	
	public void setSprint(boolean b) {
		sprint = b;
	}
	public void setForward(boolean b) {
		forward = b;
	}
	public void setBackward(boolean b) {
		backward = b;
	}
	public void setLeft(boolean b) {
		left =b;
	}
	public void setRight(boolean b) {
		right = b;
	}
	public void setTurnLeft(boolean b) {
		turnLeft = b;
	}
	public void setTurnRight(boolean b) {
		turnRight = b;
	}
	//:D!!!
	public void setTurnUp(boolean b) {
		turnUp = b;
	}
	public void setTurnDown(boolean b) {
		turnDown = b;
	}
	public void setIdle(boolean b) {
		idle = b;
	}
	public void setAiming(boolean b) {
		aiming = b;
	}
	public void setFiring(boolean b) {
		firing = b;
	}
	public void setReload(boolean b) {
		reload = b;
	}
	public void setJump(boolean b) {
		jump =b;
	}
	public void setBleeding(boolean b) {
		bleeding = b;
	}
	public boolean isSprint() {
		return sprint;
	}
	public boolean isLeft() {
		return left;
	}
	public boolean isRight() {
		return right;
	}
	public boolean isBackward() {
		return backward;
	}
	public boolean isForward() {
		return forward;
	}
	public boolean isTurningRight() {
		return turnRight;
	}
	public boolean isTurningLeft() {
		return turnLeft;
	}
	// :D!
	public boolean isTurningUp() {
		return turnUp;
	}
	public boolean isTurningDown() {
		return turnDown;
	}
	public boolean isIdle() {
		return idle;
	}
	public boolean isAiming() {
		return aiming;
	}
	public boolean isFiring() {
		return firing;
	}
	public boolean isReload() {
		return reload;
	}
	public boolean isJump() {
		return jump;
	}
	public boolean isBleeding() {
		return bleeding;
	}
	public void reset() {
		forward = false;
		sprint = false;
		backward = false;
		left = false;
		right = false;
		turnRight = false;
		turnLeft = false;
		turnUp = false;
		turnDown = false;
		idle = true;
		//aiming = false;
		firing = false;
		reload = false;
		jump = false;
		bleeding = false;
	}
	@Override
	public void HandleUpdate(float delT) {
		// TODO Auto-generated method stub
		// do nothing .. holds state info.
	}
}