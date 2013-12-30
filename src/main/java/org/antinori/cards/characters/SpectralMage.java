package org.antinori.cards.characters;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class SpectralMage extends BaseCreature {
	public SpectralMage(Cards game, Card card, CardImage cardImage, int slotIndex, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, slotIndex, owner, opponent);
	}

	public void onSummoned() throws GameOverException {
		super.onSummoned();
		
		for (int index = 0; index < 6; index++) {
			CardImage ci = opponent.getSlotCards()[index];
			if (ci == null)	continue;
			
			boolean died = ci.decrementLife(this, ci.getCard().getCost(), game);

			if (died) {
				disposeCardImage(opponent, index);
			}
			
		}
		
		
	}

	public void onAttack() throws GameOverException {
		super.onAttack();
	}
}
