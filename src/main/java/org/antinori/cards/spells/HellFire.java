package org.antinori.cards.spells;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class HellFire extends BaseSpell {
	public HellFire(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() throws GameOverException {
		super.onCast();
		
		int inc = 0;
		for (int index = 0; index < 6; index++) {
			CardImage ci = opponent.getSlotCards()[index];
			if (ci == null)	continue;
			
			boolean died = ci.decrementLife(this, adjustDamage(13), game);

			if (died) {
				disposeCardImage(opponent, index);
				inc ++;
			}
			
		}
		
		owner.getPlayerInfo().incrementStrength(CardType.FIRE, inc);
	}
}
