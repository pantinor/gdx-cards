package org.antinori.cards.characters;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class MagisterofBlood extends BaseCreature {
	public MagisterofBlood(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();
		damageOpponent(16);
		for (int index = 0; index < 6; index ++) {
			CardImage ci1 = opponent.getSlotCards()[index];
			CardImage ci2 = owner.getSlotCards()[index];
			if (ci1 == null) continue;
			if (ci2 == null) continue; //only damage blocked targets
			damageSlot(ci1, index, opponent, 16);
		}
	}

	public void onAttack() throws GameOverException {
		super.onAttack();
	}
}
