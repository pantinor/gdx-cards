package org.antinori.cards.spells;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class ChainLightning extends BaseSpell {
	public ChainLightning(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() {
		super.onCast();

		damageAll(opponent, adjustDamage(9));
		damageOpponent(adjustDamage(9));
	}
}
