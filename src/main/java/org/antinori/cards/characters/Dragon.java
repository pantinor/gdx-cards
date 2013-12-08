package org.antinori.cards.characters;import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class Dragon extends BaseCreature {

	public Dragon(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
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
