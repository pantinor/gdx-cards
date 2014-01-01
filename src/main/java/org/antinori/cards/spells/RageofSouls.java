package org.antinori.cards.spells;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;

public class RageofSouls extends BaseSpell {
	public RageofSouls(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() throws GameOverException {
		super.onCast();
		
		int inc = 0;
		for (int index = 0; index < 6; index++) {
			CardImage ci = opponent.getSlotCards()[index];
			if (ci == null)	continue;
			
			boolean died = ci.decrementLife(this, adjustDamage(9 + ownerPlayer.getStrengthSpecial()), game);

			if (died) {
				disposeCardImage(opponent, index);
			} else {
				inc ++;
			}
			
		}
		
		owner.incrementLife(inc * 2, game);
		
	}
}
