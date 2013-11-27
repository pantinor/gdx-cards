package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class MinotaurCommander extends BaseCreature {

	public MinotaurCommander(Cards game, Card card, CardImage cardImage, boolean isComputer, int slotIndex) {
		super(game, card, cardImage, isComputer, slotIndex);
	}

	public void onAttack() {
		super.onAttack();
		enhanceAttackAll(true, 1);
	}
}
