package org.antinori.cards.spells;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;
import org.antinori.cards.characters.BaseCreature;

public class Tornado extends BaseSpell {
	public Tornado(Cards game, Card card, CardImage cardImage, boolean isComputer) {
		super(game, card, cardImage, isComputer);
	}

	public void onCast() {
		super.onCast();
		
		if (this.targetedCardImage != null) {
			BaseCreature bc = (BaseCreature)targetedCardImage.getCreature();
			disposeEnemy(targetedCardImage, bc.slotIndex);
		}
		

	}
}
