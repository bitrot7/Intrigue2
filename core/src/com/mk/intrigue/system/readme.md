**Systems ~~are~~ were everything.**


~~All logic happens in the systems.~~


~~Systems have an update and a register method from the ISystem interface: ~~



~~The register method adds an integer called guid or *Globally Unique Identifier* to an array called internal:~~



~~In the update method these integers in internal are used as indexes in a global array of *Entities*:~~



~~Typical Systems Classes have a small amount of fields because all the information they operate on comes from the global array ~~
~~of entities using the internally stored guids (indexes).  Guid is a fancy word for array index in Intrigue.  Entities have many~~
~~things that systems can manipulate in fact an entity will contain much much more data than any one system will need.  But~~
~~This will be covered in the entities readme file.  All Systems know is that they can access and manipulate entites.~~


~~There is however one more thing a system must know about Entities have Components to more easily segment Code (covered more~~
~~in depth in the components readme).  Consider the following:~~


~~Now the Entity e we pulled might have many more populated Components:~~
		
		
~~**But our GraphicSystems doesnt care about those components**~~

~~We let a separate PhysicsSystem.java take care and we let a separata AISystem.java take care of the AI data and manipulate it.~~


~~How to design a System for use with Intrigue API:~~

~~Systems should at least extend GameSys as it provides functionality for creating delays in systems (without blocking the game).~~
~~GameSys also provides an implemented method:~~

      
~~This method throws an exception if Component c is missing from the System s.  After all what would happen if a user of our API~~
~~registered an Entitys with no graphic component to our GraphicSystem.java, nothing good.  RequireComponent can quickly help~~
~~you kick this common error.~~


~~A keen observer might have noticed that the Systems that come with Intrigue by default adhere to a decorator pattern but dont ~~
~~make use of it.  This is because the project is still in active development and they will be useful for an overhaul in the~~
~~near future.   Systems may soon have the ability to be recursively composed and updated.   If you wish to add a system it may be~~
 ~~wise to construct it according to the decorator pattern.~~

