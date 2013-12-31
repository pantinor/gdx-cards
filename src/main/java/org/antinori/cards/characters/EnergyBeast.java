package org.antinori.cards.characters;import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class EnergyBeast extends BaseCreature {
	public EnergyBeast(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();
		swapCard("PumpEnergy", CardType.BEAST, "EnergyBeast", owner);
	}

	public void onDying() throws GameOverException {
		super.onDying();
		swapCard("EnergyBeast", CardType.BEAST, "PumpEnergy", owner);
	}
}
