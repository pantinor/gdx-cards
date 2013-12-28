package org.antinori.cards.characters;import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class FaerieSage extends BaseCreature {
	public FaerieSage(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();
		int value = this.owner.getPlayerInfo().getStrength(CardType.EARTH);
		if (value > 10) value = 10;
		this.owner.incrementLife(value, game);
	}

	public void onAttack() throws GameOverException {
		super.onAttack();
	}
}
