import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Game {
	private static final int MIN_PLAYERS = 2;
	private List<char[]> fleetMap;
	private List<Player> players;
	private boolean gameIsOver;
	private boolean playersInitialized;
	private int currentPlayerIndex;
	private Scanner scanner;
	
	public Game() {
		loadFleets();
		this.gameIsOver = false;
		this.playersInitialized = false;
		this.scanner = new Scanner(System.in);
		this.players = new ArrayList<Player>();
	}
	
	public void start() 
	{
		try {
			while(!playersInitialized) {
				initializePlayers();
			}
			
            while (true) {
                String command = scanner.next().toLowerCase(); // Case-insensitive
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
	        System.out.print("Initializing players\nHow many players are there? ");
	        int playerCount = scanner.nextInt();

	        if (playerCount < MIN_PLAYERS) {
	            System.out.println("Invalid input. You must have at least " + MIN_PLAYERS + " players.");
	            return;
	        }

	        for (int i = 1; i <= playerCount; i++) {
	            System.out.print("Please provide the name for player " + i + ": ");
	            String playerName = scanner.next();

	            System.out.print("Please provide the fleet number for " + playerName + ": ");
	            int fleetNumber = scanner.nextInt();

	            if (fleetNumber < 1 || fleetNumber >= fleetMap.size()) {
	                System.out.println("Invalid fleet number. Please enter a valid fleet number.");
	                return;
	            }

	            Fleet newFleet = createFleetByNumber(fleetNumber);
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

	private Fleet createFleetByNumber(int fleetNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	private void processCommand(String commandType) {
        String input = scanner.nextLine();
        switch (commandType) {
            case "player" -> processPlayerCommand(input);
            case "score" -> processScoreCommand(input);
            case "fleet" -> processFleetCommand(input);
            case "shoot" -> processShootCommand(input);
            case "scores" -> processRankingCommand();
            case "players" -> processInGameCommand();
            default -> System.out.println("Invalid command");
        }
    }
	
	private void processPlayerCommand(String input) {
        // Your logic for processing "player" command
    }

    private void processScoreCommand(String input) {
        
    }

    private void processFleetCommand(String input) {
        // Your logic for processing "fleet" command
    }

    private void processRankingCommand() {
    	for(Player player : players){
            System.out.println(player.getName() + " has " + player.getScore() + " points\n");
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

	private void loadFleets() {
		// TODO Auto-generated method stub
		
	}
}
