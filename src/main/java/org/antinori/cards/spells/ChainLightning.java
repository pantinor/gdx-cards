package org.antinori.cards.spells;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class ChainLightning extends BaseSpell {
	public ChainLightning(Cards game, Card card, CardImage cardImage, boolean isComputer) {
		super(game, card, cardImage, isComputer);
	}

	public void onCast() {
		damageAll(false, 9);
		damagePlayerSpellcasting(9);
	}
}
