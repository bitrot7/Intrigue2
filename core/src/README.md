# Intrigue2 Entity Component Based API for 3D games with LibGDX


[Entity](com/mk/intrigue/entity/) [Component](com/mk/intrigue/entity/component/) [Systems](com/mk/intrigue/system/): 
A *data oriented* approach to games and the strategy  under the hood of most modern game engines.  

ECS is a poplar architecture for Game Development because of the ease in which new features can be added to games.
It also has a few other benefits that make it a good candidate for the choice of any game developer.


First, Entities in ECS are objects but they are essentially container objects 
they perform no calculations (only getter-setter methods) it simply holds data to be mutated [Entity](com/mk/intrigue/entity/Entity.java) . 

Second, Systems store relatively little information (have a small amount of fields)
They simply manipulate the data stored in the Entities they have access to.  

Intrigue is an API for creating and manipulating 3D Game Objects using Systems and
Entities.

Go look at the `system` package to start getting a better idea of ECS in the Intrigue API.  [Link to System Doc](com/mk/intrigue/system/)

![Alt text](../../intrigue_shot2.png?raw=true "Default Test App for Intrigue2")

You can play around with this test interactive world by running 

         ./gradlew desktop:run

in the root dir of your clone of this repo.  **You will need a wired xbox360 controller plugged in to a usb port to move the character**

Hopefully looking at this code gives you good ideas of your own.

[Contact Author](../../AUTHOR.md)

All code is released under Apache 2 License [license](https://www.apache.org/licenses/LICENSE-2.0.html)