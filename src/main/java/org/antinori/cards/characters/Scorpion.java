package org.antinori.cards.characters;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class Scorpion extends BaseCreature {
	public Scorpion(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();
		swapCard("Poison", CardType.BEAST, "Scorpion", owner);
		
		if (opponent.getSlotCards()[slotIndex] != null) {
			opponent.getSlotCards()[slotIndex].getCreature().setSkipNextAttack(true);
		}
	}

	public void onDying() throws GameOverException {
		super.onDying();
		swapCard("Scorpion", CardType.BEAST, "Poison", owner);
	}
}
