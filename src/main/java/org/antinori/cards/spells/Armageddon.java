package org.antinori.cards.spells;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class Armageddon extends BaseSpell {

	public Armageddon(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() {
		super.onCast();
		int value = 8 + ownerPlayer.getStrengthFire();
		damageAll(opponent, value);
		damageAll(owner, value);
		damageOpponent(value);
	}

}
