package com.mk.intrigue.entity;
//DrifterObject.java



import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.mk.intrigue.entity.component.AimingComponent;
import com.mk.intrigue.entity.component.AnimationComponent;
import com.mk.intrigue.entity.component.DecalComponent;
import com.mk.intrigue.entity.component.IntrigueAIComponent;
import com.mk.intrigue.entity.component.IntrigueActionsComponent;
import com.mk.intrigue.entity.component.IntrigueControllerComponent;
import com.mk.intrigue.entity.component.IntrigueModelComponent;
import com.mk.intrigue.entity.component.IntrigueMusicComponent;
import com.mk.intrigue.entity.component.IntrigueParticleComponent;
import com.mk.intrigue.entity.component.IntriguePhysicalComponent;
import com.mk.intrigue.entity.component.IntrigueSoundEffectComponent;
import com.mk.intrigue.entity.component.IntrigueFiringComponent;
import com.mk.intrigue.entity.component.IntrigueTargetingComponent;
import com.mk.intrigue.entity.component.MotionComponent;
import com.mk.intrigue.entity.component.ThirdPersonCameraComponent;
import com.mk.intrigue.object.ParticleObject;

/**
 * This is the extended entity class it contains 
 * more components than the @class Entity
 * 
 * @author wind2
 *
 */
public class Entity2 implements IEntity {
	
	// TODO messy refactored from Entity merge
	protected int guid;
	protected IntrigueModelComponent modelComponent = null;
	protected IntriguePhysicalComponent physicalComponent = null;
	protected IntrigueControllerComponent controllerComponent = null;
	protected IntrigueMusicComponent musicComponent = null;
	protected IntrigueParticleComponent particleComponent = null;
	protected MotionComponent motionComponent = null;
	protected DecalComponent decalComponent = null;
	protected ThirdPersonCameraComponent tpcComponent = null;
	protected AimingComponent aimingComponent = null;
	
	/**
	 * controls the animation of a 3d model
	 * Note: it cannot be serialized.
	 * 
	 * 
	 */
	transient private AnimationComponent animationComponent;
	
	/**
	 * Firing component used for fired projectile firing
	 * in general, ray tracing is the algorithm used to hit
	 * targets.
	 */
	private IntrigueFiringComponent firingComponent;
	
	/**
	 * 
	 */
	private IntrigueActionsComponent actionsComponent;
	
	/**
	 * 
	 */
	private IntrigueAIComponent aiComponent;
	
	/**
	 * 
	 */
	private IntrigueTargetingComponent targetingComponent;
	
	/**
	 * 
	 */
	private IntrigueSoundEffectComponent characterSoundComponent;
	
	/**
	 * 
	 */
	transient public static final AssetManager assetManager = new AssetManager();
	
	/**
	 * 
	 */
	transient public static final ModelBuilder modelBuilder = new ModelBuilder();
	
	
	
	private Entity2(Builder builder) {
		this.guid = builder.guid;
		this.modelComponent = builder.modelComponent;
		this.physicalComponent = builder.physicalComponent;
		this.controllerComponent = builder.controllerComponent;
		this.musicComponent = builder.musicComponent;
		this.particleComponent = builder.particleComponent;
		this.aimingComponent = builder.aimingComponent;
		this.decalComponent = builder.decalComponent;
		this.motionComponent = builder.motionComponent;
		this.tpcComponent = builder.tpcComponent;
		this.firingComponent = builder.firingComponent;
		this.actionsComponent = builder.actionsComponent;
		this.aiComponent = builder.aiComponent;
		this.targetingComponent = builder.targetingComponent;
		this.characterSoundComponent = builder.characterSoundComponent;
		this.animationComponent = builder.animationComponent;
	}
	
	/**
	 * Force all components to handle updates to the entity.
	 * 
	 * @param The time span between the current frame and the last frame in seconds
	 */
	@Override
	public void Update(float delT) {
		if(modelComponent != null)
		{
			this.modelComponent.HandleUpdate(delT);
		}
		if(physicalComponent != null)
		{
			this.physicalComponent.HandleUpdate(delT);
		}
		if(controllerComponent != null)
		{
			this.controllerComponent.HandleUpdate(delT);
		}
		if(musicComponent != null)
		{
			this.musicComponent.HandleUpdate(delT);
		}
		if(particleComponent != null)
		{
			this.particleComponent.HandleUpdate(delT);
		}
		
		if(this.aimingComponent != null)
		{
			this.aimingComponent.HandleUpdate(delT);
		}
		
		if(this.motionComponent != null)
		{
			this.motionComponent.HandleUpdate(delT);
		}
		
		if(this.decalComponent != null)
		{
			this.decalComponent.HandleUpdate(delT);
		}
		
		if(this.tpcComponent != null)
		{
			this.tpcComponent.HandleUpdate(delT);
		}
		
		if(actionsComponent != null)
		{
			this.actionsComponent.HandleUpdate(delT);
		}
		if(firingComponent != null)
		{
			this.firingComponent.HandleUpdate(delT);
		}
		if(aiComponent != null)
		{
			this.aiComponent.HandleUpdate(delT);
		}
		if(targetingComponent != null)
		{
			this.targetingComponent.HandleUpdate(delT);
		}
		if(characterSoundComponent != null)
		{
			this.characterSoundComponent.HandleUpdate(delT);
		}
		if(this.animationComponent != null)
		{
			this.animationComponent.HandleUpdate(delT);
		}
		
		// TODO more components go here
	}
	
	//////////////////////////////////////////////////////////
	// GETTERS -- ACCESSORS
	//////////////////////////////////////////////////////////
	public AnimationComponent getAnimationComponent() {
		return animationComponent;
	}

	public IntrigueFiringComponent getFiringComponent() {
		return firingComponent;
	}
	public IntrigueActionsComponent getActionsComponent() {
		return this.actionsComponent;
	}
	public IntrigueAIComponent getAIComponent() {
		return this.aiComponent;
	}
	public IntrigueTargetingComponent getTargetingComponent() {
		return this.targetingComponent;
	}
	public IntrigueSoundEffectComponent getCharacterSoundComponent() {
		return this.characterSoundComponent;
	}
	/**
	* @return A reference to the {@link IntriguePhysicalComponent} for this entity.
	*/
	public IntriguePhysicalComponent getPhysicalComponent() {
		return this.physicalComponent; //returns component with 3D physical primitive data
	}
	
	/**
	 * 
	 * @return
	 */
	public IntrigueModelComponent getModelComponent() {
		return this.modelComponent; //return component with 3D model data.
	}
	
	/**
	 * 
	 * @return
	 */
	public IntrigueControllerComponent getControllerComponent() {
		return this.controllerComponent; //return component with controller data.
	}
	
	/**
	 * 
	 * @return
	 */
	public IntrigueMusicComponent getMusicComponent() {
		return this.musicComponent;
	}
	
	/**
	 * 
	 * @return
	 */
	public IntrigueParticleComponent getParticleComponent() {
		return this.particleComponent;
	}

	/**
	 * 
	 * @return the globally unique identifier, used as an index into Entity array
	 */
	public int getGUID() {
		return this.guid;
	}
	//////////////////////////////////////////////////////////////////
	// END ACCESSORS -- GETTERS
	////////////////////////////////////////////////////////////////
	
	/**
	*	extend the builder as well. keep the design pattern.  it is nice.
	*/
	public static class Builder {
		protected int guid;
		protected IntrigueModelComponent modelComponent = null;
		protected IntriguePhysicalComponent physicalComponent = null;
		protected IntrigueControllerComponent controllerComponent = null;
		protected IntrigueMusicComponent musicComponent = null;
		protected IntrigueParticleComponent particleComponent = null;
		protected MotionComponent motionComponent = null;
		protected DecalComponent decalComponent = null;
		protected ThirdPersonCameraComponent tpcComponent = null;
		protected AimingComponent aimingComponent = null;
		private AnimationComponent animationComponent = null; 
		private IntrigueFiringComponent firingComponent = null;
		private IntrigueActionsComponent actionsComponent = null;
		private IntrigueAIComponent aiComponent = null;
		private IntrigueTargetingComponent targetingComponent = null;
		private IntrigueSoundEffectComponent characterSoundComponent = null;
		
		/**
		 * A getter for object modification during build process.
		 * 
		 * @return
		 */
		public AnimationComponent getAnimationComponent() {
			return animationComponent;
		}
		/**
		 * 
		 * @return
		 */
		public IntrigueFiringComponent getFiringComponent() {
			return firingComponent;
		}

		public IntrigueActionsComponent getCharacterActionsComponent() {
			return actionsComponent;
		}

		public IntrigueAIComponent getAiComponent() {
			return aiComponent;
		}

		public IntrigueTargetingComponent getTargetingComponent() {
			return targetingComponent;
		}

		public IntrigueSoundEffectComponent getCharacterSoundComponent() {
			return characterSoundComponent;
		}

		public Builder(int guid) {
			this.guid = guid;
		}
		/*
		*	load 3D model located at path. and build model component from it
		*/
		public Builder IntrigueModelComponent(String path_to_asset) {
			this.modelComponent = new IntrigueModelComponent(path_to_asset);
			this.modelComponent.setEid(guid);
			return this;
		}
		/*
		*	create controller data for player 1, 2, .. player_number and build component.
		*/
		public Builder IntrigueControllerComponent(int player_number) {
			this.controllerComponent = new IntrigueControllerComponent(player_number);
			this.controllerComponent.setEid(guid);
			return this;
		}
		
		/*
		*	build physical component representation from basic shape
		*/
		public Builder IntriguePhysicalComponent(btCollisionShape shape, float mass, Matrix4 transform) {
			
			this.physicalComponent = new IntriguePhysicalComponent(shape, mass, transform);
			this.physicalComponent.setEid(guid);
			return this;
		}
		/*
		*	build physical component representation from 3D model.  inanimate only.
		*	obviously model must have already been built first.
		*	Intrigue links the GUID of a game object to the "UserIndex" of a bullet world rigid body.
		*/
		public Builder IntriguePhysicalComponent(float mass, Matrix4 transform) {
			
			this.physicalComponent = new IntriguePhysicalComponent(this.modelComponent.getModel(), mass, transform);
			this.physicalComponent.setEid(guid);
			this.physicalComponent.getPhysicsBody().getRigidBody().setUserIndex(this.guid);
			return this;
		}
		public Builder IntrigueMusicComponent(String path) {
			this.musicComponent = new IntrigueMusicComponent(path);
			return this;
		}
		public Builder ParticleComponent(String name, String path, Vector3 pos, Vector3 scale) {
			particleComponent = new IntrigueParticleComponent(name, path, pos, scale);
			particleComponent.setEid(guid);
			return this;
		}
		public Builder ParticleComponent(ParticleObject p) {
			particleComponent = new IntrigueParticleComponent(p);
			particleComponent.setEid(guid);
			return this;
		}
		
		public Builder AimingComponent()
		{
			aimingComponent = new AimingComponent();
			aimingComponent.setEid(guid);
			return this;
		}
		
		public Builder DecalComponent()
		{
			decalComponent = new DecalComponent();
			decalComponent.setEid(guid);
			return this;
		}
		
		public Builder MotionComponent()
		{
			motionComponent = new MotionComponent();
			motionComponent.setEid(guid);
			return this;
		}
		
		public Builder ThirdPersonCameraComponent()
		{
			tpcComponent = new ThirdPersonCameraComponent();
			tpcComponent.setEid(guid);
			return this;
		}
		/*MUST COME AFTER Model Build*/
		public Builder AnimationComponent() {
			// TODO possible design flaw
			animationComponent = new AnimationComponent(modelComponent);
			animationComponent.setEid(guid);
			return this;
		}
		
		public Builder CharacterActionsComponent() {
			actionsComponent = new IntrigueActionsComponent();
			actionsComponent.setEid(guid);
			return this;
		}
	
		/**
		*	Pew. Pew.. Builds a component for this object allowing it to shoot.
		*/
		public Builder Fireable() {
			firingComponent = new IntrigueFiringComponent();
			firingComponent.setEid(guid);
			return this;
		}
		/**
		*	Pew. Pew.. Builds a component for this object allowing it to shoot. with a graphical crosshair
		*/
		public Builder Fireable(String path) {
			firingComponent = new IntrigueFiringComponent(path);
			firingComponent.setEid(guid);
			return this;
		}
		public Builder TargetingAI(int team_number) {
			targetingComponent = new IntrigueTargetingComponent(team_number);
			targetingComponent.setEid(guid);
			return this;
		}
		public Builder ShootingSoldierAI() {
			aiComponent = new IntrigueAIComponent();
			aiComponent.setEid(guid);
			return this;
		}
		public Builder CharacterSoundComponent(String walk_sound, String shoot_sound) {
			this.characterSoundComponent = new IntrigueSoundEffectComponent(walk_sound, shoot_sound);
			this.characterSoundComponent.setEid(guid);
			return this;
		}
		public Entity2 Build() {
			return new Entity2(this);
		}
	}
}