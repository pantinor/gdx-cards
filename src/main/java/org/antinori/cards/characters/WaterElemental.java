package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;

public class WaterElemental extends BaseCreature {
	public WaterElemental(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		this.card.setAttack(ownerPlayer.getStrengthWater());
		owner.incrementLife(10, game);
		ownerPlayer.incrementStrength(CardType.WATER, 1);
		super.onSummoned();
	}

	public void onAttack() throws GameOverException {
		super.onAttack();
	}

	public void startOfTurnCheck(boolean isComputer, PlayerImage player) {
		this.card.setAttack(ownerPlayer.getStrengthWater());
	}
}
