package org.antinori.cards.characters;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;

public class WhiteElephant extends BaseCreature {
	public WhiteElephant(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();
		swapCard("Trumpet", CardType.BEAST, "WhiteElephant", owner);
	}

	public void onDying() throws GameOverException {
		super.onDying();
		swapCard("WhiteElephant", CardType.BEAST, "Trumpet", owner);
	}
}
