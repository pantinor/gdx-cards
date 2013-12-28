package org.antinori.cards.characters;import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class AstralGuard extends BaseCreature {
	public AstralGuard(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();
		
		opposingPlayer.decrementStrength(CardType.FIRE, 1);
		opposingPlayer.decrementStrength(CardType.AIR, 1);
		opposingPlayer.decrementStrength(CardType.EARTH, 1);
		opposingPlayer.decrementStrength(CardType.WATER, 1);
		opposingPlayer.decrementStrength(CardType.OTHER, 1);
	}

	public void onAttack() throws GameOverException {
		super.onAttack();
	}
}
