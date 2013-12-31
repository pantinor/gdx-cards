package org.antinori.cards.spells;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;

public class DivineJustice extends BaseSpell {
	public DivineJustice(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() throws GameOverException {
		super.onCast();
		
		int healedIndex = -1;
		
		if (this.targetedCardImage != null) {
			this.targetedCardImage.incrementLife(12, game);
			healedIndex = targetedCardImage.getCreature().getIndex();
		}

		for (int index = 0; index < 6; index ++) {
			
			if (index == slotIndex) continue;

			CardImage ci = opponent.getSlotCards()[index];
			if (ci == null) continue;

			damageSlot(ci, index, opponent, adjustDamage(12));
		}
		
		for (int index = 0; index < 6; index ++) {

			CardImage ci = owner.getSlotCards()[index];
			if (ci == null || index == healedIndex) continue;

			damageSlot(ci, index, owner, adjustDamage(12));
		}
		
	}
}
