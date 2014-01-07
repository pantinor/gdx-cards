package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;

public class AirElemental extends BaseCreature {
	public AirElemental(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		this.card.setAttack(ownerPlayer.getStrengthAir());
		damageOpponent(8);
		ownerPlayer.incrementStrength(CardType.AIR, 1);
		super.onSummoned();
	}

	public void startOfTurnCheck() throws GameOverException {
		this.card.setAttack(ownerPlayer.getStrengthAir());
	}
}
