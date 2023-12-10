## Battleship II

[![Build Status](https://github.com/oyebayo/battleship/actions/workflows/default.yml/badge.svg?job=test&branch=main&event=push)](https://github.com/oyebayo/battleship/actions/workflows/default.yml)
[![Code Coverage](https://codecov.io/gh/oyebayo/battleship/graph/badge.svg?token=2M6ND653YT)](https://codecov.io/gh/oyebayo/battleship)
[![CodeQL Analysis](https://github.com/oyebayo/battleship/actions/workflows/default.yml/badge.svg?job=analyze&branch=main&event=push&label=CodeQL)](https://github.com/oyebayo/battleship/actions/workflows/default.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

### Description

Battleship II is a Java-based implementation of the classic game Battleship with a twist. The game supports multiple players and features a unique scoring system. The game ends when only one player remains in play.
The version of the Battleship game is described in [this document](/docs/project.md). With information about the players' names, the arrangement of their fleets, and the sequence of shots taken, the program should be able to indicate, at any point in the game, each player’s score and fleet state, the players' ranking, and which players are still in the game. It is also intended that, while the game is not over, the program can indicate who the next player to take a shot is.

### Installation

This project uses Maven for dependency management. To install the project, follow these steps:

1. Clone the repository: 
```bash
git clone https://github.com/oyebayo/battleship.git
```
2. Navigate to the project directory: 
```bash
cd battleship`
```
3. Install dependencies: 
```bash
mvn install
```

### Usage

To run the game, use the following command in the project directory:

```bash
mvn exec:java -Dexec.mainClass="com.findcomputerstuff.apps.battleship.Battleship"
```

### Contributing

Contributions are welcome. Please open an issue to discuss your idea or submit a Pull Request.  

### License
This project is licensed under the MIT License. See the LICENSE file for more details.  

### Contact
If you have any questions or feedback, please open an issue on this repository.  

### Acknowledgements
This project uses the following open-source packages:
- [Maven](https://maven.apache.org/)
- [JUnit](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)