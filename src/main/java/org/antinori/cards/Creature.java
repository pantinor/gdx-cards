package org.antinori.cards;


public interface Creature {
	public void onSummoned() throws GameOverException ;
	public void onAttack() throws GameOverException ;
	public int onAttacked(int damage) throws GameOverException ;
	public void onDying() throws GameOverException;
	public void startOfTurnCheck() throws GameOverException;
}
