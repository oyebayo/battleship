## Example

Here is an example assuming that the file ```fleets.txt``` has the content presented below. 
```
2 8
RRRGG.R.
......R.
2 8
DU...TTT
DU......
1 5
...AA
3 8
RRRGG.R.
......R.
.BB...R.
```
> [!note]
> The example below is very incomplete: there are many situations that are not illustrated and that can occur. 

The right column provides information for the reader, serving only as a reminder of the rules described earlier. In this column, “c” abbreviates “cells(s)” and “pts” abbreviates “points”. To distinguish between input and output, the input has been printed in blue, while the output is in black. All input and output lines end with a newline symbol, which has been omitted for readability. 

The column on the left provides comments or a description on the interaction.  

3 In this game, there are three players.
John Name of the first player (the one who starts playing).
1 Jonh’s fleet is the first fleet in fleets.txt.
Doe Name of the second player.
1 Doe’s fleet is the first fleet in fleets.txt.
Laura Name of the third player.
3 Laura’s fleet is the third fleet in fleets.txt.
player
Next player: John
shoot 1 3 Doe John hits Doe’s 3 c ship; gains 300 pts.
fleet Doe
***GG.R.
......R.
score John
John has 300 points
player
Next player: Doe
shoot 2 6 John Doe hits the water of John.
player
Next player: Laura
shoot 2 7 Doe Laura hits a Doe’s 2 c ship; gains 200 pts.
fleet Doe
***GG.*.
......*.
shoot 1 7 Doe John hits a sunk ship (of 2 c) of Doe; loses 60 pts.
shoot 1 8 John Doe hits the water of John.
shoot 2 3 John Laura hits the water of John.
shoot 1 5 Doe John hits Doe’s 2 c ship; gains 200 pts. Doe is eliminated.
fleet Doe
*****.*.
......*.
player
Next player: Laura Doe never plays again.
players There are now only 2 players in the game.
John
Laura
score John
John has 440 points
shoot 1 4 John Laura hits John’s 2 c ship; gains 200 pts.
shoot 0 1 Doe John cannot attack Doe.
Eliminated player
shoot 0 1 John John cannot attack himself.
Self-inflicted shot
shoot 0 1 Laura The cell (0, 1) does not exist.
Invalid shot
shoot 1 1 Laura John hits the water of Laura.
shoot 1 7 John Laura hits John’s 2 c ship; gains 200 pts.
scores
Laura has 600 points
John has 440 points
Doe has 0 points
fleet John Doe
Nonexistent player
score john
Nonexistent player
Player
Invalid command
player
Next player: John
shoot 1 2 Laura John hits the water of Laura.
shoot 1 2 John Laura hits John’s 3 c ship. John is eliminated. The game is over.
scores
Laura has 1800 points Laura earned a bonus of 900 points.
John has 440 points
Doe has 0 points
players There is now only 1 player in the game.
Laura
player
The game is over
quit
Laura won the game!
```
