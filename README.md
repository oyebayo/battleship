# Battleship II

## Concepts and Goal of the Programming Project

In this version of the game, some rules are quite different from the usual ones. There are at least two players, but there can be more, with no upper limit to the number of players. Each player is identified by their unique name. The battle takes place on rectangular grids (i.e., with multiple rows and columns), which are divided into cells, as illustrated in _Figure 1_. Cells are identified by a pair (l, c), where l and c are the row number and column number, respectively. The row number is assigned from top to bottom, and the column number is assigned from left to right. Both the first row and the first column are identified with the number 1. For example, the cell (1, 1) is the top-left cell of the grid.

![Figure 1: Grid of 4 rows and 7 columns, with a fleet of 4 boats](https://findcomputerstuff.com/wp-content/uploads/2023/12/battleship-figure-01.png)

*Figure 1: Grid of 4 rows and 7 columns, with a fleet of 4 boats*

Before the game begins, each player arranges their ships on their grid without overlaps. The dimensions of the grid (number of rows and number of columns) and the composition of the fleet (types of ships and the quantity of each type) do not have to be the same for all players. The ships are all linear and must be positioned either vertically or horizontally. Note that the ships can be adjacent to each other, with no water separating them, as shown in the schematic arrangement in _Figure 1_. In the example we are using, the fleet has four ships: two three-cell ships and two two-cell ships.

One player takes a turn at a time. Each turn involves choosing an opponent and taking a shot at a position on that opponent's grid. If the shot hits a ship, that ship sinks (ceases to exist), regardless of the number of cells it has. If the shot hits a position where a ship used to be, the player incurs a penalty. A player's score is determined by the ships they have sunk and the shots they have taken on cells with "sunk ships". The order of players in terms of performance, called ranking, is based on the players' scores.

When a player’s fleet is completely sunk, that player is automatcally eliminated, being unable to take any more shots or be attacked by opponents. The game ends as soon as there is only one player in play (i.e., not eliminated). The arrangement of the fleet on the grid will be represented by L sequences of characters, all of length C, where L is the number of rows and C is the number of columns in the grid. When a cell has no ship, the character in the sequence is '.' (full period). Cells with a ship have the same letter in corresponding positions of the sequences, and if ships are adjacent to each other (vertically or horizontally), the letters representing them are different.

![Figure 2: Examples of the representation of the grid illustrated in Figure 1](https://findcomputerstuff.com/wp-content/uploads/2023/12/battleship-figure-02.png)

*Figure 2: Examples of the representation of the grid illustrated in Figure 1*

_Figure 2_ has two possible representations of the grid from _Figure 1_. If, at a certain point in the game, there are sunk ships, the representation of the fleet’s state has the character '*' in the positions where those ships were. _Figure 3_ contains the state of the fleet described by the representation on the left in _Figure 2_, assuming the only sunk ship was in the first column of the grid.

![Figure 3: Example of the representation of the fleet’s state.](https://findcomputerstuff.com/wp-content/uploads/2023/12/battleship-figure-03.png)

*Figure 3: Example of the representation of the fleet’s state.*

The goal of this project is to program, in Java, the version of the Battleship game described in this document. With information about the players' names, the arrangement of their fleets, and the sequence of shots taken, the program should be able to indicate, at any point in the game, each player’s score and fleet state, the players' ranking, and which players are still in the game. It is also intended that, while the game is not over, the program can indicate who the next player to take a shot is.
