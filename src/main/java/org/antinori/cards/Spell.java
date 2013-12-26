package org.antinori.cards;


public interface Spell {
	
	public void onCast();
	public void setTargeted(CardImage target) ;
	public void setNetworkEventFlag(boolean flag) ;


}
