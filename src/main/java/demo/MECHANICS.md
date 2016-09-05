
# Game Speed

Brood War uses Logical Steps to run all of the game-important time-sensitive functions.
Every time-based mechanic, such as attack speed, movement speed, and build times, are all based off of this.
Logical Steps have a delay between them, and this is the game speed.

## Game Speeds

Each game speed puts a different delay, in milliseconds, between each Logical Step. This is as follows:

* Slowest = 167 milliseconds.
* Slower = 111 milliseconds.
* Slow = 83 milliseconds.
* Normal = 67 milliseconds.
* Fast = 56 milliseconds.
* Faster = 48 milliseconds.
* Fastest = 42 milliseconds.

## Unit movement

The raw movement speed values, basically, are calculated in pixels per Logical Step.
For movement speeds that are controlled via the Iscript.bin file, movement speeds are only approximate averages.
Here is a list of all the unit's raw movement speed values:

SCV	5
Marine	4
Firebat	4
Medic	4
Ghost	4
Marine (Stim Pack)	6
Firebat (Stim Pack)	6
Vulture	6.67
Vulture (Upg.)	10
Goliath	4.7
Siege Tank	4

# Links

* http://wiki.teamliquid.net/starcraft/Game_Speed#Cooldown
* http://www.starcraftai.com/wiki/Frame_Rate
* http://www.starcraftai.com/wiki/Regeneration
