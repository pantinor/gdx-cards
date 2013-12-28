package org.antinori.cards;

public class GameOverException extends Exception {
	
	private static final long serialVersionUID = 1L;

	private String diedPlayerId;
	
	public GameOverException(String id) {
		super();
		this.diedPlayerId = id;
	}

	public String getDiedPlayerId() {
		return diedPlayerId;
	}



}
