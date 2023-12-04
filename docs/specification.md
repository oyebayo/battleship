## System Specification

The application interface should be simple so that it can be used in different environments and facilitate the testing process automation. For these reasons, the input and output must adhere to the precise format specified in this section. 

> [!Note]
> You can assume that the input complies with the value and format constraints indicated, meaning that the user does not make errors beyond those foreseen in this document. 

The program reads lines from the standard input (System.in) and from the text file named ```fleets.txt```, stored in the ***current directory***. It writes lines to the standard output (System.out) and is case sensitive (for example, the words “quit” and “Quit” are different). To simplify, in the rest of the document, the word "fleet" may be used to refer to the arrangement of the fleet on the grid.

### File with the Fleets

The various fleets used in the game are stored in the file ```fleets.txt```. This file has the following structure (where f denotes the positive number of fleets in the file, and the symbol ← represents a line break):

L<sub>1</sub> C<sub>1</sub>←<br>
row 1←<br>
row 2←<br>
....<br>
row L<sub>1</sub>←<br>
L<sub>2</sub> C<sub>2</sub>←<br>
row 1←<br>
row 2←<br>
....<br>
row L<sub>2</sub>←<br>
....<br>
L<sub>f</sub> C<sub>f</sub>←<br>
row 1←<br>
row 2←<br>
....<br>
row L<sub>f</sub>←<br>

where, for each fleet i (with i = 1, 2, . . . , f):
- L<sub>i</sub> and C<sub>i</sub> are integers between 1 and 100 (inclusive), representing the number of rows and the number of columns in the grid, respectively;
- row 1, . . . , row L<sub>i</sub> are L<sub>i</sub> sequences of characters, each with a length C<sub>i</sub>, describing the fleet. 
 
Each element of these sequences is an uppercase letter (from ’A’ to ’Z’, without accents or cedillas) or the character ’.’ (full stop). Each fleet has at least one letter. The fleets appear consecutively (without blank lines separating them). The order in which the fleets occur in the ```fleets.txt``` file will be used to identify (in the standard input) the fleets used in the game: the first fleet in the file is identified by the number one, the second fleet by the number two, and so on.

### Standard Input

The standard input has the following structure:

n←<br>
playerName<sub>1</sub>←<br>
fleetNumber<sub>1</sub>←<br>
playerName<sub>2</sub>←<br>
fleetNumber<sub>2</sub>←<br>
....<br>
playerName<sub>n</sub>←<br>
fleetNumber<sub>n</sub>←<br>
command←<br>
command←<br>
....<br>
command←<br>
quit←<br>

where:
- n is an integer greater than or equal to 2;
- playerName 1, . . . , playerName n are n sequences of characters, all different, with lengths between 1 and 40 (possibly consisting of multiple words, such as “John Doe”);
- fleetNumber<sub>1</sub>, . . . , fleetNumber<sub>n</sub> are n positive integers that do not exceed the number of fleets in the ```fleets.txt``` file, in ascending order (fleetNumber<sub>1</sub> ≤ fleetNumber<sub>2</sub> ≤ · · · ≤ fleetNumber<sub>n</sub>);
- command is one of six commands (called player-command, score-command, fleet-command, ranking-command, in-game-command, and shoot-command) or an invalid command, all explained below.

After the first line, which specifies the number n of players, there are n pairs of lines, one with the name, and the other with the order number of the fleet (in the ```fleets.txt``` file) for that player. The order in which the players occur defines the order in which they play: the first player to play will be the one with the name playerName<sub>1</sub>, the second will be the one with the name playerName<sub>2</sub>, and so on. Then, an arbitrary number of commands follows. The last line of the standard input has a special command, the quit-command, which can only occur on the last line because it terminates the program execution.

### Player-Command

The player-command indicates that we want to know who is the next player to take a shot, at the current moment in the game. This command does not change the game’s state. The lines with player-commands have the following format:
```player←```
The program writes a line to the console, distinguishing two cases:
- If the game has already over, the line has:<br>
```The game is over←```
- In the remaining cases, the line has the following format, where playerName represents the name of the next player to take a shot:
```Next player: playerName←```

### Score-Command

The score-command indicates that we want to know the score of the player with the given name, at the current moment in the game. This command does not change the game’s state. 
The lines with score-commands have the following format:<br>
```score playerName←```<br>
(with a space separating the two components) where playerName is a non-empty sequence of characters. The program writes a line to the console, distinguishing two cases:
- If playerName is not one of the player’s names, the line has:<br>
```Nonexistent player←```
- In the remaining cases, the line has the following format, where X represents the score of the player referred to in the command:<br>
```playerName has X points←```

### main.Fleet-Command

The fleet-command indicates that we want to visualise the state of the fleet of the player with the given name, at the current moment in the game. This command does not change the game’s state. The lines with fleet-commands have the following format:<br>
```fleet playerName←```<br>
 (with a space separating the two components) where playerName is a non-empty sequence of characters. The program writes a line to the console, distinguishing two cases:
- If playerName is not one of the player’s names, the line has:<br>
```Nonexistent player←```<br>
- In the remaining cases, the line has the following format, where fleetStatus represents the state of the fleet of the player referred to in the command:<br>
```fleetStatus←```

The state of the fleet corresponds to the arrangement of the player’s fleet, where all the letters representing ships that have been sunk by the opponents’ shots have been replaced by the character ’*’ (asterisk).

### Ranking-Command

The ranking-command indicates that we want to know the name and score of all players (in play or eliminated), at the current moment in the game. This command does not change the game’s state. The lines with ranking-commands have the following format:<br>
```scores←```
If playerName and X represent, respectively, the name and score of a player at the current moment in the game, the program writes as many lines as there are players, with each line containing the information of a different player. Each line has the following format:<br>
```playerName has X points←```<br>
The lines should be written in descending order of score; in case of a tie in score, in lexicographical order of name.

### In-main.Game-Command

The in-game-command indicates that we want to know the names of all players in play (those whose fleets have not been fully sunk), at the current moment in the game. This command does not change the game’s state. The lines with in-game-commands have the following format:<br>
```players←```<br>
The program writes as many lines as there are players in play, at the current moment in the game. Each line has the following format, where playerName represents the name of a player in play:<br>
```playerName←```
The lines should be written in the order in which the players occurred in the standard input.

### Shoot-Command

The shoot-command indicates that, at the current moment in the game, the player who has the right to play has taken a shot at an opponent’s fleet, specifying who the opponent is and which cell the player chose. The lines with shoot-commands have the following format (with a space separating the two components):<br>
```shoot nrRow nrColumn playerName←```<br>
where:
- playerName is a non-empty sequence of characters that identifies the target opponent;
- nrRow and nrColumn are two integers indicating that the cell hit in the opponent’s grid is (nrRow ,nrColumn)

The program should use this information to update the game’s state but should not write any results except in the following cases, where the game’s state does not change, and the program writes a line to the console:
- If the game is already over, the line has:<br>
```The game is over←```<br>
- If the game is not over yet, and playerName is the name of the player who is currently playing, the line has:<br>
```Self-inflicted shot←```<br>
If the game is not over yet, and playerName is not the name of any of the players, the line has:<br>
```Nonexistent player←```<br>
- If the game is not over yet, and playerName is the name of an eliminated player, the line has:<br>
```Eliminated player←```<br>
- If the game is still ongoing and playerName is the name of an opponent in play (not eliminated), but nrRow is not within the range of 1 to the number of rows in the grid of the mentioned opponent, or nrColumn is not within the range of 1 to the number of columns in the grid of that opponent, the line has:<br>
```Invalid shot←```

### Quit-Command

The quit-command indicates that we want to terminate the execution of the program. The line with the quit-command has the following format<br>
```quit←```<br>
The program ends, writing a line to the console. Two cases are distinguished:
- If the game is not over yet, the line has:<br>
```The game was not over yet...←```<br>
- If the game is already over, the line has the following format, where winningName denotes the name of the player who won the game:<br>
```winningName won the game!←```<br>

### Invalid Commands
Whenever the user writes a line that does not start with the words “player”, “players”, “score”, “scores”, “fleet”, “shoot”, or “quit”, the game state must not be changed, and the program must write a line with:<br>
```Invalid command←```<br>

