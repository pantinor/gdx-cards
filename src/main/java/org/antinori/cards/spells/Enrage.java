package org.antinori.cards.spells;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class Enrage extends BaseSpell {
	public Enrage(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() throws GameOverException {
		super.onCast();
		
		for (int index = 0; index < 6; index++) {
			CardImage ci = owner.getSlotCards()[index];
			if (ci == null || !ci.getCard().getName().equalsIgnoreCase("Wolverine")) continue;
			int inc = ci.getCard().getOriginalLife() - ci.getCard().getLife();
			ci.incrementLife(inc, game);
			ci.getCard().incrementAttack(2);
			break;
		}
	}
}
