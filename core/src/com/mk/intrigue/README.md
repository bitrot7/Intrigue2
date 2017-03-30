# Intrigue
Lightweight 3D Entity Component System for game dev -- built ontop of Libgdx and bullet.

    Why look at Intrigue?

In game development there are certain "boiler plate" pieces of functionality that all games must define that are relatively 
standard.  For example many games use chase cameras, and many games use standard functions that take time to code but require 
only surface level knowledge to write (trivial).  Intrigue seeks to totally abstract away all the default boilerplate stuff a 
game requires while still leaving the programmer ways to make complete and custom 3D games.

    Short Description of Entity Component System and how Intrigue implements it.

Intrigue seeks to accomplish its goals with Entity Component Systems programming.  The way this works is that intrigue defines 
what objects a game might need, such as: Ragdolls (Complex Physical Shapes), Simple Atomic Physical Shapes (Rectangles/Static 
Collision Shapes), 3D graphical Models (.g3dj, .obj, .g3db), Animations; then it makes them accessible through its class 
Gobject.  Intrigue then defines "Default Systems" that act upon certain attributes of these 'Gobjects'.  For instance the 
"DefaultGraphicSys" class acts only on Gobjects 3D model and does not worry about its physical attributes, those are modified by 
other Systems like "DefaultCollisionSys" and "DefaultAnimationSys".  However, I digress if you would like more information on 
Entity Component Systems see your local library.


    Q&A with myself.
    
"But a game is a specific piece of software so I can't program it using these default systems alone.  So do you really expect me 
to parse all the Default Code to see what I need and what I don't? why use Intrigue at all if it's going to force time consuming 
analysis of code that isn't mine?"

We here you man.  In order to write a game though you need to have structure or you will eventually choke to death on your 
spaghetti code.  Programming to with Intrigue ECS (Entity Component Systems is easier than you think)  see the wiki.


