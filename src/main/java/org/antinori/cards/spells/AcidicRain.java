package org.antinori.cards.spells;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;

public class AcidicRain extends BaseSpell {
	public AcidicRain(Cards game, Card card, CardImage cardImage, boolean isComputer) {
		super(game, card, cardImage, isComputer);
	}

	public void onCast() {
		super.onCast();

		damageAll(false, 15);
		
		opposingPlayer.decrementStrength(CardType.FIRE, 1);
		opposingPlayer.decrementStrength(CardType.AIR, 1);
		opposingPlayer.decrementStrength(CardType.EARTH, 1);
		opposingPlayer.decrementStrength(CardType.WATER, 1);
		opposingPlayer.decrementStrength(CardType.OTHER, 1);

	}
}
