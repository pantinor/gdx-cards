package org.antinori.cards.spells;

import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.characters.BaseCreature;

public class Explosion extends BaseSpell {
	public Explosion(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() {
		super.onCast();
		
		if (this.targetedCardImage != null) {
			BaseCreature bc = (BaseCreature) targetedCardImage.getCreature();
			disposeCardImage(owner, bc.slotIndex);
			
			CardImage ci = opponent.getSlotCards()[bc.slotIndex];
			if (ci != null) {
				ci.decrementLife(adjustDamage(28), game);
			}
			
		}
		
		
		
		
	}
}
