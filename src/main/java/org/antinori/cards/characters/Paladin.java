package org.antinori.cards.characters;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class Paladin extends BaseCreature {
	public Paladin(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();
		
		for (int index = 0; index < 6; index++) {
			CardImage ci = owner.getSlotCards()[index];
			if (ci == null || index == slotIndex) continue;
			ci.incrementLife(4, game);
		}
		
	}

	public void onAttack() throws GameOverException {
		super.onAttack();
	}
}
