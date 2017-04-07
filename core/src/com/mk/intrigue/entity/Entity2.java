package com.mk.intrigue.entity;
//DrifterObject.java



import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.mk.intrigue.entity.component.IntrigueAIComponent;
import com.mk.intrigue.entity.component.IntrigueActionsComponent;
import com.mk.intrigue.entity.component.IntrigueSoundEffectComponent;
import com.mk.intrigue.entity.component.IntrigueFiringComponent;
import com.mk.intrigue.entity.component.IntrigueTargetingComponent;

public class Entity2 extends Entity {
	/**
	 * anything with models must be marked transient.
	 */
	transient private AnimationController animation3d;
	private IntrigueFiringComponent firingComponent;
	private IntrigueActionsComponent actionsComponent;
	private IntrigueAIComponent aiComponent;
	private IntrigueTargetingComponent targetingComponent;
	private IntrigueSoundEffectComponent characterSoundComponent;
	transient public static final AssetManager assetManager = new AssetManager();
	transient public static final ModelBuilder modelBuilder = new ModelBuilder();
	private Entity2(DrifterObjectBuilder dob) {
		super(dob);
		this.animation3d = dob.animation3d;
		this.firingComponent = dob.firingComponent;
		this.actionsComponent = dob.actionsComponent;
		this.aiComponent = dob.aiComponent;
		this.targetingComponent = dob.targetingComponent;
		this.characterSoundComponent = dob.characterSoundComponent;
	}
	/*
	*	This has got to go.
	*/
	public AnimationController getAnimationController() {
		return animation3d;
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
	*	extend the builder as well. keep the design pattern.  it is nice.
	*/
	public static class DrifterObjectBuilder extends Entity.Builder {
		private AnimationController animation3d;
		private IntrigueFiringComponent firingComponent;
		private IntrigueActionsComponent actionsComponent;
		private IntrigueAIComponent aiComponent;
		private IntrigueTargetingComponent targetingComponent;
		private IntrigueSoundEffectComponent characterSoundComponent;
		/**
		 * A getter for object modification during build process.
		 * 
		 * @return
		 */
		public AnimationController getAnimation3d() {
			return animation3d;
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

		public DrifterObjectBuilder(int guid) {
			super(guid);
		}

		public DrifterObjectBuilder BaseObject(Entity g) {
			super.guid = g.getGUID();
			super.modelComponent = g.getModelComponent();
			super.physicalComponent = g.getPhysicalComponent();
			super.controllerComponent = g.getControllerComponent();
			super.musicComponent = g.getMusicComponent();
			super.particleComponent = g.getParticleComponent();
			return this;
		}
		
		/*MUST COME AFTER Model Build*/
		public DrifterObjectBuilder AnimationController() {
			if(this.modelComponent == null) {
				System.out.println("Failure model == null, no target for animation");
				return null;
			}
			animation3d = new AnimationController(this.modelComponent.getModel());
			return this;
		}
		public DrifterObjectBuilder CharacterActionsComponent() {
			actionsComponent = new IntrigueActionsComponent();
			return this;
		}
	
		/**
		*	Pew. Pew.. Builds a component for this object allowing it to shoot.
		*/
		public DrifterObjectBuilder Fireable() {
			firingComponent = new IntrigueFiringComponent();
			return this;
		}
		/**
		*	Pew. Pew.. Builds a component for this object allowing it to shoot. with a graphical crosshair
		*/
		public DrifterObjectBuilder Fireable(String path) {
			firingComponent = new IntrigueFiringComponent(path);
			return this;
		}
		public DrifterObjectBuilder TargetingAI(int team_number) {
			targetingComponent = new IntrigueTargetingComponent(team_number);
			return this;
		}
		public DrifterObjectBuilder ShootingSoldierAI() {
			aiComponent = new IntrigueAIComponent();
			return this;
		}
		public DrifterObjectBuilder CharacterSoundComponent(String walk_sound, String shoot_sound) {
			this.characterSoundComponent = new IntrigueSoundEffectComponent(walk_sound, shoot_sound);
			return this;
		}
		public Entity2 Build() {
			return new Entity2(this);
		}
	}
}