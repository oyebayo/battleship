package com.findcomputerstuff.apps.battleship.entities;

public class Player {
    private final String name;
    private int score;
    private final Fleet fleet;

    public Player(String name, Fleet fleet){
        score = 0;
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

	public boolean isEliminated() {
		return !fleet.hasRemainingShips();
	}
}
