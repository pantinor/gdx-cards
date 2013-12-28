package org.antinori.cards;


public interface Creature {
	
	public void onSummoned() throws GameOverException ;
	public void onAttack() throws GameOverException ;
	public void onAttacked(int damage) ;
	public void onDying();
	
	public void startOfTurnCheck() throws GameOverException;

}
