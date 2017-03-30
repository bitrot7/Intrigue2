package com.mk.intrigue;


import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.math.Matrix4;


//general Purpose Game Object (entity)
//has base components
public class Gobject {
	protected int guid;
	protected IntrigueModelComponent modelComponent;// the component in entity component.
	protected IntriguePhysicalComponent physicalComponent;
	protected IntrigueControllerComponent controllerComponent;
	protected Gobject(Builder builder) {
		this.guid = builder.guid;
		this.modelComponent = builder.modelComponent;
		this.physicalComponent = builder.physicalComponent;
		this.controllerComponent = builder.controllerComponent;
	}
	/*
	*	general accessors
	*/
	public IntriguePhysicalComponent getPhysicalComponent() {
		return this.physicalComponent; //returns component with 3D physical primitive data
	}
	public IntrigueModelComponent getModelComponent() {
		return this.modelComponent; //return component with 3D model data.
	}
	public IntrigueControllerComponent getControllerComponent() {
		return this.controllerComponent; //return component with controller data.
	}
	/*
	*	end general accessors
	*/
	
	/*
	*	some useful accessor methods.
	*/
	public int getGUID() {
		return this.guid;
	}
	
	
	
	public static class Builder {
		protected int guid;
		protected IntrigueModelComponent modelComponent;
		protected IntriguePhysicalComponent physicalComponent;
		protected IntrigueControllerComponent controllerComponent;
	
		
		public Builder(int g) {
			guid = g;
			System.out.println("Building Game Object " + guid);
		}
		/*
		*	load 3D model located at path. and build model component from it
		*/
		public Builder IntrigueModelComponent(String path_to_asset) {
			this.modelComponent = new IntrigueModelComponent(path_to_asset);
			return this;
		}
		/*
		*	create controller data for player 1, 2, .. player_number and build component.
		*/
		public Builder IntrigueControllerComponent(int player_number) {
			this.controllerComponent = new IntrigueControllerComponent(player_number);
			return this;
		}
		
		/*
		*	build physical component representation from basic shape
		*/
		public Builder IntriguePhysicalComponent(btCollisionShape shape, float mass, Matrix4 transform) {
			
			this.physicalComponent = new IntriguePhysicalComponent(shape, mass, transform);
			return this;
		}
		/*
		*	build physical component representation from 3D model.  inanimate only.
		*	obviously model must have already been built first.
		*	Intrigue links the GUID of a game object to the "UserIndex" of a bullet world rigid body.
		*/
		public Builder IntriguePhysicalComponent(float mass, Matrix4 transform) {
			
			this.physicalComponent = new IntriguePhysicalComponent(this.modelComponent.getModel(), mass, transform);
			this.physicalComponent.getPhysicsBody().getRigidBody().setUserIndex(this.guid);
			return this;
		}

		public Gobject Build(){
			System.out.println("Finished building GameObject" + guid);
			return new Gobject(this);
		}
	}
}