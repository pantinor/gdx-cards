package org.antinori.cards.spells;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class CalltoThunder extends BaseSpell {
	public CalltoThunder(Cards game, Card card, CardImage cardImage, boolean isComputer) {
		super(game, card, cardImage, isComputer);
	}

	public void onCast() {
		damagePlayerSpellcasting(6);
	}
}
