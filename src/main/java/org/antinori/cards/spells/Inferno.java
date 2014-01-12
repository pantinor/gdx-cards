package org.antinori.cards.spells;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;

public class Inferno extends BaseSpell {

	public Inferno(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() throws GameOverException {
		
		if (this.targetedCardImage != null) {
			damageSlot(targetedCardImage, targetedCardImage.getCreature().getIndex(), opponent, adjustDamage(18));
		}

		for (int i = 0; i < 6; i++) {

			CardImage ci = opponent.getSlotCards()[i];
			if (ci == null)	continue;

			if (i == targetedCardImage.getCreature().getIndex()) continue;

			damageSlot(ci, i, opponent, adjustDamage(10));

		}

	}

}
