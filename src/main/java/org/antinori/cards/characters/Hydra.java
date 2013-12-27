package org.antinori.cards.characters;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class Hydra extends BaseCreature {
	public Hydra(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() {
		super.onSummoned();
	}

	public void onAttack() {
		super.onAttack();
		
		int attack = this.card.getAttack();

		damageAllExceptCurrentIndex(attack, opponent);
		
		CardImage[] enemyCards = opponent.getSlotCards();

		if (enemyCards[slotIndex] != null) {
			//damage the opponent even if there is a card opposite
			damageOpponent(attack);
		}
	}
	
	@Override
	public void startOfTurnCheck() {
		this.cardImage.incrementLife(4, game);
	}
}
