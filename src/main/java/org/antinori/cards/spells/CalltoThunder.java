package org.antinori.cards.spells;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.characters.BaseCreature;

public class CalltoThunder extends BaseSpell {
	public CalltoThunder(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() {
		super.onCast();

		if (this.targetedCardImage != null) {
			this.targetedCardImage.decrementLife(adjustDamage(6), game);

			int remainingLife = targetedCardImage.getCard().getLife();
			boolean died = (remainingLife < 1);

			if (died) {
				BaseCreature bc = (BaseCreature) targetedCardImage.getCreature();
				disposeCardImage(opponent, bc.slotIndex);
			}
		}

		damageOpponent(adjustDamage(6));
	}
}
