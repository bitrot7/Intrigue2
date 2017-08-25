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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mk.intrigue.entity.Entity;
import com.mk.intrigue.factory.IntrigueLevelFactory;
import com.mk.intrigue.system.IntrigueGraphicSystem;
import com.mk.intrigue.system.IntrigueTotalPhysicsSystem;

/**
 * {@link Intrigue} The main class that holds the data structure containing all
 * world entities, it controls the creation of all latter objects and the rendering
 * of all things.
 * 
 * @author wind2
 *
 */
public class Intrigue extends ApplicationAdapter {
	public static Array<Entity> mamaDukes = new Array<Entity>(100);
	
	private IntrigueGraphicSystem IntrigueGraphicSys;
	private IntrigueTotalPhysicsSystem IntrigueTotalPhysicsSys;

	private final IntrigueLevelFactory<Entity> level_factory = new IntrigueLevelFactory<Entity>();
	private Stage stage;
	private Table table;
	private Label text;
	public static float person_height = 60;
	public static float person_radius = 50;
	public static final int player_guid = 0;
	
	/**
	 * Creates all data structures and objects needed for rendering.
	 */
	@Override
	public void create () {
		//IntrigueGraphicalDebugger.enable();
		IntrigueGraphicSys = new IntrigueGraphicSystem();
		IntrigueTotalPhysicsSys = new IntrigueTotalPhysicsSystem();
		
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
	
		trans.translate(-3000,6000,-500);
		//Vector3 halfExtents = new Vector3(30f,90f,25f);
		btCapsuleShape person_shape = new btCapsuleShape(30f, 90f);
		
		mamaDukes.add(new Entity.Builder(player_guid)
					.IntrigueModelComponent(path_to_char).IntrigueControllerComponent(1)
					.IntriguePhysicalComponent(person_shape, 200f, trans)
					.MotionComponent()
					.AimingComponent()
					.DecalComponent()
					.ThirdPersonCameraComponent()
                    .CharacterActionsComponent()
					.AnimationComponent()
					.Fireable(path_to_crosshair)
					.TargetingAI(team1)
					//.CharacterSoundComponent("SoundEffects/Character/walking/step-spur.mp3", "SoundEffects/guns/M4A1.mp3")
					.Build());
		Json json_test = new Json(); 
		Entity d = mamaDukes.get(player_guid);
		//System.out.println(json_test.prettyPrint(d));
		
		
		mamaDukes.get(player_guid).getPhysicalComponent()
					.getPhysicsBody().getRigidBody()
					.setActivationState(Collision.DISABLE_DEACTIVATION);

		mamaDukes.add(level_factory.createLevel(path_to_snow_terrain,
				"3DParticles/blizzard.pfx", iceTrans, Entity.class));
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
		
		mamaDukes.add(new Entity.Builder(2)
					.IntrigueModelComponent(path_to_char)
					.IntriguePhysicalComponent(person_shape, 200f, trans2)
					.ParticleComponent("Blood", "3DParticles/Character/Blood.pfx", 
							new Vector3(), new Vector3(1f,1f,1f))
					.MotionComponent()
					.AimingComponent()
					.CharacterActionsComponent()
					.AnimationComponent()
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
		
		mamaDukes.add(new Entity.Builder(3)
					.IntrigueModelComponent(path_to_char)
					.IntriguePhysicalComponent(person_shape, 200f, trans3)
					.MotionComponent()
					.AimingComponent()
					.CharacterActionsComponent()
					.AnimationComponent().Fireable()
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
				"3DParticles/blizzard.pfx", iceTrans2, Entity.class));
		
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
				"3DParticles/blizzard.pfx" , iceTrans3, Entity.class));
		/**
		 * btStaticMeshShapes do not update their motionStates.  The model Translation must be set manually in these cases.
		 */
		mamaDukes.get(5).getModelComponent().getModel().transform.translate(new Vector3(-6149.6568f, 0, 6185.332f)); 
		
		iceTrans4.translate(-6149.6568f, 0, 0);
		mamaDukes.add(level_factory.createLevel(path_to_snow_terrain,
				"3DParticles/blizzard.pfx" , iceTrans4, Entity.class));
					/**new DrifterObject.DrifterObjectBuilder(6)
					.BaseObject(new Gobject.Builder(6)
					.IntrigueModelComponent(path_to_snow_terrain)
					.IntriguePhysicalComponent(iceMass, iceTrans4)
					.ParticleComponent("Blizzard","3DParticles/blizzard.pfx",
							new Vector3(-6149.6568f, 0, 0), new Vector3(3000, 1000,2000 ))
					.Build())
					.Build());*/
		mamaDukes.get(6).getModelComponent().getModel().transform.translate(new Vector3(-6149.6568f, 0, 0)); 
		
	}
	
	/**
	 * Garbage cleanup
	 */
	@Override
    public void dispose () 
	{
       //TODO c++ guys are angry already.
    }
	
	/**
	 * Renders everything that was created.
	 */
	@Override
	public void render () 
	{		
		
		float deltaTime = Gdx.graphics.getDeltaTime();
		
		
		// TODO make a world component and consider design implications
        
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		for(Entity operableEntity : mamaDukes)
		{
			operableEntity.Update(deltaTime);
		}
		 
		IntrigueGraphicSys.flush();
		
		stage.act(deltaTime);
		text.setText("FPS: " + Gdx.graphics.getFramesPerSecond());
		stage.draw();
	}
}
