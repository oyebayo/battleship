System Specification

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

