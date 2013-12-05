package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class Dragon extends BaseCreature {

	public Dragon(Cards game, Card card, CardImage cardImage, boolean isComputer, int slotIndex) {
		super(game, card, cardImage, isComputer, slotIndex);
	}
	
	public void onSummoned() {
		super.onSummoned();
		owner.setDealingSpellDamageModifier(0.50f);
	}
	
	public void onAttack() {
		super.onAttack();
	}
	
	public void onDying() {
		owner.setDealingSpellDamageModifier(0);
	}

}
