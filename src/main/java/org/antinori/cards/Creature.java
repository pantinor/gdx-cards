package org.antinori.cards;


public interface Creature {
	public void onSummoned() throws GameOverException ;
	public void onAttack() throws GameOverException ;
	public int onAttacked(BaseFunctions attacker, int damage) throws GameOverException ;
	public void onDying() throws GameOverException;
	public void startOfTurnCheck() throws GameOverException;
	public int getIndex();
	
	public boolean mustSkipNextAttack();
	public void setSkipNextAttack(boolean flag);

}
