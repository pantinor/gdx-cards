package org.antinori.cards.spells;import org.antinori.cards.PlayerImage;

import org.antinori.cards.Card;
import org.antinori.cards.CardImage;
import org.antinori.cards.CardType;
import org.antinori.cards.Cards;

public class AcidicRain extends BaseSpell {
	public AcidicRain(Cards game, Card card, CardImage cardImage, PlayerImage owner, PlayerImage opponent) {
		super(game, card, cardImage, owner, opponent);
	}

	public void onCast() {
		super.onCast();

		damageAll(opponent, 15);
		
		opposingPlayer.decrementStrength(CardType.FIRE, 1, true);
		opposingPlayer.decrementStrength(CardType.AIR, 1, true);
		opposingPlayer.decrementStrength(CardType.EARTH, 1, true);
		opposingPlayer.decrementStrength(CardType.WATER, 1, true);
		opposingPlayer.decrementStrength(CardType.OTHER, 1, true);

	}
}
