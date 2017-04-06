Intrigue2 

Entity Component Systems.  A more data oriented approach to games.  You will find a strategy like this under the hood of 
most modern game engines.  

Remove the traditional OOP idea of objects and think of a general game object -- an entity.  A game object (entity )that performs 
no calculations (has no non getter setter methods) it simply holds data to be mutated. This data is partitioned into components.

In normal OOP game programming you might have:

       Character jack = new Character();
       ..
       ...
       render() {
       	jack.move(1); //move jack one unit.
       }

In ECS (Entity Component Systems) equivalently you have:
	
	   Entity e = new Entity(new GraphicalComponent(), new PhysicalComponent());
	   PhysicsSystem p = new PhysicsSystem();
	   p.register(e);
	   ..
	   ...
	   render() {
	   	p.update();
	   }
	   
	   PhysicsSystem.update() {
	   		if(p.needsMoveForward()) {
	   			p.move(1)
	   		}
	   }
	   
but what?? ECS looks way more complicated?  Okay I will admit the initial overhead looks like a bit much but the benefits are
in the design.  Using this approach to design a game puts all the features of a game in the system and the components that make
up the data a system manipulates.  


Intrigue2:

In Intrigue every System has a corresponding component and it is for all intents and purposes one to one relationship.
The physics system only manipulates the physical part of the object.  The graphics only render the graphical component of 
the object and so on.  

This allows for incredibly feature rich games.  Want a new feature make a System add it to the main loop and add a component to 
the relevant objects.  You are off and running.
  

In Intrigue2 (and in fact in the older version) Game Objects are stored in a global Array of GameObjects. 
Each Intrigue*System has an internal array of integer index's that can be used to access relevant game objects from the global array
(kind of like accessing things in a database via the primary key).  Except the primary key in this case is an array index and the 
database is a dead simple array of Game Objects (Gobject.java).  What is a relevant object you say?  arent all objects relevant
to all Systems?  

Goo--Bad Question, no they are not an object that is only meant to be seen should not be operated on by a sound
system.

Go look at the either system package to start getting a better idea of ECS.  com.mk.*.system

Hopefully looking at this code gives you good ideas of your own.

