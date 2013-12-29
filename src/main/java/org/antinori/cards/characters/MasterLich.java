package org.antinori.cards.characters;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class MasterLich extends BaseCreature {
	public MasterLich(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();
		
		damageAll(opponent, 8);
	}

	public void onAttack() throws GameOverException {
		
		int attack = this.card.getAttack();
		CardImage[] enemyCards = opponent.getSlotCards();
		if (enemyCards[slotIndex] != null) {
			damageSlot(enemyCards[slotIndex], slotIndex, opponent, attack);
		} else {
			ownerPlayer.incrementStrength(CardType.DEATH, 2);
			damageOpponent(attack);
		}
		game.moveCardActorOnBattle(cardImage, owner);
		
	}
}
