package org.antinori.cards;

public class GameOverException extends Exception {
	
	private static final long serialVersionUID = 1L;

	private boolean computerPlayerDied;
	
	public GameOverException(boolean isComputer) {
		super();
		this.computerPlayerDied = isComputer;
	}

	public boolean isComputerPlayerDied() {
		return computerPlayerDied;
	}



}
