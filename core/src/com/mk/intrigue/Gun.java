package com.mk.intrigue;
import com.badlogic.gdx.audio.Sound;
public interface Gun {
	public float getFiringAccuracyEpsilon(); //epsilon = perfect_accuracy - this_gun_accuracy, where this_gun_accuracy < perfect_accuracy; 
	public float getFiringRangeEpsilon(); //epsilon = perfect_range - this_gun_range, where this_gun_range < perfect_range; 
	public float getFiringDamageEpsilon(); //epsilon = perfect_damage - this_gun_damage, where this_gun_damage < perfect_damage; 
	public int getFiringMode(); //single, burst, auto
	public int getClipSize();
	public int getNumRoundsInClip();
	public Sound getFiringSound();
	public String name();
	
}