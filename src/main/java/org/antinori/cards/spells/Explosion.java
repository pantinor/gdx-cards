package org.antinori.cards.spells;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.GameOverException;
import org.antinori.cards.PlayerImage;

public class Explosion extends BaseSpell {
	public Explosion(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() throws GameOverException {
		super.onCast();
		
		if (this.targetedCardImage != null) {
			
			int index = targetedCardImage.getCreature().getIndex();
			disposeCardImage(owner, index);
			
			CardImage ci = opponent.getSlotCards()[index];
			if (ci != null) {
				damageSlot(ci, index, opponent, adjustDamage(28));
			}
			
		}
		
	}
}
