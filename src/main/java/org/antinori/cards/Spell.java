package org.antinori.cards;


public interface Spell {
	
	public void onCast() throws GameOverException;
	public void setTargeted(CardImage target) ;

}
