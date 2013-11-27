package org.antinori.cards.spells;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.Cards;

public class LightningBolt extends BaseSpell {
	public LightningBolt(Cards game, Card card, CardImage cardImage, boolean isComputer) {
		super(game, card, cardImage, isComputer);
	}

	public void onCast() {
		int value = 5 + ownerPlayer.getStrengthAir();
		damagePlayerSpellcasting(value);

	}
}
