package org.antinori.cards;


public interface Creature {
	
	public void onSummoned() ;
	public void onAttack() ;
	public void onAttacked() ;
	public void onDying();
	
	public void startOfTurnCheck(PlayerImage player);

}
