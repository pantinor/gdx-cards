package org.antinori.cards.spells;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class Inferno extends BaseSpell {

	public Inferno(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() {

		for (int i = 0; i < 6; i++) {

			CardImage ci = opponent.getSlotCards()[i];
			if (ci == null)
				continue;

			int value = 10;
			if (i == slotIndex)
				value = 18;

			ci.decrementLife(value, game, true);

			int remainingLife = ci.getCard().getLife();
			boolean died = (remainingLife < 1);

			if (died) {
				disposeCardImage(opponent, i);
			}
		}

	}

}
