package org.antinori.cards.spells;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class PoisonousCloud extends BaseSpell {
	public PoisonousCloud(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() throws GameOverException {
		super.onCast();
		
		opposingPlayer.decrementStrength(CardType.FIRE, 1);
		opposingPlayer.decrementStrength(CardType.AIR, 1);
		opposingPlayer.decrementStrength(CardType.EARTH, 1);
		opposingPlayer.decrementStrength(CardType.WATER, 1);
		opposingPlayer.decrementStrength(CardType.OTHER, 1);
		
		
		for (int index = 0; index < 6; index++) {
			CardImage ci = opponent.getSlotCards()[index];
			if (ci == null)	continue;
			
			int attack = ci.getCard().getLife() / 2;
			
			boolean died = ci.decrementLife(this, adjustDamage(attack), game);

			if (died) {
				disposeCardImage(opponent, index);
			}
			
		}
	}
}
