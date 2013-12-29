package org.antinori.cards.characters;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class Banshee extends BaseCreature {
	public Banshee(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();
		
		CardImage ci = opponent.getSlotCards()[slotIndex];
		if (ci != null) {
			damageSlot(ci, slotIndex, opponent, ci.getCard().getLife()/2);
		}
	}

	public void onAttack() throws GameOverException {
		super.onAttack();
	}
}
