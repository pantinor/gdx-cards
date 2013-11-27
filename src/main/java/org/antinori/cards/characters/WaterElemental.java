package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;

public class WaterElemental extends BaseCreature {
	public WaterElemental(Cards game, Card card, CardImage cardImage, boolean isComputer, int slotIndex) {
		super(game, card, cardImage, isComputer, slotIndex);
	}

	public void onSummoned() {
		super.onSummoned();
		
		this.card.setAttack(ownerPlayer.getStrengthWater());
		
		owner.incrementLife(10, game);
		
		ownerPlayer.incrementStrength(CardType.WATER, 1);
		
	}

	public void onAttack() {
		super.onAttack();
	}
}
