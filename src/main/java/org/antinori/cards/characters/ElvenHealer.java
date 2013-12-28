package org.antinori.cards.characters;import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class ElvenHealer extends BaseCreature {

	public ElvenHealer(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}
	
	public void onAttack() throws GameOverException {
		super.onAttack();
	}
	
	@Override
	public void startOfTurnCheck() throws GameOverException {
		owner.incrementLife(3, game);
	}


}
