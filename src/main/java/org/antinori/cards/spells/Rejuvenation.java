package org.antinori.cards.spells;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class Rejuvenation extends BaseSpell {

	public Rejuvenation(Cards game, Card card, CardImage cardImage, boolean isComputer) {
		super(game, card, cardImage, isComputer);
	}
	
	public void onCast() {
		int heal = ownerPlayer.getStrengthEarth() * 2;
		healPlayerSpellcasting(heal);
	}

}
