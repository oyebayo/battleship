import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.io.File;

public class Game {
	private static final int MIN_PLAYERS = 2;
	private static final String FLEET_FILE_PATH = "fleets.txt";
	
	private List<String[]> fleetMap;
	private List<Player> players;
	private boolean playersInitialized;
	private int currentPlayerIndex;
	private Scanner scanner;
	
	public Game() {
		this.playersInitialized = false;
		this.scanner = new Scanner(System.in);
		this.players = new ArrayList<>();
		this.fleetMap = new ArrayList<>();
	}
	
	private int loadFleets() {
		int fleetCount = 0;
		try (Scanner fileScanner = new Scanner(new File(FLEET_FILE_PATH))) {
            while (fileScanner.hasNext()) {
                int rows = fileScanner.nextInt();
                int columns = fileScanner.nextInt();

                // Consume the newline after columns
                fileScanner.nextLine();

                List<String> fleetGrid = new ArrayList<>();

                // Read each row of the fleet grid
                for (int i = 0; i < rows; i++) {
                	String nextLine = fileScanner.nextLine();
                	// stop reading upon finding a bad row in the fleet file
                	if (nextLine.length() != columns) return fleetCount;  
                    fleetGrid.add(nextLine);
                }

                // Add the fleet grid to the list
                fleetMap.add(fleetGrid.toArray(new String[0]));
                fleetCount++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Fleet file could not be loaded.");
        }
		return fleetCount;
	}

	public void start() 
	{

		try {
			if(loadFleets() <= 0) return;

			initializePlayers();
			if (!playersInitialized) return;
			currentPlayerIndex = 0;
			
            while (true) {
                String command = scanner.next(); 
                if (command.equals("quit")) {
                	quit();
                }else {
                    processCommand(command);
                }
            }
        } finally {
            scanner.close();
        }
	}
	
	private void initializePlayers() {
	    try {
	        //System.out.print("Initializing players\nHow many players are there? ");
	        int playerCount = scanner.nextInt();
			scanner.nextLine(); // flush the newline character;
	        if (playerCount < MIN_PLAYERS) {
	            System.out.println("Invalid input. You must have at least " + MIN_PLAYERS + " players.");
	            return;
	        }

	        for (int i = 1; i <= playerCount; i++) {
	            //System.out.print("Please provide the name for player " + i + ": ");
				String playerName = scanner.nextLine();

	            //System.out.print("Please provide the fleet number for " + playerName + ": ");
	            int fleetNumber = scanner.nextInt();
				scanner.nextLine(); // flush the newline character;

	            if (fleetNumber < 1 || fleetNumber >= fleetMap.size()) {
	                System.out.println("Invalid fleet number. Please enter a valid fleet number.");
	                return;
	            }

	            Fleet newFleet = FleetMapper.createFleet(fleetMap.get(fleetNumber));
	            Player player = new Player(playerName, newFleet);
	            players.add(player);
	        }

	        playersInitialized = true;
	    } catch (InputMismatchException e) {
	        System.out.println("Invalid input. Please enter valid values.");
	        scanner.nextLine(); // Clear the buffer
	        players.clear();
	    }
	}

	private void processCommand(String commandType) {
        String input = scanner.nextLine().trim();
        switch (commandType) {
            case "player" -> processPlayerCommand();
            case "score" -> processScoreCommand(input);
            case "fleet" -> processFleetCommand(input);
            case "shoot" -> processShootCommand(input);
            case "scores" -> processRankingCommand();
            case "players" -> processInGameCommand();
            default -> System.out.println("Invalid command");
        }
    }
	
	private void processPlayerCommand() {
        if (isOver()) {
            System.out.println("The game is over");
			return;
        }

		String nextPlayer = players.get(currentPlayerIndex).getName();
		System.out.println("Next player: " + nextPlayer);
    }

    private void processScoreCommand(String playerName) {
    	Player player = getPlayerByName(playerName);
		if (player != null) 
            System.out.println(playerName + " has " + player.getScore() + " points");	
    }

    private Player getPlayerByName(String playerName){
		for(Player player : players){
			if (player.getName().equals(playerName)) {
				return player;
			}	
		}
		System.out.println("Nonexistent player");
		return null;
	}

	private void processFleetCommand(String playerName) {      
		Player player = getPlayerByName(playerName);
		if (player != null) 
            System.out.println(player.getFleet().printGrid());
    }

    private void processRankingCommand() {
    	for(Player player : players){
            System.out.println(player.getName() + " has " + player.getScore() + " points");
        }
    }

    private void processInGameCommand() {
        for(Player player : players){
            if (!player.isEliminated()) System.out.println(player.getName());
        }
    }

    private void processShootCommand(String shootParameters) {
        if (isOver()){
			System.out.println("The game is over");
			return;
		}

		String[] parts = shootParameters.split(" ", 3);
		if (parts.length != 3 || !isNumeric(parts[0]) || !isNumeric(parts[1])){
			System.out.println("Invalid command");
			return;
		}

		Player currentPlayer = players.get(currentPlayerIndex);
		// convert the parameters to zero-based indices
		int targetRow = Integer.parseInt(parts[0]) - 1;
		int targetColumn = Integer.parseInt(parts[1]) - 1;
		Player targetPlayer = getPlayerByName(parts[2]);
		if (!shotParametersValid(targetPlayer, currentPlayer, targetRow, targetColumn)) return;

        int points = getPointsForShot(targetPlayer, targetRow, targetColumn);
		currentPlayer.addScore(points);

		if (isOver()) currentPlayer.addScore(currentPlayer.getScore());
		currentPlayerIndex = getNextPlayerIndex();
    }

	private static boolean shotParametersValid(Player targetPlayer, Player currentPlayer, int targetRow, int targetColumn) {
		if (targetPlayer == null) return false;
		if (targetPlayer.getName().equals(currentPlayer.getName())){
			System.out.println("Self-inflicted shot");
			return false;
		}
		if(targetPlayer.isEliminated()){
			System.out.println("Eliminated player");
			return false;
		}
		if(targetRow > targetPlayer.getFleet().getMaxRows() - 1
			|| targetColumn > targetPlayer.getFleet().getMaxColumns() - 1
			|| targetRow < 0
			|| targetColumn < 0)
		{
			System.out.println("Invalid shot");
			return false;
		}
		return true;
	}

	private int getPointsForShot(Player targetPlayer, int targetRow, int targetColumn) {
		HitResult result = targetPlayer.getFleet().hitObjectAt(targetRow, targetColumn);
        int points = switch (result.HitType) {
            case SHIP -> result.CellCount * 100;
            case WRECK -> result.CellCount * -30;
            case BLANK -> 0;
        };

		return points;
	}

	private int getNextPlayerIndex() {
		int nextIndex = currentPlayerIndex;
		do{
			// modulo operator will ensure the indices remain less than max players
			nextIndex = (nextIndex + 1) % players.size();
		}while(players.get(nextIndex).isEliminated());
		return nextIndex; 
	}

	private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  // Regular expression for numeric strings
    }

    private void quit() {
    	if(isOver()){
            System.out.println(getWinningPlayer().getName() + " won the game");
            System.exit(0);
        }else {
            System.out.println("The game was not over yet...");
        }
    }

    private boolean isOver() {
		int activePlayers = 0;
		for (Player player : players) {
            if (player.getFleet().hasRemainingShips())
				activePlayers++;
        }

		return activePlayers < MIN_PLAYERS;
	}

	private Player getWinningPlayer() {
		Player winner = null;
        for (Player player : players) {
            if (winner == null || player.getScore() > winner.getScore()) {
                winner = player;
            }
        }

		int highScore = winner.getScore();
		if (moreThanOnePlayerHasScore(highScore)){
			for (Player player : players) {
				if (!player.isEliminated()) {
					winner = player;
					break;
				}
            }
        }

        return winner;
	}

	private boolean moreThanOnePlayerHasScore(int highestScore) {
		int count = 0;
		for (Player player : players) {
			if (player.getScore() == highestScore) {
				count++;
			}
		}

		return count > 1;
	}
}
