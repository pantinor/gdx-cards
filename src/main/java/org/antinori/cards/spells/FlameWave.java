package org.antinori.cards.spells;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class FlameWave extends BaseSpell {

	public FlameWave(Cards game, Card card, CardImage cardImage, boolean isComputer) {
		super(game, card, cardImage, isComputer);
	}
	
	public void onCast() {
		damageAll(false, 9);
	}

}
