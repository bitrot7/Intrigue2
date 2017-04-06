# Intrigue2 Entity Component Based API for functional 3D games with LibGDX


[Entity](core/src/com/mk/intrigue/entity/) [Component](core/src/com/mk/intrigue/entity/component/) [Systems](core/src/com/mk/intrigue/system/): 
A *data oriented* approach to games and the strategy  under the hood of most modern game engines.  

ECS is a poplar architecture for Game Development because of the ease in which new features can be added to games.
It also has a few other benefits that make it a good candidate for the choice of any game developer.


First, Entities in ECS are objects but they are essentially container objects 
they perform no calculations (only getter-setter methods) it simply holds data to be mutated [Entity](core/src/com/mk/intrigue/entity/Gobject.java) . 

Second Systems store relatively little information (have a small amount of fields)
They simply manipulate the data stored in the entites they have access to.  

Intrigue performs an API for creating and rendering Game Objects using Systems and
an Entity.

Go look at the `system` package to start getting a better idea of ECS in the Intrigue API.  [Link to System Doc](core/src/com/mk/intrigue/system/)

Hopefully looking at this code gives you good ideas of your own.