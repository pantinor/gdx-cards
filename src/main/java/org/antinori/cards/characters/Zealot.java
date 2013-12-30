package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;

public class Zealot extends BaseCreature {
	public Zealot(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();
	}

	public void onAttack() throws GameOverException {
		super.onAttack();
		int attack = this.card.getAttack();
		cardImage.decrementLife(this, attack, game);
	}
	
	
	public void startOfTurnCheck() throws GameOverException {
		card.incrementAttack(2);
	}
}
