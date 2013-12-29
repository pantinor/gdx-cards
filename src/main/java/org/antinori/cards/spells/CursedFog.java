package org.antinori.cards.spells;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class CursedFog extends BaseSpell {
	public CursedFog(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() throws GameOverException {
		super.onCast();
		
		damageAll(opponent, adjustDamage(12));
		damageAll(owner, adjustDamage(12));
		
		damagePlayer(opponent, adjustDamage(3));
		
	}
}
