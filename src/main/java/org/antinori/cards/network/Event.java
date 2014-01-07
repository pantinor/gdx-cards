package org.antinori.cards.network;

public enum Event {
	
	REMOTE_PLAYER_INFO_INIT,
	
	PLAYER_INCR_STRENGTH_ALL,
	
	CARD_SUMMONED,
	CARD_START_TURN_CHECK,
	CARD_ATTACK,
	CARD_END_TURN_CHECK,

	SPELL_CAST,
	
	GAME_OVER;

}
