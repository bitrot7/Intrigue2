package com.gamedev.drifter;
//DrifterObject.java



import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.utils.AnimationController;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.mk.intrigue.Gobject;
import com.mk.intrigue.Gobject.Builder;

public class DrifterObject extends Gobject { //extend Gobject
	private AnimationController animation3d;
	private DrifterFiringComponent firingComponent;
	private DrifterParticleComponent particleComponent;
	private DrifterCharacterActionsComponent characterActionsComponent;
	private DrifterAIComponent aiComponent;
	private DrifterTargetingComponent targetingComponent;
	private DrifterCharacterSoundComponent characterSoundComponent;
	public static final AssetManager assetManager = new AssetManager();
	public static final ModelBuilder modelBuilder = new ModelBuilder();
	private DrifterObject(DrifterObjectBuilder dob) {
		super(dob);
		this.animation3d = dob.animation3d;
		this.particleComponent = dob.particleComponent;
		this.firingComponent = dob.firingComponent;
		this.characterActionsComponent = dob.characterActionsComponent;
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

	public DrifterFiringComponent getFiringComponent() {
		return firingComponent;
	}
	public DrifterParticleComponent getParticleComponent() {
		return this.particleComponent;
	}
	public DrifterCharacterActionsComponent getCharacterActionsComponent() {
		return this.characterActionsComponent;
	}
	public DrifterAIComponent getAIComponent() {
		return this.aiComponent;
	}
	public DrifterTargetingComponent getTargetingComponent() {
		return this.targetingComponent;
	}
	public DrifterCharacterSoundComponent getCharacterSoundComponent() {
		return this.characterSoundComponent;
	}
	/*
	*	extend the builder as well. keep the design pattern.  it is nice.
	*/
	public static class DrifterObjectBuilder extends Gobject.Builder {
		private AnimationController animation3d;
		private DrifterFiringComponent firingComponent;
		private DrifterParticleComponent particleComponent;
		private DrifterCharacterActionsComponent characterActionsComponent;
		private DrifterAIComponent aiComponent;
		private DrifterTargetingComponent targetingComponent;
		private DrifterCharacterSoundComponent characterSoundComponent;
		public DrifterObjectBuilder(int guid) {
			super(guid);
		}

		public DrifterObjectBuilder BaseObject(Gobject g) {
			super.guid = g.getGUID();
			super.modelComponent = g.getModelComponent();
			super.physicalComponent = g.getPhysicalComponent();
			super.controllerComponent = g.getControllerComponent();
			super.levelComponent = g.getLevelComponent();
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
			characterActionsComponent = new DrifterCharacterActionsComponent();
			return this;
		}
		public DrifterObjectBuilder ParticleComponent(String name, String path, Vector3 pos, Vector3 scale) {
			particleComponent = new DrifterParticleComponent(name, path, pos, scale);
			return this;
		}
	
		/*
		*	Pew. Pew.. Builds a component for this object allowing it to shoot.
		*/
		public DrifterObjectBuilder Fireable() {
			firingComponent = new DrifterFiringComponent();
			return this;
		}
		/*
		*	Pew. Pew.. Builds a component for this object allowing it to shoot. with a graphical crosshair
		*/
		public DrifterObjectBuilder Fireable(String path) {
			firingComponent = new DrifterFiringComponent(path);
			return this;
		}
		public DrifterObjectBuilder TargetingAI(int team_number) {
			targetingComponent = new DrifterTargetingComponent(team_number);
			return this;
		}
		public DrifterObjectBuilder ShootingSoldierAI() {
			aiComponent = new DrifterAIComponent();
			return this;
		}
		public DrifterObjectBuilder CharacterSoundComponent(String walk_sound, String shoot_sound) {
			this.characterSoundComponent = new DrifterCharacterSoundComponent(walk_sound, shoot_sound);
			return this;
		}
		public DrifterObject Build() {
			return new DrifterObject(this);
		}
	}
}