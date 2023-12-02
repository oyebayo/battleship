1 Concepts and Goal of the Programming Project
In this version of the game, some rules are quite different from the usual ones. There are at least two players, but there can be more, with no upper limit to the number of players. Each player is identified by their unique name. The battle takes place on rectangular grids (i.e., with multiple rows and columns), which are divided into cells, as illustrated in Figure 1. Cells are identified by a pair (l, c), where l and c are the row number and column number, respectively. The row number is assigned from top to bottom, and the column number is assigned from left to right. Both the first row and the first column are identified with the number 1. For example, the cell (1, 1) is the top-left cell of the grid.

Figure 1: Grid of 4 rows and 7 columns, with a fleet of 4 boats.

Before the game begins, each player arranges their ships on their grid without overlaps. The dimensions of the grid (number of rows and number of columns) and the composition of the fleet (types of ships and the quantity of each type) do not have to be the same for all players. The ships are all linear and must be positioned either vertically or horizontally. Note that the ships can be adjacent to each other, with no water separating them, as shown in the schematic arrangement in Figure 1. In the example we are using, the fleet has four ships: two three-cell ships and two two-cell ships.

One player takes a turn at a time. Each turn involves choosing an opponent and taking a shot at a position on that opponent’s grid. If the shot hits a ship, that ship sinks (ceases to exist), regardless of the number of cells it has. If the shot hits a position where a ship used to be, the player incurs a penalty. A player’s score is determined by the ships they have sunk and the shots they have taken on cells with “sunk ships”. The order of players in terms of performance, called ranking, is based on the players’ scores.

When a player’s fleet is completely sunk, that player is automatcally eliminated, being unable to take any more shots or be attacked by opponents. The game ends as soon as there is only one player in play (i.e., not eliminated). The arrangement of the fleet on the grid will be represented by L
sequences of characters, all of length C, where L is the number of rows and C is the number of columns in the grid. When a cell has no ship, the character in the sequence is ’.’ (full period). Cells with a ship have the same letter in corresponding positions of the sequences, and if ships are adjacent to each other (vertically or horizontally), the letters representing them are different.

Figure 2: Examples of the representation of the grid illustrated in Figure 1.

Figure 2 has two possible representations of the grid from Figure 1. If, at a certain point in the game, there are sunk ships, the repre-
sentation of the fleet’s state has the character ’*’ in the positions where those ships were. Figure 3 contains the state of the fleet de-
scribed by the representation on the left in Figure 2, assuming the only sunk ship was in the first column of the grid.

Figure 3: Example of the representation of the fleet’s state.

The goal of this project is to program, in Java, the version of the Battleship game described in this document. With information about the players’ names, the arrangement of their fleets, and the sequence of shots taken, the program should be able to indicate, at any point in the game, each player’s score and fleet state, the players’ ranking, and which players are still in the game. It is also intended that, while the game is not over, the program can indicate who the next player to take a shot is.

2 Game Rules

Two or more players compete against each other. Before the game begins, each player arranges their ships on their grid (being required to have at least one boat), and the order in which players play is defined. One player takes a turn at a time. The player taking the turn rotates, respecting the previously defined order, and considering that after the last player, it is the first player’s turn again. For example, if there are four players identified by the names A, B, C, and D, and this is the order in which they play: the first player to play is player A; after A plays, it is the turn of player B; after B plays, it is the turn of player C; after C plays, it is the turn of player D; after D plays, it is again the turn of player A.

At the beginning of the game, all players are in play (no player has been eliminated), and all have zero points. However, when a player’s fleet is completely sunk, that player is immediately eliminated: the player no longer plays (does not take any more shots), and their grid cannot be the target of opponents’ shots. The elimination of a player does not change the order in which the remaining players take their turns. If the initial order were A, B, C, and D (as in the example above), and C were eliminated, after B plays, it would be D’s turn unless D has also been eliminated. If both C and D have been eliminated, after B plays, it would be A’s turn. The game ends as soon as there is only one player in play.

While the game has not ended, the player with the right to play chooses an opponent to attack and takes a shot at a position on that opponent’s grid, with one of the following three situations occurring:
 - If there is a ship in that position, the ship sinks (ceases to exist). The player earns as many points as the number of cells on the ship multiplied by 100.
 - If there has never been a ship in that position, the player’s score remains unchanged.
 - If there is no ship in that position because the existing ship was sunk, the player loses as many points as the number of cells on the sunken ship multiplied by 30.

In the last move of the game, the surviving player (the one who took the shot) receives a bonus: after earning the “regular” points for sinking a ship, the score is doubled. The winner is the player with the highest score when the game ends; if there is more than one player with the highest score, the survivor wins, even if their score is not the highest. From the moment the game ends, no player can take shots. Consequently, the players’ scores and fleet states can no longer change. Note that the score can be positive, zero, or negative.

3 System Specification

The application interface should be simple so that it can be used in different environments and facilitate the testing process automation. For these reasons, the input and output must adhere to the precise format specified in this section. You can assume that the input complies with the value and format constraints indicated, meaning that the user does not make errors beyond those foreseen in this document. The program reads lines from the standard input (System.in) and from the text file named fleets.txt, stored in the current directory. It writes lines to the standard output (System.out) and is case sensitive (for example, the words “quit” and “Quit” are different). To simplify, in the rest of the document, the word fleet may be used to refer to the arrangement of the fleet on the grid.

File with the Fleets
The various fleets used in the game are stored in the file fleets.txt. This file has the following structure (where f denotes the positive number of fleets in the file, and the symbol ← represents a line break):

L1 C1←
row 1←
row 2←
....
row L1←
L2 C2←
row 1←
row 2←
....
row L2←
....
Lf Cf ←
row 1←
row 2←
....
row Lf←

where, for each fleet i (with i = 1, 2, . . . , f):
- Li and Ci are integers between 1 and 100 (inclusive), representing the number of rows and the number of columns in the grid, respectively;
- row 1, . . . , row Li are Li sequences of characters, each with a length Ci, describing the fleet. 
 
Each element of these sequences is an uppercase letter (from ’A’ to ’Z’, without accents or cedillas) or the character ’.’ (full stop). Each fleet has at least one letter. The fleets appear consecutively (without blank lines separating them). The order in which the fleets occur in the fleets.txt file will be used to identify (in the standard input) the fleets used in the game: the first fleet in the file is identified by the number one, the second fleet by the number two, and so on.

Standard Input
The standard input has the following structure:
n←
playerName 1←
fleetNumber 1←
playerName 2←
fleetNumber 2←
....
playerName n←
fleetNumber n←
command ←
command ←
....
command ←
quit←

where:
- n is an integer greater than or equal to 2;
- playerName 1, . . . , playerName n are n sequences of characters, all different, with lengths between 1 and 40 (possibly consisting of multiple words, such as “John Doe”);
- fleetNumber 1, . . . , fleetNumber n are n positive integers that do not exceed the number of fleets in the fleets.txt file, in ascending order (fleetNumber 1 ≤ fleetNumber 2 ≤ · · · ≤ fleetNumber n);
- command is one of six commands (called player-command, score-command, fleet-command, ranking command, in-game-command, and shoot-command) or an invalid command, all explained below.

After the first line, which specifies the number n of players, there are n pairs of lines, one with the name, and the other with the order number of the fleet (in the fleets.txt file) for that player. The order in which the players occur defines the order in which they play: the first player to play will be the one with the name playerName1, the second will be the one with the name playerName2, and so on. Then, an arbitrary number of commands follows. The last line of the standard input has a special command, the quit-command, which can only occur on the last line because it terminates the program execution.

Player-Command
The player-command indicates that we want to know who is the next player to take a shot, at the current moment in the game. This command does not change the game’s state. The lines with player-commands have the following format:

player←
The program writes a line to the console, distinguishing two cases:
- If the game has already over, the line has:
The game is over←
- In the remaining cases, the line has the following format, where playerName represents the name of the next player to take a shot:

Next player: playerName ←

Score-Command
The score-command indicates that we want to know the score of the player with the given name, at the current moment in the game. This command does not change the game’s state. The lines with score-commands have the following format (with a space separating the two components):

score playerName ←
where playerName is a non-empty sequence of characters.
The program writes a line to the console, distinguishing two cases:
- If playerName is not one of the player’s names, the line has:
Nonexistent player←
- In the remaining cases, the line has the following format, where score represents the score of the player referred to in the command:
playerName has score points←

Fleet-Command
The fleet-command indicates that we want to visualise the state of the fleet of the player with the given name, at the current moment in the game. This command does not change the game’s state. The lines with fleet-commands have the following format (with a space separating the two components):

fleet playerName ←
where playerName is a non-empty sequence of characters.
The program writes a line to the console, distinguishing two cases:
- If playerName is not one of the player’s names, the line has:
Nonexistent player←
- In the remaining cases, the line has the following format, where fleetStatus represents the state of the fleet of the player referred to in the command:
fleetStatus ←

The state of the fleet corresponds to the arrangement of the player’s fleet, where all the letters representing ships that have been sunk by the opponents’ shots have been replaced by the character ’*’ (asterisk).

Ranking-Command
The ranking-command indicates that we want to know the name and score of all players (in play or eliminated), at the current moment in the game. This command does not change the game’s state. The lines with ranking-commands have the following format:
scores←
If playerName and score represent, respectively, the name and score of a player at the current moment in the game, the program writes as many lines as there are players, with each line containing the information of a different player. Each line has the following format:
playerName has score points←
The lines should be written in descending order of score; in case of a tie in score, in lexicographical order of name.

In-Game-Command
The in-game-command indicates that we want to know the names of all players in play (those whose fleets have not been fully sunk), at the current moment in the game. This command does not change the game’s state. The lines with in-game-commands have the following format:
players←
The program writes as many lines as there are players in play, at the current moment in the game. Each line has the following format, where playerName represents the name of a player in play:
playerName ←
The lines should be written in the order in which the players occurred in the standard input.

Shoot-Command
The shoot-command indicates that, at the current moment in the game, the player who has the right to play has taken a shot at an opponent’s fleet, specifying who the opponent is and which cell the player chose. The lines with shoot-commands have the following format (with a space separating the two components):
shoot nrRow nrColumn playerName ←-
where:
- playerName is a non-empty sequence of characters that identifies the target opponent;
- nrRow and nrColumn are two integers indicating that the cell hit in the opponent’s grid is (nrRow ,nrColumn ).

The program should use this information to update the game’s state but should not write any results except in the following cases, where the game’s state does not change, and the program writes a line to the console:
- If the game is already over, the line has:
The game is over←
- If the game is not over yet, and playerName is the name of the player who is currently playing, the line has:
Self-inflicted shot←
If the game is not over yet, and playerName is not the name of any of the players, the line has:
Nonexistent player←
- If the game is not over yet, and playerName is the name of an eliminated player, the line has:
Eliminated player←
- If the game is still ongoing and playerName is the name of an opponent in play (not eliminated), but nrRow is not within the range of 1 to the number of rows in the grid of the mentioned opponent, or nrColumn is not within the range of 1 to the number of columns in the grid of that opponent,
the line has:
Invalid shot←

Quit-Command
The quit-command indicates that we want to terminate the execution of the program. The line with the quit-command has the following format
quit←
The program ends, writing a line to the console. Two cases are distinguished:
- If the game is not over yet, the line has:
The game was not over yet...←
- If the game is already over, the line has the following format, where winningName denotes the name of the player who won the game:
winningName won the game!←

Invalid Commands
Whenever the user writes a line that does not start with the words “player”, “players”, “score”, “scores”, “fleet”, “shoot”, or “quit”, the game state must not be changed, and the program must write a line with:
Invalid command←

4 Example
Here is an example assuming that the file fleets.txt has the content presented below. 

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

Note that the example is very incomplete: there are many situations that are not illustrated and that can occur. The left column illustrates the interaction: the input is written in blue, and the output is in black. All input and output lines end with a newline symbol, which has been omitted for readability. The right column provides information for the reader, serving only as a reminder of the rules described earlier. In this column, “c” abbreviates “cells(s)” and “pts” abbreviates “points”.

3 | | In this game, there are three players.
John | | Name of the first player (the one who starts playing).
1 | | Jonh’s fleet is the first fleet in fleets.txt.
Doe | | Name of the second player.
1 | | Doe’s fleet is the first fleet in fleets.txt.
Laura | | Name of the third player.
3 | | Laura’s fleet is the third fleet in fleets.txt.
player | |
| Next player: John | 
shoot 1 3 Doe | | John hits Doe’s 3 c ship; gains 300 pts.
fleet Doe | |
| ***GG.R. 
......R. |
score John | | 
| John has 300 points |
player | |
| Next player: Doe | 
shoot 2 6 John | | Doe hits the water of John.
player | |
| Next player: Laura |
shoot 2 7 Doe | | Laura hits a Doe’s 2 c ship; gains 200 pts.
fleet Doe | |
| ***GG.*.
......*. |
shoot 1 7 Doe | | John hits a sunk ship (of 2 c) of Doe; loses 60 pts.
shoot 1 8 John | | Doe hits the water of John.
shoot 2 3 John | | Laura hits the water of John.
shoot 1 5 Doe | | John hits Doe’s 2 c ship; gains 200 pts. Doe is eliminated.
fleet Doe | |
|*****.*.
......*. |
player | |
| Next player: Laura | Doe never plays again.
players | | There are now only 2 players in the game.
| John
Laura | 
score John | |
| John has 440 points | 
shoot 1 4 John | | Laura hits John’s 2 c ship; gains 200 pts.
shoot 0 1 Doe | | John cannot attack Doe.
| Eliminated player |
shoot 0 1 John | | John cannot attack himself.
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
