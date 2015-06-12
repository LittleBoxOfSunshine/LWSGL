These examples are listed under demos rather than examples becasue they are
larger and/or more complicated. As such they are not very useful to use to learn
the game engine. They do, however, serve as a showcase of what the engine is
capabale of and should assuage any concerns about performance of java or the
engine itself.

While there are some exceptions (games that are physics intensive, AI intensive,
or simulate large worlds) java will not limit the performance of your game in
any meaningful way. Dwarf Fortress is an example of such a game. In the rare 
scenario that the speed of a lower level language is needed for backend tasks,
the server/game logic can be written in another language and interface with a
client written with LWSGL.

This is a powerful combination that takes the best parts of both worlds, the raw
power and control of c++ (or another low level language) with the simplicity
and cross platform support of java and LWSGL for the client.

This split language approach is more advanced and is implemented in the evolution
simulator demo. The simulator is a project of its own and while rather large only
a few of its files pertain to the split language approach.

The code is documented, but if you can't figure out how it works from the source
avaliable, it's probably best that you just stick with java for now. Our server
package gives you all the features through a simple interface. You'll probably
be surprised just how well it can run with proper optimization of your code. 

As you become more comfortable with LWSGL and game development you could revist
the split language approach to get that additional efficiency. Remember that this
game engine was designed with two primary goals:
	1) To provide a detailed, scalable platform to learn game development
	2) Improve both user and developer experience by providing high quality,
	   stable management features and allowing devs to focus on the creative
	   aspects of their game instead of the computer science tasks

This platform is serriously scalable, and you can create some amazingly intracit
projects. But it gives the freedom to ignore the bells and whistles while encouraging
simplicity in your projects.
