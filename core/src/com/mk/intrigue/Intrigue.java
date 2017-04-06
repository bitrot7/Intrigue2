/**
 * @author Matt Keating
 * Started: Tuesday, July 19th 2017
 * 
 */
package com.mk.intrigue;


import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.bullet.collision.Collision;
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mk.intrigue.entity.Entity2;
import com.mk.intrigue.entity.Entity;
import com.mk.intrigue.factory.IntrigueLevelFactory;
import com.mk.intrigue.system.BaseSystem;
import com.mk.intrigue.system.IntrigueAiSys;
import com.mk.intrigue.system.IntrigueAimingSystem;
import com.mk.intrigue.system.IntrigueAnimationSystem;
import com.mk.intrigue.system.IntrigueBulletCollisionSystem;
import com.mk.intrigue.system.IntrigueSoundEffectSystem;
import com.mk.intrigue.system.IntrigueControllerSystem;
import com.mk.intrigue.system.IntrigueMotionSystem;
import com.mk.intrigue.system.IntrigueTargetingAISystem;
import com.mk.intrigue.system.ISystem;
import com.mk.intrigue.system.IntrigueParticleSystem;
import com.mk.intrigue.system.IntrigueGraphicSystem;
import com.mk.intrigue.system.IntrigueMusicSystem;
import com.mk.intrigue.system.IntrigueThirdPersonCameraViewSystem;
import com.mk.intrigue.system.IntrigueTotalPhysicsSystem;

public class Intrigue extends ApplicationAdapter {
	public static Array<Entity2> mamaDukes = new Array<Entity2>();
	private IntrigueGraphicSystem IntrigueGraphicSys;
	private IntrigueTotalPhysicsSystem IntrigueTotalPhysicsSys;
	private IntrigueThirdPersonCameraViewSystem IntrigueCamSys;
	private IntrigueMusicSystem IntrigueLevelSys;
	private IntrigueMotionSystem IntrigueMotionSys;
	private IntrigueAnimationSystem IntrigueAnimSys;
	private IntrigueControllerSystem IntrigueControllerSys;
	private IntrigueAiSys IntrigueAiSys;
	private IntrigueParticleSystem IntrigueParticleSys;
	private IntrigueAimingSystem IntrigueAimingSystem;
	private IntrigueBulletCollisionSystem IntrigueBulletCollisionSystem;
	private IntrigueTargetingAISystem IntrigueTargetingAISystem;
	private IntrigueSoundEffectSystem DrifterCharacterSoundSys;
	private final IntrigueLevelFactory<Entity2> level_factory = new IntrigueLevelFactory<Entity2>();
	private Stage stage;
	private Table table;
	private Label text;
	public static float person_height = 60;
	public static float person_radius = 50;
	public static final int player_guid = 0;
	/*
	*	people have access to IntrigueGraphicSystem.camera, IntrigueGraphicSystem.modelBatch
	*		IntrigueTotalPhysicsSystem.dynamicsWorld
	*/
	@Override
	public void create () {
		IntrigueGraphicalDebugger.enable();
		BaseSystem base = new BaseSystem();
		IntrigueGraphicSys = new IntrigueGraphicSystem(base);
		IntrigueTotalPhysicsSys = new IntrigueTotalPhysicsSystem(base);
		IntrigueCamSys = new IntrigueThirdPersonCameraViewSystem(base);
		IntrigueAnimSys = new IntrigueAnimationSystem(base);
		IntrigueControllerSys = new IntrigueControllerSystem(base);
		IntrigueMotionSys = new IntrigueMotionSystem(base);
		IntrigueAiSys = new IntrigueAiSys(base);
        IntrigueParticleSys = new IntrigueParticleSystem(base);
		IntrigueAimingSystem = new IntrigueAimingSystem(base);
		IntrigueBulletCollisionSystem = new IntrigueBulletCollisionSystem(base);
		IntrigueTargetingAISystem = new IntrigueTargetingAISystem(base);
		DrifterCharacterSoundSys = new IntrigueSoundEffectSystem(base);
		IntrigueLevelSys = new IntrigueMusicSystem(base);
		final int team1 = 1;
		final int team2 = 2;
		
		stage = new Stage();
		table = new Table();
		table.bottom();
		table.left();
		table.setFillParent(true);
		
		LabelStyle textStyle;
		BitmapFont font = new BitmapFont();
		

		textStyle = new LabelStyle();
		textStyle.font = font;

		text = new Label("Intrigue",textStyle);
		Label text2 = new Label("@author: Matt", textStyle);
		Label text3 = new Label("OPERATION 1", textStyle);
		
		text.setFontScale(1f,1f);
		table.add(text);
		table.row();
		table.add(text2);
		table.row();
		table.add(text3);
		stage.addActor(table);

		table.setDebug(true);
		String path_to_char = "3Dmodels/Soldier/ArmyPilot/ArmyPilot.g3dj";
        //String path_to_road = "3Dmodels/Road/roadV2.g3db";
		//String path_to_rink = "3Dmodels/Rink/Rink2.g3dj";
		String path_to_snow_terrain = "3Dmodels/Snow Terrain/st5.g3dj";
		String path_to_crosshair = "2Dart/Crosshairs/rifle_cross.png";
	
		Matrix4 trans = new Matrix4();
		Matrix4 iceTrans = new Matrix4();
		Matrix4 iceTrans2 = new Matrix4();
		Matrix4 iceTrans3 = new Matrix4();
		Matrix4 iceTrans4 = new Matrix4();
		Matrix4 trans2 = new Matrix4();
		Matrix4 trans3 = new Matrix4();
	
		float iceMass = 0f;
		//btCollisionShape iceShape = null;
		trans.translate(0,1000,-2500);
		Vector3 halfExtents = new Vector3(30f,90f,25f);
		btCapsuleShape person_shape = new btCapsuleShape(30f, 90f);
		
		mamaDukes.add(new Entity2.DrifterObjectBuilder(player_guid)
					.BaseObject(new Entity.Builder(player_guid)
					.IntrigueModelComponent(path_to_char).IntrigueControllerComponent(1)
					.IntriguePhysicalComponent(person_shape, 200f, trans)
					.Build()).CharacterActionsComponent()
					.AnimationController()
					.Fireable(path_to_crosshair)
					.TargetingAI(team1)
					.CharacterSoundComponent("SoundEffects/Character/walking/step-spur.mp3", "SoundEffects/guns/M4A1.mp3")
					.Build());
		Json json_test = new Json(); 
		Entity2 d = mamaDukes.get(player_guid);
		System.out.println(json_test.prettyPrint(d));
		
		mamaDukes.get(player_guid).getPhysicalComponent()
					.getPhysicsBody().getRigidBody()
					.setAngularFactor(new Vector3(0,0,0));
		mamaDukes.get(player_guid).getPhysicalComponent()
					.getPhysicsBody().getRigidBody().applyGravity();
		
		mamaDukes.get(player_guid).getPhysicalComponent()
					.getPhysicsBody().getRigidBody()
					.setActivationState(Collision.DISABLE_DEACTIVATION);

		mamaDukes.add(level_factory.createLevel(path_to_snow_terrain,
				"3DParticles/blizzard.pfx", iceTrans, Entity2.class));
				/*
				new DrifterObject.DrifterObjectBuilder(1)
					.BaseObject(new Gobject.Builder(1)
					.IntrigueModelComponent(path_to_snow_terrain)
					.IntriguePhysicalComponent(iceMass, iceTrans)
					.ParticleComponent("Blizzard",
							"3DParticles/blizzard.pfx",new Vector3(1000,1000, -2500), 
							new Vector3(3000, 1000,2000 ))
					.Build())
					.Build());*/
		trans2.translate(-1000,1000,1500);
		
		mamaDukes.add(new Entity2.DrifterObjectBuilder(2)
					.BaseObject(new Entity.Builder(2)
					.IntrigueModelComponent(path_to_char)
					.IntriguePhysicalComponent(person_shape, 200f, trans2)
					.ParticleComponent("Blood", "3DParticles/Character/Blood.pfx", new Vector3(), new Vector3(1f,1f,1f))
					.Build())
					.CharacterActionsComponent()
					.AnimationController()
					
					.Fireable()
					.ShootingSoldierAI()
					.TargetingAI(team2)
					.Build());
		
		mamaDukes.get(2).getPhysicalComponent()
					.getPhysicsBody()
					.getRigidBody()
					.setAngularFactor(new Vector3(0,0,0));
		mamaDukes.get(2).getPhysicalComponent()
					.getPhysicsBody()
					.getRigidBody()
					.setActivationState(Collision.DISABLE_DEACTIVATION);
		
		mamaDukes.get(2).getPhysicalComponent()
					.getPhysicsBody().getRigidBody()
					.setUserIndex(2);
		
		trans3.translate(-1000, 1000, 2000);
		
		mamaDukes.add(new Entity2.DrifterObjectBuilder(3)
					.BaseObject(new Entity.Builder(3).IntrigueModelComponent(path_to_char)
					.IntriguePhysicalComponent(person_shape, 200f, trans3)
					.Build()).CharacterActionsComponent().AnimationController().Fireable()
					.ShootingSoldierAI().TargetingAI(team2)
					.Build());
		
		mamaDukes.get(3).getPhysicalComponent()
					.getPhysicsBody().getRigidBody()
					.setAngularFactor(new Vector3(0,0,0));
		mamaDukes.get(3).getPhysicalComponent()
					.getPhysicsBody().getRigidBody()
					.setActivationState(Collision.DISABLE_DEACTIVATION);
		mamaDukes.get(3).getPhysicalComponent()
					.getPhysicsBody().getRigidBody().setUserIndex(3);
		
		Vector3 rpos = new Vector3();
		
		mamaDukes.get(1).getModelComponent().getModel()
					.transform.getTranslation(rpos);
		
		iceTrans2.translate(0, 0, 6185.332f);
		
		mamaDukes.add(level_factory.createLevel(path_to_snow_terrain,
				"SoundEffects/stages/snow stage/wind1.mp3",
				"3DParticles/blizzard.pfx", iceTrans2, Entity2.class));
		
					/*new DrifterObject.DrifterObjectBuilder(4)
					.BaseObject(new Gobject.Builder(4)
					.IntrigueModelComponent(path_to_snow_terrain)
					.IntriguePhysicalComponent(iceMass, iceTrans2)
					.IntrigueLevelComponent("SoundEffects/stages/snow stage/wind1.mp3")
					.Build())
					.ParticleComponent("Blizzard","3DParticles/blizzard.pfx",
							new Vector3(0, 0, 6185.332f),
							new Vector3(3000, 1000,2000 ))
					.Build());*/
		
		mamaDukes.get(4).getModelComponent().getModel().transform.translate(new Vector3(0, 0, 6185.332f)); //btStaticMeshShapes do not update their motionStates.  The model Translation must be set manually in these cases.
		iceTrans3.translate(-6149.6568f, 0, 6185.332f);
		
		mamaDukes.add(level_factory.createLevel(path_to_snow_terrain,
				"3DParticles/blizzard.pfx" , iceTrans3, Entity2.class));
		
		/**
		 * btStaticMeshShapes do not update their motionStates.  The model Translation must be set manually in these cases.
		 */
		mamaDukes.get(5).getModelComponent().getModel().transform.translate(new Vector3(-6149.6568f, 0, 6185.332f)); 
		
		iceTrans4.translate(-6149.6568f, 0, 0);
		mamaDukes.add(level_factory.createLevel(path_to_snow_terrain,
				"3DParticles/blizzard.pfx" , iceTrans4, Entity2.class));
					/**new DrifterObject.DrifterObjectBuilder(6)
					.BaseObject(new Gobject.Builder(6)
					.IntrigueModelComponent(path_to_snow_terrain)
					.IntriguePhysicalComponent(iceMass, iceTrans4)
					.ParticleComponent("Blizzard","3DParticles/blizzard.pfx",
							new Vector3(-6149.6568f, 0, 0), new Vector3(3000, 1000,2000 ))
					.Build())
					.Build());*/
		mamaDukes.get(6).getModelComponent().getModel().transform.translate(new Vector3(-6149.6568f, 0, 0)); 
		
		//register objects to respective systems
		IntrigueCamSys.register(player_guid);
		IntrigueGraphicSys.register(player_guid);
		IntrigueAnimSys.register(player_guid);
		IntrigueControllerSys.register(player_guid);	
		IntrigueMotionSys.register(player_guid);
		IntrigueTotalPhysicsSys.register(player_guid);
		IntrigueAimingSystem.register(player_guid);
		IntrigueBulletCollisionSystem.register(player_guid); //snow terrain
		IntrigueTargetingAISystem.register(player_guid);
		DrifterCharacterSoundSys.register(player_guid);
		
		IntrigueGraphicSys.register(1);
		IntrigueTotalPhysicsSys.register(1);
		IntrigueParticleSys.register(1);  //snow terrain
		
		IntrigueGraphicSys.register(2);
		IntrigueAnimSys.register(2);
		IntrigueMotionSys.register(2);
		IntrigueTotalPhysicsSys.register(2);
		IntrigueAiSys.register(2);
		IntrigueBulletCollisionSystem.register(2);  //player 2
		IntrigueTargetingAISystem.register(2);
		
		IntrigueGraphicSys.register(3);
		IntrigueAnimSys.register(3);
		IntrigueMotionSys.register(3);
		IntrigueTotalPhysicsSys.register(3);
		IntrigueAiSys.register(3);
		IntrigueBulletCollisionSystem.register(3); //player 3
		IntrigueTargetingAISystem.register(3);
		
		IntrigueGraphicSys.register(4);
		IntrigueTotalPhysicsSys.register(4);
		//IntrigueParticleSys.register(4);
		IntrigueLevelSys.register(4);
		IntrigueGraphicSys.register(5);
		IntrigueTotalPhysicsSys.register(5);
		IntrigueParticleSys.register(5); //snow terrain
		
		IntrigueGraphicSys.register(6);
		IntrigueTotalPhysicsSys.register(6);
	}
	@Override
    public void dispose () {
     //   Gobject.assets.dispose();
		IntrigueGraphicSys.destroy();
    }
	@Override
	public void render () {		
		float deltaTime = Gdx.graphics.getDeltaTime();
		//graphics system {
		
		IntrigueParticleSys.update(deltaTime);
		IntrigueMotionSys.update(deltaTime);//motionsys.update(deltaTime);
		IntrigueGraphicSys.update(deltaTime);
		IntrigueCamSys.update(deltaTime);
		IntrigueAnimSys.update(deltaTime);
		IntrigueLevelSys.update(deltaTime);
		//}
		
		//physics System {
		
		
		IntrigueTotalPhysicsSys.update(deltaTime);
		
		//}
		
		IntrigueControllerSys.update(deltaTime);
		IntrigueAiSys.update(deltaTime);
		IntrigueAimingSystem.update(deltaTime);
		IntrigueBulletCollisionSystem.update(deltaTime);
		IntrigueTargetingAISystem.update(deltaTime);
		DrifterCharacterSoundSys.update(deltaTime);
		stage.act(deltaTime);
		text.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
		stage.draw();
	}
}
