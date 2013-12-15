package org.antinori.cards.characters;import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;

public class AstralGuard extends BaseCreature {
	public AstralGuard(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() {
		super.onSummoned();
		
		opposingPlayer.decrementStrength(CardType.FIRE, 1, true);
		opposingPlayer.decrementStrength(CardType.AIR, 1, true);
		opposingPlayer.decrementStrength(CardType.EARTH, 1, true);
		opposingPlayer.decrementStrength(CardType.WATER, 1, true);
		opposingPlayer.decrementStrength(CardType.OTHER, 1, true);
	}

	public void onAttack() {
		super.onAttack();
	}
}
