package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;

public class AstralGuard extends BaseCreature {
	public AstralGuard(Cards game, Card card, CardImage cardImage, boolean isComputer, int slotIndex) {
		super(game, card, cardImage, isComputer, slotIndex);
	}

	public void onSummoned() {
		super.onSummoned();
		
		opposingPlayer.decrementStrength(CardType.FIRE, 1);
		opposingPlayer.decrementStrength(CardType.AIR, 1);
		opposingPlayer.decrementStrength(CardType.EARTH, 1);
		opposingPlayer.decrementStrength(CardType.WATER, 1);
		opposingPlayer.decrementStrength(CardType.OTHER, 1);
	}

	public void onAttack() {
		super.onAttack();
	}
}
