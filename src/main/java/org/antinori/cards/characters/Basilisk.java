package org.antinori.cards.characters;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class Basilisk extends BaseCreature {
	public Basilisk(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();
		swapCard("Gaze", CardType.BEAST, "Basilisk", owner);
	}
	
	public void onDying() throws GameOverException {
		super.onDying();
		swapCard("Basilisk", CardType.BEAST, "Gaze", owner);
	}
	
	@Override
	public void endOfTurnCheck() throws GameOverException {
		for (int index = 0; index < 6; index++) {
			CardImage ci = opponent.getSlotCards()[index];
			if (ci == null) continue;
			if (ci.getCard().getLife() <= 8) {
				damageSlot(ci, index, opponent, 4);
			}
		}
	}
}
