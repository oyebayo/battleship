public class Player {
    private String name;
    private boolean active;
    private int score;
    private Fleet fleet;

    public Player(String name, Fleet fleet){
        score = 0;
        active = true;
        this.name = name;
        this.fleet = fleet;
    }
    
    public Fleet getFleet() {
    	return fleet;
    }
    
    public String getName() {
    	return name;
    }

	public void addScore(int points){
	    score += points;
	}
	
	public int getScore(){
	    return score;
	}
	
	public void setEliminated() {
		active = false;
	}
	public boolean isEliminated() {
		return active;
	}
}