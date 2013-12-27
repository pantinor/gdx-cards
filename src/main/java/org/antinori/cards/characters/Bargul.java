package org.antinori.cards.characters;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class Bargul extends BaseCreature {

	public Bargul(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() {
		super.onSummoned();
		
		damageAll(opponent, 4);
		
		for (int index = 0; index < 6; index ++) {

			CardImage ci = owner.getSlotCards()[index];
			if (ci == null || index == slotIndex) continue;

			damageSlot(ci, index, owner, 4);
		}
		
		
	}
}
