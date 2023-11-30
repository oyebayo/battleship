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
	private boolean gameIsOver;
	private boolean playersInitialized;
	private int currentPlayerIndex;
	private Scanner scanner;
	
	public Game() {
		this.gameIsOver = false;
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
	        if (playerCount < MIN_PLAYERS) {
	            System.out.println("Invalid input. You must have at least " + MIN_PLAYERS + " players.");
	            return;
	        }

	        for (int i = 1; i <= playerCount; i++) {
	            //System.out.print("Please provide the name for player " + i + ": ");
	            String playerName = scanner.next();

	            //System.out.print("Please provide the fleet number for " + playerName + ": ");
	            int fleetNumber = scanner.nextInt();

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
        String input = scanner.nextLine();
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
        if (gameIsOver) {
            System.out.println("The game is over");
			return;
        }

		String nextPlayer = players.get(currentPlayerIndex).getName();
		System.out.println("Next player: " + nextPlayer);
    }

    private void processScoreCommand(String input) {
        
    }

    private void processFleetCommand(String input) {
        // Your logic for processing "fleet" command
    }

    private void processRankingCommand() {
    	for(Player player : players){
            System.out.println(player.getName() + " has " + player.getScore() + " points");
        }
    }

    private void processInGameCommand() {
        // Your logic for processing "in-game" command
    }

    private void processShootCommand(String input) {
        // Your logic for processing "shoot" command
    }

    private void quit() {
    	if(gameIsOver){
            System.out.println(getWinningPlayer() + " won the game");
            System.exit(0);
        }else {
            System.out.println("The game was not over yet...");
        }
    }

    private String getWinningPlayer() {
		// TODO Auto-generated method stub
		return null;
	}
}
